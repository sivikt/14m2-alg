package algs.adt.symboltable.impl.searchtree.bst;

import algs.adt.symboltable.impl.searchtree.RecursiveTraverse;
import algs.adt.symboltable.impl.searchtree.TreeNode;
import algs.adt.symboltable.searchtree.BinarySearchTree;
import algs.adt.symboltable.searchtree.Mergable;

import java.io.IOException;
import java.io.StringWriter;

import static algs.adt.symboltable.impl.searchtree.TreePrinter.paddedText;
import static algs.adt.symboltable.impl.SymbolTableTest.*;
import static algs.adt.symboltable.impl.searchtree.bst.BstUtil.isBst;
import static algs.adt.symboltable.impl.searchtree.bst.SplayTree.Direction.left;
import static algs.adt.symboltable.impl.searchtree.bst.SplayTree.Direction.right;
import static algs.adt.symboltable.impl.searchtree.bst.SplayTree.OrderRelation.*;

/**
 * """ A splay tree is a self-adjusting binary search tree with the additional property that recently accessed elements
 *     are quick to access again. It performs basic operations such as insertion, look-up and removal in O(log n) amortized
 *     time. For many sequences of non-random operations, splay trees perform better than other search trees, even when
 *     the specific pattern of the sequence is unknown. The splay tree was invented by Daniel Dominic Sleator and Robert
 *     Endre Tarjan in 1985.
 *
 *     All normal operations on a binary search tree are combined with one basic operation, called splaying. Splaying the
 *     tree for a certain element rearranges the tree so that the element is placed at the root of the tree. One way to do
 *     this is to first perform a standard binary tree search for the element in question, and then use tree rotations in
 *     a specific fashion to bring the element to the top. Alternatively, a top-down algorithm can combine the search and
 *     the tree reorganization into a single phase.
 *
 *     Good performance for a splay tree depends on the fact that it is self-optimizing, in that frequently accessed
 *     nodes will move nearer to the root where they can be accessed more quickly. The worst-case height—though
 *     unlikely—is O(n), with the average being O(log n). Having frequently used nodes near the root is an advantage for
 *     nearly all practical applications, and is particularly useful for implementing caches and garbage collection algorithms.
 *
 *     Amortized cost of any zig-zig or zig-zag operation is bounded by:
 *         amortized cost = cost + P(tf) - P(ti) ≤ 3(rankf(x) - ranki(x))
 *
 * """ wikipedia
 *
 * Supports only unique keys.
 * Does not thread-safe.
 *
 * @author Serj Sintsov
 */
public class SplayTree<Key extends Comparable<? super Key>, Value> implements BinarySearchTree<Key,Value>,
        Mergable<SplayTree<Key, Value>> {

    static enum Direction {
        left, right
    }

    static enum OrderRelation {
        less, greater, equal, unknown
    }

    private static final class Node<Key extends Comparable<? super Key>, Value> extends
            TreeNode<Key, Value, Node<Key, Value>>
    {
        public Node(Key key, Value value) {
            super(key, value);
        }

        public Node<Key, Value> rotateLeft() {
            Node<Key, Value> p = this;
            assert p.right != null;
            Node<Key, Value> c = p.right;
            p.right = c.left;
            c.left  = p;
            return c;
        }

        public Node<Key, Value> rotateRight() {
            Node<Key, Value> p = this;
            assert p.left != null;
            Node<Key, Value> c = p.left;
            p.left  = c.right;
            c.right = p;
            return c;
        }

        public Node<Key, Value> rotateTo(Direction dir) {
            if (dir == Direction.right && left != null)
                return rotateRight();
            else if (dir == Direction.left && right != null)
                return rotateLeft();
            return this;
        }

        public OrderRelation cmpChild(Key k, Direction dir) {
            assert dir != null;

            int cmp;
            if (dir == Direction.right) {
                if (right == null)
                    return unknown;
                cmp = right.key.compareTo(k);
            }
            else {
                if (left == null)
                    return unknown;
                cmp = left.key.compareTo(k);
            }

            return (cmp > 0) ? greater : (cmp < 0 ? less : equal);
        }

        @Override
        public String toString() {
            return "[" + key + "]";
        }
    }

    private Node<Key, Value> root;
    private int size;

    @Override
    public void put(Key key, Value val) {
        checkKeyNotNull(key);

        Node<Key, Value> newNode = new Node<>(key, val);

        if (isEmpty()) {
            root = newNode;
            size += 1;
            return;
        }

        root = splay(root, key);

        int cmp = root.key.compareTo(key);

        if (cmp == 0) { // replace existing key value
            root.value = val;
            assert isBst(root);
            return;
        }

        size += 1;
        if (cmp > 0) {
            newNode.left  = root.left;
            newNode.right = root;
            root.left = null;
            root = newNode;
        }
        else {
            newNode.right = root.right;
            newNode.left  = root;
            root.right = null;
            root = newNode;
        }

        assert isBst(root);
    }

    @Override
    public Value get(Key key) {
        checkKeyNotNull(key);
        Node<Key, Value> x = getNode(key);
        return x != null ? x.value : null;
    }

    @Override
    public void delete(Key key) {
        checkKeyNotNull(key);

        if (isEmpty())
            return;

        root = splay(root, key);
        int cmp = root.key.compareTo(key);

        if (cmp == 0) {
            size -= 1;

            if (root.right != null) {
                root.right = splay(root.right, key);
                root.right.left = root.left;
                root = root.right;
            }
            else if (root.left != null) {
                root.left = splay(root.left, key);
                root.left.right = root.right;
                root = root.left;
            }
        }

        assert isBst(root);
    }

    /**
     * Mutable approach. New splay tree will use old sub-trees which can be modified separately outside. But it mainly
     * to show the idea of merging.
     * Also consider keys from {@code this} tree are less to keys from {@code that} tree.
     */
    @Override
    public SplayTree<Key, Value> merge(SplayTree<Key, Value> that) {
        assert checkIsLess(this.root, that.root) : "that splay tree is not greater than this";

        if (this.isEmpty())
            return that;
        else if (that.isEmpty())
            return this;

        this.root = this.splay(root, that.root.key);
        this.root.right = that.root;

        return this;
    }

    @Override
    public boolean contains(Key key) {
        return getNode(key) != null;
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

    @Override
    public String toString() {
        try {
            StringWriter w = new StringWriter();
            w.write("size=" + size + "\n");
            paddedText(root, w);
            return w.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Node<Key, Value> getNode(Key key) {
        if (isEmpty())
            return null;

        root = splay(root, key);
        assert isBst(root);

        if (root.key.compareTo(key) == 0)
            return root;
        else
            return null;
    }

    /**
     * Recursive implementation. Consider using iterative implementation for production, since Splay Tree
     * does not guarantee to have logarithmic height and splay will fail with stack overflow error.
     */
    private Node<Key, Value> splay(Node<Key, Value> x, Key key) {
        if (x == null)
            return null;

        int cmp = x.key.compareTo(key);
        if (cmp == 0)
            return x;

        Direction dir = (cmp < 0) ? right : left;
        OrderRelation xOrder = (cmp < 0) ? less : greater;
        OrderRelation childOrder = x.cmpChild(key, dir);

        if (childOrder == equal)
            return zig(x, dir);

        if (childOrder == xOrder)
            return zigzig(x, key, dir);

        if (childOrder != unknown)
            return zigzag(x, key, dir);

        return x;
    }

    private Node<Key, Value> zig(Node<Key, Value> x, Direction dir) {
        assert dir != null;
        if (dir == Direction.right)
            return x.rotateTo(left);
        else
            return x.rotateTo(right);
    }

    private Node<Key, Value> zigzig(Node<Key, Value> x, Key key, Direction dir) {
        assert dir != null;

        if (dir == Direction.right) {
            x.right.right = splay(x.right.right, key);
            x = x.rotateTo(left);
            x = x.rotateTo(left);
        }
        else {
            x.left.left = splay(x.left.left, key);
            x = x.rotateTo(right);
            x = x.rotateTo(right);
        }

        return x;
    }

    private Node<Key, Value> zigzag(Node<Key, Value> x, Key key, Direction dir) {
        assert dir != null;

        if (dir == Direction.right) {
            x.right.left = splay(x.right.left, key);
            x.right = x.right.rotateTo(right);
            x = x.rotateTo(left);
        }
        else {
            x.left.right = splay(x.left.right, key);
            x.left = x.left.rotateTo(left);
            x = x.rotateTo(right);
        }

        return x;
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

    private void checkKeyNotNull(Key k) {
        if (k == null) throw new IllegalArgumentException("Null keys not allowed");
    }

    private boolean checkIsLess(Node<Key, Value> t1, Node<Key, Value> t2) {
        if (t1 == null || t2 == null)
            return true;
        Node<Key, Value> max1 = max(t1);
        Node<Key, Value> min2 = min(t2);
        return max1.key.compareTo(min2.key) <= 0;
    }


    // testing
    public static void main(String[] args) throws Exception {
        testSearch(new SplayTree<>(), 3_000_000, intGen());
        testInsert(new SplayTree<>(), 3_000_000, intGen());
        testDelete(new SplayTree<>(), 3_000_000, 0.3, intGen());
    }

}
