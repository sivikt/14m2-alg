package algs.adt.pqueue;

import algs.adt.queue.Queue;

/**
 * Priority queue generic API.
 *
 * @author Serj Sintsov
 */
public interface PriorityQueue<Key> extends Queue<Key> {

    void increaseKey(Key key);

    void decreaseKey(Key key);

    Key peek();

}
