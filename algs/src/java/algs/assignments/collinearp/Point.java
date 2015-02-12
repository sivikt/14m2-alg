package algs.assignments.collinearp;

import edu.princeton.cs.introcs.StdDraw;

import java.util.Comparator;

/**
 * An immutable data type for points in the plane.
 *
 * @author Serj Sintov
 */
public class Point implements Comparable<Point> {

    /**
     * Compare points by the slopes they make with the invoking point (x0, y0).
     * Formally, the point (x1, y1) is less than the point (x2, y2) if and only if the slope (y1 − y0) / (x1 − x0)
     * is less than the slope (y2 − y0) / (x2 − x0). Treat horizontal, vertical, and degenerate line segments as
     * in the slopeTo() method.
     */
    public final Comparator<Point> SLOPE_ORDER = (p1, p2) -> {
        if (p1 == null) throw new NullPointerException("p1 arg is null");
        if (p2 == null) throw new NullPointerException("p2 arg is null");

        double p0p1 = Point.this.slopeTo(p1);
        double p0p2 = Point.this.slopeTo(p2);

        return Double.compare(p0p1, p0p2);
    };

    // x coordinate
    private final int x;

    // y coordinate
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * plot this point to standard drawing
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * draw line between this point and that point to standard drawing
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between the invoking point (x0, y0) and the argument point
     * (x1, y1), which is given by the formula (y1 − y0) / (x1 − x0). Treat the slope
     * of a horizontal line segment as positive zero; treat the slope of a vertical
     * line segment as positive infinity; treat the slope of a degenerate line segment
     * (between a point and itself) as negative infinity.
     *
     * """
     * Q: Is it ok to compare two floating-point numbers a and b for exactly equality?
     * A: In general, it is hazardous to compare a and b for equality if either is susceptible to floating-point
     *    roundoff error. However, in this case, you are computing b/a, where a and b are integers between -32,767
     *    and 32,767. In Java (and the IEEE 754 floating-point standard), the result of a floating-point operation
     *    (such as division) is the nearest representable value. Thus, for example, it is guaranteed that
     *    (9.0/7.0 == 45.0/35.0). In other words, it's sometimes OK to compare floating-point numbers for exact
     *    equality (but only when you know exactly what you are doing!)
     * """
     */
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException("That arg is null");

        if ((this == that) || (this.y == that.y && this.x == that.x))
            return Double.NEGATIVE_INFINITY;

        if (this.x == that.x)
            return Double.POSITIVE_INFINITY;
        else if (this.y == that.y)
            return 0;
        else
            return (double) (that.y - this.y) / (double) (that.x - this.x);
    }

    /**
     * Compare points by their y-coordinates, breaking ties by their x-coordinates.
     * Formally, the invoking point (x0, y0) is less than the argument point (x1, y1)
     * if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     */
    @Override
    public int compareTo(Point that) {
        if (that == null) throw new NullPointerException("That arg is null");

        if ((this == that) || (this.y == that.y && this.x == that.x))
            return 0;

        if ((this.y < that.y) || (this.y == that.y && this.x < that.x))
            return -1;
        else
            return 1;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point p0 = new Point(1, 3);
        assert p0.compareTo(p0) == 0;
        assert p0.compareTo(new Point(1, 3)) == 0;
        assert p0.compareTo(new Point(2, 2)) == 1;
        assert p0.compareTo(new Point(2, 4)) == -1;

        assert new Point(19, 216).slopeTo(new Point(19, 173)) == Double.POSITIVE_INFINITY;
        assert new Point(12459, 12521).slopeTo(new Point(12459, 11665)) == Double.POSITIVE_INFINITY;
        assert new Point(114, 193).slopeTo(new Point(378, 193)) == 0;
        assert new Point(275, 254).slopeTo(new Point(209, 261)) == -0.10606060606060606;
    }

}
