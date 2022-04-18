package algs.adt.symboltable.impl.searchtree.bst;

import algs.adt.queue.Queue;
import algs.adt.queue.impl.LinkedListQueue;
import algs.adt.symboltable.impl.searchtree.RecursiveTraverse;
import algs.adt.symboltable.impl.searchtree.TreeNode;
import algs.adt.symboltable.searchtree.BinarySearchTree;
import algs.adt.symboltable.searchtree.OrderedSearchTree;

import static algs.adt.symboltable.impl.SymbolTableTest.*;
import static algs.adt.symboltable.impl.searchtree.bst.BstUtil.isBst;

/**
 * Simple implementation of Binary Search Tree without balancing.
 * Use recursive implementation for simplicity.
 *
 * If N distinct keys are inserted into this implementation of BST in random order,
 * the expected number of compares for a search/insert is 2*ln(N). (1-1 correspondence to quicksort partitioning)
 *
 * This implementation isn't defended from degenerate case when tree is completely unbalanced.
 *
 * Worst case for insert/search   is O(N)
 * Average case for insert/search is O(log(N)) 1.39lg(N) and sqrt(N) for delete
 *
 * Supports only unique keys.
 * Does not thread-safe.
 *
 * @author Serj Sintsov
 */
public class UnbalancedBinarySearchTree<Key extends Comparable<? super Key>, Value> implements
        OrderedSearchTree<Key, Value>, BinarySearchTree<Key, Value>
{

    private Node<Key, Value> root;

    private static final class Node<Key, Value> extends TreeNode<Key, Value, Node<Key, Value>> {
        public int count;

        public Node(Key key, Value value) {
            super(key, value);
            count = 1;
        }
    }
    
    /**
     * Cost is (1 + depth_of_node)
     */
    @Override
    public Key min() {
        Node<Key, Value> x = min(root);
        assert isBst(root);
        return x == null ? null : x.key;
    }

    private Node<Key, Value> min(Node<Key, Value> x) {
        if (x == null) return null;
        while (true)
            if (x.left == null) return x;
            else x = x.left;
    }

    /**
     * Cost is (1 + depth_of_node) if balanced
     */
    @Override
    public Key max() {
        Node<Key, Value> x = root;
        if (x == null) return null;
        while (true)
            if (x.right == null) return x.key;
            else x = x.right;
    }

    /**
     * Cost is (1 + depth_of_node) if balanced
     */
    @Override
    public Key floor(Key key) {
        Node<Key, Value> x = floor(root, key);
        assert isBst(root);
        return x == null ? null : x.key;
    }

    private Node<Key, Value> floor(Node<Key, Value> x, Key k) {
        if (x == null) return null;

        int cmp = k.compareTo(x.key);

        if      (cmp == 0) return x;
        else if (cmp < 0)  return floor(x.left, k);

        Node<Key, Value> t = floor(x.right, k);
        if (t != null) return t;
        else           return x;
    }

    /**
     * Cost is (1 + depth_of_node) if balanced
     */
    @Override
    public Key ceiling(Key key) {
        Node<Key, Value> x = ceiling(root, key);
        assert isBst(root);
        return x == null ? null : x.key;
    }

    private Node<Key, Value> ceiling(Node<Key, Value> x, Key k) {
        if (x == null) return null;

        int cmp = k.compareTo(x.key);

        if      (cmp == 0) return x;
        else if (cmp > 0)  return floor(x.right, k);

        Node<Key, Value> t = floor(x.left, k);
        if (t != null) return t;
        else           return x;
    }

    /**
     * Cost is (1 + depth_of_node) if balanced
     */
    @Override
    public int rank(Key key) {
        assert isBst(root);
        return rank(root, key);
    }

    private int rank(Node<Key, Value> x, Key k) {
        if (x == null) return 0;

        int cmp = k.compareTo(x.key);

        if      (cmp > 0) return 1 + size(x.left) + rank(x.right, k);
        else if (cmp < 0) return rank(x.left, k);
        else              return size(x.left);
    }

    /**
     * Cost is (1 + depth_of_node) if balanced
     */
    @Override
    public Key select(int r) {
        if (r < 0 || r >= size()) return null;
        Node<Key, Value> x = select(root, r);
        assert isBst(root);
        return x == null ? null : x.key;
    }

    private Node<Key, Value> select(Node<Key, Value> x, int r) {
        if (x == null) return null;

        int t = size(x.left);
        if      (r < t) return select(x.left, r);
        else if (r > t) return select(x.right, r-t-1);
        else            return x;
    }

    /**
     * Cost is (1 + depth_of_node) if balanced
     */
    @Override
    public void deleteMin() {
        root = deleteMin(root);
        assert isBst(root);
    }

    private Node<Key, Value> deleteMin(Node<Key, Value> x) {
        if (x == null) return null;
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Cost is (1 + depth_of_node) if balanced
     */
    @Override
    public void deleteMax() {
        root = deleteMax(root);
        assert isBst(root);
    }

    private Node<Key, Value> deleteMax(Node<Key, Value> x) {
        if (x == null) return null;
        if (x.right == null) return x.left;
        x.right = deleteMin(x.right);
        x.count = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Method called as "Hibbard deletion".
     * Makes tree unbalanced.
     */
    @Override
    public void delete(Key key) {
        root = delete(root, key);
        assert isBst(root);
    }

    private Node<Key, Value> delete(Node<Key, Value> x, Key key) {
        if (x == null) return null;

        int cmp = key.compareTo(x.key);
        if      (cmp > 0) x.right = delete(x.right, key);
        else if (cmp < 0) x.left  = delete(x.left, key);
        else {
            if (x.left == null)  return x.right;
            if (x.right == null) return x.left;

            // replace with successor
            Node<Key, Value> t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }

        x.count = 1 + size(x.left) + size(x.right);

        return x;
    }

    /**
     * Cost is (1 + depth_of_node) if balanced
     */
    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val);
        assert isBst(root);
    }

    private Node<Key, Value> put(Node<Key, Value> x, Key k, Value v) {
        if (x == null) return new Node<>(k, v);

        int cmp = k.compareTo(x.key);
        if      (cmp > 0) x.right = put(x.right, k, v);
        else if (cmp < 0) x.left  = put(x.left, k, v);
        else              x.value = v;

        x.count = 1 + size(x.left) + size(x.right);

        return x;
    }

    /**
     * Cost is (1 + depth_of_node) if balanced
     */
    @Override
    public Value get(Key key) {
        Node<Key, Value> x = getNode(key);
        return x == null ? null : x.value;
    }

    private Node<Key, Value> getNode(Key k) {
        Node<Key, Value> x = root;
        while (x != null) {
            int cmp = k.compareTo(x.key);
            if      (cmp > 0) x = x.right;
            else if (cmp < 0) x = x.left;
            else              return x;
        }
        return null;
    }

    @Override
    public boolean contains(Key key) {
        return getNode(key) != null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node<Key, Value> x) {
        return x == null ? 0 : x.count;
    }

    @Override
    public int size(Key lo, Key hi) {
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else              return rank(hi) - rank(lo);
    }

    @Override
    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> q = new LinkedListQueue<>();
        inorder(root, lo, hi, q);
        return q;
    }

    private void inorder(Node<Key, Value> x, Key lo, Key hi, Queue<Key> q) {
        if (x == null) return;
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);

        if (cmpLo < 0)                inorder(x.left, lo, hi, q);
        if (cmpHi > 0)                inorder(x.right, lo, hi, q);
        if (cmpLo >= 0 && cmpHi <= 0) q.enqueue(x.key);
    }

    /**
     * Cost is N
     */
    @Override
    public Iterable<Key> keys() {
        return inorder();
    }

    @Override
    public Iterable<Key> preorder() {
        return RecursiveTraverse.preorder(root);
    }

    @Override
    public Iterable<Key> inorder() {
        return RecursiveTraverse.inorder(root);
    }

    @Override
    public Iterable<Key> postorder() {
        return RecursiveTraverse.postorder(root);
    }

    @Override
    public Iterable<Key> lvlorder() {
        return RecursiveTraverse.lvlorder(root);
    }

    public static void main(String[] args) throws Exception {
        testInsert(new UnbalancedBinarySearchTree<>(), 2_000, intGen());
        testDelete(new UnbalancedBinarySearchTree<>(), 200_000, 0.2, intGen());
    }

}
