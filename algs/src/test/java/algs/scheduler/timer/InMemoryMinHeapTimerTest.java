package algs.scheduler.timer;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * TODO: Some of the tests are fragile because of timeout,
 *       so Test thread can exit before tasks will be finished, of vise versa.
 */
public class InMemoryMinHeapTimerTest {

    final int POLL_MAX_SIZE = 2000;

    private InMemoryMinHeapTimer timer;
    private TimerStats stats;

    private TestTask taskMock;
    private ExecutorService executor;

    @BeforeEach
    public void setup() {
        executor = new ThreadPoolExecutor(
                POLL_MAX_SIZE,
                POLL_MAX_SIZE,
                60L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()
        );
        stats = new TimerStatsImpl();
        timer = new InMemoryMinHeapTimer(executor, stats);
        taskMock = new TestTask();
    }

    @AfterEach
    public void down() {
        System.out.println(timer.getStats());

        timer.shutdown();
        executor.shutdownNow();

        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void singleTimerFiresOnce() {
        timer.schedule(taskMock, 100, TimeUnit.MILLISECONDS);
        taskMock.timeout(300).times(1);
    }

    @Test
    public void singleTimerDoesNotFirePrematurely() {
        timer.schedule(taskMock, 200, TimeUnit.MILLISECONDS);
        taskMock.timeout(100).times(0);
        taskMock.timeout(10).times(0);
        taskMock.timeout(200).times(1);
    }

    @Test
    public void multipleTimersFireSequentially() {
        timer.schedule(taskMock, 100, TimeUnit.MILLISECONDS);
        timer.schedule(taskMock, 1000, TimeUnit.MILLISECONDS);
        timer.schedule(taskMock, 2000, TimeUnit.MILLISECONDS);
        timer.schedule(taskMock, 3000, TimeUnit.MILLISECONDS);

        taskMock.timeout(500).times(1);
        taskMock.timeout(700).times(2);
        taskMock.timeout(1000).times(3);
        taskMock.timeout(1000).times(4);
    }

    @Test
    public void multipleAlreadyExpiredTimersFireSequentially() {
        List<TestTask> tasks = generateTasks(5, null);

        for (TestTask t : tasks)
            timer.schedule(t, 0, TimeUnit.MILLISECONDS);

        taskMock.timeout(200);

        for (TestTask t : tasks)
            t.times(1);
    }

    @Test
    public void millionTasksFireSequentially() {
        final int TASKS_NUM = 1_000_000;
        final int DELAY_MUL = 10;

        CountDownLatch finishedLatch = new CountDownLatch(TASKS_NUM);
        List<TestTask> tasks = generateTasks(TASKS_NUM, finishedLatch);

        for (int i = 0; i < tasks.size(); i++)
            timer.schedule(tasks.get(i), i*DELAY_MUL, TimeUnit.MICROSECONDS);


        // wait until all tasks are finished
        final int REPEATS_NUM = 10000;
        final long WAIT_DELAY = 100;
        taskMock.waitForLatch(finishedLatch, 30, TimeUnit.SECONDS);
        taskMock.waitCondition(REPEATS_NUM, WAIT_DELAY, timer, (x) -> x.getStats().getState().getFinishedTasksCount() == TASKS_NUM);


        // assert that number of finished tasks equals to initial scheduled number of tasks

        assertThat(
            "Number of finished tasks is incorrect",
            timer.getStats().getState().getFinishedTasksCount(),
            is(tasks.size())
        );

        for (int i = 0; i < tasks.size(); i++) {
            tasks.get(i).times(1);

            long actualDelay = tasks.get(i).getExecutedAt() - tasks.get(i).getCreatedAt();

            assertThat(
                "Execution timestamp must be after specified delay",
                actualDelay >= TimeUnit.MICROSECONDS.toNanos(i*DELAY_MUL),
                is(true)
            );
        }

        assertThat("Final FinishedTasksCount is incorrect", timer.getStats().getState().getFinishedTasksCount(), is(TASKS_NUM));
        //assertThat("Final MinExecutionTime is incorrect", timer.getStats().getState().getMinExecutionTime(TimeUnit.NANOSECONDS)  < 1000L, is(true));
        //assertThat("Final MaxExecutionTime is incorrect", timer.getStats().getState().getMaxExecutionTime(TimeUnit.NANOSECONDS) > 134550000L, is(true));

        assertThat("Timer still is alive", timer.isActive(), is(true));
    }

    @Test
    public void checkTimerShutdown() {
        Exception exception = assertThrows(IllegalStateException.class, this::doCheckTimerShutdown);
        assertThat("Exception message is incorrect", exception.getMessage(), is("Timer was canceled"));
    }


    private void doCheckTimerShutdown() throws ExecutionException, InterruptedException {
        final int TASKS_NUM = 1_000_000;
        final int ALLOWED_TASKS_NUM = TASKS_NUM/1000;
        final int THREADS_THAT_CLOSES_TIMER = 10;

        final CountDownLatch tasksLimitLatch = new CountDownLatch(ALLOWED_TASKS_NUM);

        // create threads which will cancel, shutdown the timer
        List<CompletableFuture> futures = new ArrayList<>();
        for (int i = 0; i < THREADS_THAT_CLOSES_TIMER; ++i) {
            futures.add(
                CompletableFuture.runAsync(() -> {
                    try {
                        tasksLimitLatch.await();
                        timer.shutdown();

                        Exception exception = assertThrows(IllegalStateException.class, () -> timer.schedule(taskMock, 0L, TimeUnit.NANOSECONDS));
                        assertThat("Exception message is incorrect", exception.getMessage(), is("Timer was canceled"));
                    }
                    catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                })
            );
        }

        // schedule tasks
        final CountDownLatch finishedTasksLatch = new CountDownLatch(TASKS_NUM);
        List<TestTask> tasks = generateTasks(TASKS_NUM, finishedTasksLatch);
        int scheduledTasksCount = 0;

        try {
            for (int i = 0; i < tasks.size(); i++) {
                if (i < ALLOWED_TASKS_NUM)
                    timer.schedule(tasks.get(i), i, TimeUnit.NANOSECONDS);
                else
                    timer.schedule(tasks.get(i), i, TimeUnit.SECONDS);

                scheduledTasksCount++;

                tasksLimitLatch.countDown();
            }
        }
        catch (IllegalStateException ex) {
            for (CompletableFuture f : futures)
                f.get();

            // wait until all tasks are finished
            final int REPEATS_NUM = 10000;
            final long WAIT_DELAY = 100;
            final int tasksCount = scheduledTasksCount;
            taskMock.waitCondition(REPEATS_NUM, WAIT_DELAY, timer, (x) -> x.getStats().getState().getFinishedTasksCount() <= tasksCount);

            assertThat("Final FinishedTasksCount is incorrect", timer.getStats().getState().getFinishedTasksCount() <= tasksCount, is(true));
            assertThat("Some tasks somehow were scheduled and count down finishedTasksLatch", (long)timer.getStats().getState().getFinishedTasksCount(), is(TASKS_NUM-finishedTasksLatch.getCount()));

            throw ex;
        }
    }

    private List<TestTask> generateTasks(int num, CountDownLatch finishedLatch) {
        List<TestTask> tasks = new ArrayList<>(num);
        for (int i = 0; i < num; i++)
            tasks.add(new TestTask(i, finishedLatch));
        return tasks;
    }
}
