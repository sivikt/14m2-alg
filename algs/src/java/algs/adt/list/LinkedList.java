package algs.adt.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Singly linked list with sentinel nodes.
 *
 * @author Serj Sintsov
 */
public class LinkedList<T> implements List<T> {

    public class Node<Item> {

        public Item value;
        public Node<Item> next;

        public Node(Item value) {
            this(value, null);
        }

        public Node(Item value, Node<Item> next) {
            this.value = value;
            this.next = next;
        }

    }

    private Node<T> tail;
    private Node<T> sentinel;
    private int size;

    public LinkedList() {
        sentinel = new Node<>(null, null);
        sentinel.next = sentinel;
        tail = sentinel;
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node<T> next = sentinel.next;

        @Override
        public boolean hasNext() {
            return next != sentinel;
        }

        @Override
        public T next() {
            if (next == sentinel)
                throw new NoSuchElementException();
            T item = next.value;
            next = next.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from list");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    @Override
    public void delete(T item) {
        Node<T> prev = sentinel;
        Node<T> next = sentinel.next;

        while (next != sentinel) {
            if (next.value.equals(item)) {
                prev.next = next.next;
                size -= 1;
                return;
            }
            else {
                prev = next;
                next = next.next;
            }
        }
    }

    @Override
    public void add(T item) {
        Node<T> oldTail = tail;
        tail = new Node<>(item);
        oldTail.next = tail;
        tail.next = sentinel;
        size += 1;
    }

    @Override
    public T get(int i) {
        if (i < 0 || i >= size)
            return null;

        Node<T> next = sentinel.next;
        while (i > 0) {
            next = next.next;
            i -= 1;
        }

        return next.value;
    }

    @Override
    public boolean contains(T item) {
        Node<T> next = sentinel.next;
        while (next != sentinel) {
            if (next.value.equals(item))
                return true;
            else
                next = next.next;
        }
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        LinkedList<Integer> queue = new LinkedList<>();

        assert queue.isEmpty();
        assert queue.size() == 0;

        queue.add(1);
        queue.add(2);
        queue.add(3);

        assert !queue.isEmpty();
        assert queue.size() == 3;
        assert queue.contains(2);

        queue.delete(2);
        assert queue.size() == 2;

        queue.delete(3);
        queue.delete(1);
        queue.delete(5);
        assert queue.size() == 0;
    }

}
