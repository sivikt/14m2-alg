package algs.adt.deque.impl;

import algs.adt.deque.Deque;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * """This deque implementation supports each deque operation in constant worst-case
 *    time and use space proportional to the number of items currently in the deque.
 *
 *    Iterator implementation supports the operations next() and hasNext() (plus
 *    construction) in constant worst-case time and uses a constant amount of extra
 *    space per iterator.
 * """ (from materials by Robert Sedgewick)
 *
 * @see Deque
 *
 * @author Serj Sintsov
 */
public class LinkedListDeque<Item> implements Deque<Item> {

    private Node<Item> head, tail;
    private int size;

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> next = head;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Item next() {
            if (next == null) throw new NoSuchElementException();
            Item item = next.getValue();
            next = next.getNext();
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from deque");
        }
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private static class Node<Item> {
        private Item value;
        private Node<Item> next;
        private Node<Item> prev;

        public Node(Item value) {
            this.value = value;
        }

        public Item getValue() {
            return value;
        }

        public void setValue(Item value) {
            this.value = value;
        }

        public Node<Item> getNext() {
            return next;
        }

        public void setNext(Node<Item> next) {
            this.next = next;
        }

        public Node<Item> getPrev() {
            return prev;
        }

        public void setPrev(Node<Item> prev) {
            this.prev = prev;
        }
    }

    @Override
    public void addFirst(Item t) {
        assertNotNull(t);

        Node<Item> oldHead = head;
        head = new Node<>(t);
        if (isEmpty()) tail = head;
        else {
            head.setNext(oldHead);
            oldHead.setPrev(head);
        }

        size++;
    }

    @Override
    public void addLast(Item t) {
        assertNotNull(t);

        Node<Item> oldTail = tail;
        tail = new Node<>(t);
        if (isEmpty()) head = tail;
        else {
            oldTail.setNext(tail);
            tail.setPrev(oldTail);
        }

        size++;
    }

    @Override
    public Item removeFirst() {
        errorOnEmpty();

        Item item = head.getValue();
        head = head.getNext();
        size--;

        if (isEmpty()) tail = null; // avoid loitering
        else           head.setPrev(null);

        return item;
    }

    @Override
    public Item removeLast() {
        errorOnEmpty();

        Item item = tail.getValue();
        tail = tail.getPrev();
        size--;

        if (isEmpty()) head = null; // avoid loitering
        else           tail.setNext(null);

        return item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void assertNotNull(Item t) {
        if (t == null) throw new NullPointerException("Item to insert must be not null");
    }

    private void errorOnEmpty() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
    }


    public static void main(String[] args) {
        LinkedListDeque<String> deque = new LinkedListDeque<>();

        assert deque.isEmpty();
        assert deque.size() == 0;

        deque.addFirst("1");
        deque.addLast("2");
        deque.addLast("3");

        assert !deque.isEmpty();
        assert deque.size() == 3;
        assert deque.removeFirst().equals("1");
        assert deque.removeLast().equals("3");

        deque.addFirst("4");
        deque.addFirst("5");
        deque.addLast("6");
        deque.addLast("7");
        deque.removeFirst();
        deque.removeLast();

        String buf = "";
        for (String s : deque)
            buf += s;

        assert buf.equals("426");
        assert deque.size() == 3;


        deque = new LinkedListDeque<>();
        deque.addFirst("1");
        deque.addFirst("2");
        deque.addFirst("3");
        deque.addFirst("4");
        deque.addFirst("5");

        buf = "";
        while (!deque.isEmpty()) {
            buf += deque.removeLast();
        }

        assert buf.equals("12345");


        deque = new LinkedListDeque<>();
        deque.addLast("1");
        deque.addLast("2");
        deque.addLast("3");
        deque.addLast("4");
        deque.addLast("5");

        buf = "";
        while (!deque.isEmpty()) {
            buf += deque.removeFirst();
        }

        assert buf.equals("12345");
    }

}
