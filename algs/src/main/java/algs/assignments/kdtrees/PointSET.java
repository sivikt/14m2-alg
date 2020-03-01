package algs.assignments.kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;

/**
 * """Task.
 *    Write a data type to represent a set of points in the unit square (all points have x- and y-coordinates between 0
 *    and 1) using a 2d-tree to support efficient range search (find all of the points contained in a query rectangle)
 *    and nearest neighbor search (find a closest point to a query point). 2d-trees have numerous applications, ranging
 *    from classifying astronomical objects to computer animation to speeding up neural networks to mining data to image
 *    retrieval.
 *
 *    Brute-force implementation. Write a mutable data type PointSET that represents a set of points in the unit square.
 *    Implement the following API by using a red-black BST.
 *
 *    Performance requirements.  Your implementation should support insert() and contains() in time proportional to the
 *    logarithm of the number of points in the set in the worst case; it should support nearest() and range() in time
 *    proportional to the number of points in the set.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * Use own implementation of RB tree.
 *
 * @author Serj Sintsov
 */
public class PointSET {

    private static final boolean RED   = true;
    private static final boolean BLACK = false;

    private Node root;
    private int size;

    private static final class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private boolean color;

        public Node(Point2D p, boolean color) {
            this.point = p;
            this.color = color;
        }

        /**
         * Maintains symmetric order and perfect black balance.
         */
        public Node rotateLeft() {
            Node p = this;
            assert p.right != null;
            Node c = p.right;
            p.right = c.left;
            c.left  = p;
            return c;
        }

        /**
         * Maintains symmetric order and perfect black balance.
         */
        public Node rotateRight() {
            Node p = this;
            assert p.left != null;
            Node c = p.left;
            p.left  = c.right;
            c.right = p;
            return c;
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

        public void setRed() {
            color = RED;
        }

        public void setBlk() {
            color = BLACK;
        }
    }

    private interface TraversalVisitor<Item> {
        void visit(Item item);
    }

    private static class Pair<A, B> {
        private A a;
        private B b;

        public A getA() {
            return a;
        }

        public void setA(A value) {
            this.a = value;
        }

        public B getB() {
            return b;
        }

        public void setB(B value) {
            this.b = value;
        }
    }

    /**
     * Add the point to the set (if it is not already in the set).
     *
     * When adding new node:
     *   - property 3 (all leaves are black) always holds.
     *   - property 4 (both children of every red node are black) is threatened only by adding a red node, repainting a
     *     black node red, or a rotation.
     *   - property 5 (all paths from any given node to its leaf nodes contain the same number of black nodes) is
     *     threatened only by adding a black node, repainting a red node black (or vice versa), or a rotation.
     */
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("p must be not null");
        root = insert(root, p, true); // assume root is right link of virtual node
        root.setBlk(); // satisfy rule 2
    }

    /**
     * Recursive top-down implementation.
     * @param x node which will be ancestor of new point
     * @param p point to add
     * @param rightSubtree indicates that we came to node x follow right link (x is in right subtree)
     */
    private Node insert(Node x, Point2D p, boolean rightSubtree) {
        if (x == null) {
            size += 1;
            return new Node(p, RED);
        }

        int cmp = p.compareTo(x.point);

        if (isRed(x.left) && isRed(x.right))
            x.flipRedColors();

        if      (cmp == 0) return x;
        else if (cmp < 0)  x = insertL(x, p, rightSubtree);
        else               x = insertR(x, p, rightSubtree);

        return x;
    }

    private Node insertL(Node x, Point2D p, boolean rightSubtree) {
        x.left = insert(x.left, p, false);

        if (isRed(x) && isRed(x.left) && rightSubtree)
            x = x.rotateRight();

        if (isRed(x.left) && isRed(x.left.left)) {
            x = x.rotateRight();
            x.setBlk();
            x.right.setRed();
        }

        return x;
    }

    /**
     * Mirrors {@link #insertL(Node, Point2D, boolean)}
     */
    private Node insertR(Node x, Point2D p, boolean rightSubtree) {
        x.right = insert(x.right, p, true);

        if (isRed(x) && isRed(x.right) && !rightSubtree)
            x = x.rotateLeft();

        if (isRed(x.right) && isRed(x.right.right)) {
            x = x.rotateLeft();
            x.setBlk();
            x.left.setRed();
        }

        return x;
    }

    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * number of points in the set
     */
    public int size() {
        return size;
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("p must be not null");
        return get(root, p) != null;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        inorder(root, item -> item.point.draw());
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(final RectHV rect) {
        if (rect == null) throw new NullPointerException("rect must be not null");

        final Queue<Point2D> inRect = new Queue<>();

        inorder(root, item -> {
            if (rect.contains(item.point)) inRect.enqueue(item.point);
        });

        return inRect;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(final Point2D p) {
        if (p == null) throw new NullPointerException("p must be not null");

        if (isEmpty()) return null;

        final Pair<Point2D, Double> nearest = new Pair<>();

        inorder(root, item -> {
            if (nearest.getA() == null) {
                nearest.setA(item.point);
                nearest.setB(p.distanceSquaredTo(item.point));
            } else {
                double dist = p.distanceSquaredTo(item.point);
                if (Double.compare(nearest.getB(), dist) > 0) {
                    nearest.setA(item.point);
                    nearest.setB(dist);
                }
            }
        });

        return nearest.getA();
    }

    private Node get(Node x, Point2D k) {
        while (x != null) {
            int cmp = k.compareTo(x.point);
            if      (cmp > 0) x = x.right;
            else if (cmp < 0) x = x.left;
            else              return x;
        }
        return null;
    }

    private void inorder(Node x, TraversalVisitor<Node> visitor) {
        if (x == null) return;
        inorder(x.left, visitor);
        visitor.visit(x);
        inorder(x.right, visitor);
    }

    @SuppressWarnings("PointlessBooleanExpression")
    private static boolean isRed(Node x) {
        // null links are black
        return x != null && x.color == RED;
    }

    public static void main(String[] args) {
        PointSET ps = new PointSET();

        ps.insert(new Point2D(0.5, 0.5));
        ps.insert(new Point2D(0.5, 0.6));
        ps.insert(new Point2D(0.4, 0.6));

        assert ps.contains(new Point2D(0.5, 0.5));
        assert ps.contains(new Point2D(0.5, 0.6));
        assert ps.contains(new Point2D(0.4, 0.6));

        PointSET ps2 = new PointSET();
        ps2.insert(new Point2D(0.5, 0.3));
        ps2.insert(new Point2D(0.5, 0.7));
        ps2.insert(new Point2D(0.7, 0.5));

        ps2.nearest(new Point2D(0.7, 0.7));
        ps2.draw();
    }

}
