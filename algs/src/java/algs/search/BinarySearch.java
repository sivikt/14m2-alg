package algs.search;

import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

import java.util.Arrays;

/**
 * """The <tt>BinarySearch</tt> class provides a static method for binary
 *    searching for an integer in a sorted array of integers.
 *    <p>The <em>rank</em> operations takes logarithmic time in the worst case.
 *    <p>For additional documentation, see <a href="http://algs4.cs.princeton.edu/11model">Section 1.1</a> of
 *    <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 * """ (from materials by Robert Sedgewick)
 *
 *  @author Serej Sintsov
 */
public class BinarySearch {

    /**
     * Searches for the integer key in the sorted array a[].
     * @param key the search key
     * @param a the array of integers, must be sorted in ascending order
     * @return index of key in array a[] if present; -1 if not present
     */
    public static int search(int key, int[] a) {
        int lo = 0;
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
     * 2-way compare search
     */
    public static int search2cmp(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;

        while (lo < hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo)/2;

            if (key > a[mid]) lo = mid + 1;
            else              hi = mid;
        }

        if (a[hi] == key)
            return hi;
        else
            return -1;
    }

    /**
     * Reads in a sequence of integers from the whitelist file, specified as
     * a command-line argument. Reads in integers from standard input and
     * prints to standard output those integers that also appear in the file.
     */
    public static void main(String[] args) {
        int[] a = {1, 2, 4, 5, 6, 8, 10, 11, 15};
        assert search(1, a) == 0;
        assert search(5, a) == 3;
        assert search(15, a) == 8;
        assert search2cmp(1, a) == 0;
        assert search2cmp(5, a) == 3;
        assert search2cmp(15, a) == 8;

        // read in the integers from a file
        In in = new In(args[0]);
        int[] whitelist = in.readAllInts();

        // sort the array
        Arrays.sort(whitelist);

        // read keys to search from a file; print if not in whitelist
        In keysIn = new In(args[1]);
        while (!keysIn.isEmpty()) {
            int key = keysIn.readInt();
            if (search(key, whitelist) == -1)
                StdOut.println(key);
        }
    }

}
