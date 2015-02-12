package algs.adt.deque;

/**
 * """A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a
 *    queue that supports inserting and removing items from either the front or the back of
 *    the data structure.
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public interface Deque<Item> extends Iterable<Item> {

    /**
     * is the deque empty?
     */
    boolean isEmpty();

    /**
     * returns the number of items on the deque
     */
    int size();

    /**
     * inserts the item at the front
     */
    void addFirst(Item item);

    /**
     * inserts the item at the end
     */
    void addLast(Item item);

    /**
     * deletes and return the item at the front
     */
    Item removeFirst();

    /**
     * deletes and return the item at the end
     */
    Item removeLast();

}
