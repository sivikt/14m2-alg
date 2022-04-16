package algs.coding_jam;

import algs.sorting.QuickSort;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static algs.sorting.SortUtil.isSorted;
import static java.lang.String.format;

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
 *                  Recur in a sub-problem of roughly half the size.
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

        int alo = 0,
            ahi = a.length-1,
            blo = 0,
            bhi = ahi;

        while (true) {
            assert ahi - alo == bhi - blo;

            if (a[ ahi ].compareTo(b[ blo ]) <= 0)
                return a[ ahi ];
            else if (b[ bhi ].compareTo(a[ alo ]) <= 0)
                return b[ bhi ];

            int N = ahi - alo + 1;
            int amid = alo + (ahi - alo)/2;
            int bmid = blo + (bhi - blo)/2;
            boolean odd = (N & 1) == 1;

            if (amid == alo)
                return odd ? a[ amid ] : max(a[ amid ], b[ bmid ]);

            int cmp = a[ amid ].compareTo(b[ bmid ]);
            if (cmp == 0) {
                return a[ amid ];
            }
            else if (cmp > 0) {
                T[] t = a;
                a = b;
                b = t;

                int q = amid;
                amid = bmid;
                bmid = q;

                blo = alo;
            }

            assert bmid-1 >= 0;
            if (a[ amid ].compareTo(b[ bmid-1 ]) >= 0) {
                return odd ? a[ amid ] : min(a[ amid+1 ], b[ bmid ]);
            }
            else { // binary search for the place of a[mid]
                int i = searchForPlace(b, a[ amid ], blo, (odd ? bmid - 1 : bmid));

                // i - is the index of the first element > a[mid]
                alo = amid + 1;
                ahi = amid + (odd ? bmid-i : bmid-i+1);
                blo = i;
                bhi = blo + ahi - alo;
            }
        }
    }

    private static <T extends Comparable<? super T>> int searchForPlace(T[] a, T x, int lo, int hi) {
        int i = lo,
            j = hi;

        while (i < j) {
            int m = i + (j - i)/2;
            int c = x.compareTo(a[m]);
            if (c >= 0) i = m + 1;
            else        j = m;
        }

        return i;
    }

    private static <T extends Comparable<? super T>> T min(T a, T b) {
        return a.compareTo(b) >= 0 ? b : a;
    }

    private static <T extends Comparable<? super T>> T max(T a, T b) {
        return a.compareTo(b) >= 0 ? a : b;
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
        //System.out.println(Arrays.toString(m));

        return m[k-1];
    }


    // testing
    public static void main(String args[]) {
        testVersion1();
    }

    private static void testVersion1() {
        ExecutorService tpool = Executors.newFixedThreadPool(4);

        for (int i = 1; i <= 1000; i++) {
            for (int j = 0; j < 10000; j++) {
                final int size = i;
                //System.out.println("size="+size);

                tpool.execute(() -> {
                    Integer[] a = generate(size);
                    Integer[] b = generate(size);
                    //System.out.println(Arrays.toString(a));
                    //System.out.println(Arrays.toString(b));

                    Integer kth1 = select_slow(a, b, a.length);
                    Integer kth2 = version1(a, b);
                    assert kth1.equals(kth2) : format(
                        "Incorrect values [size=%s]: %s, %s\n%s\n%s", size, kth1, kth2, Arrays.toString(a), Arrays.toString(b)
                    );
                });
            }
        }

        tpool.shutdown();
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
