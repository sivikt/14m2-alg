package algs.sorting;

import edu.princeton.cs.introcs.In;

import java.util.Arrays;
import java.util.stream.IntStream;

import static algs.sorting.SortUtil.isSorted;
import static algs.sorting.SortUtil.print;

/**
 * """
 * Heapsort is a comparison-based sorting algorithm. Heapsort can be thought of as an improved selection sort: like that
 * algorithm, it divides its input into a sorted and an unsorted region, and it iteratively shrinks the unsorted region
 * by extracting the smallest element and moving that to the sorted region. The improvement consists of the use of a
 * heap data structure rather than a linear-time search to find the minimum.
 *
 * Although somewhat slower in practice on most machines than a well-implemented quicksort, it has the advantage of a
 * more favorable worst-case O(n log n) runtime. Heapsort is an in-place algorithm, but it is not a stable sort.
 *
 * Heapsort was invented by J. W. J. Williams in 1964. This was also the birth of the heap, presented already by
 * Williams as a useful data structure in its own right. In the same year, R. W. Floyd published an improved version
 * that could sort an array in-place, continuing his earlier research into the treesort algorithm.
 *
 * Heapsort primarily competes with quicksort, another very efficient general purpose nearly-in-place comparison-based
 * sort algorithm. Quicksort is typically somewhat faster due to some factors, but the worst-case running time for
 * quicksort is O(n^2), which is unacceptable for large data sets and can be deliberately triggered given enough
 * knowledge of the implementation, creating a security risk.
 *
 * Thus, because of the O(n log n) upper bound on heapsort's running time and constant upper bound on its auxiliary
 * storage, embedded systems with real-time constraints or systems concerned with security often use heapsort.
 *
 * Heapsort also competes with merge sort, which has the same time bounds. Merge sort requires Ω(n) auxiliary space,
 * but heapsort requires only a constant amount. Heapsort typically runs faster in practice on machines with small or
 * slow data caches. On the other hand, merge sort has several advantages over heapsort:
 *   - Merge sort on arrays has considerably better data cache performance, often outperforming heapsort on modern
 *     desktop computers because merge sort frequently accesses contiguous memory locations (good locality of reference);
 *     heapsort references are spread throughout the heap.
 *   - Heapsort is not a stable sort; merge sort is stable.
 *   - Merge sort parallelizes well and can achieve close to linear speedup with a trivial implementation;
 *     heapsort is not an obvious candidate for a parallel algorithm.
 *   - Merge sort can be adapted to operate on singly linked lists with O(1) extra space. Heapsort can be adapted to
 *     operate on doubly linked lists with only O(1) extra space overhead.
 *   - Merge sort is used in external sorting; heapsort is not. Locality of reference is the issue.
 *
 * Introsort is an alternative to heapsort that combines quicksort and heapsort to retain advantages of both: worst case
 * speed of heapsort and average speed of quicksort.
 *
 * Not stable
 * Worst case performance      O(n log n)
 * Best case performance       O(n log n)
 * Average case performance    O(n log n)
 * Worst case space complexity O(1) auxiliary
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class HeapSort {

    public static <T extends Comparable<? super T>> void sort(T[] a) {
        int N = a.length;
        heapify(a, N);     // O(N)
        extractMins(a, N); // O(N*log(N))
    }

    /**
     * Put elements of a in heap order, in-place
     */
    private static <T extends Comparable<? super T>> void heapify(T[] a, int N) {
        for (int k = N/2; k >= 1; k--)
            sink(a, k, N);
    }

    private static <T extends Comparable<? super T>> void extractMins(T[] a, int N) {
        while (N > 1) {
            swap(a, 1, N--);
            sink(a, 1, N);
        }
    }

    private static <T extends Comparable<? super T>> void sink(T[] a, int k, int size) {
        int child = k << 1; // k*2
        while (child <= size) {
            if (child < size && less(a, child, child+1))
                child += 1;

            if (less(a, child, k))
                break;

            swap(a, k, child);

            k = child;
            child = k << 1;
        }
    }

    public static void swap(Object[] a, int i, int j) {
        i -= 1; // adjust indexes to input array
        j -= 1;
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static <T extends Comparable<? super T>> boolean less(T[] a, int x, int y) {
        x -= 1; // adjust indexes to input array
        y -= 1;
        return a[x].compareTo(a[y]) < 0;
    }

    public static void main(String[] args) {
        Integer[] a = {1, 10, 6, 4, 5, 0, 9, 7, 8, 2, 3, 3};
        sort(a);
        assert isSorted(a, 0, a.length - 1);
        print(a);

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
