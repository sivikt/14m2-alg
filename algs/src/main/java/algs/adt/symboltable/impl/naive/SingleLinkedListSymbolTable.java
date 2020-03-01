package algs.adt.symboltable.impl.naive;

import algs.adt.symboltable.SymbolTable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Unordered Symbol table implementation based on single linked list.
 *
 * @author Serj Sintsov
 */
public class SingleLinkedListSymbolTable<Key, Value> implements SymbolTable<Key, Value>, Iterable<Key> {

    private Node<Key, Value> head;
    private int size;

    @Override
    public Iterator<Key> iterator() {
        return new LinkedListIterator();
    }

    private static class Node<Key, Value> {

        public Key key;
        public Value value;
        public Node<Key, Value> prev;
        public Node<Key, Value> next;

        public Node(Key key, Value value) {
            this(key, value, null);
        }

        public Node(Key key, Value value, Node<Key, Value> next) {
            this.key   = key;
            this.value = value;
            this.next  = next;
        }

    }

    private class LinkedListIterator implements Iterator<Key> {
        private Node<Key, Value> next = head;

        @Override
        public boolean hasNext() {
            return next != null;
        }

        @Override
        public Key next() {
            if (next == null)
                throw new NoSuchElementException();
            Key item = next.key;
            next = next.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Cannot remove from queue");
        }
    }

    @Override
    public void put(Key key, Value val) {
        Node<Key, Value> oldNode = getNode(key);

        if (oldNode == null) { // add new node
            if (val == null)
                return;

            Node<Key, Value> oldHead = head;
            head = new Node<>(key, val);
            head.next = oldHead;

            if (oldHead != null)
                oldHead.prev = head;

            size += 1;
        }
        else {
            if (val != null) { // update old value
                oldNode.value = val;
                return;
            }

            // remove oldNode
            Node<Key, Value> prevNode = oldNode.prev;
            Node<Key, Value> nextNode = oldNode.next;
            if (prevNode != null)
                prevNode.next = nextNode;

            if (nextNode != null)
                nextNode.prev = prevNode;

            if (size == 1)
                head = null;

            size -= 1;
        }
    }

    @Override
    public Value get(Key key) {
        Node<Key, Value> node = getNode(key);
        return node == null ? null : node.value;
    }

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

    private Node<Key, Value> getNode(Key k) {
        Node<Key, Value> next = head;
        while (next != null) {
            if (next.key.equals(k)) return next;
            else next = next.next;
        }
        return null;
    }

    public static void main(String[] args) {
        SingleLinkedListSymbolTable<String, Integer> st = new SingleLinkedListSymbolTable<>();

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
        assert buf.equals("6-60,4-40,3-30,1-100,");
        assert st.size() == 4;
    }

}
