package algs.assignments.jam;

import algs.adt.pqueue.PriorityQueue;

import java.util.*;

import static algs.sorting.SortUtil.isSorted;

/**
 * """Randomized priority queue.
 *    Describe how to add the methods sample() and delRandom() to our binary heap
 *    implementation. The two methods return a key that is chosen uniformly at random
 *    among the remaining keys, with the latter method also removing that key.
 *
 *    The sample() method should take constant time; the delRandom() method should
 *    take logarithmic time. Do not worry about resizing the underlying array.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class RandomizedPriorityQueue<Item> implements PriorityQueue<Item> {

    private Comparator<Item> cmptr;
    private Item[] items;
    private int size;

    @SuppressWarnings("unchecked")
    public RandomizedPriorityQueue(int capacity, Comparator<Item> cmp) {
        this.cmptr = cmp;
        this.items = (Item[]) new Object[capacity+1];
    }

    @Override
    public Item peek() {
        checkCapacityForGet();
        return items[1];
    }

    @Override
    public Item dequeue() {
        checkCapacityForGet();

        Item result = items[1];
        swap(1, size);
        items[size--] = null;
        sink(1);

        return result;
    }

    @Override
    public void enqueue(Item item) {
        checkCapacityForAdd();
        items[++size] = item;
        swim(size);
    }

    public Item sample() {
        checkCapacityForGet();
        Random rnd = new Random();
        return items[rnd.nextInt(size) + 1];
    }

    public Item delRandom() {
        checkCapacityForGet();

        Random rnd = new Random();
        int i = rnd.nextInt(size) + 1;

        Item result = items[i];
        swap(i, size);
        items[size--] = null;
        sink(i);

        return result;
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
    public Iterator<Item> iterator() {
        throw new UnsupportedOperationException();
    }

    private boolean less(int x, int y) {
        return cmptr.compare(items[x], items[y]) < 0;
    }

    private void swap(int i, int j) {
        Item tmp = items[i];
        items[i] = items[j];
        items[j] = tmp;
    }

    private void checkCapacityForAdd() {
        if (size == items.length-1)
            throw new IllegalStateException("Queue is full");
    }

    private void checkCapacityForGet() {
        if (size == 0)
            throw new IllegalStateException("Queue is empty");
    }

    private void swim(int k) {
        int parent = k >> 1; // k/2
        while (k > 1 && less(parent, k)) {
            swap(k, parent);
            k = parent;
            parent = k >> 1;
        }
    }

    private void sink(int k) {
        int child = k << 1; // k*2
        while (child <= size) {
            if (child < size && less(child, child+1))
                child += 1;

            if (less(child, k))
                break;

            swap(k, child);

            k = child;
            child = k << 1;
        }
    }

    // testing
    public static void main(String[] args) {
        RandomizedPriorityQueue<Integer> queue = new RandomizedPriorityQueue<>(10, (a, b) -> a - b);

        assert queue.isEmpty();
        assert queue.size() == 0;

        queue.enqueue(8);
        queue.enqueue(0);
        queue.enqueue(9);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(5);
        queue.enqueue(7);
        queue.enqueue(4);
        queue.enqueue(6);
        queue.enqueue(3);

        String buf = "";
        while (!queue.isEmpty()) {
            buf += queue.dequeue();
        }
        assert buf.equals("9876543210");
        assert queue.size() == 0;

        //
        queue.enqueue(0);
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);
        queue.enqueue(7);
        queue.enqueue(8);
        queue.enqueue(9);

        Map<Integer, Integer> frequencies = new HashMap<>();
        for (int i = 0; i < 3000000; i++) {
            Integer k = queue.sample();
            Integer n = frequencies.get(k);

            if (n != null)
                frequencies.put(k, n+1);
            else
                frequencies.put(k ,1);
        }
        System.out.println(frequencies);

        queue.delRandom();
        queue.delRandom();
        queue.delRandom();
        assert queue.size() == 7;
        Integer[] a = new Integer[queue.size()];
        for (int i = a.length-1; i >= 0; i--)
            a[i] = queue.dequeue();

        assert isSorted(a, 0, a.length-1);
    }

}
