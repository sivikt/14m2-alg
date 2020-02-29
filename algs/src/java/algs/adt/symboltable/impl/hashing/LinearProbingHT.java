package algs.adt.symboltable.impl.hashing;

import algs.adt.symboltable.SymbolTable;

import java.util.Random;

import static algs.adt.symboltable.impl.SymbolTableTest.*;
import static algs.adt.symboltable.impl.SymbolTableTest.intGen;

/**
 * Hash table based implementation of dictionary.
 *
 * @author Serj Sintsov
 */
public class LinearProbingHT<Key, Value> implements SymbolTable<Key, Value> {

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

    private Slot<Key, Value>[] slots;
    private boolean[] deleted;

    public class Slot<K, V> {
        private final K key;
        private V val;

        public Slot(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    public LinearProbingHT() {
        this(defaultHashFunction(), DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public LinearProbingHT(HashFunction<Key> h, int capacity) {
        slots = new Slot[capacity];
        deleted = new boolean[capacity];
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
        Slot<Key, Value> l = getLink(hash(key), key);
        return l != null ? l.val : null;
    }

    @Override
    public void delete(Key key) {
        checkKeyNotNull(key);
        if (deleteLink(hash(key), key)) {
            size -= 1;
            refreshLoadFactor();
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
        int i = hash;
        while (slots[i] != null) {
            if (slots[i].key.equals(key))
                break;
            i = (i + 1) % slots.length;
        }

        if (slots[i] == null)
            slots[i] = new Slot<>(key, val);
        else
            slots[i].val = val;

        deleted[i] = false;
    }

    private Slot<Key, Value> getLink(int hash, Key key) {
        int i = hash;

        while (slots[i] != null || deleted[i]) {
            if (!deleted[i] && slots[i].key.equals(key))
                return slots[i];

            i = (i + 1) % slots.length;
            if (i == hash)
                return null;
        }

        return null;
    }

    private boolean deleteLink(int hash, Key key) {
        int i = hash;

        while (slots[i] != null) {
            if (slots[i].key.equals(key)) {
                deleted[i] = true;
                slots[i] = null;
                return true;
            }

            i = (i + 1) % slots.length;
            if (i == hash)
                return false;
        }

        return false;
    }

    private void ensureCapacity() {
        // for simplicity capacity only increases
        if (slots.length < MAXIMUM_CAPACITY && loadFactor >= MAX_LOAD_FACTOR)
            resize(getOptimalCapacity());
    }

    private int getOptimalCapacity() {
        for (int cap : OPTIMAL_CAPACITIES)
            if (cap > slots.length)
                return cap;
        return MAXIMUM_CAPACITY;
    }

    @SuppressWarnings("unchecked")
    private void resize(int capacity) {
        Slot<Key, Value>[] newSlots = new Slot[capacity];

        for (int i = 0; i < slots.length; i++)
            if (slots[i] != null && !deleted[i])
                rehashAndPutLink(newSlots, slots[i]);

        slots = newSlots;
        deleted = new boolean[capacity];
        refreshLoadFactor();
    }

    private void rehashAndPutLink(Slot<Key, Value>[] slots, Slot<Key, Value> l) {
        int i = hash(l.key, slots.length);
        while (slots[i] != null) {
            i = (i + 1) % slots.length;
        }
        slots[i] = l;
    }

    private void refreshLoadFactor() {
        loadFactor = (float) size / slots.length;
    }

    private int hash(Key key) {
        return hash(key, slots.length);
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
        testSearch(new LinearProbingHT<>(), 8_000_000, intGen());
        testInsert(new LinearProbingHT<>(), 8_000_000, intGen());
        testDelete(new LinearProbingHT<>(), 8_000_000, 0.3, intGen());
    }

}
