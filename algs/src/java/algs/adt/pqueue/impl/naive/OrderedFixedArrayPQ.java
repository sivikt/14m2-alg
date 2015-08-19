package algs.adt.pqueue.impl.naive;

import algs.adt.pqueue.PriorityQueue;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Ordered (using insertion sort approach) priority queue implementation based on fixed array.
 *
 * @author Serj Sintsov
 */
public class OrderedFixedArrayPQ<Key> implements PriorityQueue<Key> {

    private final Comparator<Key> keyCmptr;
    private Key[] pq;
    private int N;

    @SuppressWarnings("unchecked")
    public OrderedFixedArrayPQ(int capacity, Comparator<Key> cmp) {
        pq = (Key[]) new Comparable[capacity];
        keyCmptr = cmp;
    }

    @Override
    public void enqueue(Key k) {
        checkCapacityForAdd();
        pq[N++] = k;
        for (int i = N-1; i > 0; i--)
            if (less(pq[i], pq[i - 1]))
                swap(pq, i, i-1);
            else
                break;
    }

    @Override
    public boolean isEmpty() {
        return N == 0;
    }

    @Override
    public int size() {
        return N;
    }

    @Override
    public Key dequeue() {
        checkCapacityForGet();
        N -= 1;
        Key highest = pq[N];
        pq[N] = null;
        return highest;
    }

    @Override
    public Key peek() {
        checkCapacityForGet();
        return pq[N-1];
    }

    @Override
    public Iterator<Key> iterator() {
        throw new UnsupportedOperationException();
    }

    private void swap(Key[] a, int i, int j) {
        Key tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private boolean less(Key x, Key y) {
        return keyCmptr.compare(x, y) < 0;
    }

    private void checkCapacityForAdd() {
        if (N == pq.length)
            throw new IllegalStateException("Queue is full");
    }

    private void checkCapacityForGet() {
        if (N == 0)
            throw new IllegalStateException("Queue is empty");
    }

    public static void main(String[] args) {
        OrderedFixedArrayPQ<Integer> queue = new OrderedFixedArrayPQ<>(10, (a, b) -> a - b);

        assert queue.isEmpty();
        assert queue.size() == 0;

        queue.enqueue(8);
        queue.enqueue(0);
        queue.enqueue(9);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(5);
        queue.enqueue(7);
        queue.enqueue(4);
        queue.enqueue(6);
        queue.enqueue(3);

        String buf = "";
        while (!queue.isEmpty()) {
            buf += queue.dequeue();
        }
        assert buf.equals("9876543210");
        assert queue.size() == 0;
    }

}
