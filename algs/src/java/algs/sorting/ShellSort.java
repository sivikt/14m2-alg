package algs.sorting;

import static algs.sorting.SortUtil.*;

/**
 * """
 * Shellsort, also known as Shell sort or Shell's method, is an in-place comparison sort.
 * It can be seen as either a generalization of sorting by exchange (bubble sort) or sorting
 * by insertion (insertion sort). The method starts by sorting elements far apart from each
 * other and progressively reducing the gap between them. Starting with far apart elements can
 * move some out-of-place elements into position faster than a simple nearest neighbor exchange.
 * Donald Shell published the first version of this sort in 1959. The running time of Shellsort
 * is heavily dependent on the gap sequence it uses.
 * For many practical variants, determining  their time complexity remains an open problem.
 *
 * Shellsort is a generalization of insertion sort that allows the exchange of items that are far
 * apart. The idea is to arrange the list of elements so that, starting anywhere, considering every
 * hth element gives a sorted list. Such a list is said to be h-sorted. Equivalently, it can be
 * thought of as h interleaved lists, each individually sorted. Beginning with large values of h,
 * this rearrangement allows elements to move long distances in the original list, reducing large
 * amounts of disorder quickly, and leaving less work for smaller h-sort steps to do. If the file
 * is then k-sorted for some smaller integer k, then the file remains h-sorted. Following this idea
 * for a decreasing sequence of h values ending in 1 is guaranteed to leave a sorted list in the end.
 *
 * Shellsort is now rarely used in serious applications. It performs more operations and has higher
 * cache miss ratio than quicksort. However, since it can be implemented using little code and does
 * not use the call stack, some implementations of the qsort function in the C standard library targeted
 * at embedded systems use it instead of quicksort. Shellsort is, for example, used in the uClibc library.
 * For similar reasons, an implementation of Shellsort is present in the Linux kernel.
 * Shellsort can also serve as a sub-algorithm of introspective sort, to sort short subarrays and to prevent
 * a pathological slowdown when the recursion depth exceeds a given limit. This principle is employed, for
 * instance, in the bzip2 compressor.
 *
 * Worst case performance      O(n^2)
 * Best case performance       O(n*log(n))
 * Average case performance	   depends on gap sequence
 * Worst case space complexity О(n) total, O(1) auxiliary
 *
 * Gap sequences: Shell 1959, Frank & Lazarus 1960, Hibbard 1963, Papernov & Stasevich 1965,
 *                Pratt 1971, Knuth 1973, Incerpi & Sedgewick 1985 Knuth, Sedgewick 1986,
 *                Gonnet & Baeza-Yates 1991, Tokuda 1992, Ciura 2001
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class ShellSort {

    /**
     * Implemented using Knuth's gaps [1, 4, 13, 40, 121, ...] = 3x + 1. Θ(N^3/2)
     */
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int n = a.length,
            h = 1;

        while (h < n/3) h = 3*h + 1;

        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j-h]); j -= h)
                    swap(a, j, j-h);
            }

            h /= 3;
        }

        assert isSorted(a, 0, a.length-1);
    }


    public static void main(String[] args) {
        Integer[] a = {1, 10, 6, 4, 5, 0, 9, 7, 8, 2, 3, 3};
        sort(a);
        assert isSorted(a, 0, a.length-1);
        print(a);
    }

}
