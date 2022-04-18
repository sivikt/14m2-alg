package algs.adt.symboltable;

/**
 * """Ordered symbol table.
 *    In typical applications, keys are Comparable objects, so the option exists of using the code a.compareTo(b) to
 *    compare two keys a and b. Several symbol-table implementations take advantage of order among the keys that is
 *    implied by Comparable to provide efficient implementations of the put() and get() operations. More important, in
 *    such implementations, we can think of the symbol table as keeping the keys in order and consider a significantly
 *    expanded API that defines numerous natural and useful operations involving relative key order.
 * """ Robert Sedgewick
 *
 * Range count and Rage search have different applications in CAD, games, movies, databases, geometric (find points in
 * a given 1d interval).
 *
 * @author Serj Sintsov
 */
public interface OrderedSymbolTable<Key extends Comparable<? super Key>, Value> extends SymbolTable<Key, Value> {

    Key min();

    Key max();

    /**
     * Largest key less or equal to input key.
     */
    Key floor(Key key);

    /**
     * Smallest key greater or equal to input key.
     */
    Key ceiling(Key key);

    /**
     * Number of keys less than input key.
     */
    int rank(Key key);

    /**
     * Key of rank r.
     */
    Key select(int r);

    void deleteMin();

    void deleteMax();

    /**
     * Range count.
     * How many keys are between lo and hi.
     */
    int size(Key lo, Key hi);

    /**
     * Range search.
     * Keys in [lo..hi] in sorted order.
     */
    Iterable<Key> keys(Key lo, Key hi);

    /**
     * All keys in sorted order.
     */
    @Override
    Iterable<Key> keys();

}
