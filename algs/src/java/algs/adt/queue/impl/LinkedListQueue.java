package algs.adt.queue.impl;

import algs.adt.queue.Queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This implementation uses linked list data structure under the hood and
 * allows you to add {@code null} values.
 * <p/>
 * Mostly the same to {@link algs.adt.stack.impl.LinkedListStack}.
 *
 * @see algs.adt.queue.Queue
 *
 * @author Serj Sintsov
 */
public class LinkedListQueue<T> implements Queue<T> {

    private Node<T> head, tail;
    private int size;

    private class LinkedListIterator implements Iterator<T> {
        private Node<T> next = head;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public T next() {
            if (next == null)
                throw new NoSuchElementException();
            T item = next.value;
            next = next.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from queue");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private static class Node<T> {
        public Node(T value) {
            this(value, null);
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        T value;
        Node<T> next;
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");

        T item = head.value;
        head = head.next;
        if (isEmpty()) tail = null; // avoid loitering
        size--;
        return item;
    }

    @Override
    public void enqueue(T item) {
        Node<T> oldTail = tail;
        tail = new Node<>(item);
        if (isEmpty()) head = tail;
        else           oldTail.next = tail;
        size++;
    }

    @Override
    public boolean isEmpty() {
        return head == null;
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        LinkedListQueue<String> queue = new LinkedListQueue<>();

        assert queue.isEmpty();
        assert queue.size() == 0;

        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");

        assert !queue.isEmpty();
        assert queue.size() == 3;
        assert queue.dequeue().equals("1");

        queue.enqueue("4");
        queue.enqueue("5");
        queue.dequeue();

        assert queue.dequeue().equals("3");
        assert queue.dequeue().equals("4");
        assert queue.dequeue().equals("5");

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
