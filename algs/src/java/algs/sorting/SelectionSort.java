package algs.sorting;

import static algs.sorting.SortUtil.*;

/**
 * """
 * Selection sort is a sorting algorithm, specifically an in-place comparison sort.
 * It has ~1/2*n^2 time complexity, making it inefficient on large lists, and generally
 * performs worse than the similar insertion sort. Selection sort is noted for its
 * simplicity, and it has performance advantages over more complicated algorithms in
 * certain situations, particularly where auxiliary memory is limited.
 *
 * Selection sort is not difficult to analyze compared to other sorting algorithms since
 * none of the loops depend on the data in the array. Selecting the lowest element requires
 * scanning all n elements (this takes n − 1 comparisons) and then swapping it into the first
 * position. Finding the next lowest element requires scanning the remaining n − 1 elements
 * and so on, for (n − 1) + (n − 2) + ... + 2 + 1 = n(n − 1) / 2 ∈ Θ(n^2) comparisons (see
 * arithmetic progression). Each of these scans requires one swap for n − 1 elements
 * (the final element is already in place).
 *
 * Among simple average-case Θ(n^2) algorithms, selection sort almost always outperforms BubbleSort
 * and GnomeSort. Insertion sort is very similar in that after the k-th iteration, the first
 * k elements in the array are in sorted order. Insertion sort's advantage is that it only scans as
 * many elements as it needs in order to place the k + 1st element, while selection sort must scan
 * all remaining elements to find the k + 1st element.
 *
 * Simple calculation shows that insertion sort will therefore usually perform about half as many
 * comparisons as selection sort, although it can perform just as many or far fewer depending on
 * the order the array was in prior to sorting. It can be seen as an advantage for some real-time
 * applications that selection sort will perform identically regardless of the order of the array,
 * while insertion sort's running time can vary considerably. However, this is more often an advantage
 * for insertion sort in that it runs much more efficiently if the array is already sorted or "close to sorted."
 *
 * While selection sort is preferable to insertion sort in terms of number of writes (Θ(n) swaps versus Ο(n^2) swaps),
 * it almost always far exceeds (and never beats) the number of writes that CycleSort makes, as CycleSort is
 * theoretically optimal in the number of writes. This can be important if writes are significantly more expensive
 * than reads, such as with EEPROM or Flash memory, where every write lessens the lifespan of the memory.
 *
 * Finally, selection sort is greatly outperformed on larger arrays by Θ(n*log(n)) divide-and-conquer algorithms such
 * as mergesort. However, insertion sort or selection sort are both typically faster for small arrays (i.e. fewer
 * than 10–20 elements). A useful optimization in practice for the recursive algorithms is to switch to insertion
 * sort or selection sort for "small enough" sublists.
 *
 * Worst case performance      О(n^2)
 * Best case performance       О(n^2)
 * Average case performance    О(n^2)
 * Worst case space complexity О(n) total, O(1) auxiliary
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class SelectionSort {

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        for (int i = 0; i < a.length-1; i++) {
            int min = i;

            for (int j = i+1; j < a.length; j++) {
                if (less(a[j], a[min]))
                    min = j;
            }

            swap(a, i, min);
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
