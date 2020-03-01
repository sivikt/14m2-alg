package algs.adt.symboltable.impl.searchtree.bst;

import algs.adt.symboltable.impl.searchtree.RecursiveTraverse;
import algs.adt.symboltable.impl.searchtree.TreeNode;

import java.util.Iterator;

/**
 * Groups some of the tasks related to binary search trees.
 *
 * @author Serj Sintsov
 */
public class BstUtil {

    /**
     * """Check if a binary tree is a BST. Given a binary tree where each Node contains a key, determine whether it is a
     *    binary search tree. Use extra space proportional to the height of the tree.
     *
     *    Hint: design a recursive function isBST(Node x, Key min, Key max) that determines whether x is the root of a
     *          binary search tree with all keys between min and max.
     * """ (from materials by Robert Sedgewick)
     */
    public static <Key extends Comparable<? super Key>, Value, Node extends TreeNode<Key, Value, Node>> boolean
    isBst(Node x)
    {
        return isBst(x, null, null);
    }

    private static <Key extends Comparable<? super Key>, Value, Node extends TreeNode<Key, Value, Node>> boolean
    isBst(Node x, Key min, Key max)
    {
        if (x == null) return true;
        if (min != null && x.key.compareTo(min) < 0) return false;
        if (max != null && x.key.compareTo(max) > 0) return false;

        return isBst(x.left, min, x.key) && isBst(x.right, x.key, max);
    }

    @SuppressWarnings("ConstantConditions")
    public static <Key extends Comparable<? super Key>, Value, Node extends TreeNode<Key, Value, Node>> boolean
    isBstUsingInorderTraversal(Node x)
    {
        // or optimize it to do implicit in-order traversal with storing prev node and comparing
        // it with the next one
        Iterator<Key> it = RecursiveTraverse.inorder(x).iterator();

        Key g = it.hasNext() ? it.next() : null;
        while (it.hasNext()) {
            Key h = it.next();
            if (g.compareTo(h) > 0)
                return false;
            g = h;
        }

        return true;
    }

    /**
     * """Inorder traversal with constant extra space. Design an algorithm to perform an inorder traversal of a binary
     *    search tree using only a constant amount of extra space.
     *
     *    Hint: you may modify the BST during the traversal provided you restore it upon completion.
     * """ (from materials by Robert Sedgewick)
     */
    public static void traversalInorderWithConstantSpace() {
        // TODO implement
    }

    @SuppressWarnings("ConstantConditions")
    public static void main(String[] args) {
        Node<Integer, String> root = new Node<>(30, "a");
        root.left  = new Node<>(10, "b");
        root.right = new Node<>(55, "c");

        root.left.left   = new Node<>(6, "d");
        root.left.right  = new Node<>(20, "e");

        root.right.left  = new Node<>(31, "f");
        root.right.right = new Node<>(60, "g");

        root.left.left.left  = new Node<>(0, "h");
        root.left.left.right = new Node<>(9, "i");

        root.left.right.left  = new Node<>(15, "j");
        root.left.right.right = new Node<>(25, "k");

        assert isBst(root);
        assert isBstUsingInorderTraversal(root);

        assert isBst((Node<Integer, String>) null);
        assert isBstUsingInorderTraversal((Node<Integer, String>) null);

        root.right.right.key = 29;
        assert !isBst(root);
        assert !isBstUsingInorderTraversal(root);
    }

    private static final class Node<Key, Value> extends TreeNode<Key, Value, Node<Key, Value>> {
        public Node(Key key, Value value) {
            super(key, value);
        }
    }

}
