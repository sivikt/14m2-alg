package algs.adt.pqueue.impl.naive;

import algs.adt.pqueue.PriorityQueue;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Unordered priority queue implementation based on fixed array.
 *
 * @author Serj Sintsov
 */
public class UnorderedFixedArrayPQ<Key> implements PriorityQueue<Key> {

    private final Comparator<Key> keyCmptr;
    private Key[] pq;
    private int N;

    @SuppressWarnings("unchecked")
    public UnorderedFixedArrayPQ(int capacity, Comparator<Key> cmp) {
        pq = (Key[]) new Comparable[capacity];
        keyCmptr = cmp;
    }

    @Override
    public void enqueue(Key k) {
        checkCapacityForAdd();
        pq[N++] = k;
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
        int highest = findHighest();
        Key highestKey = pq[highest];
        N -= 1;
        swap(pq, highest, N);
        pq[N] = null;
        return highestKey;
    }

    @Override
    public Key peek() {
        checkCapacityForGet();
        return pq[findHighest()];
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

    private void checkCapacityForAdd() {
        if (N == pq.length)
            throw new IllegalStateException("Queue is full");
    }

    private void checkCapacityForGet() {
        if (N == 0)
            throw new IllegalStateException("Queue is empty");
    }

    private int findHighest() {
        int highest = 0;
        for (int i = 1; i < N; i++)
            if (less(pq[i], pq[highest]))
                highest = i;
        return highest;
    }

    private boolean less(Key x, Key y) {
        return keyCmptr.compare(x, y) < 0;
    }

    public static void main(String[] args) {
        UnorderedFixedArrayPQ<Integer> queue = new UnorderedFixedArrayPQ<>(10, (a, b) -> a - b);

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
