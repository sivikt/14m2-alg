package algs.adt.queue;

/**
 * Queue class represents first-in-first-out (FIFO) collection data
 * structure API.
 *
 * T - collection items' type
 *
 * @author Serj Sintsov
 */
public interface Queue<T> extends Iterable<T> {

    /**
     * Returns and remove an item from the queue.
     */
    T dequeue();

    /**
     * Adds item to the queue
     */
    void enqueue(T item);

    /**
     * Checks if the queue is empty.
     * @return {@code true} if the queue is empty, otherwise returns
     *         {@code false}
     */
    boolean isEmpty();

    /**
     * Actual queue size, e.g. items count.
     */
    int size();

}
