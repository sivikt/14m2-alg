package algs.coding_jam;
import java.util.*;

class NumberOfDiscIntersections {
    
    private static class Cmp implements Comparator<Integer> {
        private final Integer[] B;
        private final int[] A;
        
        public Cmp(Integer[] B, int[] A) {
            this.B = B;
            this.A = A;
        }   
        
        @Override
        public int compare(Integer v, Integer w) {
            int x1 = v - A[v];
            int x2 = w - A[w];
            
            if (x1 < x2)
                return -1;
            else if (x2 < x1)
                return 1;
            else
                return 0;
        }
    }
    
    public int solution(int[] A) {
        final int LIMIT = 10_000_000;
        int N = A.length;
        
        Integer[] B = new Integer[N]; 
        
        for (int i = 0; i < N; i++)
            B[i] = i;
           
        Comparator<Integer> cmp = new Cmp(B, A);
        
        Arrays.sort(B, cmp);
        
        int result = 0;
        
        //System.out.println(Arrays.toString(B));
            
        for (int i = 0; i < N-1; i++) {
            int p1 = 0;

            if ((Integer.MAX_VALUE - B[i]) < A[B[i]])
                p1 = Integer.MAX_VALUE;
            else
                p1 = B[i] + A[B[i]];
            
            int r = N-1;
            int l = i+1;
            
            while (l<=r) {
                
                int m = l + (r-l)/2;
                int p2 = B[m] - A[B[m]];
                
                //System.out.println("l=" + l + " r=" + r + " m=" + m + " p1=" + p1 + " p2=" + p2);
                
                if (p2 > p1) 
                    r = m - 1;
                else
                    l = m + 1;
            }
            
            //System.out.println("B" + B[i] + "=" + (l-1 - i) + " l=" + l);
                
            result += l-1 - i;
            
            if (result > LIMIT)
                return -1;
        }
        
        return result;
    }
}