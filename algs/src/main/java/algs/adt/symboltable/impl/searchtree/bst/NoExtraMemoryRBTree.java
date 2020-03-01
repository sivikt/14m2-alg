package algs.adt.symboltable.impl.searchtree.bst;

import algs.adt.symboltable.impl.searchtree.RecursiveTraverse;
import algs.adt.symboltable.impl.searchtree.TreeNode;
import algs.adt.symboltable.impl.searchtree.TreePrinter;
import algs.adt.symboltable.searchtree.BinarySearchTree;

import java.io.IOException;
import java.io.StringWriter;

import static algs.adt.symboltable.impl.SymbolTableTest.intGen;
import static algs.adt.symboltable.impl.SymbolTableTest.testDelete;
import static algs.adt.symboltable.impl.SymbolTableTest.testInsert;
import static algs.adt.symboltable.impl.searchtree.bst.BstUtil.isBst;

/**
 * """Red-black BST with no extra memory.
 *    Describe how to save the memory for storing the color information when implementing a red-black BST.
 *
 *    Hint: modify the structure of the BST to encode the color information.
 * """ (from materials by Robert Sedgewick)
 *
 * Bottom-up implementation.
 * Supports only unique keys.
 * Does not thread-safe.
 *
 * @author Serj Sintsov
 */
public class NoExtraMemoryRBTree<Key extends Comparable<? super Key>, Value>
        implements BinarySearchTree<Key, Value>
{

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

    private static abstract class Node<Key, Value> extends TreeNode<Key, Value, Node<Key, Value>> {

        public Node(Key key, Value value) {
            super(key, value);
        }

        /**
         * Maintains symmetric order and perfect black balance.
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
         * Maintains symmetric order and perfect black balance.
         */
        public Node<Key, Value> rotateRight() {
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
        public Node<Key, Value> flipRedColors(Node<Key, Value> parent, boolean isLeft) {
            assert  isRed(left);
            assert  isRed(right);
            left.colorBlack(this, true);
            right.colorBlack(this, false);
            return colorRed(parent, isLeft);
        }

        public Node<Key, Value> colorTo(boolean isRed, Node<Key, Value> parent, boolean isLeft) {
            if (isRed) return colorRed(parent, isLeft);
            else       return colorBlack(parent, isLeft);
        }

        public Node<Key, Value> colorRed(Node<Key, Value> parent, boolean isLeft) {
            if (getClass().equals(RedNode.class)) return this;
            Node<Key, Value> red = new RedNode<>(key, value);
            red.left  = left;
            red.right = right;
            setChildSafely(parent, red, isLeft);
            return red;
        }

        public Node<Key, Value> colorBlack() {
            return colorBlack(null, false);
        }

        public Node<Key, Value> colorBlack(Node<Key, Value> parent, boolean isLeft) {
            if (getClass().equals(BlackNode.class)) return this;
            Node<Key, Value> blk = new BlackNode<>(key, value);
            blk.left  = left;
            blk.right = right;
            setChildSafely(parent, blk, isLeft);
            return blk;
        }

        public Node<Key, Value> child(boolean isLeft) {
            return isLeft ? left : right;
        }

        public void setChild(boolean isLeft, Node<Key, Value> child) {
            if (isLeft) left = child;
            else        right = child;
        }

        private void setChildSafely(Node<Key, Value> parent, Node<Key, Value> child, boolean isLeft) {
            if (parent != null) parent.setChild(isLeft, child);
        }
    }

    private static final class BlackNode<Key, Value> extends Node<Key, Value> {
        public BlackNode(Key key, Value value) {
            super(key, value);
        }

        @Override
        public String toString() {
            return String.format("[k=%s]B", key);
        }
    }

    private static final class RedNode<Key, Value> extends Node<Key, Value> {
        public RedNode(Key key, Value value) {
            super(key, value);
        }

        @Override
        public String toString() {
            return String.format("[k=%s]R", key);
        }
    }

    private Node<Key, Value> root;
    private int size;

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

    @Override
    public void put(Key key, Value val) {
        checkKeyNotNull(key);
        root = put(root, null, key, val, false);
        root = root.colorBlack();
        assertIsRbBst();
    }

    private Node<Key, Value> put(Node<Key, Value> x, Node<Key, Value> parent, Key k, Value v, boolean isLeft) {
        if (x == null) {
            size += 1;
            return new RedNode<>(k, v);
        }

        if (isRed(x.left) && isRed(x.right))
            x = x.flipRedColors(parent, isLeft);

        int cmp = k.compareTo(x.key);
        if      (cmp == 0) x.value = v;
        else if (cmp < 0)  x = putLeft(x, k, v,  isLeft);
        else               x = putRight(x, k, v, isLeft);

        return x;
    }

    private Node<Key, Value> putLeft(Node<Key, Value> x, Key k, Value v, boolean isLeft) {
        x.left = put(x.left, x, k, v, true);

        if (isRed(x) && isRed(x.left) && !isLeft)
            x = x.rotateRight();

        if (isRed(x.left) && isRed(x.left.left)) {
            x = x.rotateRight();
            x = x.colorBlack();
            x.right.colorRed(x, false);
        }

        return x;
    }

    /**
     * Mirrors {@link #putLeft(Node, Comparable, Object, boolean)}
     */
    private Node<Key, Value> putRight(Node<Key, Value> x, Key k, Value v, boolean isLeft) {
        x.right = put(x.right, x, k, v, false);

        if (isRed(x) && isRed(x.right) && isLeft)
            x = x.rotateLeft();

        if (isRed(x.right) && isRed(x.right.right)) {
            x = x.rotateLeft();
            x = x.colorBlack();
            x.left.colorRed(x, true);
        }

        return x;
    }

    @Override
    public void delete(Key k) {
        checkKeyNotNull(k);
        root = delete(root, null, k, false, BooleanFlag.flag());
        if (!isEmpty()) root = root.colorBlack();
        assertIsRbBst();
    }

    private Node<Key, Value>
    delete(Node<Key, Value> x, Node<Key, Value> parent, Key k, boolean isLeft, BooleanFlag doneFlag)
    {
        if (x == null) return null;

        int cmp = k.compareTo(x.key);
        if (cmp > 0) {
            x.right = delete(x.right, x, k, false, doneFlag);
            return fixDeletion(x, parent, isLeft, false, doneFlag);
        }
        else if (cmp < 0) {
            x.left  = delete(x.left, x, k, true, doneFlag);
            return fixDeletion(x, parent, isLeft, true, doneFlag);
        }
        else
            return replaceAndDelete(x, doneFlag);
    }

    private Node<Key, Value>
    replaceAndDelete(Node<Key, Value> x, BooleanFlag doneFlag)
    {
        if (x.left == null || x.right == null) {
            size -= 1;

            Node<Key, Value> replacement = x.child(x.left != null);

            if (isRed(x)) {
                doneFlag.on();
                return replacement;
            }
            else if (isRed(replacement)) {
                replacement.colorBlack();
                doneFlag.on();
                return replacement;
            }
            else return replacement;
        }
        else {
            Node<Key, Value> predecessor = max(x.left);
            x.key   = predecessor.key;
            x.value = predecessor.value;
            x.left  = delete(x.left, x, predecessor.key, true, doneFlag);
            return x;
        }
    }

    private Node<Key, Value>
    fixDeletion(Node<Key, Value> x, Node<Key, Value> parent, boolean isLeftCld, boolean isLeftRm, BooleanFlag doneFlag)
    {
        if (doneFlag.isOn()) return x;

        Node<Key, Value> virginSibling = x.child(!isLeftRm);

        if (virginSibling != null && !isRed(virginSibling)) {
            if (!isRed(virginSibling.left) && !isRed(virginSibling.right)) {
                if (isRed(x))
                    doneFlag.on();

                x = x.colorBlack(parent, isLeftCld);
                virginSibling.colorRed(x, !isLeftRm);

                return x;
            }
            else {
                Node<Key, Value> outerChild = virginSibling.child(!isLeftRm);
                Node<Key, Value> oldParent  = x;

                if (isRed(outerChild)) {
                    x = x.rotateTo(isLeftRm);
                }
                else {
                    virginSibling = virginSibling.rotateTo(!isLeftRm);
                    x.setChild(!isLeftRm, virginSibling);
                    x = x.rotateTo(isLeftRm);
                }

                x = x.colorTo(isRed(oldParent), parent, isLeftCld);
                oldParent.colorBlack(x, isLeftRm);
                doneFlag.on();
                return x;
            }
        }
        else if (virginSibling != null) {
            Node<Key, Value> innerChild = virginSibling.child(isLeftRm);

            if (!isRed(innerChild.left) && !isRed(innerChild.right)) {
                x = x.rotateTo(isLeftRm);
                virginSibling = virginSibling.colorBlack(x, !isLeftRm);
                innerChild.colorRed(virginSibling, isLeftRm);

                doneFlag.on();
                return x;
            }
            else {
                if (isRed(innerChild.child(isLeftRm))) {
                    innerChild = innerChild.rotateTo(!isLeftRm);
                    innerChild.colorBlack(virginSibling, isLeftRm);
                }

                virginSibling = virginSibling.rotateTo(!isLeftRm);
                x.setChild(!isLeftRm, virginSibling);
                x = x.rotateTo(isLeftRm);

                doneFlag.on();
                return x;
            }
        }

        return x;
    }

    @Override
    public String toString() {
        try {
            StringWriter w = new StringWriter();
            TreePrinter.paddedText(root, w);
            return w.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Node<Key, Value> max(Node<Key, Value> x) {
        if (x == null) return null;
        while (true)
            if (x.right == null) return x;
            else x = x.right;
    }

    protected static <Key, Value> boolean isRed(Node<Key, Value> x) {
        // null links are black
        return x != null && x instanceof RedNode;
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

    /**
     * Testing
     */
    public static void main(String[] args) throws Exception {
        testInsert(new NoExtraMemoryRBTree<>(), 10_000, intGen());
        testDelete(new NoExtraMemoryRBTree<>(), 100_000, 0.4, intGen());
    }

}
