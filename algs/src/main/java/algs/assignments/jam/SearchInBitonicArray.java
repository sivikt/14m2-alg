package algs.assignments.jam;

/**
 * """An array is bitonic if it is comprised of an increasing sequence of integers
 *    followed immediately by a decreasing sequence of integers.
 *    <p>Write a program that, given a bitonic array of N distinct integer values,
 *    determines whether a given integer is in the array.
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class SearchInBitonicArray {

    /**
     * Uses ~3lgN compares in the worst case.
     *
     * @param key value to search
     * @param a array to search in
     * @return key index in the input array or -1 if it doesn't exist in the
     *         array
     */
    public static int find_3lgN(int key, int[] a) {
        int mi = findMax(a);

        if (mi == -1) return mi;

        // search in the increasing part
        int asc = searchAsc(key, a, 0, mi);
        if (asc != -1)
            return asc;
        else
            // search in the decreasing part
            return searchDesc(key, a, mi + 1, a.length - 1);
    }

    private static int findMax(int[] a) {
        int lst = a.length - 1;
        int lo  = 0;
        int hi  = lst;
        int max, lt, gt;

        while (lo <= hi) {
            max = lo + (hi - lo)/2;
            lt  = max - 1;
            gt  = max + 1;

            if (lt < 0 || gt > lst)
                return max;
            else if (a[max] > a[lt] && a[max] > a[gt])
                return max;
            else if (a[max] < a[gt])
                lo = gt;
            else
                hi = lt;
        }

        return -1;
    }

    /**
     * Tuned binary search algorithm.
     * Searches for the integer key in the ASC sorted array a[],
     * starting from the specified lower and higher bounds.
     *
     * @param key the search key
     * @param a the array of integers, must be sorted in ascending order
     * @param lo search lower bound
     * @param hi search higher bound
     * @return index of key in array a[] if present; -1 if not present
     */
    private static int searchAsc(int key, int[] a, int lo, int hi) {
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;

            if      (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }

        return -1;
    }

    /**
     * Tuned binary search algorithm.
     * Searches for the integer key in the DESC sorted array a[],
     * starting from the specified lower and higher bounds.
     *
     * @see {@link #searchAsc(int, int[], int, int)}
     */
    private static int searchDesc(int key, int[] a, int lo, int hi) {
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;

            if      (key > a[mid]) hi = mid - 1;
            else if (key < a[mid]) lo = mid + 1;
            else return mid;
        }

        return -1;
    }

    /**
     * Uses ~2lgN compares in the worst case. Implementation without
     * finding the maximum array value.
     *
     * @param key value to search
     * @param a array to search in
     * @return key index in the input array or -1 if it doesn't exist in the
     *         array
     */
    public static int find_2lgN(int key, int[] a) {
        int lst = a.length - 1;
        int lo  = 0;
        int hi  = lst;
        int mid, lt, gt;

        while (lo <= hi) {
            mid = lo + (hi - lo)/2;
            lt  = mid - 1;
            gt  = mid + 1;

            if (lt < 0 && gt > lst)
                return a[0] == key ? 0 : -1;

            if (lt < 0) {
                return (a[mid] > a[gt]) ? searchDesc(key, a, mid, hi)
                                        : searchAsc(key, a, mid, hi);
            }
            else if (gt > lst) {
                return (a[mid] > a[lt]) ? searchAsc(key, a, lo, mid)
                                        : searchDesc(key, a, lo, mid);
            }
            else if (a[mid] > a[lt]) {
                int res = searchAsc(key, a, lo, mid);
                if (res > -1)
                    return res;
                else
                    lo = gt;
            }
            else if (a[mid] > a[gt]) {
                int res = searchDesc(key, a, mid, hi);
                if (res > -1)
                    return res;
                else
                    hi = lt;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        int[] a1 = {1, 2, 4, 6, 8, 9, 10, 100, -3, -7, -10, -25};
        assert find_3lgN(9, a1) == 5;
        assert find_3lgN(0, a1) == -1;
        assert find_3lgN(1, a1) == 0;
        assert find_3lgN(-25, a1) == 11;
        assert find_2lgN(9, a1) == 5;
        assert find_2lgN(0, a1) == -1;
        assert find_2lgN(1, a1) == 0;
        assert find_2lgN(-25, a1) == 11;

        int[] a2 = {5};
        assert find_3lgN(5, a2) == 0;
        assert find_3lgN(9, a2) == -1;
        assert find_2lgN(5, a2) == 0;
        assert find_2lgN(9, a2) == -1;

        int[] a3 = {};
        assert find_3lgN(0, a3) == -1;
        assert find_2lgN(0, a3) == -1;

        int[] a4 = {1, -1};
        assert find_3lgN(1, a4) == 0;
        assert find_3lgN(-1, a4) == 1;
        assert find_3lgN(2, a4) == -1;
        assert find_2lgN(1, a4) == 0;
        assert find_2lgN(-1, a4) == 1;
        assert find_2lgN(2, a4) == -1;

        int[] a5 = {1, 2, 3};
        assert find_3lgN(3, a5) == 2;
        assert find_3lgN(-3, a5) == -1;
        assert find_3lgN(1, a5) == 0;
        assert find_3lgN(2, a5) == 1;
        assert find_2lgN(3, a5) == 2;
        assert find_2lgN(-3, a5) == -1;
        assert find_2lgN(1, a5) == 0;
        assert find_2lgN(2, a5) == 1;

        int[] a6 = {3, 2, 1};
        assert find_3lgN(3, a6) == 0;
        assert find_3lgN(2, a6) == 1;
        assert find_3lgN(1, a6) == 2;
        assert find_3lgN(4, a6) == -1;
        assert find_2lgN(3, a6) == 0;
        assert find_2lgN(2, a6) == 1;
        assert find_2lgN(1, a6) == 2;
        assert find_2lgN(4, a6) == -1;
    }

}