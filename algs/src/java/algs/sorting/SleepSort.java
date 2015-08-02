package algs.sorting;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Just a joke sort....
 *
 * In general, sleep sort works by starting a separate task for each item to be sorted, where each task sleeps for
 * an interval corresponding to the item's sort key, then emits the item. Items are then collected sequentially in time.
 *
 * for parallel Ai..An do
 *    sleep(Ai)
 *    print(Ai)
 */
public class SleepSort {

    public static <T extends Ordered> void sort(T[] src) {
        List<SortingTask<T>> tasks = new ArrayList<>(src.length);
        AtomicInteger outIndex = new AtomicInteger(0);

        for (T item : src)
            tasks.add(new SortingTask<>(item, src, outIndex));

        try {
            for (int i = 0; i < src.length; i++) {
                Thread t = new Thread(tasks.get(i));
                t.start();
                t.join();
            }
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static interface Ordered {
        int order();
    }

    private static class SortingTask<T extends Ordered> implements Runnable {

        private final T item;
        private final T[] src;
        private final AtomicInteger outIndex;

        public SortingTask(T item, T[] src, AtomicInteger outIndex) {
            this.item = item;
            this.src = src;
            this.outIndex = outIndex;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(item.order());
                System.out.println(item.order());
                int i = outIndex.getAndIncrement();
                src[i] = item;
            }
            catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
