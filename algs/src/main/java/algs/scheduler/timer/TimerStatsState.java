package algs.scheduler.timer;

import java.util.concurrent.TimeUnit;

/**
 * Provides Timer statistics instant state. Implementations must be thread-safe.
 */
public class TimerStatsState {

    private final int finishedTasksCount;
    private final long minExecutionTimeNanos;
    private final long maxExecutionTimeNanos;

    public TimerStatsState(int finishedTasksCount, long minExecutionTimeNanos, long maxExecutionTimeNanos) {
        this.finishedTasksCount = finishedTasksCount;
        this.minExecutionTimeNanos = minExecutionTimeNanos;
        this.maxExecutionTimeNanos = maxExecutionTimeNanos;
    }

    public int getFinishedTasksCount() {
        return this.finishedTasksCount;
    }

    public long getMinExecutionTime(TimeUnit unit) {
        return unit.convert(this.minExecutionTimeNanos, TimeUnit.NANOSECONDS);
    }

    public long getMaxExecutionTime(TimeUnit unit) {
        return unit.convert(this.maxExecutionTimeNanos, TimeUnit.NANOSECONDS);
    }

    @Override
    public String toString() {
        return "{ minExecutionTimeNanos=" + minExecutionTimeNanos +
                ", maxExecutionTimeNanos=" + maxExecutionTimeNanos +
                ", finishedTasksCount=" + finishedTasksCount + "}";
    }

}
