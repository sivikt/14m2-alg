package algs.scheduler.timer;

/**
 * This class supports Timer statistics, implementations must be thread-safe.
 */
public interface TimerStats {

    /**
     * Updates summary statistics adding {@param task} to previous summary stats.
     * Supposed to be thread-safe.
     * @param task new task
     */
    void update(ScheduledTaskInfo task);

    TimerStatsState getState();

}