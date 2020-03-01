package algs.scheduler.timer;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TimerStatsImplTest {

    private ExecutorService executor;

    @Test
    public void checkInitialState() {
        TimerStatsImpl stats = new TimerStatsImpl();

        assertThat("Initial FinishedTasksCount is incorrect", stats.getState().getFinishedTasksCount(), is(0));
        assertThat("Initial MinExecutionTime is incorrect", stats.getState().getMinExecutionTime(TimeUnit.NANOSECONDS), is(Long.MAX_VALUE));
        assertThat("Initial MaxExecutionTime is incorrect", stats.getState().getMaxExecutionTime(TimeUnit.NANOSECONDS), is(0L));
    }

    @Test
    public void checkStatsAreUpdated() {
        TimerStatsImpl stats = new TimerStatsImpl();

        assertThat("Initial FinishedTasksCount is incorrect", stats.getState().getFinishedTasksCount(), is(0));
        assertThat("Initial MinExecutionTime is incorrect", stats.getState().getMinExecutionTime(TimeUnit.NANOSECONDS), is(Long.MAX_VALUE));
        assertThat("Initial MaxExecutionTime is incorrect", stats.getState().getMaxExecutionTime(TimeUnit.NANOSECONDS), is(0L));

        stats.update(createTask(1_243_435_000L, 5_555_555_000L));

        assertThat("1 Next FinishedTasksCount is incorrect", stats.getState().getFinishedTasksCount(), is(1));
        assertThat("1 Next MinExecutionTime is incorrect", stats.getState().getMinExecutionTime(TimeUnit.NANOSECONDS), is(4_312_120_000L));
        assertThat("1 Next MaxExecutionTime is incorrect", stats.getState().getMaxExecutionTime(TimeUnit.NANOSECONDS), is(4_312_120_000L));

        stats.update(createTask(11L, 55L));

        assertThat("2 Next FinishedTasksCount is incorrect", stats.getState().getFinishedTasksCount(), is(2));
        assertThat("2 Next MinExecutionTime is incorrect", stats.getState().getMinExecutionTime(TimeUnit.NANOSECONDS), is(44L));
        assertThat("2 Next MaxExecutionTime is incorrect", stats.getState().getMaxExecutionTime(TimeUnit.NANOSECONDS), is(4_312_120_000L));


        stats.update(createTask(10L, 5_555_555_010L));

        assertThat("3 Next FinishedTasksCount is incorrect", stats.getState().getFinishedTasksCount(), is(3));
        assertThat("3 Next MinExecutionTime is incorrect", stats.getState().getMinExecutionTime(TimeUnit.NANOSECONDS), is(44L));
        assertThat("3 Next MaxExecutionTime is incorrect", stats.getState().getMaxExecutionTime(TimeUnit.NANOSECONDS), is(5_555_555_000L));
    }

    @Nested
    class ConcurrencyTests {

        final int POLL_MAX_SIZE = 2000;

        @BeforeEach
        public void setup() {
            executor = new ThreadPoolExecutor(
                    POLL_MAX_SIZE,
                    POLL_MAX_SIZE,
                    60L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>()
            );
        }

        @AfterEach
        public void down() {
            executor.shutdownNow();

            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }

        @RepeatedTest(3)
        public void checkThreadSafety() throws ExecutionException, InterruptedException {
            final int CLIENTS_COUNT = POLL_MAX_SIZE;

            CyclicBarrier barrier = new CyclicBarrier(CLIENTS_COUNT);

            List< Future<Tuple<TimerStatsState, ScheduledTaskInfo>> > futures = new ArrayList<>(CLIENTS_COUNT);

            final TimerStatsImpl stats = new TimerStatsImpl();
            stats.update(createTask(9L, 99L));

            for (int i = 0; i < CLIENTS_COUNT; ++i) {
                final int t = i;

                futures.add(
                    executor.submit(
                        () -> {
                            assertThat("Initial FinishedTasksCount is incorrect in the thread " + t, stats.getState().getFinishedTasksCount(), is(1));
                            assertThat("Initial MinExecutionTime is incorrect in the thread " + t, stats.getState().getMinExecutionTime(TimeUnit.NANOSECONDS), is(90L));
                            assertThat("Initial MaxExecutionTime is incorrect in the thread " + t, stats.getState().getMaxExecutionTime(TimeUnit.NANOSECONDS), is(90L));

                            barrier.await();

                            TimerStatsState state = stats.getState();

                            ScheduledTaskInfo task;
                            if (t == 0 || t == CLIENTS_COUNT/3)
                                task = createTask(t, t+1);
                            else
                                task = createTask(t, t+t);

                            stats.update(task);

                            return new Tuple(state, task);
                        }
                    )
                );
            }

            List<Tuple<TimerStatsState, ScheduledTaskInfo>> result = new ArrayList<>(CLIENTS_COUNT);
            for (Future<Tuple<TimerStatsState, ScheduledTaskInfo>> f : futures)
                result.add(f.get());

            for (Tuple<TimerStatsState, ScheduledTaskInfo> r : result) {
                TimerStatsState state = r.x;

                assertThat(
                        "Intermediary FinishedTasksCount is less than one",
                        state.getFinishedTasksCount() > 0,
                        is(true)
                );

                assertThat(
                        "Intermediary FinishedTasksCount is greater than " + CLIENTS_COUNT+1,
                        state.getFinishedTasksCount() <= CLIENTS_COUNT+1,
                        is(true)
                );

                assertThat(
                        "Intermediary MinExecutionTime is incorrect",
                        state.getMinExecutionTime(TimeUnit.NANOSECONDS) > 0 && state.getMinExecutionTime(TimeUnit.NANOSECONDS) <= (long)CLIENTS_COUNT,
                        is(true)
                );

                assertThat(
                        "Intermediary MaxExecutionTime is incorrect",
                        state.getMaxExecutionTime(TimeUnit.NANOSECONDS) > 0 && state.getMaxExecutionTime(TimeUnit.NANOSECONDS) <= (long)CLIENTS_COUNT,
                        is(true)
                );

                assertThat(
                        "MinExecutionTime > MaxExecutionTime is incorrect",
                        state.getMinExecutionTime(TimeUnit.NANOSECONDS) <= state.getMaxExecutionTime(TimeUnit.NANOSECONDS),
                        is(true)
                );
            }

            assertThat("Final FinishedTasksCount is incorrect", stats.getState().getFinishedTasksCount(), is(CLIENTS_COUNT+1));
            assertThat("Final MinExecutionTime is incorrect", stats.getState().getMinExecutionTime(TimeUnit.NANOSECONDS), is(1L));
            assertThat("Final MaxExecutionTime is incorrect", stats.getState().getMaxExecutionTime(TimeUnit.NANOSECONDS), is((long)CLIENTS_COUNT-1));
            assertThat("Final MinExecutionTime > MaxExecutionTime is incorrect", stats.getState().getMinExecutionTime(TimeUnit.NANOSECONDS) <= stats.getState().getMaxExecutionTime(TimeUnit.NANOSECONDS), is(true));
        }


        @RepeatedTest(3)
        public void checkAtomicStateUpdate() throws ExecutionException, InterruptedException {
            final int CLIENTS_COUNT = POLL_MAX_SIZE;

            CyclicBarrier barrier = new CyclicBarrier(CLIENTS_COUNT);

            List< Future<Tuple<TimerStatsState, ScheduledTaskInfo>> > futures = new ArrayList<>(CLIENTS_COUNT);

            final TimerStatsImpl stats = new TimerStatsImpl();
            stats.update(createTask(9L, 99L)); // set such values so min and max will be even

            final TimerStatsState initState = stats.getState();

            for (int i = 0; i < CLIENTS_COUNT; ++i) {
                final int t = i;

                futures.add(
                        executor.submit(
                                () -> {
                                    barrier.await();

                                    TimerStatsState state = stats.getState();

                                    ScheduledTaskInfo task;
                                    task = createTask(t, (t%2 == 0) ? t+t+1 : t+t);  // set such values so min and max will be odd

                                    stats.update(task);

                                    return new Tuple(state, task);
                                }
                        )
                );
            }

            List<Tuple<TimerStatsState, ScheduledTaskInfo>> result = new ArrayList<>(CLIENTS_COUNT);
            for (Future<Tuple<TimerStatsState, ScheduledTaskInfo>> f : futures)
                result.add(f.get());


            for (Tuple<TimerStatsState, ScheduledTaskInfo> r : result) {
                TimerStatsState state = r.x;

                assertThat(
                        "Intermediary FinishedTasksCount must be 1 for initial or more than 1 for other states",
                        (state == initState && state.getFinishedTasksCount() == 1) || (state != initState && state.getFinishedTasksCount() > 1),
                        is(true)
                );

                assertThat(
                        "Intermediary FinishedTasksCount must be 1 for initial or less than " + CLIENTS_COUNT+2 + " for other states",
                        (state == initState && state.getFinishedTasksCount() == 1) || (state != initState && state.getFinishedTasksCount() <= CLIENTS_COUNT+1),
                        is(true)
                );


                assertThat(
                        "Intermediary MinExecutionTime and MaxExecutionTime must be even for initial state and not both even for other states",
                        (state == initState) ||
                               !((state.getMinExecutionTime(TimeUnit.NANOSECONDS) % 2 == 0) && (state.getMaxExecutionTime(TimeUnit.NANOSECONDS) % 2 == 0)),
                        is(true)
                );
            }

            assertThat("Final FinishedTasksCount is incorrect", stats.getState().getFinishedTasksCount(), is(CLIENTS_COUNT+1));
            assertThat("Final MinExecutionTime is incorrect", stats.getState().getMinExecutionTime(TimeUnit.NANOSECONDS), is(1L));
            assertThat("Final MaxExecutionTime is incorrect", stats.getState().getMaxExecutionTime(TimeUnit.NANOSECONDS), is((long)CLIENTS_COUNT-1));
        }

    }

    private static class Tuple<X, Y> {
        final X x;
        final Y y;

        Tuple(X x, Y y) {
            this.x = x;
            this.y = y;
        }
    }

    private ScheduledTaskInfo createTask(long startAt, long finishAt) {
        return new ScheduledTaskInfo() {

            @Override
            public long getStartedAt() {
                return startAt;
            }

            @Override
            public long getFinishedAt() {
                return finishAt;
            }
        };
    }
}