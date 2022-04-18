package algs.assignments.collinearp;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdDraw;

import java.util.Arrays;
import java.util.Comparator;

/**
 * """The problem. Given a set of N distinct points in the plane, draw every (maximal) line segment that connects a
 *    subset of 4 or more of the points.
 *
 *    Write a program to recognize line patterns in a given set of points.
 *    Computer vision involves analyzing patterns in visual images and reconstructing the real-world objects that
 *    produced them. The process in often broken up into two phases: feature detection and pattern recognition.
 *    Feature detection involves selecting important features of the image; pattern recognition involves discovering
 *    patterns in the features. We will investigate a particularly clean pattern recognition problem involving points
 *    and line segments. This kind of pattern recognition arises in many other applications such as statistical data
 *    analysis.
 *
 *    Brute force. Write a program Brute.java that examines 4 points at a time and checks whether they all lie on the
 *    same line segment, printing out any such line segments to standard output and drawing them using standard drawing.
 *    To check whether the 4 points p, q, r, and s are collinear, check whether the slopes between p and q, between p
 *    and r, and between p and s are all equal.
 *    The order of growth of the running time of your program should be N4 in the worst case and it should use space
 *    proportional to N.
 *
 *    Input format. Read the points from an input file in the following format: An integer N, followed by N pairs of
 *    integers (x, y), each between 0 and 32,767.
 * """ (from materials by Robert Sedgewick)
 *
 * @see Fast
 *
 * @author Serj Sintov
 */
public class Brute {

    private static final Comparator<Point> POINTS_ASC = (p1, p2) -> p1.compareTo(p2);

    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        Point[] points = readPoints(args[0]);
        recognizeLines(points);

        StdDraw.show(0);
        StdDraw.setPenRadius();
    }

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
        int N = points.length;

        for (int p = 0; p < N-3; p++)
            for (int q = p+1; q < N-2; q++)
                for (int r = q+1; r < N-1; r++)
                    for (int s = r+1; s < N; s++) {
                        double pq = points[p].slopeTo(points[q]);
                        double pr = points[p].slopeTo(points[r]);
                        double ps = points[p].slopeTo(points[s]);

                        if (Double.compare(pq, pr) == 0 && Double.compare(pr, ps) == 0) {
                            Point[] segment = ordered(points, p, q, r, s);
                            segment[0].drawTo(segment[3]);
                            printSegment(segment);
                        }
                    }
    }

    private static Point[] ordered(Point[] points, int p, int q, int r, int s) {
        Point[] segment = { points[p], points[q], points[r], points[s] };
        Arrays.sort(segment, POINTS_ASC);
        return segment;
    }

    private static void printSegment(Point[] segment) {
        int N = segment.length;
        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < N; i++) {
            if (i == N - 1) {
                buf.append(segment[i]);
            } else {
                buf.append(segment[i]);
                buf.append(" -> ");
            }
        }

        System.out.println(buf.toString());
    }

}
