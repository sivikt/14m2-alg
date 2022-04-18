package algs.coding_jam;

import java.util.Arrays;

/**
 * """
 * In computational complexity theory, the 3SUM problem asks if a given set
 * of N integers, each with absolute value bounded by some polynomial in n,
 * contains three elements that sum to zero.
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class ThreeSumProblem {

    /**
     * This solution takes time proportional to ~(1/2*N^2 * log(N)) in
     * the worst case doing binary search ~1/2*N^2 times.
     * It's more suitable to find all 3-sum triples but not just to answer
     * the question "is the array is 3-sum array".
     */
    public static boolean is3SumArray(int[] a) {
        if (a.length < 3) return false;

        Arrays.sort(a); // sorting with O(n*log(n)) algorithm

        /** search for each pair the third element */
        for (int i = 0; i < a.length-2; i++)
            for (int j = i+1; j < a.length-1; j++) {
                int cIndex = search(-(a[i] + a[j]), a, j + 1);
                if (cIndex != -1) {
                    System.out.println(a[i] + ", " + a[j] + ", " + a[cIndex]);
                    return true;
                }
            }

        return false;
    }

    /**
     * Tuned binary search algorithm.
     * Searches for the integer key in the sorted array a[], starting from the
     * specified lower bound.
     *
     * @param key the search key
     * @param a the array of integers, must be sorted in ascending order
     * @param lo array lower bound
     * @return index of key in array a[] if present; -1 if not present
     */
    private static int search(int key, int[] a, int lo) {
        int hi = a.length - 1;

        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo)/2;

            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }

        return -1;
    }

    /**
     * This solution takes time proportional to ~1/2*N^2 in the worst case and
     * matching ~1/2*N^2 lower bounds.
     * Such algorithm need to be tuned if you want to count 3sum triples since
     * current implementation doesn't avoid duplication.
     */
    public static boolean is3SumArrayPlus(int[] a) {
        if (a.length < 3) return false;

        Arrays.sort(a); // sorting with O(n*log(n)) algorithm

        for (int i = 0; i < a.length-2; i++) {
            int x = a[i];
            int yI = i+1;
            int zI = a.length-1;

            while (yI < zI) {
                int y = a[yI];
                int z = a[zI];

                if (x+y+z == 0) {
                    System.out.println(x + ", " + y + ", " + z);
                    return true;
                }
                else if (x+y+z > 0) zI--;
                else yI++;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[] a = {4, -10, -7, -3, 2, 8, -25, 10, 6, 1, 9, 100};
        boolean result = is3SumArray(a);
        assert result;

        int[] b = {4, -10, -7, -3, 2, 8, -25, 10, 6, 1, 9, 100};
        result = is3SumArrayPlus(b);
        assert result;
    }

}
