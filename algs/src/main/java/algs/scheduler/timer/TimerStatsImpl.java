package algs.scheduler.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;


/**
 * In-memory implementation of {@link TimerStats} timer statistics aggregator.
 */
public class TimerStatsImpl implements TimerStats {

    private static final Logger log = LoggerFactory.getLogger(TimerStatsImpl.class);

    private final ReentrantLock lock = new ReentrantLock();

    private static final TimerStatsState INITIAL_STATE = new TimerStatsState(0, Long.MAX_VALUE, 0);

    private final AtomicReference<TimerStatsState> stateRef = new AtomicReference<>(INITIAL_STATE);

    @Override
    public void update(ScheduledTaskInfo task) {
        final ReentrantLock lock = this.lock;
        lock.lock();

        try {
            long elapsed = task.getFinishedAt() - task.getStartedAt();

            if (elapsed < 0) {
                log.error("Incorrect elapsed time for task [{}]", task);
                throw new IllegalArgumentException("Elapsed time can not be less than zero");
            }

            TimerStatsState curr = stateRef.get();

            long minExecutionTimeNanos = Math.min(curr.getMinExecutionTime(TimeUnit.NANOSECONDS), elapsed);
            long maxExecutionTimeNanos = Math.max(curr.getMaxExecutionTime(TimeUnit.NANOSECONDS), elapsed);
            int finishedTasksCount = curr.getFinishedTasksCount() + 1;

            stateRef.set(new TimerStatsState(finishedTasksCount, minExecutionTimeNanos, maxExecutionTimeNanos));
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public TimerStatsState getState() {
        return stateRef.get();
    }

}