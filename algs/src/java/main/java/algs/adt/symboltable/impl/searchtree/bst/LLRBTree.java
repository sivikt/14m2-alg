package algs.adt.symboltable.impl.searchtree.bst;

import algs.adt.queue.Queue;
import algs.adt.queue.impl.LinkedListQueue;
import algs.adt.symboltable.impl.SymbolTableTest;
import algs.adt.symboltable.impl.searchtree.RecursiveTraverse;
import algs.adt.symboltable.impl.searchtree.TreeNode;
import algs.adt.symboltable.searchtree.BinarySearchTree;
import algs.adt.symboltable.searchtree.OrderedSearchTree;

import static algs.adt.symboltable.impl.SymbolTableTest.intGen;
import static algs.adt.symboltable.impl.searchtree.bst.BstUtil.isBst;

/**
 * Left-Leaning Red-Black Binary Search Tree (LLRB tree) implementation.
 * This data structure was suggested by R. Sedgewick in 2008.
 *
 * A left-leaning red–black (LLRB) tree is a type of self-balancing binary search tree. It is a variant of the red–black
 * tree and guarantees the same asymptotic complexity for operations, but is designed to be easier to implement.
 *
 *  	        Average     Worst case
 *    Space     O(n)        O(n)
 *    Search    O(log n)    O(log n)
 *    Insert    O(log n)    O(log n)
 *    Delete    O(log n)    O(log n)
 *
 * @see <a href="http://www.cs.princeton.edu/~rs/talks/LLRB/LLRB.pdf"></a>
 *
 * This implementation is based on 1-1 correspondence to 2-3 tree (differs from top-down 2-3-4 trees only in position of
 * one line of code).
 * LLRB 2-3 tree (represents 2-3 tree as BST) has next properties:
 *   1. no node has two red links connected to it
 *   2. red links lean left
 *   3. no path from the root to the bottom contains two consecutive red links
 *   4. every path from root to null links has the same number of black links
 *
 * These invariants imply that the length of every path in a red-black tree with N nodes is no longer than 2*lgN.
 *
 * So LLRB search, range search, range count, floor, ceiling and others read-only operation
 * the same as in {@link UnbalancedBinarySearchTree}. It uses "internal"
 * left-leaning links as "glue" for 3-node.
 *
 * Supports only unique keys.
 * Does not thread-safe.
 * TODO buggy (insertion and delete)
 *
 * @author Serj Sintsov
 */
public class LLRBTree<Key extends Comparable<? super Key>, Value> implements
        OrderedSearchTree<Key, Value>, BinarySearchTree<Key, Value>
{

    private static boolean RED   = true;
    private static boolean BLACK = false;

    private Node<Key, Value> root;

    private static final class Node<Key, Value> extends TreeNode<Key, Value, Node<Key, Value>> {
        public int count;
        public boolean color;

        public Node(Key key, Value value, boolean color) {
            super(key, value);
            this.color = color;
            this.count = 1;
        }

        public Node<Key, Value> rotateLeft() {
            //     P           C
            //    / \    ->   / \
            //   a   C       P   d
            //      / \     / \
            //     b   d   a   b
            Node<Key, Value> p = this;
            assert isRed(p.right);
            Node<Key, Value> c = p.right;
            p.right = c.left;
            c.left  = p;
            c.color = p.color;
            p.color = RED;
            p.swapCount(c);
            return c;
        }

        public Node<Key, Value> rotateRight() {
            //     P          C
            //    / \   ->   / \
            //   C   a      b   P
            //  / \            / \
            // b   d          d   a
            Node<Key, Value> p = this;
            assert isRed(p.left);
            Node<Key, Value> c = p.left;
            p.left  = c.right;
            c.right = c;
            c.color = p.color;
            p.color = RED;
            p.swapCount(c);
            return c;
        }

        /**
         * Split 4-node.
         */
        public void flipRedColors() {
            assert !isRed(this);
            assert  isRed(left);
            assert  isRed(right);
            left.color  = BLACK;
            right.color = BLACK;
            color = RED;
        }

        private void swapCount(Node<Key, Value> x) {
            int tmp = this.count;
            this.count = x.count;
            x.count = tmp;
        }

        public boolean color() {
            return color;
        }

        public void colorBlack() {
            color = BLACK;
        }
    }

    @Override
    public Key min() {
        Node<Key, Value> x = min(root);
        return x == null ? null : x.key;
    }

    private Node<Key, Value> min(Node<Key, Value> x) {
        if (x == null) return null;
        while (true)
            if (x.left == null) return x;
            else                x = x.left;
    }

    @Override
    public Key max() {
        Node<Key, Value> x = root;
        if (x == null) return null;
        while (true)
            if (x.right == null) return x.key;
            else                 x = x.right;
    }

    @Override
    public Key floor(Key key) {
        Node<Key, Value> x = floor(root, key);
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

    @Override
    public Key ceiling(Key key) {
        Node<Key, Value> x = ceiling(root, key);
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

    @Override
    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node<Key, Value> x, Key k) {
        if (x == null) return 0;

        int cmp = k.compareTo(x.key);

        if      (cmp > 0) return 1 + size(x.left) + rank(x.right, k);
        else if (cmp < 0) return rank(x.left, k);
        else              return size(x.left);
    }

    @Override
    public Key select(int r) {
        if (r < 0 || r >= size()) return null;
        Node<Key, Value> x = select(root, r);
        return x == null ? null : x.key;
    }

    private Node<Key, Value> select(Node<Key, Value> x, int r) {
        if (x == null) return null;

        int t = size(x.left);
        if      (r < t) return select(x.left, r);
        else if (r > t) return select(x.right, r-t-1);
        else            return x;
    }

    @Override
    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.colorBlack();
        assertIsLLRBBst();
    }

    private Node<Key, Value> put(Node<Key, Value> x, Key k, Value v) {
        if (x == null)
            return new Node<>(k, v, RED);

        int cmp = k.compareTo(x.key);
        if      (cmp > 0) x.right = put(x.right, k, v);
        else if (cmp < 0) x.left  = put(x.left, k, v);
        else              x.value = v;

        x = fixPutViolationsOnWayUp(x);
        x.count = 1 + size(x.left) + size(x.right);

        return x;
    }

    /**
     *  Fix right-leaning reds and eliminate 4-nodes (making 2-3 tree) on the way up.
     */
    private Node<Key, Value> fixPutViolationsOnWayUp(Node<Key, Value> x) {
        if (isRed(x.right) && !isRed(x.left))     x = x.rotateLeft();  // lean left
        if (isRed(x.left)  && isRed(x.left.left)) x = x.rotateRight(); // balance 4-node
        if (isRed(x.left)  && isRed(x.right))     x.flipRedColors();   // split 4-node
        return x;
    }

    @Override
    public void deleteMin() {
        root = deleteMin(root);
        if (!isEmpty()) root.colorBlack();
        assertIsLLRBBst();
    }

    private Node<Key, Value> deleteMin(Node<Key, Value> x) {
        if (x.left == null) return null;

        if (!isRed(x.left) && !isRed(x.left.left))
            x = moveRedLeft(x);

        x.left = deleteMin(x.left);
        x = fixPutViolationsOnWayUp(x);

        x.count = 1 + size(x.left) + size(x.right);

        return x;
    }

    private Node<Key, Value> moveRedLeft(Node<Key, Value> x) {
        x.flipRedColors();
        if (x.right != null && isRed(x.right.left)) {
            x.right = x.right.rotateRight();
            x.rotateLeft();
            x.flipRedColors();
        }
        return x;
    }

    @Override
    public void deleteMax() {
        root = deleteMax(root);
        if (!isEmpty()) root.colorBlack();
        assertIsLLRBBst();
    }

    private Node<Key, Value> deleteMax(Node<Key, Value> x) {
        if (isRed(x.left)) x = x.rotateRight();

        if (x.right == null) return null;

        if (!isRed(x.right) && !isRed(x.right.left))
            x = moveRedRight(x);

        x.right = deleteMax(x.right);
        x = fixPutViolationsOnWayUp(x);
        x.count = 1 + size(x.left) + size(x.right);

        return x;
    }

    private Node<Key, Value> moveRedRight(Node<Key, Value> x) {
        x.flipRedColors();
        if (x.left != null && isRed(x.left.left)) {
            x.rotateRight();
            x.flipRedColors();
        }
        return x;
    }

    @Override
    public void delete(Key key) {
        root = delete(root, key);
        if (!isEmpty()) root.colorBlack();
        assertIsLLRBBst();
    }

    /**
     * Delete strategy (works for 2-3 and 2-3-4 trees):
     *  - invariant: current node is not a 2-node
     *  - introduce 4-nodes if necessary
     *  - remove key from bottom
     *  - eliminate 4-nodes on the way up
     */
    private Node<Key, Value> delete(Node<Key, Value> x, Key key) {
        if (key.compareTo(x.key) < 0) {
            if (!isRed(x.left) && !isRed(x.left.left))
                x = moveRedLeft(x);

            x.left = delete(x.left, key);
        }
        else {
            if (isRed(x.left)) x = x.rotateRight();

            if (key.compareTo(x.key) == 0 && (x.right == null))
                return null;

            if (x.right != null && !isRed(x.right) && !isRed(x.right.left))
                x = moveRedRight(x);

            if (key.compareTo(x.key) == 0) {
                x.value = getNode(x.right, min(x.right).key).value;
                x.key   = min(x.right).key;
                x.right = deleteMin(x.right);
            }
            else
                x.right = delete(x.right, key);
        }

        x = fixPutViolationsOnWayUp(x);
        x.count = 1 + size(x.left) + size(x.right);

        return x;
    }

    /**
     * Cost is (1 + depth_of_node) if balanced
     */
    @Override
    public Value get(Key key) {
        Node<Key, Value> x = getNode(root, key);
        return x == null ? null : x.value;
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

    @Override
    public boolean contains(Key key) {
        return getNode(root, key) != null;
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

    protected void assertIsLLRBBst() {
        assert isBst(root);
        assert !isRed(root);
        assert noRedViolation(root);
        assert noBlackViolation(root);
    }

    private boolean noRedViolation(Node<Key, Value> x) {
        if (x == null)
            return true;
        else if (isRed(x) && (isRed(x.left) || isRed(x.right)))
            return false;
        else if (isRed(x.right))
            return false;

        return noRedViolation(x.left) && noRedViolation(x.right);
    }

    private boolean noBlackViolation(Node<Key, Value> x) {
        return x == null || countBlackHeight(x) > 0;
    }

    private int countBlackHeight(Node<Key, Value> x) {
        if (x == null) return 1;

        int lh = countBlackHeight(x.left);
        int rh = countBlackHeight(x.right);

        if (lh == 0) return 0;
        if (rh == 0) return 0;

        if (lh != rh) return 0;

        if (!isRed(x)) return lh + 1;
        else           return lh;
    }

    private static <Key, Value> boolean isRed(Node<Key, Value> x) {
        // null links are black
        return x != null && x.color() == RED;
    }

    public static void main(String[] args) throws Exception {
        SymbolTableTest.testInsert(new LLRBTree<>(), 10_000, intGen());

//        System.out.println("Test huge insertions/deletions");
//        LeftLeaningRedBlackSearchTree<Integer, Integer> bst2 = new LeftLeaningRedBlackSearchTree<>();
//        BstTest.testDelete(bst2, 30, 0.3, itemGen);
    }

}
