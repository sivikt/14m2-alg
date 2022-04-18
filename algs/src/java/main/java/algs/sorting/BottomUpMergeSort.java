package algs.sorting;

import edu.princeton.cs.introcs.In;

import java.util.Arrays;
import java.util.stream.IntStream;

import static algs.sorting.SortUtil.isSorted;
import static algs.sorting.SortUtil.less;

/**
 * Recursion free implementation of merge sort algorithm.
 *
 * @see BottomUpMergeSort
 *
 * @author Serj Sintsov
 */
public class BottomUpMergeSort {

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[N];

        for (int sz = 1; sz < N; sz += sz)
            for (int lo = 0; lo+sz < N; lo += sz+sz)
                merge(a, aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
    }

    private static <T extends Comparable> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        System.arraycopy(a, lo, aux, lo, hi-lo+1);

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)                   a[k] = aux[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[i], aux[j])) a[k] = aux[i++];
            else                           a[k] = aux[j++];
        }

        assert isSorted(a, hi, lo);
    }

    /**
     * This mergesort implementation uses a half of aux array
     * by copying only on part into it and do merging in the
     * input array.
     *
     * """Merging with smaller auxiliary array.
     *    Suppose that the subarray a[0] to a[N-1] is sorted and the subarray a[N] to a[2*N-1]
     *    is sorted. How can you merge the two subarrays so that a[0] to a[2*N-1] is sorted
     *    using an auxiliary array of size N (instead of 2N)?
     *
     * """ (from materials by Robert Sedgewick)
     */
    public static <T extends Comparable<? super T>> void sort1(T[] a) {
        int N = a.length;
        Comparable[] aux = new Comparable[nearestPowerOfTwo(N/2)]; // since it's bottom-up mergesort

        for (int sz = 1; sz < N; sz += sz)
            for (int lo = 0; lo+sz < N; lo += sz+sz)
                merge1(a, aux, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
    }

    private static <T extends Comparable> void merge1(T[] a, T[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        int auxHi = mid-lo;
        System.arraycopy(a, lo, aux, 0, auxHi+1);

        int i = 0, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > auxHi)                 a[k] = a[j++];
            else if (j > hi)               a[k] = aux[i++];
            else if (less(aux[i], a[j]))   a[k] = aux[i++];
            else                           a[k] = a[j++];
        }

        assert isSorted(a, hi, lo);
    }

    private static int nearestPowerOfTwo(int a) {
        if (isPowerOfTwo(a)) return a;

        a |= (a >>  1);
        a |= (a >>  2);
        a |= (a >>  4);
        a |= (a >>  8);
        a |= (a >> 16);

        return a+1;
    }

    private static boolean isPowerOfTwo(int a) {
        return (a & (a-1)) == 0;
    }

    public static void main(String[] args) {
        // read in the integers from a file
        In in = new In(args[0]);

        int[] primSrc = in.readAllInts();
        Integer[] src = new Integer[primSrc.length];
        Integer[] src1 = new Integer[primSrc.length];
        Integer[] referenceSrc = new Integer[primSrc.length];

        IntStream.range(0, src.length).forEach(i -> {
            src[i]  = primSrc[i];
            src1[i] = primSrc[i];
            referenceSrc[i] = primSrc[i];
        });

        sort(src);
        sort1(src1);
        Arrays.sort(referenceSrc);

        for (int i = 0; i < src.length; i++) {
            assert src[i].equals(referenceSrc[i])  : "[src] Values at index " + i + " are" + " not equal";
            assert src1[i].equals(referenceSrc[i])  : "[src1] Values at index " + i + " are" + " not equal";
        }
    }

}
