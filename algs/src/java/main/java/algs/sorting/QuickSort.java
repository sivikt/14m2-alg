package algs.sorting;

import edu.princeton.cs.introcs.In;

import java.util.Arrays;
import java.util.stream.IntStream;

import static algs.shuffling.KnuthShuffle.shuffle;
import static algs.sorting.SortUtil.*;

/**
 * """
 * This is the 2-way partitioning implementation of quicksort sorting algorithm.
 * <p>
 * Quicksort, or partition-exchange sort, is a sorting algorithm developed
 * by Tony Hoare that, on average, makes O(n log n) comparisons to sort n
 * items. In the worst case, it makes O(n^2) comparisons, though this behavior
 * is rare.
 * Quicksort is often faster in practice than other O(n log n) algorithms.
 * Additionally, quicksort's sequential and localized memory references work well
 * with a cache.
 * Quicksort is a comparison sort and, in efficient implementations, is not a
 * stable sort. Quicksort can be implemented with an in-place partitioning algorithm,
 * so the entire sort can be done with only O(log n) additional space used by the
 * stack during the recursion.
 * <p>
 * The quicksort algorithm was developed in 1960 by Tony Hoare while in the Soviet Union,
 * as a visiting student at Moscow State University. At that time, Hoare worked in a project
 * on machine translation for the National Physical Laboratory. He developed the algorithm
 * in order to sort the words to be translated, to make them more easily matched to an
 * already-sorted Russian-to-English dictionary that was stored on magnetic tape.
 * <p>
 * Quicksort gained widespread adoption, appearing, for example, in Unix as the default library
 * sort function, hence it lent its name to the C standard library function qsort and in the
 * reference implementation of Java. It was analyzed extensively by Robert Sedgewick, who wrote
 * his Ph.D. thesis about the algorithm and suggested several improvements.
 * <p>
 * Quicksort is a divide and conquer algorithm. Quicksort first divides a large array into two
 * smaller sub-arrays: the low elements and the high elements. Quicksort can then recursively
 * sort the sub-arrays. The steps are:
 *   1) Pick an element, called a pivot, from the array.
 *   2) Reorder the array so that all elements with values less than the pivot come before
 *      the pivot, while all elements with values greater than the pivot come after it
 *      (equal values can go either way). After this partitioning, the pivot is in its final
 *      position. This is called the partition operation.
 *   3) Recursively apply the above steps to the sub-array of elements with smaller values
 *      and separately to the sub-array of elements with greater values.
 *
 * The base case of the recursion is arrays of size zero or one, which never need to be sorted.
 *
 * Optimizations:
 * Two important optimizations, also suggested by Sedgewick and widely used in practice are:
 *   1) To make sure at most O(logi N) space is used, recurse first into the smaller side of the partition,
 *      then use a tail call to recurse into the other.
 *   2) Use insertion sort, which has a smaller constant factor and is thus faster on small arrays, for
 *      invocations on small arrays (i.e. where the length is less than a threshold k determined experimentally).
 *      This can be implemented by simply stopping the recursion when less than k elements are left, leaving the
 *      entire array k-sorted: each element will be at most k positions away from its final position. Then, a single
 *      insertion sort pass finishes the sort in O(k×n) time. A separate insertion sort of each small segment as they
 *      are identified adds the overhead of starting and stopping many small sorts, but avoids wasting effort comparing
 *      keys across the many segment boundaries, which keys will be in order due to the workings of the quicksort process.
 *
 * Not stable algorithm
 * Worst case performance         O(n^2)
 * Best case performance          O(n log n) (simple partition) or O(n) (three-way partition and equal keys)
 * Average case performance       O(n log n)
 * Worst case space complexity    O(n) auxiliary (naive) or O(log n) auxiliary (Sedgewick 1978)
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class QuickSort {

    /**
     * Basic 2-way partitioning implementation without any improvements.
     * It goes quadratic if input contains many duplicates or array is sorted or reverse sorted.
     */
    public static <T extends Comparable<? super T>> void sort(T[] a) {
        shuffle(a); // to make performance guarantee
        sort(a, 0, a.length - 1);
        assert isSorted(a, 0, a.length - 1);
    }

    private static <T extends Comparable<? super T>> void sort(T[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j-1); // a[j] is in the right place
        sort(a, j+1, hi);
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