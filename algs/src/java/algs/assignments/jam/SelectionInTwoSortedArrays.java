package algs.assignments.jam;

import algs.sorting.QuickSort;
import algs.sorting.SortUtil;

import java.util.Random;

import static algs.sorting.SortUtil.isSorted;

/**
 * """Selection in two sorted arrays.
 *    Given two sorted arrays a[] and b[], of sizes N1 and N2, respectively, design an algorithm to find the k-th largest
 *    key. The order of growth of the worst case running time of your algorithm should be logN, where N=N1+N2.
 *      -Version 1: N1=N2 and k=N/2
 *      -Version 2: k=N/2
 *      -Version 3: no restrictions
 *
 *    Hints: there are two basic approaches.
 *      Approach A: Compute the median in a[] and the median in b[]. Recur in a subproblem of roughly half the size.
 *      Approach B: Design a constant-time algorithm to determine whether a[i] is the kth largest key. Use this subroutine
 *                  and binary search.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class SelectionInTwoSortedArrays {

    public static <T extends Comparable<? super T>> T select(T[] a, T[] b, int k) {
        assert isSorted(a, 0, a.length - 1);
        assert isSorted(b, 0, b.length - 1);

        int N = a.length + b.length;

        if (k < 1 && k > N)
            throw new IllegalArgumentException("K must be in [1," + N + "]");


        return a[0];
    }

    /**
     * Merge and find. Time O(N), Memory O(N)
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> T select_slow(T[] a, T[] b, int k) {
        assert isSorted(a, 0, a.length - 1);
        assert isSorted(b, 0, b.length - 1);

        int N = a.length + b.length;

        if (k < 1 && k > N)
            throw new IllegalArgumentException("K must be in [1," + N + "]");

        T[] m = (T[]) new Comparable[a.length + b.length];

        int i = 0,
            j = 0,
            l = 0;

        while (i < a.length && j < b.length) {
            if (a[i].compareTo(b[j]) > 0) {
                m[l++] = a[i];
                i++;
            }
            else {
                m[l++] = b[j];
                j++;
            }
        }

        if (i < a.length)
            System.arraycopy(a, i, m, l, a.length-i);

        if (j < b.length)
            System.arraycopy(b, j, m, l, b.length-j);

        assert isSorted(m, 0, N - 1);

        return m[k];
    }


    // testing
    public static void main(String args[]) {
        final int N1 = 1000000;
        final int N2 = 9999990;

        Integer[] a = generate(N1);
        Integer[] b = generate(N2);

        Integer kth = select_slow(a, b, 33);
        System.out.println(kth);
    }

    private static Integer[] generate(int size) {
        Random rnd = new Random();
        Integer[] a = new Integer[size];

        for (int i = 0; i < size; i++)
            a[i] = rnd.nextInt();

        QuickSort.sort(a);
        assert isSorted(a, 0, size - 1);

        return a;
    }

}
