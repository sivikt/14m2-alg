package algs.scheduler.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.util.Objects;

/**
 * The simplest {@link Timer} can be implemented by means of {@link ScheduledExecutorService}
 * and its {@linkplain ScheduledExecutorService#schedule(Callable, long, TimeUnit) schedule}
 * method. For more detail see {@link InMemoryScheduledThreadPoolTimer}.
 *
 * This implementation of {@link Timer} is based on {@link DelayQueue} data structure whose
 * basic idea is to use thread-safe priority queue (min heap) data structure to keep track of
 * scheduled tasks as well as to poll the first delayed task (expired task) in O(log(N)) time.
 *
 * To look after delayed tasks a dedicated thread is used, called {@link InMemoryMinHeapTimer.ExpiredTaskConsumer},
 * which submits expired tasks for execution.
 *
 * Potential issues:
 * 1) Unlimited size of priority queue. Probably some applications of this Timer need
 *    specific retention policies.
 *
 * 2) Limited CPUs available on single machine and limit of OS resources (like limits values in Linux).
 *  Although clients can initialize this Timer providing {@link ExecutorService} of needed capacity,
 *  but it can cause a huge delays between a task expiration moment and the moment when the task
 *  instructions started to be executed directly by CPU due to shortage of computational resources.
 *  To be more precise, there are two reasons for that:
 *     2.1) all CPUs are busy executing a part of expired tasks, but the rest of other expired tasks
 *          are still waiting for their execution;
 *     2.2) some tasks may take significant amount of computing resources while other tasks are not.
 *  To cope with this issue one can implement its own {@link ExecutorService} to submit tasks into a
 *  distributed jobs execution cluster, like Spark. But in this case one needs to deal with data
 *  serialization and managing access to shared memory, since in simple case when tasks are executed on
 *  a single JVM it is relatively easy to do by using java concurrency utils.
 *  So, adding up to a million of scheduled tasks rapidly can cause significant latencies in tasks execution,
 *  though adding N tasks into the PQueue takes O(N) time. And therefore tasks execution throughput will be low.
 *  For example, on my computer Intel i5-3570 3.4GHz and 16GB RAM, execution time for simple TestTask takes
 *  min ~500ns (for zero delay task) and max ~100 millis.
 *
 * 3) This Timer is not persistent, that is all scheduled tasks which were not executed will be lost after
 *  JVM crash or any other system failures.
 *  To provide durability it is possible to serialize the information about future tasks into, for example
 *  DBSM or other type of persistent storage and recover Timer's state after failure.
 *
 * 4) This Timer is not fine-grained and real-time. That is, it can not work with nano seconds precision since
 *  there is an overhead to create tasks, add them into queue, wait until expiration status, submit the tasks
 *  for execution and etc. So precision will be up to milliseconds and the Timer is considered to be rather near
 *  real-time. So if the system has to react immediately handling millions of tasks it's better not to use this Timer.
 *
 * 5) Generally this Timer doesn't preserve execution order of expired tasks. Let's consider task A and task B
 *  were scheduled simultaneously and Delay(A) < Delay(B). Define StartAt(A) and StartAt(B) as the system times
 *  when tasks A and B were submitted for execution. The Timer doesn't guarantee StartAt(A) < StartAt(B).
 *
 * 6) The Timer fits good for short-term (< 1s), lightweight (not acquiring a lot of machine resource) tasks.
 *
 * 7) Pure ability to to monitor the status of scheduled tasks and different other statistics like task execution
 *  elapsed time, tasks number, min/max execution time, throughput, latency, etc.
 *
 * 8) Timer supports no more than {@link Integer#MAX_VALUE} tasks scheduled at the same time.
 */
public class InMemoryMinHeapTimer implements Timer {
    
    private static final Logger log = LoggerFactory.getLogger(InMemoryMinHeapTimer.class);

    private final BlockingQueue<DelayedTask> inboundq;

    private final ExpiredTaskConsumer expiredTaskConsumer;

    private final TimerStats stats;

    /**
     * used to signal {@link InMemoryMinHeapTimer} that all preparatory work is done
     */
    private final CountDownLatch readinessLatch;


    public InMemoryMinHeapTimer(ExecutorService tasksExecutor, TimerStats stats) {
        this.inboundq = new DelayQueue<>();
        this.stats = stats;

        this.readinessLatch = new CountDownLatch(1);
        this.expiredTaskConsumer = new ExpiredTaskConsumer(tasksExecutor, inboundq, stats, readinessLatch);

        this.expiredTaskConsumer.start();

        try {
            readinessLatch.await();
        } 
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Couldn't initialize timer", e);
        }
    }
    
    @Override
    public void schedule(Runnable command, long delay, TimeUnit unit) {
        String funcName = "schedule";
        log.debug("START {}", funcName);
        
        Objects.requireNonNull(command, "command is required");
        Objects.requireNonNull(unit, "unit is required");
        
        if (delay < 0)
            throw new IllegalArgumentException("delay must be greater or equal to zero");
        
        if (isActive()) {
            DelayedTask task = new DelayedTask(command, delay, unit);
            log.debug("IN {} add new task {}", funcName, task);

            // Note! The queue is unbounded, so for further improvements
            // tasks queue capacity has to be introduced. 
            inboundq.offer(task);
        }
        else 
            throw new IllegalStateException("Timer was canceled");
        
        log.debug("END {}", funcName);
    }

    public TimerStats getStats() {
        return this.stats;
    }

    public boolean isActive() {
        return !this.expiredTaskConsumer.isCanceled();
    } 
    
    public synchronized void shutdown() {
        if (isActive()) {
            doSilently(this.expiredTaskConsumer::cancel);
            doSilently(this.inboundq::clear);
        }
    }

    private void doSilently(Runnable f) {
        try {
            f.run();
        }
        catch (Exception ex) {
            log.error("Error during function [{}] execution", f);
        }
    }

    /**
     *  This class is for getting expired tasks from the queue and submitting them for execution.
     */
    protected static final class ExpiredTaskConsumer extends Thread {
        
        private static final Logger log = LoggerFactory.getLogger(ExpiredTaskConsumer.class);
        
        private volatile boolean canceled = false;
        private final ExecutorService executor;
        private final BlockingQueue<DelayedTask> inq;
        private final CountDownLatch readinessLatch;
        private final TimerStats stats;

        public ExpiredTaskConsumer(ExecutorService executor,
                                   BlockingQueue<DelayedTask> inboundq,
                                   TimerStats stats,
                                   CountDownLatch readinessLatch)
        {
            super(InMemoryMinHeapTimer.class.getSimpleName() + "_" + ExpiredTaskConsumer.class.getSimpleName());
            this.executor = executor;
            this.readinessLatch = readinessLatch; 
            this.inq = inboundq;
            this.stats = stats;
        }   
        
        public boolean isCanceled() {
            return canceled;     
        }

        public void cancel() {
            canceled = true;
            this.interrupt();
        }

        @Override
        public void run() {
            readinessLatch.countDown();
            
            while (!canceled) {
                try {
                    submitForExecution(inq.take());
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    log.warn("Operator thread was interrupted. Its current status canceled={}", canceled);
                }
            }
        }
        
        private void submitForExecution(DelayedTask task) {
            log.debug("START submitForExecution for task {}", task);

            task.setStartedAt(System.nanoTime());

            CompletableFuture<Void> f = CompletableFuture.runAsync(task.getCommand(), this.executor);

            f.whenComplete((in, ex) -> {
                task.setFinishedAt(System.nanoTime());
                stats.update(task);
            });

            log.debug("END submitForExecution for task {}", task);
        }
    }


    protected static class DelayedTask implements Delayed, ScheduledTaskInfo {

        private final Runnable command;
        private final long origDelayNanos;
        private final long origDelay;
        private final TimeUnit origUnit;
        private final long expireAt;
        private volatile long execStartAt;
        private volatile long execFinishedAt;


        public DelayedTask(Runnable command, long delay, TimeUnit unit) {
            this.command = command;
            this.origDelayNanos = unit.toNanos(delay);
            this.origDelay = delay;
            this.origUnit = unit;
            this.execStartAt = -1;
            this.expireAt = System.nanoTime() + this.origDelayNanos;
        }
            
        public Runnable getCommand() {
            return command;    
        }

        /**
         * This method is called only once by a thread which submits tasks for execution.
         * @param nanos current timestamp
         */
        public void setStartedAt(long nanos) {
            this.execStartAt = nanos;
        }

        @Override
        public long getStartedAt() {
            return this.execStartAt;
        }

        /**
         * This method is called only once by a thread which signals that task is finished.
         * @param nanos current timestamp
         */
        public void setFinishedAt(long nanos) {
            this.execFinishedAt = nanos;
        }

        @Override
        public long getFinishedAt() {
            return this.execFinishedAt;
        }

        public long getOrigDelayNanos() {
            return this.origDelayNanos;
        }
        
        @Override
        public long getDelay(TimeUnit unit) {
            if (unit != NANOSECONDS)
                throw new IllegalArgumentException("NANOSECONDS is expected");
            
            return this.expireAt - System.nanoTime();
        }

        @Override
        public int compareTo(Delayed o) {
            if (!(o instanceof DelayedTask))
                throw new IllegalArgumentException("Can not compare with " + o.getClass().getName());

            DelayedTask that = (DelayedTask) o;
            long d = this.getOrigDelayNanos() - that.getOrigDelayNanos();

            return (d < 0) ? -1 : ((d == 0) ? 0 : 1);           
        }

        @Override
        public String toString() {
            return command + 
                "{ delay=" + origDelay +
                ", unit=" + origUnit +
                ", expireAt=" + expireAt + "}";
        }
    }

}
