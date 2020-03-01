package algs.adt.list;

/**
 * Abstract List data structure.
 *
 * @author Serj Sintsov
 */
public interface List<T> extends Iterable<T> {

    void add(T item);

    void delete(T item);

    T get(int i);

    int size();

    boolean isEmpty();

    boolean contains(T item);

}
