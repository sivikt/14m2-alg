package algs.adt.queue.impl;

import algs.adt.queue.RandomizedQueue;
import edu.princeton.cs.introcs.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * """Your randomized queue implementation must support each randomized queue operation
 *    (besides creating an iterator) in constant amortized time and use space proportional
 *    to the number of items currently in the queue. That is, any sequence of M randomized
 *    queue operations (starting from an empty queue) should take at most cM steps in the
 *    worst case, for some constant c. Additionally, your iterator implementation must support
 *    construction in time linear in the number of items and it must support the operations next()
 *    and hasNext() in constant worst-case time; you may use a linear amount of extra memory per
 *    iterator. The order of two or more iterators to the same randomized queue should be mutually
 *    independent; each iterator must maintain its own random order.
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class ArrayRandomizedQueue<Item> implements RandomizedQueue<Item> {

    private static final int   DEF_CAPACITY = 10;
    private static final float TRIM_FACTOR  = 0.65F;

    private Item[] elementsData;
    private int head;
    private int size;

    private static class RandomIterator<Item> implements Iterator<Item> {

        private Item[] itData;
        private int current;
        private long itSeed = System.nanoTime();

        public RandomIterator(Item[] elementsData, int head, int size) {
            itData = newElementsData(size);
            System.arraycopy(elementsData, head, itData, 0, size);
            shuffle(itData);
        }

        @Override
        public boolean hasNext() {
            return current < itData.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return itData[current++];
        }

        private void shuffle(Item[] a) {
            StdRandom.setSeed(itSeed);
            for (int i = 0; i < a.length; i++)
                rswap(a, i);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from queue");
        }

        private void rswap(Item[] a, int swapi) {
            int r = StdRandom.uniform(swapi + 1);
            swap(a, swapi, r);
        }

        private void swap(Item[] a, int i, int j) {
            Item tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public ArrayRandomizedQueue() {
        elementsData = newElementsData(DEF_CAPACITY);
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
    public void enqueue(Item t) {
        assertNotNull(t);
        ensureCapacity();

        // keep elements in order of enqueue
        elementsData[head + size] = t;
        size += 1;
    }

    @Override
    public Item dequeue() {
        errorOnEmpty();
        gc();

        int r = randomItemIndex();
        Item t = elementsData[r];

        elementsData[r] = elementsData[head];
        elementsData[head] = null;
        head += 1;
        size -= 1;

        return t;
    }

    @Override
    public Item sample() {
        errorOnEmpty();
        // always return uniformly randomized item
        return elementsData[randomItemIndex()];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomIterator<>(elementsData, head, size);
    }

    private void ensureCapacity() {
        if (head + size >= elementsData.length) grow(DEF_CAPACITY);
    }

    private void grow(int minCapacity) {
        int oldCapacity = elementsData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity < minCapacity)
            newCapacity = minCapacity;

        resize(newCapacity);
    }

    private void gc() {
        if ((float) size / elementsData.length > TRIM_FACTOR) return;
        resize(size);
    }

    private void resize(int capacity) {
        Item[] newElemsData = newElementsData(capacity);
        System.arraycopy(elementsData, head, newElemsData, 0, size);
        elementsData = newElemsData;
        head = 0;
    }

    private int randomItemIndex() {
        return StdRandom.uniform(head, head + size);
    }

    @SuppressWarnings("unchecked")
    private static <Item> Item[] newElementsData(int capacity) {
        return (Item[]) new Object[capacity];
    }

    private void assertNotNull(Item t) {
        if (t == null) throw new NullPointerException("Item to insert must be not null");
    }

    private void errorOnEmpty() {
        if (isEmpty()) throw new NoSuchElementException("Queue is empty");
    }


    public static void main(String[] args) {
        RandomizedQueue<String> queue = new ArrayRandomizedQueue<>();

        assert queue.isEmpty();
        assert queue.size() == 0;

        queue.enqueue("1 ");
        queue.enqueue("2 ");
        queue.enqueue("3 ");
        queue.enqueue("4 ");
        queue.enqueue("5 ");
        queue.enqueue("6 ");
        queue.enqueue("7 ");
        queue.enqueue("8 ");
        queue.enqueue("9 ");
        queue.enqueue("10 ");

        String buf1 = "";
        for (String s : queue)
            buf1 += s;

        assert !buf1.equals("1 2 3 4 5 6 7 8 9 10 ");
        assert queue.size() == 10;

        String buf2 = "";
        for (String s : queue)
            buf2 += s;

        assert !buf2.equals("1 2 3 4 5 6 7 8 9 10 ");
        assert !buf2.equals(buf1);

        assert !queue.sample().equals("1 ");
        assert queue.size() == 10;

        assert !queue.dequeue().equals("1 ");
        assert queue.size() == 9;

        queue.enqueue("11 ");
        queue.enqueue("12 ");
        queue.enqueue("13 ");
        queue.enqueue("14 ");
        queue.enqueue("15 ");
        assert queue.size() == 14;


        RandomizedQueue<String> queue2 = new ArrayRandomizedQueue<>();
        queue2.enqueue("1 ");
        queue2.enqueue("2 ");
        queue2.enqueue("3 ");
        queue2.enqueue("4 ");
        queue2.enqueue("5 ");
        queue2.enqueue("6 ");
        queue2.enqueue("7 ");
        queue2.enqueue("8 ");
        queue2.enqueue("9 ");
        queue2.enqueue("10 ");

        queue2.dequeue();
        queue2.dequeue();
        queue2.dequeue();
        queue2.dequeue();
        queue2.dequeue();
        queue2.dequeue();
        queue2.dequeue();
        queue2.dequeue();
        queue2.dequeue();
        queue2.dequeue();

        RandomizedQueue<String> queue3 = new ArrayRandomizedQueue<>();
        queue3.enqueue("1 ");
        queue3.dequeue();
        queue3.enqueue("2 ");
        queue3.dequeue();
        queue3.enqueue("3 ");
        queue3.dequeue();
        queue3.enqueue("4 ");
        queue3.dequeue();
        queue3.enqueue("5 ");
        queue3.dequeue();
        queue3.enqueue("6 ");
        queue3.dequeue();
        queue3.enqueue("7 ");
        queue3.dequeue();
        queue3.enqueue("8 ");
        queue3.dequeue();
        queue3.enqueue("9 ");
        queue3.dequeue();
        queue3.enqueue("10 ");
        queue3.dequeue();
        queue3.enqueue("11 ");


        queue2 = new ArrayRandomizedQueue<>();
        queue2.enqueue("1 ");
        queue2.enqueue("2 ");
        queue2.enqueue("3 ");

        queue3 = new ArrayRandomizedQueue<>();
        queue3.enqueue("1 ");
        queue3.enqueue("2 ");
        queue3.enqueue("3 ");

        for (String val1 : queue2) {
            System.out.println("val1: " + val1);
            for (String val2 : queue3)
                System.out.println("val2: " + val2);
        }
    }
}
