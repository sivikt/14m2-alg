package algs.sorting;

import static algs.sorting.SortUtil.*;

/**
 * """
 * Bubble sort, sometimes referred to as sinking sort, is a simple sorting algorithm
 * that works by repeatedly stepping through the list to be sorted, comparing each
 * pair of adjacent items and swapping them if they are in the wrong order. The pass
 * through the list is repeated until no swaps are needed, which indicates that the
 * list is sorted. The algorithm gets its name from the way smaller elements "bubble"
 * to the top of the list. Because it only uses comparisons to operate on elements,
 * it is a comparison sort. Although the algorithm is simple, most of the other sorting
 * algorithms are more efficient for large lists.
 *
 * The only significant advantage that bubble sort has over most other implementations,
 * even quicksort, but not insertion sort, is that the ability to detect that the list
 * is sorted is efficiently built into the algorithm. When the list is already sorted
 * (best-case), the complexity of bubble sort is only O(n). By contrast, most other
 * algorithms, even those with better average-case complexity, perform their entire
 * sorting process on the set and thus are more complex. However, not only does insertion
 * sort have this mechanism too, but it also performs better on a list that is substantially
 * sorted (having a small number of inversions). Bubble sort should be avoided in case of
 * large collections. It will not be efficient in case of reverse ordered collection.
 *
 * Stable sort algorithm
 * Worst case performance	   O(n^2)
 * Best case performance	   O(n)
 * Average case performance	   O(n^2)
 * Worst case space complexity O(1) auxiliary
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class BubbleSort {

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int n = a.length,
            swapped;

        while (n > 0) {
            swapped = 0;

            for (int i = 0; i < n-1; i++) {
                if (less(a[i+1], a[i])) {
                    swap(a, i, i+1);
                    swapped = i+1;
                }
            }

            n = swapped;
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
