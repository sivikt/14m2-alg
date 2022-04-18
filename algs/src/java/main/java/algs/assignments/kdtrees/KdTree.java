package algs.assignments.kdtrees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.util.Comparator;

/**
 * """2d-tree implementation.
 *    Write a mutable data type KdTree.java that uses a 2d-tree to implement the same API (but replace PointSET with
 *    KdTree). A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is to build a BST with points
 *    in the nodes, using the x- and y-coordinates of the points as keys in strictly alternating sequence.
 *
 *    Search and insert.
 *    The algorithms for search and insert are similar to those for BSTs, but at the root we use the x-coordinate (if
 *    the point to be inserted has a smaller x-coordinate than the point at the root, go left; otherwise go right);
 *    then at the next level, we use the y-coordinate (if the point to be inserted has a smaller y-coordinate than the
 *    point in the node, go left; otherwise go right); then at the next level the x-coordinate, and so forth.
 *
 *    Draw.
 *    A 2d-tree divides the unit square in a simple way: all the points to the left of the root go in the left subtree;
 *    all those to the right go in the right subtree; and so forth, recursively. Your draw() method should draw all of
 *    the points to standard draw in black and the subdivisions in red (for vertical splits) and blue (for horizontal
 *    splits). This method need not be efficient—it is primarily for debugging.
 *
 *    Range search.
 *    To find all points contained in a given query rectangle, start at the root and recursively search for points in
 *    both subtrees using the following pruning rule: if the query rectangle does not intersect the rectangle
 *    corresponding to a node, there is no need to explore that node (or its subtrees). A subtree is searched only if
 *    it might contain a point contained in the query rectangle.
 *
 *    Nearest neighbor search.
 *    To find a closest point to a given query point, start at the root and recursively search in both subtrees using
 *    the following pruning rule: if the closest point discovered so far is closer than the distance between the query
 *    point and the rectangle corresponding to a node, there is no need to explore that node (or its subtrees). That is,
 *    a node is searched only if it might contain a point that is closer than the best one found so far. The
 *    effectiveness of the pruning rule depends on quickly finding a nearby point. To do this, organize your recursive
 *    method so that when there are two possible subtrees to go down, you always choose the subtree that is on the same
 *    side of the splitting line as the query point as the first subtree to explore—the closest point found while
 *    exploring the first subtree may enable pruning of the second subtree.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class KdTree {

    private static final double MIN_XY = 0;
    private static final double MAX_XY = 1;
    private static final RectHV BIGGEST_RECT = new RectHV(MIN_XY, MIN_XY, MAX_XY, MAX_XY);

    private static final LevelComparator VERTICAL_CMP   = new XComparator();
    private static final LevelComparator HORIZONTAL_CMP = new YComparator();
    private static final LevelComparator ROOT_LVL_CMP   = VERTICAL_CMP;

    static {
        VERTICAL_CMP.setOpposite(HORIZONTAL_CMP);
        HORIZONTAL_CMP.setOpposite(VERTICAL_CMP);
    }

    private Node root;
    private int size;

    private static class Pair<A, B> {
        private A a;
        private B b;

        public Pair(A aVal, B bVal) {
            this.a = aVal;
            this.b = bVal;
        }

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

    private abstract static class LevelComparator implements Comparator<Point2D> {
        private LevelComparator opposite;

        public LevelComparator flip() {
            return opposite;
        }

        public void setOpposite(LevelComparator opp) {
            this.opposite = opp;
        }

        public abstract boolean isVertical();
    }

    private static class XComparator extends LevelComparator {
        @Override
        public int compare(Point2D p1, Point2D p2) {
            int cmp = Double.compare(p1.x(), p2.x());
            if (cmp != 0) return cmp;
            else          return +1; // treat p1 as greater
        }

        @Override
        public boolean isVertical() {
            return true;
        }
    }

    private static class YComparator extends LevelComparator {
        @Override
        public int compare(Point2D p1, Point2D p2) {
            int cmp = Double.compare(p1.y(), p2.y());
            if (cmp != 0) return cmp;
            else          return +1; // treat p1 as greater
        }

        @Override
        public boolean isVertical() {
            return false;
        }
    }

    private static final class Node {
        private Point2D point;
        private RectHV  rect;
        private Node left;
        private Node right;

        public Node(Point2D p, RectHV r) {
            this.point = p;
            this.rect  = r;
            assert r.contains(p);
        }

        public RectHV halfyRect(Point2D p, boolean vertical) {
            if (vertical) {
                if (lessOrEq(point.y(), p.y()))
                    // top half
                    return new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
                else
                    // bottom half
                    return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
            }
            else {
                if (lessOrEq(point.x(), p.x()))
                    // right half
                    return new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                else
                    // left half
                    return new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax());
            }
        }
    }

    /**
     * add the point to the set (if it is not already in the set).
     */
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("p must be not null");
        root = insert(null, root, p, ROOT_LVL_CMP);
    }

    private Node insert(Node parent, Node child, Point2D p, LevelComparator lvlcmp) {
        if (child == null) {
            size += 1;

            if (parent == null)
                return new Node(p, BIGGEST_RECT);
            else
                return new Node(p, parent.halfyRect(p, lvlcmp.isVertical()));
        }

        if (p.equals(child.point)) return child; // supports set invariant

        int cmp = lvlcmp.compare(p, child.point);

        if      (cmp > 0) child.right = insert(child, child.right, p, lvlcmp.flip());
        else if (cmp < 0) child.left  = insert(child, child.left,  p, lvlcmp.flip());

        return child;
    }

    /**
     * all points that are inside the rectangle
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException("rect must be not null");
        Queue<Point2D> out = new Queue<>();
        range(root, rect, out);
        return out;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> out) {
        if (x == null) return;
        if (x.rect.intersects(rect)) {
            if (rect.contains(x.point))
                out.enqueue(x.point);
            range(x.left,  rect, out);
            range(x.right, rect, out);
        }
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new NullPointerException("p must be not null");
        if (isEmpty()) return null;
        return nearest(root, null, p, true).getA();
    }

    private Pair<Point2D, Double> nearest(Node n, Pair<Point2D, Double> min, Point2D p, boolean vertical) {
        if (n == null) return min;

        double dist = n.point.distanceSquaredTo(p);

        if (min == null) {
            min = new Pair<>(n.point, dist);
        }
        else if (less(dist, min.getB())) {
            min.setA(n.point);
            min.setB(dist);
        }

        if (vertical && less(p.x(), n.point.x())) { // go left
            nearest(n.left, min, p, false);

            if (lessOrEq(sqrXDistance(n.point, p), min.getB()))
                nearest(n.right, min, p, false);
        }
        else if (vertical) { // go right
            nearest(n.right, min, p, false);

            if (lessOrEq(sqrXDistance(n.point, p), min.getB()))
                nearest(n.left, min, p, false);
        }
        else if (less(p.y(), n.point.y())) { // go bottom
            nearest(n.left, min, p, true);

            if (lessOrEq(sqrYDistance(n.point, p), min.getB()))
                nearest(n.right, min, p, true);
        } else { // go top
            nearest(n.right, min, p, true);

            if (lessOrEq(sqrYDistance(n.point, p), min.getB()))
                nearest(n.left, min, p, true);
        }

        return min;
    }

    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("p must be not null");
        return get(p) != null;
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
     * draw all points to standard draw
     */
    public void draw() {
        draw(root, ROOT_LVL_CMP);
    }

    private void draw(Node n, LevelComparator lvlcmp) {
        if (n == null) return;

        if (lvlcmp.isVertical()) {
            StdDraw.setPenColor(Color.RED);
            StdDraw.line(n.point.x(), n.rect.ymin(), n.point.x(), n.rect.ymax());
        }
        else {
            StdDraw.setPenColor(Color.BLUE);
            StdDraw.line(n.rect.xmin(), n.point.y(), n.rect.xmax(), n.point.y());
        }

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor();
        n.point.draw();
        StdDraw.setPenRadius();

        draw(n.left,  lvlcmp.flip());
        draw(n.right, lvlcmp.flip());
    }

    private Node get(Point2D p) {
        return get(root, p, VERTICAL_CMP);
    }

    private Node get(Node x, Point2D p, LevelComparator lvlcmp) {
        LevelComparator cmprt = lvlcmp;

        while (x != null) {
            if (p.equals(x.point)) return x;

            int cmp = cmprt.compare(p, x.point);
            if      (cmp > 0) x = x.right;
            else if (cmp < 0) x = x.left;

            cmprt = cmprt.flip();
        }

        return null;
    }

    private static boolean less(double d0, double d1) {
        return Double.compare(d0, d1) < 0;
    }

    private static boolean lessOrEq(double d0, double d1) {
        return Double.compare(d0, d1) <= 0;
    }

    private static double sqrXDistance(Point2D p0, Point2D p1) {
        double d = p0.x() - p1.x();
        return d*d;
    }

    private static double sqrYDistance(Point2D p0, Point2D p1) {
        double d = p0.y() - p1.y();
        return d*d;
    }

    public static void main(String[] args) {
        KdTree kd = new KdTree();

        kd.insert(new Point2D(0.5, 0.5));
        kd.insert(new Point2D(0.5, 0.6));
        kd.insert(new Point2D(0.4, 0.6));

        assert kd.contains(new Point2D(0.5, 0.5));
        assert kd.contains(new Point2D(0.5, 0.6));
        assert kd.contains(new Point2D(0.4, 0.6));

        KdTree kd2 = new KdTree();
        kd2.insert(new Point2D(0.5, 0.3));
        kd2.insert(new Point2D(0.5, 0.7));
        kd2.insert(new Point2D(0.7, 0.5));

        kd2.draw();

        assert kd2.nearest(new Point2D(0.34960937500000006, 0.5751953125)).equals(new Point2D(0.5, 0.7));
        assert kd2.nearest(new Point2D(0.8544921875, 0.701953125)).equals(new Point2D(0.7, 0.5));
    }

}
