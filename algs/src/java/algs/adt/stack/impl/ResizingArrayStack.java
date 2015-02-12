package algs.adt.stack.impl;

import algs.adt.stack.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This implementation uses linked list data structure under the hood and
 * allows you to add {@code null} values.
 * <p/>
 * 1. Storage the array is doubling when the array is full.
 * 2. Halve size of the array when the array is one-quarter full. (Array is
 *    between 25% and 100% full)
 * <p/>
 * Pros:
 *     - Every operation takes constant amortized time;
 *     - Less wasted space then {@link LinkedListStack}. Consumes from ~8N to
 *       ~32N bytes of memory for the array and 16+24+8+4+4 bytes for the stack
 *       object itself (on 64bit system and HotSpot JVM)
 * Cons:
 *     - In the worst case it takes time proportional to N. So it's O(N).
 *
 * @see algs.adt.stack.Stack
 *
 * @author Serj Sintsov
 */
public class ResizingArrayStack<T> implements Stack<T> {

    private T[] storage;
    private int topIndex;

    @SuppressWarnings("unchecked")
    public ResizingArrayStack() {
        storage = (T[]) new Object[1];
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

        if (topIndex > 0 && topIndex == storage.length/4)
            resize(storage.length / 2);

        return item;
    }

    @Override
    public T peek() {
        if (isEmpty()) throw new NoSuchElementException("Stack is empty");
        return storage[topIndex-1];
    }

    @Override
    public void push(T item) {
        if (topIndex == storage.length) resize(2 * storage.length);
        storage[topIndex++] = item;
    }

    private void resize(int newSz) {
        @SuppressWarnings("unchecked")
        T[] newStorage = (T[]) new Object[newSz];

        for (int i = 0; i < min(newSz, storage.length); i++)
            newStorage[i] = storage[i];

        storage = newStorage;
    }

    private int min(int a, int b) {
        return a <= b ? a : b;
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
        ResizingArrayStack<String> stack = new ResizingArrayStack<String>();

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
