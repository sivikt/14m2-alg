package algs.coding_jam;
import java.util.*;


class MaxProductOfThree {
    public int solution(int[] A) {
        Arrays.sort(A);
        
        int max_mul = Integer.MIN_VALUE;
        int max_el = A[A.length-1];

        for (int i = 0; i < A.length-2; i++) {
            int t = A[i] * A[i+1] * max_el;
            if (t > max_mul) 
                max_mul = t;
        }
        
        return max_mul;
    }
}