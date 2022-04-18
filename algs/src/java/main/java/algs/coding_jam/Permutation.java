package algs.coding_jam;

import static algs.sorting.ShellSort.sort;

/**
 * """Given two integer arrays of size N, design a subquadratic algorithm to determine whether
 *    one is a permutation of the other. That is, do they contain exactly the same entries but,
 *    possibly, in a different order.
 * """ (from materials by Robert Sedgewick)
 *
 * An algorithm is said to be subquadratic time if T(n) = o(n^2).
 * For example, most naïve comparison-based sorting algorithms are quadratic (e.g.
 * insertion sort), but more advanced algorithms can be found that are subquadratic
 * (e.g. Shell sort). No general-purpose sorts run in linear time, but the change
 * from quadratic to sub-quadratic is of great practical importance.
 *
 * @author Serj Sintsov
 */
public class Permutation {

    /**
     * Sort using subquadratic algorithm (shell sort) and then loop and compare.
     */
    public static boolean isPermute(Integer[] a, Integer[] b) {
        if (a.length != b.length)
            throw new IllegalArgumentException("Lengths of arrays have to be the same");

        sort(a); // Θ(N^3/2)
        sort(b); // Θ(N^3/2)

        int N = a.length;
        for (int i = 0; i < N; i++) { // O(N)
            if (!a[i].equals(b[i]))
                return false;
        }

        return true;
    }


    public static void main(String[] args) {
        Integer[] a0 = {9, 1, 4, 3, 5, 6, 7, 2, 8};
        Integer[] b0 = {7, 8, 4, 3, 6, 5, 9, 2, 1};

        Integer[] a1 = {13, 6, 5, 10, 7, 9, 12, 1, 11};
        Integer[] b1 = {11, 6, 5, 10, 9, 7, 12, 8, 13};

        assert isPermute(a0, b0);
        assert isPermute(a1, b1);
    }

}
