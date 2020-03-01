package algs.sorting;

import static algs.sorting.SortUtil.*;

/**
 * """
 * Sorting algorithm that builds the final sorted array (or list) one item at
 * a time. It is much less efficient on large lists than more advanced algorithms
 * such as quicksort, heapsort, or merge sort. However, insertion sort provides
 * several advantages:
 *
 * 1) Simple implementation
 * 2) Efficient for (quite) small data sets
 * 3) Adaptive (i.e., efficient) for data sets that are already substantially sorted:
 *    the time complexity is O(n + d), where d is the number of inversions
 * 4) More efficient in practice than most other simple quadratic (i.e., O(n^2)) algorithms
 *    such as selection sort or bubble sort; the best case (nearly sorted input) is O(n)
 * 5) Stable; i.e., does not change the relative order of elements with equal keys
 * 6) In-place; i.e., only requires a constant amount O(1) of additional memory space
 * 7) Online; i.e., can sort a list as it receives it
 *
 * {@link InsertionSort} sort is very similar to {@link SelectionSort}. As in selection sort, after k passes
 * through the array, the first k elements are in sorted order. For selection sort these are
 * the k smallest elements, while in insertion sort they are whatever the first k elements
 * were in the unsorted array. Insertion sort's advantage is that it only scans as many
 * elements as needed to determine the correct location of the k+1st element, while selection
 * sort must scan all remaining elements to find the absolute smallest element.
 *
 * Calculations show that insertion sort will usually perform about half as many comparisons as
 * selection sort. Assuming the k+1st element's rank is random, insertion sort will on average
 * require shifting half of the previous k elements, while selection sort always requires scanning
 * all unplaced elements. If the input array is reverse-sorted, insertion sort performs as many
 * comparisons as selection sort. If the input array is already sorted, insertion sort performs as
 * few as n-1 comparisons, thus making insertion sort more efficient when given sorted or "nearly
 * sorted" arrays.
 *
 * While insertion sort typically makes fewer comparisons than selection sort, it requires more
 * writes because the inner loop can require shifting large sections of the sorted portion of the
 * array. In general, insertion sort will write to the array O(n^2) times, whereas selection sort
 * will write only O(n) times. For this reason selection sort may be preferable in cases where
 * writing to memory is significantly more expensive than reading, such as with EEPROM or flash memory.
 *
 * Some divide-and-conquer algorithms such as quicksort and mergesort sort by recursively dividing
 * the list into smaller sublists which are then sorted. A useful optimization in practice for these
 * algorithms is to use insertion sort for sorting small sublists, where insertion sort outperforms
 * these more complex algorithms. The size of list for which insertion sort has the advantage varies
 * by environment and implementation, but is typically between 8-20 elements. A variant of this scheme
 * runs quicksort with a constant cutoff K, then runs a single insertion sort on the final array.
 *
 * Stable sort algorithm
 * Worst case performance      О(n^2) comparisons, swaps
 * Best case performance       O(n) comparisons, O(1) swaps
 * Average case performance    О(n^2) comparisons, swaps
 * Worst case space complexity О(n) total, O(1) auxiliary
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class InsertionSort {

    public static <T extends Comparable<? super T>> void sort(T[] a, int lo, int hi) {
        if (hi <= lo) return;

        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > 0; j--) {
                if (less(a[j], a[j-1]))
                    swap(a, j, j-1);
                else
                    break;
            }
        }

        assert isSorted(a, lo, hi);
    }

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        sort(a, 0, a.length - 1);
    }


    public static void main(String[] args) {
        Integer[] a = {1, 10, 6, 4, 5, 0, 9, 7, 8, 2, 3, 3};
        sort(a);
        assert isSorted(a, 0, a.length-1);
        print(a);
    }

}
