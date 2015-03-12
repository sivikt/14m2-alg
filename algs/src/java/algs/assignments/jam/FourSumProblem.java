package algs.assignments.jam;

import algs.adt.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * """4-SUM.
 *    Given an array a[] of N integers, the 4-SUM problem is to determine if there exist distinct indices i, j, k, and l
 *    such that a[i]+a[j]=a[k]+a[l]. Design an algorithm for the 4-SUM problem that takes time proportional to N2 (under
 *    suitable technical assumptions).
 *
 *    Hint: create a hash table with (N2) key-value pairs.
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class FourSumProblem {

    public static boolean isFourSum(int[] a) {
        Map<Integer, Pair<Integer, Integer>> sums = new HashMap<>();

        for (int i = 0; i < a.length-1; i++)
            for (int j = i+1; j < a.length; j++) {
                int sum = a[i] + a[j];
                if (sums.containsKey(sum)) {
                    Pair<Integer, Integer> idx = sums.get(sum);
                    if (i != idx.getFirst() && i != idx.getSecond() &&
                        j != idx.getFirst() && j != idx.getSecond())
                    {
                        System.out.println(idx);
                        System.out.println(Pair.of(i, j));
                        return true;
                    }
                }
                else
                    sums.put(sum, Pair.of(i, j));
            }

        return false;
    }

    public static void main(String[] args) {
        int[] a = {1, 4, 6, 4, 3, 6, 8, 1, 0};
        assert isFourSum(a);
    }
}
