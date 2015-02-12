package algs.adt.queue.impl;

import algs.adt.queue.Queue;
import algs.adt.stack.impl.LinkedListStack;
import algs.adt.stack.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This implementation uses two {@link LinkedListStack} stacks to provide
 * FIFO data structure and allows you to add {@code null} values.
 *
 * @see algs.adt.queue.Queue
 *
 * @author Serj Sintsov
 */
public class StacksQueue<T> implements Queue<T> {

    private Stack<T> pushSt, popSt;

    public StacksQueue() {
        pushSt = new LinkedListStack<T>();
        popSt = new LinkedListStack<T>();
    }

    @Override
    public Iterator<T> iterator() {
        fullPopStack();
        return popSt.iterator();
    }

    @Override
    public T dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");

        if (!popSt.isEmpty()) {
            return popSt.pop();
        }
        else {
            fullPopStack();
            return popSt.pop();
        }
    }

    private void fullPopStack() {
        while (!pushSt.isEmpty())
            popSt.push(pushSt.pop());
    }

    @Override
    public void enqueue(T item) {
        pushSt.push(item);
    }

    @Override
    public boolean isEmpty() {
        return pushSt.isEmpty() && popSt.isEmpty();
    }

    @Override
    public int size() {
        return pushSt.size() + popSt.size();
    }

    public static void main(String[] args) {
        StacksQueue<String> queue = new StacksQueue<String>();

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
