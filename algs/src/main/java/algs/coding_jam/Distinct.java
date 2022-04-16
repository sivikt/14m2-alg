package algs.coding_jam;
import java.util.*;

class Distinct {
    public int solution(int[] A) {
        Arrays.sort(A);
        
        if (A.length == 0) return 0;
        if (A.length == 1) return 1;
        
        int i = 0;
        int c = 1;
        
        while (i < A.length) {
            i++;
            
            while ((i < A.length) && (A[i-1] == A[i]))
                i++;
            
            if (i < A.length) 
                c++;
        }
        
        return c;
    }
}