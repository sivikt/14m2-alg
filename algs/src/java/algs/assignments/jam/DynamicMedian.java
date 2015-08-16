package algs.assignments.jam;

import algs.adt.pqueue.PriorityQueue;
import algs.adt.pqueue.impl.heap.FixedArrayBinaryHeapPQ;

import java.util.Comparator;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * """Dynamic median.
 *    Design a data type that supports insert in logarithmic time, find-the-median
 *    in constant time, and remove-the-median in logarithmic time.
 *
 *    Hint: maintain two binary heaps, one that is max-oriented and one that is min-oriented.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class DynamicMedian {

    private static final Comparator<Integer> DESC = Integer::compareTo;
    private static final Comparator<Integer> ASC = (o1, o2) -> o2.compareTo(o1);

    private PriorityQueue<Integer> left;
    private PriorityQueue<Integer> right;
    private int size;
    private Integer median;

    public DynamicMedian(int capacity) {
        int pqCapacity = capacity/2;
        left = new FixedArrayBinaryHeapPQ<>(pqCapacity, DESC);
        right = new FixedArrayBinaryHeapPQ<>(pqCapacity, ASC);
    }

    public void insert(int item) {
        if (size == 0) {
            median = item;
        }
        else if (isOdd(size)) {
            left.enqueue(min(median, item));
            right.enqueue(max(median, item));
        }
        else {
            if (item >= left.peek() && item <= right.peek())
                median = item;
            else if (item < left.peek()) {
                median = left.dequeue();
                left.enqueue(item);
            }
            else {
                median = right.dequeue();
                right.enqueue(item);
            }
        }

        size += 1;
    }

    public double median() {
        if (isOdd(size))
            return median;
        else
            return (double)(left.peek() + right.peek()) / 2;
    }

    public void removeMedian() {
        if (isOdd(size)) {
            size -= 1;
        }
        else {
            left.dequeue();
            right.dequeue();
            size -= 2;
        }
    }

    private static boolean isOdd(int a) {
        return a % 2 == 1;
    }

    // testing
    public static void main(String[] args) {
        DynamicMedian dm = new DynamicMedian(10);
        dm.insert(1);
        assert dm.median() == 1;

        dm.insert(8);
        assert dm.median() == 4.5;

        dm.insert(5);
        assert dm.median() == 5;

        dm.insert(0);
        dm.insert(1);
        dm.insert(1);
        dm.insert(6);
        dm.insert(5);
        dm.insert(5);
        assert dm.median() == 5;

        dm.insert(9);
        assert dm.median() == 5;

        dm.removeMedian();
        assert dm.median() == 3;
    }

}
