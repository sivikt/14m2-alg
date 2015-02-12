package algs.adt.stack.impl;

import algs.adt.stack.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This implementation uses a fixed size array data structure under the hood
 * and allows you to add {@code null} values.
 * <p/>
 * Pros:
 *     - It's fast. Constant time for inserting and removing items (~ like
 *       array item access);
 *     - Consumes ~8N bytes of memory for the array and 16+24+8+4+4 bytes for
 *       the stack object itself (on 64bit system and HotSpot JVM)
 * Cons:
 *     - You cannot exceed stack predefined capacity
 *
 * @see algs.adt.stack.Stack
 *
 * @author Serj Sintsov
 */
public class FixedArrayStack<T> implements Stack<T> {

    private T[] storage;
    private int topIndex;

    @SuppressWarnings("unchecked")
    public FixedArrayStack(int capacity) {
        storage = (T[]) new Object[capacity];
    }

    private class ArrayIterator implements Iterator<T> {
        private int next = topIndex;

        @Override
        public boolean hasNext() {
            return next != 0;
        }

        @Override
        public T next() {
            if (next == 0)
                throw new NoSuchElementException();
            return storage[--next];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from stack");
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator();
    }

    @Override
    public T pop() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");

        T item = storage[--topIndex];
        storage[topIndex] = null; // avoid loitering
        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");
        return storage[topIndex-1];
    }

    @Override
    public void push(T item) {
        if (topIndex == storage.length)
            throw new IllegalStateException("Stack is full");

        storage[topIndex++] = item;
    }

    @Override
    public boolean isEmpty() {
        return topIndex == 0;
    }

    @Override
    public int size() {
        return topIndex;
    }

    public static void main(String[] args) {
        FixedArrayStack<String> stack = new FixedArrayStack<String>(5);

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
