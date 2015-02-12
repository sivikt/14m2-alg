package algs.adt.queue.impl;

import algs.adt.queue.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This implementation uses a fixed size array data structure under the hood
 * and allows you to add {@code null} values.
 * <p/>
 * Queue adds items only in one way: from zero index to max array capacity. If
 * the tail of the queue reaching max capacity then all items are shifted to
 * the beginning of the array in place of empty heading cells. Otherwise
 * queue is full. This means that there is a huge overhead if you do
 * enqueue, dequeue, enqueue, dequeue, ..., operations on a almost full queue
 * frequently.
 *
 * @see algs.adt.queue.Queue
 *
 * @author Serj Sintsov
 */
public class FixedShiftingArrayQueue<T> implements Queue<T> {

    private T[] storage;
    private int head, tail = -1;

    @SuppressWarnings("unchecked")
    public FixedShiftingArrayQueue(int capacity) {
        storage = (T[]) new Object[capacity];
    }

    private class ArrayIterator implements Iterator<T> {
        private int next = head;

        @Override
        public boolean hasNext() {
            return next != -1 && next <= tail;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return storage[next++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from queue");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");

        T item = storage[head];
        storage[head] = null; // avoid loitering
        head++;
        if (isEmpty()) head = tail = -1;
        return item;
    }

    @Override
    public void enqueue(T item) {
        if (tail+1 == storage.length && head == 0)
            throw new IllegalStateException("Queue is full");
        else if (tail+1 == storage.length && head > 0)
            shiftHead();

        storage[++tail] = item;
        if (isEmpty()) head = tail;
    }

    private void shiftHead() {
        for (int i = 0; i < storage.length; i++) {
            if (head+i == storage.length)
                storage[i] = null;
            else
                storage[i] = storage[head+i];
        }

        tail = tail - head;
        head = 0;
    }

    @Override
    public boolean isEmpty() {
        return head == -1 || head > tail;
    }

    @Override
    public int size() {
        return isEmpty() ? 0 : tail - head + 1;
    }

    public static void main(String[] args) {
        FixedShiftingArrayQueue<String> queue = new FixedShiftingArrayQueue<String>(3);

        assert queue.isEmpty();
        assert queue.size() == 0;

        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");

        assert !queue.isEmpty();
        assert queue.size() == 3;
        assert queue.dequeue().equals("1");

        queue.enqueue("4");
        assert queue.dequeue().equals("2");
        queue.enqueue("5");
        queue.dequeue();

        assert queue.dequeue().equals("4");
        assert queue.dequeue().equals("5");
        assert queue.isEmpty();

        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");

        String buf = "";
        for (String s : queue) {
            buf += s;
        }
        assert buf.equals("123");
        assert queue.size() == 3;
    }

}
