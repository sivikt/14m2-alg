package algs.coding_jam;
import java.util.*;

class MissingInteger {
    public int solution(int[] A) {
        Arrays.sort(A);
        
        int s = 1;
        int p = 0;
        
        for (; p < A.length; p++) {
            if (A[p] > 0)
                break;
        }
        
        if (p < A.length && A[p] == s) {
            while (p < A.length) {
                p++;            
                s++;
                
                while ((p < A.length) && (A[p-1] == A[p]))
                    p++;
                    
                if ((p < A.length) && (A[p] - s) > 0)
                    break;
            }
        }
        
        
        return s;
    }
}