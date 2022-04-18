package algs.scheduler.timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

import java.util.Objects;


/**
 * Simple {@link Timer} implementation by means of {@link ScheduledExecutorService}.
 */
public class InMemoryScheduledThreadPoolTimer implements Timer {
    
    private static final Logger log = LoggerFactory.getLogger(InMemoryScheduledThreadPoolTimer.class);
       
    private final ScheduledExecutorService executor;
    
    public InMemoryScheduledThreadPoolTimer(ScheduledExecutorService tasksExecutor) {
        this.executor = tasksExecutor;
    }
    
    @Override
    public void schedule(Runnable command, long delay, TimeUnit unit) {
        String funcName = "schedule";
        log.debug("START {}", funcName);
        
        Objects.requireNonNull(command, "command is required");
        Objects.requireNonNull(unit, "unit is required");
        
        if (delay < 0)
            throw new IllegalArgumentException("delay must be greater or equal to zero");

        executor.schedule(command, delay, unit);

        log.debug("END {}", funcName);
    }

}
