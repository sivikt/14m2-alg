package algs.adt.symboltable.impl.searchtree.bst;

import algs.adt.symboltable.impl.searchtree.RecursiveTraverse;
import algs.adt.symboltable.impl.searchtree.TreeNode;
import algs.adt.symboltable.searchtree.CartesianTree;
import algs.adt.symboltable.searchtree.Mergable;

import java.util.Arrays;
import java.util.Random;

import static algs.adt.symboltable.impl.SymbolTableTest.*;
import static algs.adt.symboltable.impl.searchtree.bst.BstUtil.isBst;

/**
 * """ In computer science, the treap and the randomized binary search tree are two closely related forms of binary
 *     search tree data structures that maintain a dynamic set of ordered keys and allow binary searches among the keys.
 *     After any sequence of insertions and deletions of keys, the shape of the tree is a random variable with the same
 *     probability distribution as a random binary tree; in particular, with high probability its height is proportional
 *     to the logarithm of the number of keys, so that each search, insertion, or deletion operation takes logarithmic
 *     time to perform.
 *
 *     The treap was first described by Cecilia R. Aragon and Raimund Seidel in 1989; its name is a portmanteau of tree
 *     and heap. It is a Cartesian tree in which each key is given a (randomly chosen) numeric priority. As with any
 *     binary search tree, the inorder traversal order of the nodes is the same as the sorted order of the keys. The
 *     structure of the tree is determined by the requirement that it be heap-ordered: that is, the priority number for
 *     any non-leaf node must be greater than or equal to the priority of its children. Thus, as with Cartesian trees
 *     more generally, the root node is the maximum-priority node, and its left and right subtrees are formed in the
 *     same manner from the subsequences of the sorted order to the left and right of that node.
 *
 *     Blelloch and Reid-Miller describe an application of treaps to a problem of maintaining sets of items and
 *     performing set union, set intersection, and set difference operations, using a treap to represent each set. Naor
 *     and Nissim describe another application, for maintaining authorization certificates in public-key cryptosystems.
 * """ wikipedia
 *
 * Supports only unique keys (keys and priorities unique in pairs).
 * Does not thread-safe.
 *
 * @author Serj Sintsov
 */
public class Treap<Key extends Comparable<? super Key>, Value> implements CartesianTree<Key, Value>,
        Mergable<Treap<Key, Value>>
{

    private static final class Node<Key, Value> extends TreeNode<Key, Value, Node<Key, Value>> {
        private long priority;
        private Node<Key, Value> parent;

        public Node() {
            this(null, null, 0);
        }

        public Node(Key key, Value value, long priority) {
            super(key, value);
            this.priority = priority;
        }
    }

    private final Random rnd = new Random();

    private Node<Key, Value> root;
    private int size;

    public Treap() {
    }

    public Treap(Key[] keys, Value[] vals) {
        if (keys == null && vals == null)
            root = null;
        else if (keys == null || vals == null || keys.length != vals.length)
            throw new IllegalArgumentException("Keys size don't match values size");
        else {
            root = build(keys, vals);
            assertIsTreap(root);
        }
    }

    @Override
    public void put(Key key, Value val) {
        checkKeyNotNull(key);
        Node<Key, Value> out = new Node<>();
        split(root, key, out);

        if (out.key == null)
            size += 1;

        Node<Key, Value> x = new Node<>(key, val, rnd.nextLong());
        root = merge(merge(out.left, x), out.right);
        assertIsTreap(root);
    }

    @Override
    public Value get(Key key) {
        checkKeyNotNull(key);
        Node<Key, Value> x = getNode(root, key);
        return x == null ? null : x.value;
    }

    @Override
    public void delete(Key key) {
        checkKeyNotNull(key);
        Node<Key, Value> out = new Node<>();
        split(root, key, out);

        if (out.key != null)
            size -= 1;

        root = merge(out.left, out.right);
        assertIsTreap(root);
    }

    @Override
    public Treap<Key, Value> merge(Treap<Key, Value> that) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(Key key) {
        checkKeyNotNull(key);
        return getNode(root, key) != null;
    }

    @Override
    public boolean isEmpty() {
        return size != 0;
    }

    @Override
    public int size() {
        return size;
    }

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

    /**
     * Builds treap from ASC ordering unique keys.
     */
    private Node<Key, Value> build(Key[] keys, Value[] vals) {
        assert keys.length == vals.length;

        if (keys.length == 0)
            return null;

        Node<Key, Value> last = new Node<>(keys[0], vals[0], rnd.nextLong());

        for (int i = 1; i < keys.length; i++) {
            Node<Key, Value> x = new Node<>(keys[i], vals[i], rnd.nextLong());

            if (isLessPriority(x, last)) {
                last.right = x;
                x.parent = last;
                last = x;
            }
            else {
                Node<Key, Value> c = last;
                while (c.parent != null && isLessPriority(c.parent, x)) {
                    x.parent = c.parent;
                    c.parent = null;
                    c = x.parent;
                }

                x.left = c;
                if (c.parent != null)
                    c.parent.right = x;

                x.parent = c.parent;
                c.parent = null;
                last = x;
            }
        }

        while (last.parent != null) {
            Node<Key, Value> c = last.parent;
            last.parent = null;
            last = c;
        }

        assert checkParentsNull(last);
        return last;
    }

    /**
     * Assumes that all keys in {@code l} tree is less to keys in {@code r} tree.
     */
    private Node<Key, Value> merge(Node<Key, Value> l, Node<Key, Value> r) {
        assert checkIsLess(l, r) : "left tree is not less than the right tree";

        if (l == null)
            return r;
        if (r == null)
            return l;

        if (isLessPriority(l, r)) {
            r.left = merge(l, r.left);
            return r;
        }
        else {
            l.right = merge(l.right, r);
            return l;
        }
    }

    /**
     * Produces tuple of two trees. Tree out.left contains keys which are less than {@code lambda}, tree
     * out.right contains keys which are greater than {@code lambda}.
     */
    private void split(Node<Key, Value> x, Key lambda, Node<Key, Value> out) {
        if (x == null) {
            out.left = out.right = null;
            return;
        }

        Node<Key, Value> newOut = new Node<>();
        int cmp = x.key.compareTo(lambda);

        if (cmp > 0) {
            split(x.left, lambda, newOut);
            x.left    = newOut.right;
            out.key   = newOut.key;
            out.right = x;
            out.left  = newOut.left;
        }
        else if (cmp < 0) {
            split(x.right, lambda, newOut);
            x.right   = newOut.left;
            out.key   = newOut.key;
            out.right = newOut.right;
            out.left  = x;
        }
        else {
            out.key   = x.key;
            out.left  = x.left;
            out.right = x.right;
        }
    }

    private Node<Key, Value> max(Node<Key, Value> x) {
        if (x == null) return null;
        while (true)
            if (x.right == null) return x;
            else x = x.right;
    }

    private Node<Key, Value> min(Node<Key, Value> x) {
        if (x == null) return null;
        while (true)
            if (x.left == null) return x;
            else x = x.left;
    }

    private boolean isLessPriority(Node<Key, Value> x, Node<Key, Value> t) {
        return x.priority < t.priority;
    }

    private Node<Key, Value> getNode(Node<Key, Value> x, Key k) {
        while (x != null) {
            int cmp = k.compareTo(x.key);
            if      (cmp > 0) x = x.right;
            else if (cmp < 0) x = x.left;
            else              return x;
        }
        return null;
    }

    private boolean checkParentsNull(Node<Key, Value> x) {
        RecursiveTraverse.lvlorder(x, n -> {
            assert n.parent == null;
        });
        return true;
    }

    private void checkKeyNotNull(Key k) {
        if (k == null) throw new IllegalArgumentException("Null keys not allowed");
    }

    private void assertIsTreap(Node<Key, Value> x) {
        assert isBst(x);
        assert assertIsHeap(x);
    }

    private boolean assertIsHeap(Node<Key, Value> x) {
        if (x.right != null) {
            assert x.priority >= x.right.priority;
            assertIsHeap(x.right);
        }

        if (x.left != null) {
            assert x.priority >= x.left.priority;
            assertIsHeap(x.left);
        }

        return true;
    }

    private boolean checkIsLess(Node<Key, Value> t1, Node<Key, Value> t2) {
        if (t1 == null || t2 == null)
            return true;
        Node<Key, Value> max1 = max(t1);
        Node<Key, Value> min2 = min(t2);
        return max1.key.compareTo(min2.key) <= 0;
    }

    public static void main(String[] args) throws Exception {
        testSearch(new Treap<>(), 3_000_000, intGen());
        testInsert(new Treap<>(), 3_000_000, intGen());
        testDelete(new Treap<>(), 3_000_000, 0.3, intGen());
        testOfflineBuild(50_000);
    }

    private static void testOfflineBuild(int offlineSize) {
        Random rnd = new Random();
        Integer[] keys = new Integer[offlineSize];
        Integer[] vals = new Integer[offlineSize];

        int i = 0;
        int j = 0;
        while (i < offlineSize) {
            if (rnd.nextBoolean()) {
                keys[i] = j;
                vals[i] = i;
                i += 1;
            }
            j += 1;
        }

        Arrays.sort(keys);

        Treap<Integer, Integer> treap = new Treap<>(keys, vals);
        for (int k : keys)
            assert treap.contains(k) : k + " is absent";
    }

}
