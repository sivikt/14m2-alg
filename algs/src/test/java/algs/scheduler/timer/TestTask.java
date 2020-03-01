package algs.scheduler.timer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestTask implements Runnable {
    private final AtomicLong numberOfInvocations = new AtomicLong(0L);

    private final Integer id;
    private final String idStr;
    private long executedAt;
    private long createdAt;
    private CountDownLatch signalLatch;

    public TestTask() {
        this(null);
    }

    public TestTask(Integer id) {
        this(id, null);
    }

    public TestTask(Integer id, CountDownLatch signalLatch) {
        this.id = id;
        this.idStr = (id == null) ? null : (TestTask.class.getSimpleName() + "_ID" + id);
        this.signalLatch = signalLatch;
        this.createdAt = System.nanoTime();
    }

    @Override
    public void run() {
        executedAt = System.nanoTime();
        numberOfInvocations.getAndIncrement();
        if (signalLatch != null)
            signalLatch.countDown();
    }

    public long getExecutedAt() {
        return executedAt;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    @Override
    public String toString() {
        return  (idStr != null) ? idStr : super.toString();
    }

    void times(long times) {
        assertThat("Number of invocations is incorrect", numberOfInvocations.get(), is(times));
    }

    TestTask timeout(long delay) {
        return timeout(delay, TimeUnit.MILLISECONDS);
    }

    TestTask timeout(long delay, TimeUnit unit) {
        final long timeout = TimeUnit.MILLISECONDS.convert(delay, unit);
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {}
        return this;
    }

    TestTask waitForLatch(CountDownLatch latch, long delay, TimeUnit unit) {
        try {
            latch.await(delay, unit);
        }
        catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Latch had to be freed before delay");
        }

        return this;
    }

    <T> TestTask waitCondition(int repeatCount, long delayMillis, T x, Predicate<T> p) {
        for (int w = 0; w < repeatCount; w++) {
            if (p.test(x))
                break;

            timeout(delayMillis);
        }

        return this;
    }
}