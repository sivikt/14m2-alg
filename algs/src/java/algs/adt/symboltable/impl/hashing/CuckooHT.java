package algs.adt.symboltable.impl.hashing;

import algs.adt.symboltable.SymbolTable;

/** TODO implement
 * """
 * """ wikipedia
 *
 * @author Serj Sintsov
 */
public class CuckooHT<Key, Value> implements SymbolTable<Key, Value> {

    private int size;
    private int capacity;
    private float loadFactor;

    @Override
    public void put(Key key, Value val) {

    }

    @Override
    public Value get(Key key) {
        return null;
    }

    @Override
    public void delete(Key key) {

    }

    @Override
    public boolean contains(Key key) {
        return false;
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
    public Iterable<Key> keys() {
        return null;
    }

}
