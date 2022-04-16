package algs.coding_jam;

import java.util.*;

import static algs.sorting.SortUtil.swap;

/**
 * """Decimal dominants.
 *    Given an array with N comparable keys, design an algorithm to find all values that occur
 *    more than N/10 times. The expected running time of your algorithm should be linear.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class DecimalDominants {

    private static class Borders {

        public final int lt;
        public final int gt;

        public Borders(int lt, int gt) {
            this.lt = lt;
            this.gt = gt;
        }

        public int dist() {
            return gt - lt + 1;
        }
    }

    /**
     * Brute-force
     */
    public static <T extends Comparable<? super T>> Map<T, Integer> bruteDominants(T[] a) {
        Map<T, Integer> result = new HashMap<>(a.length);

        for (int i = 0; i < a.length-1; i++) {
            int n = 1;

            for (int j = i+1; j < a.length; j++) {
                if (a[i].compareTo(a[j]) == 0)
                    n++;
            }

            if (!result.containsKey(a[i]))
                result.put(a[i], n);
        }

        return result;
    }

    /**
     * The idea is to use 3-way partitioning quick selection.
     */
    public static <T extends Comparable<? super T>> Collection<T> dominants(T[] a) {
        Collection<T> result = new ArrayList<>(10);

        int lo = 0,
            hi = a.length-1,
            kth = -1,
            threshold = a.length/10;

        while (lo < hi) {
            kth += threshold;

            if (kth >= lo) {
                Borders borders = selection(a, kth, lo, hi);

                if (borders.dist() > threshold)
                    result.add(a[kth]);

                lo = borders.gt + 1;
            }
        }

        return result;
    }

    private static <T extends Comparable<? super T>> Borders selection(T[] a, int kth, int lo, int hi) {
        while (true) {
            Borders borders = partition(a, lo, hi);

            if (kth > borders.gt)
                lo = borders.gt + 1;
            else if (kth < borders.lt)
                hi = borders.lt - 1;
            else
                return borders;
        }
    }

    private static <T extends Comparable<? super T>> Borders partition(T[] a, int lo, int hi) {
        int lt = lo,
            gt = hi,
             i = lo;
        T p = a[lo];

        while (i <= gt) {
            int cmp = p.compareTo(a[i]);

            if (cmp < 0)
                swap(a, i, gt--);
            else if (cmp > 0)
                swap(a, i++, lt++);
            else
                i++;
        }

        return new Borders(lt, gt);
    }

    // test it
    public static void main(String[] args) {
        int N = 30;
        int threshold = N/10;
        Integer[] a = new Integer[N];
        Random rnd = new Random();

        long start = System.nanoTime();
        for (int i = 0; i < 3000000; i++) {
            for (int j = 0; j < N; j++)
                a[j] = rnd.nextInt(10);

            System.out.println(Arrays.toString(a));
            Map<Integer, Integer> frequencies = bruteDominants(a);
            System.out.println(frequencies);

            Collection<Integer> dominants = dominants(a);
            System.out.println(dominants);

            for (Integer d : dominants)
                assert frequencies.get(d) > threshold;
        }

        System.out.println(System.nanoTime() - start);
    }

}
