package algs.coding_jam;// you can also use imports, for example:
import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Triangle {
    public int solution(int[] A) {
        // write your code in Java SE 8
        Arrays.sort(A);
        
        if (A.length < 3) return 0;
        if (A[A.length-1] <= 0) return 0;
        
        for (int i = 0; i < A.length-2; i++) {
            for (int j = i+1; j < A.length-1; j++) {
                int p = A[i];
                int q = A[j];
                int r = A[j+1];
                
                if ((r-q) < p) {
                    return 1;
                }
            }
        }
        
        return 0;
    }
}