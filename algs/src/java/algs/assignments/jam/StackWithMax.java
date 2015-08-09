package algs.assignments.jam;

import algs.adt.stack.Stack;
import algs.adt.stack.impl.ResizingArrayStack;

import java.util.Iterator;

/**
 * """Stack with max.
 *    Create a data structure that efficiently supports the stack operations
 *    (push and pop) and also a return-the-maximum operation. Assume the
 *    elements are reals numbers so that you can compare them.
 *
 * """ (from materials by Robert Sedgewick)
 */
public class StackWithMax<T extends Number & Comparable<T>> implements Stack<T> {

    private Stack<T> delegate;
    private Stack<T> maximums;

    public StackWithMax() {
        delegate = new ResizingArrayStack<>();
        maximums = new ResizingArrayStack<>();
    }

    @Override
    public T pop() {
        maximums.pop();
        return delegate.pop();
    }

    @Override
    public T peek() {
        return delegate.peek();
    }

    public T peekMax() {
        return maximums.peek();
    }

    @Override
    public void push(T item) {
        delegate.push(item);

        if (size() == 1 || item.compareTo(maximums.peek()) > 0)
            maximums.push(item);
        else
            maximums.push(maximums.peek());
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public Iterator<T> iterator() {
        return delegate.iterator();
    }


    // testing
    public static void main(String[] args) {
        StackWithMax<Integer> maxs = new StackWithMax<>();

        maxs.push(4);
        maxs.push(1);
        maxs.push(9);
        maxs.push(0);

        assert maxs.peek() == 0;
        assert maxs.peekMax() == 9;
        assert maxs.size() == 4;

        maxs.pop();
        assert maxs.peekMax() == 9;
        assert maxs.size() == 3;

        maxs.pop();
        assert maxs.peekMax() == 4;
        assert maxs.size() == 2;
    }
}
