package algs.assignments.jam;

import algs.sorting.ShellSort;

/**
 * """Given two arrays a[] and b[], each containing N distinct 2D points in the plane,
 *    design a sub-quadratic algorithm to count the number of points that are contained
 *    both in array a[] and array b[].
 * """ (from materials by Robert Sedgewick)
 *
 * """
 * An algorithm is said to be subquadratic time if T(n) = o(n2).
 * For example, most naïve comparison-based sorting algorithms are quadratic (e.g.
 * insertion sort), but more advanced algorithms can be found that are subquadratic
 * (e.g. Shell sort). No general-purpose sorts run in linear time, but the change
 * from quadratic to sub-quadratic is of great practical importance.
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class SetsIntersection {

    /**
     * Sort using subquadratic algorithm (shell sort) and then loop and compare.
     */
    public static int intersect(Integer[] a, Integer[] b) {
        if (a.length != b.length)
            throw new IllegalArgumentException("Lengths of arrays have to be the same");

        ShellSort.sort(a); // Θ(N^3/2)
        ShellSort.sort(b); // Θ(N^3/2)

        // O(N)
        int N  = a.length,
            aI = 0,
            bI = 0,
            count = 0;

        while (aI < N && bI < N) {
            if (a[ aI ].equals(b[ bI ])) {
                aI += 1;
                bI += 1;
                count += 1;
            }
            else if (a[ aI ].compareTo(b[ bI ]) < 0)
                aI += 1;
            else
                bI += 1;
        }

        return count;
    }


    public static void main(String[] args) {
        Integer[] a = {9, 1, 4, 3, 5, 6, 7, 2, 8};
        Integer[] b = {11, 6, 5, 10, 9, 7, 12, 8, 13};

        assert intersect(a, b) == 5;
    }

}
