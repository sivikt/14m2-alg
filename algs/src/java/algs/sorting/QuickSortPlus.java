package algs.sorting;

import edu.princeton.cs.introcs.In;

import java.util.Arrays;
import java.util.stream.IntStream;

import static algs.shuffling.KnuthShuffle.shuffle;
import static algs.sorting.SortUtil.*;

/**
 * This implementation is based on R.Sedgewick improvements.
 * 1. Cutoff to insertion sort (which has a smaller constant factor and is thus faster on small arrays) for ~10 items;
 * 2. Try to estimate the partitioning item index;
 * 3. Remove recursion (use explicit stack data-structure); // not implemented
 * 4. Use 3-way partitioning scheme;
 * 5. To make sure at most O(log N) space is used, recurse first into the smaller side of the partition, then use a tail
 *    call to recurse into the other.
 */
public class QuickSortPlus {

    private static final int CUTOFF_THRESH = 7;

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        shuffle(a); // to make performance guarantee
        sort(a, 0, a.length - 1);
        assert isSorted(a, 0, a.length - 1);
    }

    private static <T extends Comparable<? super T>> void sort(T[] a, int lo, int hi) {
        if (hi - lo <= CUTOFF_THRESH - 1) {
            InsertionSort.sort(a, lo, hi); // may improve running time even about 20%
            return;
        }

        // 3-way partitioning
        int m = median3(a, lo, lo + (hi - lo)/2, hi); // If the boundary indices of the subarray being sorted are
                                                      // sufficiently large, the naïve expression for the middle
                                                      // index, (left + right)/2, will cause overflow and provide an
                                                      // invalid pivot index. This can be overcome by using, for
                                                      // example, left + (right-left)/2 to index the middle element,
                                                      // at the cost of more complex arithmetic.
        swap(a, lo, m);

        int lt = lo, gt = hi, i = lo;
        T pivot = a[lo];

        while (i <= gt) {
            int cmp = a[i].compareTo(pivot);

            if (cmp < 0)      swap(a, lt++, i++);
            else if (cmp > 0) swap(a, i, gt--);
            else              i++;
        }

        // recurse to left and right parts
        sort(a, lo, lt-1); // items between lt and gt are in the right places
        sort(a, gt+1, hi);
    }

    /**
     * Simple decision tree for three values. Returns the index of median of these values.
     */
    private static <T extends Comparable<? super T>> int median3(T[] a, int i, int j, int k) {
        return (less(a[i], a[j]) ?
               (less(a[j], a[k]) ? j : less(a[i], a[k]) ? k : i) :
               (less(a[k], a[j]) ? j : less(a[k], a[i]) ? k : i));
    }

    public static void main(String[] args) {
        // read in the integers from a file
        In in = new In(args[0]);

        int[] primSrc = in.readAllInts();
        Integer[] src = new Integer[primSrc.length];
        Integer[] referenceSrc = new Integer[primSrc.length];

        IntStream.range(0, src.length).forEach(i -> {
            src[i]  = primSrc[i];
            referenceSrc[i] = primSrc[i];
        });

        sort(src);
        Arrays.sort(referenceSrc);

        for (int i = 0; i < src.length; i++) {
            assert src[i].equals(referenceSrc[i])  : "[src] Values at index " + i + " are" + " not equal";
        }
    }

}