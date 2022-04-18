package algs.adt.symboltable.impl.searchtree;

import algs.adt.queue.Queue;
import algs.adt.queue.impl.LinkedListQueue;

/**
 * Provide possibility to traverse tree in different ways.
 *
 * @author Serj Sintsov
 */
public class RecursiveTraverse {

    /**
     * Depth-first traversal. We visit parent node before going to its children.
     * From left to right.
     */
    public static <Key, Value, Node extends TreeNode<Key, Value, Node>> Iterable<Key> preorder(Node root) {
        Queue<Key> q = new LinkedListQueue<>();
        preorder(root, q);
        return q;
    }

    private static <Key, Value, Node extends TreeNode<Key, Value, Node>> void preorder(Node x, Queue<Key> q) {
        if (x == null) return;
        q.enqueue(x.key);
        preorder(x.left, q);
        preorder(x.right, q);
    }

    /**
     * Depth-first traversal. We visit left subtree, than visit its left most node and than we visit next subtree which
     * is considered to be right subtree.
     * From left to right.
     */
    public static <Key, Value, Node extends TreeNode<Key, Value, Node>> Iterable<Key> inorder(Node root) {
        Queue<Key> q = new LinkedListQueue<>();
        inorder(root, q);
        return q;
    }

    private static <Key, Value, Node extends TreeNode<Key, Value, Node>> void inorder(Node x, Queue<Key> q) {
        if (x == null) return;
        inorder(x.left, q);
        q.enqueue(x.key);
        inorder(x.right, q);
    }

    /**
     * Depth-first traversal. We visit all children before visiting its parent.
     * From left to right.
     */
    public static <Key, Value, Node extends TreeNode<Key, Value, Node>> Iterable<Key> postorder(Node root) {
        Queue<Key> q = new LinkedListQueue<>();
        postorder(root, q);
        return q;
    }

    private static <Key, Value, Node extends TreeNode<Key, Value, Node>> void postorder(Node x, Queue<Key> q) {
        if (x == null) return;
        postorder(x.left, q);
        postorder(x.right, q);
        q.enqueue(x.key);
    }

    /**
     * Breadth-first traversal. We visit every node on a level before going to a lower level.
     * From left to right.
     */
    public static <Key, Value, Node extends TreeNode<Key, Value, Node>> Iterable<Key> lvlorder(Node root) {
        Queue<Key> visited = new LinkedListQueue<>();
        lvlorder(root, n -> visited.enqueue(n.key));
        return visited;
    }

    /**
     * Breadth-first traversal. We visit every node on a level before going to a lower level.
     * From left to right.
     */
    public static <Key, Value, Node extends TreeNode<Key, Value, Node>> void
    lvlorder(Node root, TraverseVisitor<Key, Value, Node> visitor)
    {
        if (root == null) return;

        Queue<Node> toVisit = new LinkedListQueue<>();
        toVisit.enqueue(root);

        while (!toVisit.isEmpty()) {
            Node x = toVisit.dequeue();
            visitor.visit(x);

            if (x.left  != null) toVisit.enqueue(x.left);
            if (x.right != null) toVisit.enqueue(x.right);
        }
    }

}
