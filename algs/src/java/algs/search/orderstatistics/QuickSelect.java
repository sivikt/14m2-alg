package algs.search.orderstatistics;

import static algs.shuffling.KnuthShuffle.shuffle;
import static algs.sorting.SortUtil.less;
import static algs.sorting.SortUtil.swap;

/**
 * The goal:
 *   Given an array of N items. Find the k-th largest.
 *
 * The idea of this algorithm is to use quicksort partitioning
 * approach to find needed item.
 *
 * @author Serj Sintsov
 */
public class QuickSelect {

    public static <T extends Comparable<? super T>> T select(T[] a, int k) {
        shuffle(a); // quick-select uses ~1/2N^2 compares in the worst
                    // case, but random shuffle provide a probabilistic
                    // guarantee
        int lo = 0, hi = a.length - 1;
        int j = 0;

        while (hi > lo) {
            j = partition(a, lo, hi);
            if (j < k)
                lo = j + 1;
            else if (j > k)
                hi = j - 1;
            else
                return a[k];
        }

        return a[j];
    }

    private static <T extends Comparable<? super T>> int partition(T[] a, int lo, int hi) {
        int i = lo, j = hi + 1;

        while (true) {
            while (less(a[++i], a[lo])) {
                if (i == hi) break;
            }

            while (less(a[lo], a[--j])) {
                if (j == lo) break;
            }

            if (i >= j) break;
            swap(a, i, j);
        }

        swap(a, lo, j);
        return j;
    }

    public static void main(String[] args) {
        Integer[] src = {0, 9, 4, 5, 1, 8, 7, 2, 6, 3};
        assert select(src, 3) == 3;
    }

}