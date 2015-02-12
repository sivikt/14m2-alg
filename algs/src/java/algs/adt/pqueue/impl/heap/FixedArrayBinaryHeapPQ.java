package algs.adt.pqueue.impl.heap;

import algs.adt.pqueue.PriorityQueue;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Binary heap priority queue implementation based on fixed array.
 * Binary heap is complete binary tree. No parent's Key is less than
 * its children nodes.
 *
 * Height of binary heap with N nodes is log(N). So it provide log(N) for
 * enqueue and delete operations.
 *
 * @author Serj Sintsov
 */
public class FixedArrayBinaryHeapPQ<Key> implements PriorityQueue<Key>
{
    
    private final Comparator<Key> keyCmptr;
    private Key[] pq;
    private int size;

    @SuppressWarnings("unchecked")
    public FixedArrayBinaryHeapPQ(int capacity, Comparator<Key> cmp) {
        pq = (Key[]) new Comparable[capacity+1];
        keyCmptr = cmp;
    }

    /**
     * Cost is at most 1+lg(N) compares.
     */
    @Override
    public void enqueue(Key k) {
        checkCapacityForAdd();
        pq[++size] = k;
        swim(size);
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Key dequeue() {
        checkCapacityForGet();
        Key highest = pq[1];
        swap(1, size);
        size -= 1;
        pq[size+1] = null; // prevent loitering
        sink(1);
        return highest;
    }

    /**
     * Largest key is pq[1], which is root of binary tree.
     */
    @Override
    public Key peek() {
        checkCapacityForGet();
        return pq[1];
    }

    @Override
    public void increaseKey(Key key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void decreaseKey(Key key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<Key> iterator() {
        throw new UnsupportedOperationException();
    }

    private void swim(int k) {
        int parent = k >> 1; // k/2
        while (k > 1 && less(parent, k)) {
            swap(k, parent);
            k = parent;
            parent = k >> 1;
        }
    }

    private void sink(int k) {
        int child = k << 1; // k*2
        while (child <= size) {
            if (child < size && less(child, child+1))
                child += 1;

            if (less(child, k))
                break;

            swap(k, child);

            k = child;
            child = k << 1;
        }
    }

    private void swap(int i, int j) {
        Key tmp = pq[i];
        pq[i] = pq[j];
        pq[j] = tmp;
    }

    private boolean less(int x, int y) {
        return keyCmptr.compare(pq[x], pq[y]) < 0;
    }

    private void checkCapacityForAdd() {
        if (size == pq.length-1)
            throw new IllegalStateException("Queue is full");
    }

    private void checkCapacityForGet() {
        if (size == 0)
            throw new IllegalStateException("Queue is empty");
    }

    public static void main(String[] args) {
        FixedArrayBinaryHeapPQ<Integer> queue = new FixedArrayBinaryHeapPQ<>(10, (a, b) -> a - b);

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
