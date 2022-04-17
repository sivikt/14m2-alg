package algs.coding_jam;

/* A non-empty array A consisting of N integers is given.

 A triplet (X, Y, Z), such that 0 ≤ X < Y < Z < N, is called a double slice.

 The sum of double slice (X, Y, Z) is the total of A[X + 1] + A[X + 2] + ... + A[Y − 1] + A[Y + 1] + A[Y + 2] + ... + A[Z − 1].

 For example, array A such that:

     A[0] = 3
     A[1] = 2
     A[2] = 6
     A[3] = -1
     A[4] = 4
     A[5] = 5
     A[6] = -1
     A[7] = 2
 contains the following example double slices:

 double slice (0, 3, 6), sum is 2 + 6 + 4 + 5 = 17,
 double slice (0, 3, 7), sum is 2 + 6 + 4 + 5 − 1 = 16,
 double slice (3, 4, 5), sum is 0.
 The goal is to find the maximal sum of any double slice.

 Write a function:

 class Solution { public int solution(int[] A); }

 that, given a non-empty array A consisting of N integers, returns the maximal sum of any double slice.

 For example, given:

     A[0] = 3
     A[1] = 2
     A[2] = 6
     A[3] = -1
     A[4] = 4
     A[5] = 5
     A[6] = -1
     A[7] = 2
 the function should return 17, because no double slice of array A has a sum of greater than 17.

 Write an efficient algorithm for the following assumptions:

 N is an integer within the range [3..100,000];
 each element of array A is an integer within the range [−10,000..10,000].;
 */

class MaxDoubleSliceSum {
     // TODO possibly incorrect
     public int solution(int[] A) {
         int N = A.length;

         int x_sum = 0;
         int x_max = 0;
         int x_min = 0;
         int min = Integer.MAX_VALUE;

         int y = 0;

         for (int x=1; x < N-2; x++) {
             if (x_sum + A[x] > 0) {
                 x_sum = x_sum + A[x];
                 min = Math.min(min, A[x]);
             }
             else {
                 x_sum = 0;
             }

             //System.out.println("x_sum=" + x_sum);

             if (x_sum >= x_max) {
                 x_max = x_sum;
                 y = x;
                 x_min = min;
             }
         }

         //System.out.println("x_max=" + x_max + " y=" + y + " x_min="+x_min);

         int y_sum = 0;
         int y_max = 0;

         if (A[y+1] < 0)
             y += 1;
         else if (A[y+1] > x_min)
             x_max += A[y+1] - x_min;

         for (y += 1; y < N-2; y++) {
             y_sum += A[y];
             y_max = Math.max(y_max, y_sum);
         }

         return Math.max(x_max + y_max, x_max - x_min);
     }

     ///

    public int solution2(int[] A) {
        int N = A.length;
        int[] K1 = new int[N];
        int[] K2 = new int[N];

        for(int i = 1; i < N-1; i++){
            K1[i] = Math.max(K1[i-1] + A[i], 0);
        }
        for(int i = N-2; i > 0; i--){
            K2[i] = Math.max(K2[i+1]+A[i], 0);
        }

        int max = 0;

        for(int i = 1; i < N-1; i++){
            max = Math.max(max, K1[i-1]+K2[i+1]);
        }

        return max;
    }
}
