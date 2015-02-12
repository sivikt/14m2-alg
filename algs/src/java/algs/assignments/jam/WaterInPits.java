package algs.assignments.jam;

/**
 * Source http://qandwhat.apps.runkite.com/i-failed-a-twitter-interview
 *
 * Consider the following bar chart:
 *
 *  ^
 *  |      11
 *  |      111
 *  | 1    111
 *  | 1   1111
 *  | 1  11111
 *  |11 111111
 *  |111111111
 *  |----------->
 *
 * Vertical columns of units ("1111") is walls. This figure is represented by
 * input array [2,5,1,2,3,4,7,7,6] of integers.
 *
 * The question is how much water is going to be accumulated in puddles
 * between walls?
 *
 * For example for the array above this value is 10 and marked with "X" on
 * the figure below.
 *
 *  ^
 *  |      11
 *  |      111
 *  | 1XXXX111
 *  | 1XXX1111
 *  | 1XX11111
 *  |11X111111
 *  |111111111
 *  |----------->
 * 
 * @author Serj Sintsov <ssivikt@gmail.com>, 2013 Public Domain.
 */
public class WaterInPits {

    private static int capacity(int[] a) {
        int capacity  = 0;

        int leftPeak  = 0;
        int rightPeak = 0;

        int left  = 0;
        int right = a.length-1;

        while (left <= right) {
            if (leftPeak < a[left])   { leftPeak  = a[left++];  continue; }
            if (rightPeak < a[right]) { rightPeak = a[right--]; continue; }

            if (leftPeak <= rightPeak)
                capacity += leftPeak - a[left++];
            else
                capacity += rightPeak - a[right--];
        }

        return capacity;
    }

    public static void main(String[] args) {
        int[] a0 = {2, 5, 1, 2, 3, 4, 7, 7, 6}; // sim = 10
        int[] a1 = {1, 2, 4, 6, 6, 6, 5, 2, 5, 8}; // sum = 6
        int[] a2 = {1, 2, 1, 6}; // sum = 1
        int[] a3 = {1, 2, 2, 2, 3, 5, 8}; // sum 0
        int[] a4 = {2, 2, 2, 2, 2}; // sum 0
        int[] a5 = {1, 2, 1}; // sum 0
        int[] a6 = {6, 5, 2, 1, 0}; // sum 0
        int[] a7 = {1, 2, 4, 6, 6, 10, 5, 2, 5, 8};

        System.out.println(capacity(a0));
        System.out.println(capacity(a1));
        System.out.println(capacity(a2));
        System.out.println(capacity(a3));
        System.out.println(capacity(a4));
        System.out.println(capacity(a5));
        System.out.println(capacity(a6));
        System.out.println(capacity(a7));
    }

}
