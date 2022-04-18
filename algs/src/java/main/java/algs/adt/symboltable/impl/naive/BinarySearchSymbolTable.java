package algs.adt.symboltable.impl.naive;

import algs.adt.symboltable.SymbolTable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Ordered Symbol table implementation based binary search.
 * Does not thread-safe.
 *
 * @author Serj Sintsov
 */
public class BinarySearchSymbolTable<Key extends Comparable<? super Key>, Value> implements SymbolTable<Key, Value>,
        Iterable<Key>
{

    private Key[]   keys;
    private Value[] vals;
    private int size;

    @SuppressWarnings("unchecked")
    public BinarySearchSymbolTable(int capacity) {
        keys = (Key[])   new Comparable[capacity];
        vals = (Value[]) new Object[capacity];
    }

    private class ArrayIterator implements Iterator<Key> {
        private int next = 0;

        @Override
        public boolean hasNext() {
            return next < size;
        }

        @Override
        public Key next() {
            if (!hasNext())
                throw new NoSuchElementException();
            return keys[next++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from queue");
        }
    }

    @Override
    public Iterator<Key> iterator() {
        return new ArrayIterator();
    }

    /**
     * O(N)
     */
    @Override
    public void put(Key key, Value val) {
        int index = rank(key);
        if (index < size && keys[index].equals(key)) // key already is in the table
            updateOld(index, val);
        else
            putNew(key, val);
    }

    /**
     * O(log(n))
     */
    @Override
    public Value get(Key key) {
        if (isEmpty()) return null;
        int index = rank(key);
        if (index < size && keys[index].equals(key)) return vals[index];
        else return null;
    }

    /**
     * O(N)
     */
    @Override
    public void delete(Key key) {
        put(key, null);
    }

    @Override
    public boolean contains(Key key) {
        return get(key) != null;
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
        return this;
    }

    private void updateOld(int keyIndex, Value v) {
        if (v != null) {
            vals[keyIndex] = v;
            return; // update old value
        }

        // else remove null key-value
        if (keyIndex < size-1) {
            System.arraycopy(keys, keyIndex+1, keys, keyIndex, size-keyIndex-1);
            System.arraycopy(vals, keyIndex+1, vals, keyIndex, size-keyIndex-1);
        }

        size -= 1;
        keys[size] = null;
        vals[size] = null;
    }

    private void putNew(Key k, Value v) {
        if (v == null) return; // nulls are not allowed

        keys[size] = k;
        vals[size] = v;

        for (int i = size; i > 0; i--)
            if (less(keys[i], keys[i-1])) {
                swap(keys, i, i-1);
                swap(vals, i, i-1);
            } else break;

        size += 1;
    }

    private int rank(Key k) {
        int lo = 0, hi = size-1;
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            int cmp = k.compareTo(keys[mid]);
            if      (cmp > 0) lo = mid + 1;
            else if (cmp < 0) hi = mid - 1;
            else return mid;
        }
        return lo;
    }

    private void swap(Object[] a, int i, int j) {
        Object tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private boolean less(Key x, Key y) {
        return x.compareTo(y) < 0;
    }

    public static void main(String[] args) {
        BinarySearchSymbolTable<String, Integer> st = new BinarySearchSymbolTable<>(10);

        assert st.isEmpty();
        assert st.size() == 0;

        st.put("1", 10);
        st.put("2", 20);
        st.put("3", 30);

        assert !st.isEmpty();
        assert st.size() == 3;
        assert st.get("1") == 10;
        assert st.contains("2");

        st.delete("2");
        st.put("1", 100);
        st.put("4", 40);
        st.put("6", 60);

        String buf = "";
        for (String k : st) {
            buf += k + "-" + st.get(k) + ",";
        }
        assert buf.equals("1-100,3-30,4-40,6-60,");
        assert st.size() == 4;
    }

}
