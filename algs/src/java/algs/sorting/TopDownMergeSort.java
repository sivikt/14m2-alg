package algs.sorting;

import edu.princeton.cs.introcs.In;

import java.util.Arrays;
import java.util.stream.IntStream;

import static algs.sorting.SortUtil.isSorted;
import static algs.sorting.SortUtil.less;

/**
 * """
 * Recursion based implementation of merge sort algorithm.
 *
 * Merge sort (also commonly spelled mergesort) is an O(n*log n)
 * comparison-based sorting algorithm. Mergesort is a divide and conquer
 * algorithm that was invented by John von Neumann in 1945.
 * <p/>
 * Conceptually, a merge sort works as follows:<br/>
 * 1. Divide the unsorted list into n sublists, each containing 1 element
 *    (a list of 1 element is considered sorted).
 * 2. Repeatedly merge sublists to produce new sorted sublists until there is
 *    only 1 sublist remaining. This will be the sorted list.
 *
 * Most implementations produce a stable sort, which means that the implementation
 * preserves the input order of equal elements in the sorted output.
 *
 * Data structure	Array
 * Worst case performance O(n log n)
 * Best case performance  O(n log n) typical,
 *                        O(n) natural variant
 * Average case performance    O(n log n)
 * Worst case space complexity O(n) auxiliary
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class TopDownMergeSort {
    private static final int CUTOFF = 7;

    /**
     * Basic implementation without improvements.
     */
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort(a, aux, 0, a.length - 1);
    }

    private static <T extends Comparable> void sort(T[] a, T[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo)/2;
        sort(a, aux, lo, mid);
        sort(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }

    private static <T extends Comparable<? super T>> void merge(T[] a, T[] aux, int lo, int mid, int hi) {
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
     * Improvement 1:
     * cutoff to insertion (selection) sort for ~7 items
     */
    public static <T extends Comparable<? super T>> void sort1(T[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort1(a, aux, 0, a.length - 1);
    }

    private static <T extends Comparable> void sort1(T[] a, T[] aux, int lo, int hi) {
        if (hi - lo <= CUTOFF - 1) {
            InsertionSort.sort(a, lo, hi);
            return;
        }

        int mid = lo + (hi - lo) / 2;
        sort1(a, aux, lo, mid);
        sort1(a, aux, mid + 1, hi);
        merge(a, aux, lo, mid, hi);
    }


    /**
     * Improvement 2:
     * stop if array is already sorted
     */
    public static <T extends Comparable<? super T>> void sort2(T[] a) {
        Comparable[] aux = new Comparable[a.length];
        sort2(a, aux, 0, a.length - 1);
    }

    private static <T extends Comparable> void sort2(T[] a, T[] aux, int lo, int hi) {
        if (hi - lo <= CUTOFF - 1) {
            InsertionSort.sort(a, lo, hi);
            return;
        }

        int mid = lo + (hi - lo)/2;
        sort1(a, aux, lo, mid);
        sort1(a, aux, mid + 1, hi);

        if (less(a[mid], a[mid+1])) return;

        merge(a, aux, lo, mid, hi);
    }


    /**
     * Improvement 3:
     * eliminate the copy to the aux array by switching the role
     * of the input and aux array in each recursive call
     */
    public static <T extends Comparable<? super T>> void sort3(T[] a) {
        Comparable[] aux = new Comparable[a.length];
        System.arraycopy(a, 0, aux, 0, a.length);
        sort3(aux, a, 0, a.length - 1);
    }

    private static <T extends Comparable> void sort3(T[] a, T[] aux, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo)/2;
        sort3(aux, a, lo, mid);
        sort3(aux, a, mid + 1, hi);
        merge3(a, aux, lo, mid, hi);
    }

    private static <T extends Comparable> void merge3(T[] a, T[] aux, int lo, int mid, int hi) {
        assert isSorted(a, lo, mid);
        assert isSorted(a, mid+1, hi);

        int i = lo, j = mid+1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid)               aux[k] = a[j++];
            else if (j > hi)           aux[k] = a[i++];
            else if (less(a[i], a[j])) aux[k] = a[i++];
            else                       aux[k] = a[j++];
        }

        assert isSorted(a, hi, lo);
    }


    public static void main(String[] args) {
        // read in the integers from a file
        In in = new In(args[0]);

        int[] primSrc = in.readAllInts();
        Integer[] src = new Integer[primSrc.length];
        Integer[] src1 = new Integer[primSrc.length];
        Integer[] src2 = new Integer[primSrc.length];
        Integer[] src3 = new Integer[primSrc.length];

        Integer[] referenceSrc = new Integer[primSrc.length];

        IntStream.range(0, src.length).forEach(i -> {
            src[i]  = primSrc[i];
            src1[i] = primSrc[i];
            src2[i] = primSrc[i];
            src3[i] = primSrc[i];
            referenceSrc[i] = primSrc[i];
        });

        sort(src);
        sort1(src1);
        sort2(src2);
        sort3(src3);
        Arrays.sort(referenceSrc);

        for (int i = 0; i < src.length; i++) {
            assert src[i].equals(referenceSrc[i])  : "[src] Values at index " + i + " are" + " not equal";
            assert src1[i].equals(referenceSrc[i]) : "[src1] Values at index " + i + " are" + " not equal";
            assert src2[i].equals(referenceSrc[i]) : "[src2] Values at index " + i + " are" + " not equal";
            assert src3[i].equals(referenceSrc[i]) : "[src3] Values at index " + i + " are" + " not equal";
        }
    }

}
