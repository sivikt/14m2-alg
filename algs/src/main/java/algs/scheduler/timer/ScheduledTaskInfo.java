package algs.scheduler.timer;


/**
 * This class is aimed to provide raw task information for 
 * different implementations of {@link TimerStats}. 
 */
public interface ScheduledTaskInfo {

    long getStartedAt();

    long getFinishedAt();

}
