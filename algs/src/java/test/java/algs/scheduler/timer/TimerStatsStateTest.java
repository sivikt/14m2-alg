package algs.scheduler.timer;

import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TimerStatsStateTest {
    
    @Test
    public void checkAllGetters() {
        TimerStatsState st = new TimerStatsState(15, 1_243_435_000L, 5_555_555_000L);

        assertThat("Finished tasks count is incorrect", st.getFinishedTasksCount(), is(15));

        assertThat("MinExecutionTime in NANOSECONDS is incorrect", st.getMinExecutionTime(NANOSECONDS), is(1_243_435_000L));
        assertThat("MaxExecutionTime in NANOSECONDS is incorrect", st.getMaxExecutionTime(NANOSECONDS), is(5_555_555_000L));

        assertThat("MinExecutionTime in MICROSECONDS is incorrect", st.getMinExecutionTime(MICROSECONDS), is(1_243_435L));
        assertThat("MaxExecutionTime in MICROSECONDS is incorrect", st.getMaxExecutionTime(MICROSECONDS), is(5_555_555L));

        assertThat("MinExecutionTime in MILLISECONDS is incorrect", st.getMinExecutionTime(MILLISECONDS), is(1_243L));
        assertThat("MaxExecutionTime in MILLISECONDS is incorrect", st.getMaxExecutionTime(MILLISECONDS), is(5_555L));

        assertThat("MinExecutionTime in SECONDS is incorrect", st.getMinExecutionTime(SECONDS), is(1L));
        assertThat("MaxExecutionTime in SECONDS is incorrect", st.getMaxExecutionTime(SECONDS), is(5L));
    }
    
}