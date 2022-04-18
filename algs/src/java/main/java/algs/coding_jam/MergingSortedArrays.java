package algs.coding_jam;

import java.util.Arrays;
import java.util.Random;

/**
 * """Merging with smaller auxiliary array.
 *    Suppose that the subarray a[0] to a[N-1] is sorted and the subarray a[N] to a[2*N-1] is sorted. How can you merge the
 *    two subarrays so that a[0] to a[2*N-1] is sorted using an auxiliary array of size N (instead of 2N)?
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class MergingSortedArrays {

    public static void merge(int[] a) {
        int N = a.length / 2;
        int[] aux = new int[N];

        System.arraycopy(a, 0, aux, 0, N);

        int i = 0;
        int j = N;
        int k = 0;

        while (k < N && j < a.length) {
            if (aux[k] <= a[j]) a[i++] = aux[k++];
            else                a[i++] = a[j++];
        }

        if (k < N)
            System.arraycopy(aux, k, a, i, N - k);
    }

    public static void main(String[] args) {
        testPartTwoNotGreaterThanPartOne();
        testPartOneGreaterThanPartOne();
        testPartTwoGreaterThanPartOne();
    }

    private static void testPartTwoNotGreaterThanPartOne() {
        final int N = 1000;
        final Random rnd = new Random();

        int[] a1 = new int[N];
        int[] a2 = new int[N];

        for (int i = 0; i < N; i++) {
            a1[i] = rnd.nextInt();
            a2[i] = rnd.nextInt();
        }

        assertParts(a1, a2);
    }

    private static void testPartOneGreaterThanPartOne() {
        final int N = 1000;
        final Random rnd = new Random();

        int[] a1 = new int[N];
        int[] a2 = new int[N];

        for (int i = 0; i < N; i++) {
            a1[i] = rnd.nextInt(1000) + 1000;
            a2[i] = rnd.nextInt(100);
        }

        assertParts(a1, a2);
    }

    private static void testPartTwoGreaterThanPartOne() {
        final int N = 1000;
        final Random rnd = new Random();

        int[] a1 = new int[N];
        int[] a2 = new int[N];

        for (int i = 0; i < N; i++) {
            a2[i] = rnd.nextInt(1000) + 1000;
            a1[i] = rnd.nextInt(100);
        }

        assertParts(a1, a2);
    }

    private static void assertParts(int[] a1, int[] a2) {
        int N = a1.length;
        int a[] = new int[2*N];
        int sorted[] = new int[2*N];

        Arrays.sort(a1);
        Arrays.sort(a2);

        System.arraycopy(a1, 0, a, 0, N);
        System.arraycopy(a2, 0, a, N, N);

        System.arraycopy(a1, 0, sorted, 0, N);
        System.arraycopy(a2, 0, sorted, N, N);

        merge(a);
        Arrays.sort(sorted);
        assertWithExpected(a, sorted);
    }

    private static void assertWithExpected(int[] actual, int[] expected) {
        for (int i = 0; i < actual.length; i++)
            assert actual[i] == expected[i] : "Did not match at position " + i;
    }
}
