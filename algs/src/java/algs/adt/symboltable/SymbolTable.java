package algs.adt.symboltable;

/**
 * """A symbol table A symbol table is a data structure that associates a value with a key.
 *    It supports two primary operations: insert (put) a new pair into the table and search for
 *    (get) the value associated with a given key.
 * """ Robert Sedgewick
 *
 * """In computer science, an associative array, map, symbol table, or dictionary is an abstract data type composed of a
 *    collection of (key, value) pairs, such that each possible key appears at most once in the collection.
 *
 *    Operations associated with this data type allow:
 *      - the addition of pairs to the collection
 *      - the removal of pairs from the collection
 *      - the modification of the values of existing pairs
 *      - the lookup of the value associated with a particular key
 *
 *    The dictionary problem is a classic computer science problem: the task of designing a data structure that maintains
 *    a set of data during 'search' 'delete' and 'insert' operations. A standard solution to the dictionary problem is a
 *    hash table; in some cases it is also possible to solve the problem using directly addressed arrays, binary search
 *    trees, or other more specialized structures. Many programming languages include associative arrays as primitive
 *    data types, and they are available in software libraries for many others. Content-addressable memory is a form of
 *    direct hardware-level support for associative arrays.
 *
 *    Associative arrays have many applications including such fundamental programming patterns as memoization and the
 *    decorator pattern, dictionary, book index, file share, account management, web search, compiler and etc.
 * """ wikipedia (c)
 *
 *
 * @author Serj Sintsov
 */
public interface SymbolTable<Key, Value> {

    void put(Key key, Value val);

    Value get(Key key);

    void delete(Key key);

    boolean contains(Key key);

    boolean isEmpty();

    int size();

    Iterable<Key> keys();

}
