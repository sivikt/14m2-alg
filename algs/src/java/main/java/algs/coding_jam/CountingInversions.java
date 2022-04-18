package algs.coding_jam;

import static algs.sorting.SortUtil.isSorted;
import static algs.sorting.SortUtil.lessOrEq;

/**
 * """An inversion in an array a[] is a pair of entries a[i] and a[j] such that i<j but a[i]>a[j].
 *    Given an array, design a linearithmic algorithm to count the number of inversions.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class CountingInversions {

    /**
     * The idea is to count while merge-sorting.
     */
    public static <T extends Comparable<? super T>> int count(T[] a) {
        int inversions = 0;
        int N = a.length;
        Comparable[] aux = new Comparable[N];

        for (int sz = 1; sz < N; sz += sz)
            for (int lo = 0; lo+sz < N; lo += sz+sz)
                inversions += merge(a, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));

        return inversions;
    }

    private static <T extends Comparable> int merge(T[] a, T[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        int inversions = 0;

        System.arraycopy(a, lo, aux, lo, hi-lo+1);

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                a[k] = aux[j++];
            }
            else if (j > hi) {
                a[k] = aux[i++];
            }
            else if (lessOrEq(aux[i], aux[j])) {
                a[k] = aux[i++];
            }
            else {
                a[k] = aux[j++];
                inversions += mid - i + 1;
            }
        }

        assert isSorted(a, hi, lo);
        return inversions;
    }

    // test it
    public static void main(String[] args) {
        Integer[] a = {5, 4, 6, 0, 7, 5, 1, 2};
        assert count(a) == 16;
    }

}
