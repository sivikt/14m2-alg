package algs.adt.symboltable.impl.searchtree.bst;

import algs.adt.symboltable.impl.searchtree.RecursiveTraverse;
import algs.adt.symboltable.impl.searchtree.TreeNode;
import algs.adt.symboltable.searchtree.BinarySearchTree;

import java.io.IOException;
import java.io.StringWriter;

import static algs.adt.symboltable.impl.searchtree.TreePrinter.paddedText;
import static algs.adt.symboltable.impl.SymbolTableTest.*;
import static algs.adt.symboltable.impl.searchtree.bst.BstUtil.isBst;

/**
 * """In computer science, an AVL tree (Georgy Adelson-Velsky and Landis' tree, named after the inventors) is a
 *    self-balancing binary search tree. It was the first such data structure to be invented. In an AVL tree, the
 *    heights of the two child subtrees of any node differ by at most one; if at any time they differ by more than one,
 *    rebalancing is done to restore this property. Lookup, insertion, and deletion all take O(log n) time in both the
 *    average and worst cases, where n is the number of nodes in the tree prior to the operation. Insertions and
 *    deletions may require the tree to be rebalanced by one or more tree rotations.
 *
 *    The AVL tree is named after its two Soviet inventors, Georgy Adelson-Velsky and E. M. Landis, who published it in
 *    their 1962 paper "An algorithm for the organization of information".
 *
 *    AVL trees are often compared with red-black trees because both support the same set of operations and take O(log n)
 *    time for the basic operations. For lookup-intensive applications, AVL trees are faster than red-black trees because
 *    they are more rigidly balanced.
 * """ wikipedia
 *
 * Supports only unique keys.
 * Does not thread-safe.
 *
 * @author Serj Sintsov
 */
public class AVLTree<Key extends Comparable<? super Key>, Value> implements BinarySearchTree<Key, Value> {

    private static class BooleanFlag {
        private boolean state;

        public static BooleanFlag flag() {
            BooleanFlag flag = new BooleanFlag();
            flag.off();
            return flag;
        }

        public void on() {
            state = true;
        }

        public void off() {
            state = false;
        }

        public boolean isOn() {
            return state;
        }
    }

    private static enum Balance {
        /**
         *    a.balance == BALANCED
         *   / \
         *  b   c
         */
        BALANCED,

        /**
         *    a.balance == R_OVER_HEIGHT
         *     \
         *      c
         */
        R_OVER_HEIGHT,

        /**
         *    a.balance == L_OVER_HEIGHT
         *   /
         *  b
         */
        L_OVER_HEIGHT
    }

    private static final class Node<Key, Value> extends TreeNode<Key, Value, Node<Key, Value>> {
        /**
         * Difference between height of left and right sub-trees:
         * -1 - tree has to be balanced;
         *  0 - left sub-tree will over height after insertion;
         * +1 - left sub-tree will have the same height after insertion.
         */
        private Balance balance;

        public Node(Key key, Value value) {
            super(key, value);
            balance = Balance.BALANCED;
        }

        /**
         * Single RR-rotation
         */
        public Node<Key, Value> rotateLeft() {
            Node<Key, Value> p = this;
            assert p.right != null;
            Node<Key, Value> c = p.right;
            p.right = c.left;
            c.left  = p;
            return c;
        }

        /**
         * Single LL-rotation
         */
        public Node<Key, Value> rotateRight() {
            Node<Key, Value> p = this;
            assert p.left != null;
            Node<Key, Value> c = p.left;
            p.left  = c.right;
            c.right = p;
            return c;
        }

        public boolean hasRightDisbalance() {
            return balance == Balance.R_OVER_HEIGHT;
        }

        public boolean hasLeftDisbalance() {
            return balance == Balance.L_OVER_HEIGHT;
        }

        public boolean isBalanced() {
            return balance == Balance.BALANCED;
        }

        public void balance() {
            balance = Balance.BALANCED;
        }

        public void disbalanceLeft() {
            balance = Balance.L_OVER_HEIGHT;
        }

        public void disbalanceRight() {
            balance = Balance.R_OVER_HEIGHT;
        }

        public void copyBalance(Node<Key, Value> x) {
            balance = x.balance;
        }

        @Override
        public String toString() {
            return "[" + key + "]" + balance;
        }
    }

    private Node<Key, Value> root;
    private int size;

    @Override
    public void put(Key key, Value val) {
        if (key == null)
            throw new IllegalArgumentException("Key is null");

        root = put(root, key, val, BooleanFlag.flag());
        assertIsAVLTree();
    }

    private Node<Key, Value> put(Node<Key, Value> x, Key k, Value v, BooleanFlag branchGrew) {
        if (x == null) {
            size += 1;
            branchGrew.on();
            return new Node<>(k, v);
        }

        int cmp = x.key.compareTo(k);
        if (cmp > 0) {
            x.left = put(x.left, k, v, branchGrew);
            x = putBalanceL(x, branchGrew);
        }
        else if (cmp < 0) {
            x.right = put(x.right, k, v, branchGrew);
            x = putBalanceR(x, branchGrew);
        }
        else
            x.value = v;

        return x;
    }

    private Node<Key, Value> putBalanceL(Node<Key, Value> x, BooleanFlag branchGrew) {
        if (!branchGrew.isOn())
            return x;

        if (x.hasRightDisbalance()) {
            x.balance();
            branchGrew.off();
        }
        else if (x.isBalanced()) {
            x.disbalanceLeft();
        }
        else {
            if (x.left.hasLeftDisbalance()) {
                x = x.rotateRight();
                x.right.balance();
            }
            else {
                x.left = x.left.rotateLeft();
                x = x.rotateRight();

                if (x.hasLeftDisbalance())
                    x.right.disbalanceRight();
                else
                    x.right.balance();

                if (x.hasRightDisbalance())
                    x.left.disbalanceLeft();
                else
                    x.left.balance();
            }

            x.balance();
            branchGrew.off();
        }

        return x;
    }

    private Node<Key, Value> putBalanceR(Node<Key, Value> x, BooleanFlag branchGrew) {
        if (!branchGrew.isOn())
            return x;

        if (x.hasLeftDisbalance()) {
            x.balance();
            branchGrew.off();
        }
        else if (x.isBalanced()) {
            x.disbalanceRight();
        }
        else {
            if (x.right.hasRightDisbalance()) {
                x = x.rotateLeft();
                x.left.balance();
            }
            else {
                x.right = x.right.rotateRight();
                x = x.rotateLeft();

                if (x.hasLeftDisbalance())
                    x.right.disbalanceRight();
                else
                    x.right.balance();

                if (x.hasRightDisbalance())
                    x.left.disbalanceLeft();
                else
                    x.left.balance();
            }

            x.balance();
            branchGrew.off();
        }

        return x;
    }

    @Override
    public Value get(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null");

        Node<Key, Value> x = get(root, key);
        return x != null ? x.value : null;
    }

    private Node<Key, Value> get(Node<Key, Value> x, Key k) {
        if (x == null)
            return null;

        int cmp = x.key.compareTo(k);
        if (cmp < 0)
            return get(x.right, k);
        else if (cmp > 0)
            return get(x.left, k);
        else
            return x;
    }

    private Node<Key, Value> deleteMin(Node<Key, Value> x, BooleanFlag branchDecreased) {
        if (x == null)
            return null;

        if (x.left == null) {
            branchDecreased.on();
            return x.right;
        }

        x.left = deleteMin(x.left, branchDecreased);
        x = deleteBalanceL(x, branchDecreased);

        return x;
    }

    @Override
    public void delete(Key key) {
        if (key == null)
            throw new IllegalArgumentException("Key is null");
        root = delete(root, key, BooleanFlag.flag());
        assertIsAVLTree();
    }

    private Node<Key, Value> delete(Node<Key, Value> x, Key key, BooleanFlag branchDecreased) {
        if (x == null)
            return null;

        int cmp = x.key.compareTo(key);
        if (cmp < 0) {
            x.right = delete(x.right, key, branchDecreased);
            x = deleteBalanceR(x, branchDecreased);
        }
        else if (cmp > 0) {
            x.left = delete(x.left, key, branchDecreased);
            x = deleteBalanceL(x, branchDecreased);
        }
        else {
            size -= 1;

            if (x.left == null) {
                x = x.right;
                branchDecreased.on();
            }
            else if (x.right == null) {
                x = x.left;
                branchDecreased.on();
            }
            else {
                // replace with low successor
                Node<Key, Value> t = x;
                x = min(t.right);
                x.copyBalance(t);
                x.right = deleteMin(t.right, branchDecreased);
                x.left  = t.left;
                x = deleteBalanceR(x, branchDecreased);
            }
        }

        return x;
    }

    private Node<Key, Value> min(Node<Key, Value> x) {
        if (x == null) return null;
        while (true)
            if (x.left == null) return x;
            else x = x.left;
    }

    private Node<Key, Value> deleteBalanceL(Node<Key, Value> x, BooleanFlag branchDecreased) {
        if (!branchDecreased.isOn())
            return x;

        if (x.hasLeftDisbalance()) {
            x.balance();
        }
        else if (x.isBalanced()) {
            x.disbalanceRight();
            branchDecreased.off();
        }
        else { // else has right disbalance
            if (x.left.hasLeftDisbalance()) {
                x.left = x.left.rotateRight();
                x = x.rotateLeft();

                if (x.hasLeftDisbalance())
                    x.right.disbalanceRight();
                else
                    x.right.balance();

                if (x.hasRightDisbalance())
                    x.left.disbalanceLeft();
                else
                    x.left.balance();

                x.balance();
            }
            else {
                x = x.rotateLeft();
                if (x.isBalanced()) {
                    x.disbalanceLeft();
                    branchDecreased.off();
                }
                else {
                    x.balance();
                    x.left.balance();
                }
            }
        }

        return x;
    }

    private Node<Key, Value> deleteBalanceR(Node<Key, Value> x, BooleanFlag branchDecreased) {
        if (!branchDecreased.isOn())
            return x;

        if (x.hasRightDisbalance()) {
            x.balance();
        }
        else if (x.isBalanced()) {
            x.disbalanceLeft();
            branchDecreased.off();
        }
        else { // else has left disbalance
            if (x.right.hasRightDisbalance()) {
                x.right = x.right.rotateLeft();
                x = x.rotateRight();

                if (x.hasLeftDisbalance())
                    x.right.disbalanceRight();
                else
                    x.right.balance();

                if (x.hasRightDisbalance())
                    x.left.disbalanceLeft();
                else
                    x.left.balance();

                x.balance();
            }
            else {
                x = x.rotateRight();
                if (x.isBalanced()) {
                    x.disbalanceRight();
                    branchDecreased.off();
                }
                else {
                    x.balance();
                    x.right.balance();
                }
            }
        }

        return x;
    }

    @Override
    public boolean contains(Key key) {
        return get(root, key) != null;
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

    private void assertIsAVLTree() {
        assert isBst(root);
        assert noBalanceViolation(root);
    }

    private boolean noBalanceViolation(Node<Key, Value> x) {
        assertHeight(x);
        return true;
    }

    private int assertHeight(Node<Key, Value> x) {
        if (x == null) return 0;
        int lh = 1 + assertHeight(x.left);
        int rh = 1 + assertHeight(x.right);
        assert Math.abs(lh - rh) <= 1;
        return Math.max(lh, rh);
    }

    /**
     * Testing
     */
    public static void main(String[] args) throws Exception {
        testSearch(new AVLTree<>(), 3_000_000, intGen());
        testInsert(new AVLTree<>(), 3_000_000, intGen());
        testDelete(new AVLTree<>(), 3_000_000, 0.3, intGen());
    }

}
