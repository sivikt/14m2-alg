package algs.adt.stack.impl;

import algs.adt.stack.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This implementation uses linked list data structure under the hood and
 * allows you to add {@code null} values.
 * <p/>
 * Pros:
 *     - Every operation takes constant time  in the worst case;
 * Cons:
 *     - Use extra time and space to deal with links. Consumes ~40N bytes of
 *       memory for the linked list and 16+8+4+4 bytes for the stack object
 *       itself (on 64bit system and HotSpot JVM)
 *
 * @see Stack
 *
 * @author Serj Sintsov
 */
public class LinkedListStack<T> implements Stack<T> {

    private Node<T> top;
    private int size;

    private class LinkedListIterator implements Iterator<T> {
        private Node<T> next = top;

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
            throw new UnsupportedOperationException("Cannot remove from stack");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private static class Node<T> {
        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        T value;
        Node<T> next;
    }

    @Override
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");

        T item = top.value;
        top = top.next;
        size--;
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");
        return top.value;
    }

    @Override
    public void push(T item) {
        Node<T> oldTop = top;
        top = new Node<>(item, oldTop);
        size++;
    }

    @Override
    public boolean isEmpty() {
        return top == null;
    }

    @Override
    public int size() {
        return size;
    }

    public static void main(String[] args) {
        LinkedListStack<String> stack = new LinkedListStack<String>();

        assert stack.isEmpty();
        assert stack.size() == 0;

        stack.push("1");
        stack.push("2");
        stack.push("3");

        assert !stack.isEmpty();
        assert stack.size() == 3;
        assert stack.pop().equals("3");

        stack.push("4");
        stack.push("5");
        stack.pop();

        assert stack.pop().equals("4");
        assert stack.pop().equals("2");
        assert stack.pop().equals("1");

        stack.push("1");
        stack.push("2");
        stack.push("3");

        String buf = "";
        for (String s : stack) {
            buf += s;
        }
        assert buf.equals("321");
        assert stack.size() == 3;
    }

}
