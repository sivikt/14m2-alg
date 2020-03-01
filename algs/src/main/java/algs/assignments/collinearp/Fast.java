package algs.assignments.collinearp;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Arrays;

/**
 * """A faster, sorting-based solution. Remarkably, it is possible to solve the problem much faster than the brute-force
 *    solution described above. Given a point p, the following method determines whether p participates in a set of 4 or
 *    more collinear points.
 *
 *    1) Think of p as the origin.
 *    2) For each other point q, determine the slope it makes with p.
 *    3) Sort the points according to the slopes they makes with p.
 *    4) Check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p. If so, these
 *       points, together with p, are collinear.
 *
 *    Applying this method for each of the N points in turn yields an efficient algorithm to the problem. The algorithm
 *    solves the problem because points that have equal slopes with respect to p are collinear, and sorting brings such
 *    points together. The algorithm is fast because the bottleneck operation is sorting.
 *
 *    Write a program Fast.java that implements this algorithm. The order of growth of the running time of your program
 *    should be N^2*log(N) in the worst case and it should use space proportional to N.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * @see Brute
 *
 * @author Serj Sintov
 */
public class Fast {

    private static final int MIN_POINTS_ON_LINE = 4;

    private static Point[] readPoints(String filename) {
        In in = new In(filename);

        int N = in.readInt();
        Point[] points = new Point[N];

        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            Point p = new Point(x, y);
            p.draw();

            points[i] = p;
        }

        return points;
    }

    private static void recognizeLines(Point[] points) {
        if (points.length < MIN_POINTS_ON_LINE)
            return;

        int N = points.length;

        Point[] origins = new Point[N];
        System.arraycopy(points, 0, origins, 0, N);

        for (Point origin : origins) { // solved in N
            Arrays.sort(points);       // keep all in lexicographical order (solved in N*log(N))
            Arrays.sort(points, origin.SLOPE_ORDER); // solved in N*log(N)

            int segmentLt = 0,
                segmentRt = 0;
            boolean inLexOrder = true;

            while (segmentLt <= N - MIN_POINTS_ON_LINE + 1) { // solved in N
                if ((segmentRt < N - 1)
                        && (origin.SLOPE_ORDER.compare(points[segmentRt], points[segmentRt + 1]) == 0))
                {
                    inLexOrder = inLexOrder // don't compare if it's already doesn't
                            && (origin.compareTo(points[segmentRt]) < 0)
                            && (points[segmentRt].compareTo(points[segmentRt + 1]) < 0);
                    segmentRt += 1;
                }
                else {
                    // print if sorted lexicographically
                    if (inLexOrder && (segmentRt - segmentLt >= MIN_POINTS_ON_LINE - 2))
                        printSegment(points, origin, segmentLt, segmentRt);

                    segmentLt = segmentRt + 1;
                    segmentRt = segmentLt;
                    inLexOrder = true;
                }
            }
        }
    }

    private static void printSegment(Point[] segment, Point origin, int from, int to) {
        origin.drawTo(segment[to]); // draw segment

        StringBuilder buf = new StringBuilder();
        buf.append(origin);
        buf.append(" -> ");

        for (int i = from; i <= to; i++) {
            if (i == to) {
                buf.append(segment[i]);
            } else {
                buf.append(segment[i]);
                buf.append(" -> ");
            }
        }

        System.out.println(buf.toString());
    }

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show();
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        Point[] points = readPoints(args[0]);
        recognizeLines(points);

        StdDraw.show();
        StdDraw.setPenRadius();
    }

}