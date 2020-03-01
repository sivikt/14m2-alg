package algs.sorting;

import edu.princeton.cs.introcs.StdOut;

/**
 * Helpful methods used for sorts.
 *
 * @author Serj Sintsov
 */
public class SortUtil {

    public static void swap(Object[] a, int i, int j) {
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static <T extends Comparable<? super T>> boolean isSorted(T[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++)
            if (less(a[i], a[i-1])) return false;
        return true;
    }

    public static <T extends Comparable<? super T>> boolean less(T x, T y) {
        return x.compareTo(y) < 0;
    }

    public static <T extends Comparable<? super T>> boolean lessOrEq(T x, T y) {
        return x.compareTo(y) <= 0;
    }

    public static <T extends Comparable<? super T>> void print(T[] a) {
        for (T x : a) StdOut.println(x);
    }

}
