package algs.adt.symboltable.impl.hashing;

import algs.adt.symboltable.SymbolTable;

import java.util.Random;

import static algs.adt.symboltable.impl.SymbolTableTest.*;
import static algs.adt.symboltable.impl.SymbolTableTest.intGen;

/**
 * Hash table based implementation of dictionary. Hash table is implemented
 * using closed hashing with separate chaining collisions resolution.
 *
 * @author Serj Sintsov
 */
public class SeparateChainingHT<Key, Value> implements SymbolTable<Key, Value> {

    private static final int[] OPTIMAL_CAPACITIES = {
            17, 37, 67, 131, 257, 521, 1031, 2053, 4099, 8209, 16411, 32771,
            65537, 131101, 262147, 524309, 1048583, 2097169, 4194319, 8388617,
            16777259, 33554467, 67108879, 134217757, 268435459, 536870923,
            1073741825, 2147483647
    };

    private static final int DEFAULT_CAPACITY  = 17;
    private static final int MAXIMUM_CAPACITY  = Integer.MAX_VALUE;

    private static final float MAX_LOAD_FACTOR = 0.80F;

    private final HashFunction<Key> hashFunc;

    private int size;
    private float loadFactor;

    private Link<Key, Value>[] chain;

    public class Link<K, V> {
        private final K key;
        private V val;

        private Link<K, V> next;

        public Link(K key, V val, Link<K, V> next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }

    }

    public SeparateChainingHT() {
        this(defaultHashFunction(), DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public SeparateChainingHT(HashFunction<Key> h, int capacity) {
        chain = new Link[capacity];
        hashFunc = h;
    }

    @Override
    public void put(Key key, Value val) {
        checkKeyNotNull(key);
        ensureCapacity();
        putLink(hash(key), key, val);
        size += 1;
        refreshLoadFactor();
    }

    @Override
    public Value get(Key key) {
        if (key == null)
            return null;
        Link<Key, Value> l = getLink(hash(key), key);
        return l != null ? l.val : null;
    }

    @Override
    public void delete(Key key) {
        checkKeyNotNull(key);
        if (deleteLink(hash(key), key)) {
            size -= 1;
            refreshLoadFactor();
            ensureCapacity();
        }
    }

    @Override
    public boolean contains(Key key) {
        return key != null && getLink(hash(key), key) != null;
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

    private void putLink(int hash, Key key, Value val) {
        Link<Key, Value> old = getLink(hash, key);
        if (old != null)
            old.val = val;
        else {
            Link<Key, Value> head = chain[hash];
            chain[hash] = new Link<>(key, val, head);
        }
    }

    private Link<Key, Value> getLink(int hash, Key key) {
        Link<Key, Value> curr = chain[hash];
        Link<Key, Value> prev = curr;

        while (curr != null) {
            if (curr.key.equals(key)) {

                // try to optimize frequently accessed links
                if (prev != curr) {
                    prev.next = curr.next;
                    curr.next = chain[hash];
                    chain[hash] = curr;
                }
                return curr;
            }

            prev = curr;
            curr = curr.next;
        }

        return null;
    }

    private boolean deleteLink(int hash, Key key) {
        Link<Key, Value> curr = chain[hash];
        Link<Key, Value> prev = curr;

        while (curr != null) {
            if (curr.key.equals(key)) {
                if (prev != curr)
                    prev.next = curr.next;
                else
                    chain[hash] = null;

                return true;
            }

            prev = curr;
            curr = curr.next;
        }

        return false;
    }

    private void ensureCapacity() {
        // for simplicity capacity only increases
        if (chain.length < MAXIMUM_CAPACITY && loadFactor >= MAX_LOAD_FACTOR)
            resize(getOptimalCapacity());
    }

    private int getOptimalCapacity() {
        for (int cap : OPTIMAL_CAPACITIES)
            if (cap > chain.length)
                return cap;
        return MAXIMUM_CAPACITY;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Link<Key, Value>[] newChain = new Link[capacity];
        for (Link<Key, Value> l : chain) {
            while (l != null) {
                rehashAndPutLink(newChain, l);
                l = l.next;
            }
        }
        chain = newChain;
        refreshLoadFactor();
    }

    private void rehashAndPutLink(Link<Key, Value>[] chain, Link<Key, Value> l) {
        int hash = hash(l.key, chain.length);
        Link<Key, Value> head = chain[hash];
        chain[hash] = new Link<>(l.key, l.val, head);
    }

    private void refreshLoadFactor() {
        loadFactor = (float) size / chain.length;
    }

    private int hash(Key key) {
        return hash(key, chain.length);
    }

    private int hash(Key key, int capacity) {
        return Math.abs(hashFunc.hash(key) % capacity);
    }

    private static <Key> HashFunction<Key> defaultHashFunction() {
        Random rnd = new Random();
        final int p = Integer.MAX_VALUE;
        final int a = rnd.nextInt(p-1) + 1;
        final int b = rnd.nextInt(p);

        return key ->
            (int) ((long) (a*key.hashCode() + b) % p);
    }

    private void checkKeyNotNull(Key k) {
        if (k == null)
            throw new IllegalArgumentException("Null keys not allowed");
    }

    /**
     * testing
     */
    public static void main(String[] args) throws Exception {
        testSearch(new SeparateChainingHT<>(), 8_000_000, intGen());
        testInsert(new SeparateChainingHT<>(), 8_000_000, intGen());
        testDelete(new SeparateChainingHT<>(), 8_000_000, 0.3, intGen());
    }

}
