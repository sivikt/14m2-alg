package algs.adt.stack;

/**
 * Stack class represents last-in-first-out (LIFO) collection data structure
 * API.
 *
 * T - collection items' type
 *
 * @author Serj Sintsov
 */
public interface Stack<T> extends Iterable<T> {

    /**
     * Returns and remove the top stack item.
     */
    T pop();

    T peek();

    /**
     * Adds item to the stack
     */
    void push(T item);

    /**
     * Checks if the stack is empty.
     * @return {@code true} if the stack is empty, otherwise returns
     *         {@code false}
     */
    boolean isEmpty();

    /**
     * Actual stack size, e.g. items count.
     */
    int size();

}
