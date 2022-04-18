package algs.adt.symboltable.impl.searchtree.bst;

import algs.adt.symboltable.impl.searchtree.RecursiveTraverse;
import algs.adt.symboltable.impl.searchtree.TreeNode;
import algs.adt.symboltable.searchtree.BinarySearchTree;

import static algs.adt.symboltable.impl.searchtree.bst.BstUtil.isBst;

/**
 * """A red–black tree is a data structure which is a type of self-balancing binary search tree. Balance is preserved
 *    by painting each node of the tree with one of two colors (typically called 'red' and 'black') in a way that
 *    satisfies certain properties, which collectively constrain how unbalanced the tree can become in the worst case.
 *    When the tree is modified, the new tree is subsequently rearranged and repainted to restore the coloring
 *    properties. The properties are designed in such a way that this rearranging and recoloring can be performed
 *    efficiently.
 *    The balancing of the tree is not perfect but it is good enough to allow it to guarantee searching in O(log n)
 *    time, where n is the total number of elements in the tree. The insertion and deletion operations, along with the
 *    tree rearrangement and recoloring, are also performed in O(log n) time.
 *    Tracking the color of each node requires only 1 bit of information per node because there are only two colors.
 *    The tree does not contain any other data specific to its being a red–black tree so its memory footprint is almost
 *    identical to a classic (uncolored) binary search tree. In many cases the additional bit of information can be
 *    stored at no additional memory cost.
 *
 *    In addition to the requirements imposed on a binary search tree the following must be satisfied by a red–black
 *    tree:
 *      1. Every node is either red or black.
 *      2. The root is always black.
 *      3. If a node is red, its children must be black (although the converse isn’t necessarily true).
 *      4. Every path from the root to a leaf, or to a null child, must contain the same number of black nodes
 *
 * """ wikipedia
 *
 *         Average   Worst case
 * Space   O(n)      O(n)
 * Search  O(log n)  O(log n)
 * Insert  O(log n)  O(log n)
 * Delete  O(log n)  O(log n)
 *
 * Red-black trees (Guibas-Sedgewick, 1978)
 *  - reduce code complexity
 *  - minimize or eliminate space overhead
 *  - unify balanced tree algorithms
 *  - single top-down pass (for concurrent algorithms)
 *  - find version amenable to average-case analysis
 *
 * @author Serj Sintsov
 */
public abstract class RBTree<Key extends Comparable<? super Key>, Value>
        implements BinarySearchTree<Key, Value>
{

    protected static boolean RED   = true;
    protected static boolean BLACK = false;

    protected Node<Key, Value> root;
    protected int size;

    protected static final class Node<Key, Value> extends TreeNode<Key, Value, Node<Key, Value>> {
        private boolean color;

        public Node(Key key, Value value, boolean color) {
            super(key, value);
            this.color = color;
        }

        /**
         * Maintains symmetric order and perfect black balance.
         */
        public Node<Key, Value> rotateLeft() {
            //     P           C
            //    / \    ->   / \
            //   a   C       P   d
            //      / \     / \
            //     b   d   a   b
            Node<Key, Value> p = this;
            assert p.right != null;
            Node<Key, Value> c = p.right;
            p.right = c.left;
            c.left  = p;
            return c;
        }

        /**
         * Maintains symmetric order and perfect black balance.
         */
        public Node<Key, Value> rotateRight() {
            //     P          C
            //    / \   ->   / \
            //   C   a      b   P
            //  / \            / \
            // b   d          d   a
            Node<Key, Value> p = this;
            assert p.left != null;
            Node<Key, Value> c = p.left;
            p.left  = c.right;
            c.right = p;
            return c;
        }

        public Node<Key, Value> rotateTo(boolean left) {
            return left ? rotateLeft() : rotateRight();
        }

        /**
         * Maintains symmetric order and perfect black balance.
         * Split 4-node.
         */
        public void flipRedColors() {
            assert  isRed(left);
            assert  isRed(right);
            left.color  = BLACK;
            right.color = BLACK;
            color = RED;
        }

        public void colorTo(boolean color) {
            this.color = color;
        }

        public void colorRed() {
            colorTo(RED);
        }

        public void colorBlack() {
            colorTo(BLACK);
        }

        public boolean color() {
            return color;
        }

        public Node<Key, Value> child(boolean isLeft) {
            return isLeft ? left : right;
        }

        public void setChild(boolean isLeft, Node<Key, Value> child) {
            if (isLeft) left = child;
            else        right = child;
        }
    }

    @Override
    public Value get(Key key) {
        checkKeyNotNull(key);
        Node<Key, Value> x = get(root, key);
        assertIsRbBst();
        return x == null ? null : x.value;
    }

    private Node<Key, Value> get(Node<Key, Value> x, Key k) {
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
        return (key != null) && (get(root, key) != null);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
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

    protected static <Key, Value> boolean isRed(Node<Key, Value> x) {
        // null links are black
        return x != null && x.color == RED;
    }

    protected void checkKeyNotNull(Key k) {
        if (k == null) throw new IllegalArgumentException("Null keys not allowed");
    }

    protected void assertIsRbBst() {
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

}
