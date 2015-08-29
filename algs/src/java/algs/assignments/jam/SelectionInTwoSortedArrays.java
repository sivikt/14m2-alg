package algs.assignments.jam;

import algs.sorting.QuickSort;

import java.util.Arrays;
import java.util.Random;

import static algs.sorting.SortUtil.isSorted;

/**
 * """Selection in two sorted arrays.
 *    Given two sorted arrays a[] and b[], of sizes N1 and N2, respectively,
 *    design an algorithm to find the k-th largest key. The order of growth
 *    of the worst case running time of your algorithm should be logN,
 *    where N=N1+N2:
 *      -Version 1: N1=N2 and k=N/2
 *      -Version 2: k=N/2
 *      -Version 3: no restrictions
 *
 *    Hints: there are two basic approaches.
 *      Approach A: Compute the median in a[] and the median in b[].
 *                  Recur in a subproblem of roughly half the size.
 *      Approach B: Design a constant-time algorithm to determine
 *                  whether a[i] is the kth largest key. Use this
 *                  subroutine and binary search.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class SelectionInTwoSortedArrays {

    public static <T extends Comparable<? super T>> T version1(T[] a, T[] b) {
        if (a.length != b.length)
            throw new IllegalArgumentException("Input arrays must be of equal sizes");

        T[] le = a,
            gt = b;
        int k  = 0,
            lelo = 0,
            lehi = a.length-1,
            gtlo = 0,
            gthi = lehi;

        while (true) {
            assert lehi - lelo == gthi - gtlo;

            if (le[ lehi ].compareTo(gt[ gtlo ]) <= 0)
                return le[ lehi ];
            else if (gt[ gthi ].compareTo(le[ lelo ]) <= 0)
                return gt[ gthi ];

            int N = gthi - gtlo + 1;
            int lemid = lelo + (lehi - lelo)/2;
            int gtmid = gtlo + (gthi - gtlo)/2;

            int cmp = le[ lemid ].compareTo(gt[ gtmid ]);
            if (cmp == 0)
                return le[ lemid ];

            if (cmp > 0) {
                T[] t = le;
                le = gt;
                gt = t;
            }

            k += lemid - lelo + 1;
            if (k == a.length)
                return le[ lemid ];

            boolean odd = (N & 1) == 1;
            if (le[ lemid ].compareTo(gt[ gtmid-1 ]) >= 0) {
                return odd ? le[ lemid ] : gt[ gtmid ];
            }
            else { // binary search for the place of le[mid]
                int i = gtlo,
                    j = odd ? gtmid - 1 : gtmid,
                    m = i + (j - i)/2;

                while (i < j) {
                    int c = le[ lemid ].compareTo(gt[m]);
                    if (c >= 0) i = m + 1;
                    else        j = m;
                }

                // i - is the index of the first element > le[mid]
                k += i - gtlo;
                lelo = lemid + 1;
                lehi = lelo + (odd ? gtmid-i : gtmid-i+1);
                gtlo = i;
                gthi = gtlo + lehi-lelo;
            }
        }
    }

    /**
     * Merge and find. Time O(N), Memory O(N)
     */
    @SuppressWarnings("unchecked")
    public static <T extends Comparable<? super T>> T select_slow(T[] a, T[] b, int k) {
        assert isSorted(a, 0, a.length - 1);
        assert isSorted(b, 0, b.length - 1);

        int N = a.length + b.length;

        if (k < 1 && k > N)
            throw new IllegalArgumentException("K must be in [1," + N + "]");

        T[] m = (T[]) new Comparable[a.length + b.length];

        int i = 0,
            j = 0,
            l = 0;

        while (i < a.length && j < b.length) {
            if (a[i].compareTo(b[j]) < 0) {
                m[l++] = a[i];
                i++;
            }
            else {
                m[l++] = b[j];
                j++;
            }
        }

        if (i < a.length)
            System.arraycopy(a, i, m, l, a.length-i);

        if (j < b.length)
            System.arraycopy(b, j, m, l, b.length-j);

        assert isSorted(m, 0, N - 1);

        return m[k-1];
    }


    // testing
    public static void main(String args[]) {
        final int N1 = 2;
        final int N2 = 2;

        Integer[] a = generate(N1);
        Integer[] b = generate(N2);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.toString(b));

        Integer kth1 = select_slow(a, b, a.length);
        Integer kth2 = version1(a, b);


        System.out.println(kth1);
        System.out.println(kth2);
    }

    private static Integer[] generate(int size) {
        Random rnd = new Random();
        Integer[] a = new Integer[size];

        for (int i = 0; i < size; i++)
            a[i] = rnd.nextInt();

        QuickSort.sort(a);
        assert isSorted(a, 0, size - 1);

        return a;
    }

}
