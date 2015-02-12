package algs.adt.queue;

import java.util.Iterator;

/**
 * """A randomized queue is similar to a stack or queue, except that the
 *    item removed is chosen uniformly at random from items in the data
 *    structure.
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public interface RandomizedQueue<Item> extends Iterable<Item> {

    /**
     * is the queue empty?
     */
    boolean isEmpty();

    /**
     * returns the number of items on the queue
     */
    int size();

    /**
     * add the item
     */
    void enqueue(Item item);

    /**
     * deletes and return a random item
     */
    Item dequeue();

    /**
     * returns (but do not delete) a random item
     */
    Item sample();

    /**
     * returns an independent iterator over items in random order
     */
    @Override
    Iterator<Item> iterator();

}
