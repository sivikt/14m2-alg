package algs.coding_jam;

/**
 * From Toptal
 */

class LoadBalancer {
    public boolean solution(int[] A) {
        int N = A.length;
        int[] P = new int[N];
        P[0] = A[0];
        
        // compute prefix sums
        for (int i = 1; i < N; i++) {
            P[i] = P[i-1] + A[i];
        }
        
        // then we have to split array in 3 parts so that the sum of each part is equal
        for (int i = 1; i < N-2; i++) {
            // throw request i
            int w1_sum = P[i-1];
            
            // then find other 2 the same sums
            // find j using binary search
            
            int l = i+2;
            int r = N-2;
            
            while (l<=r) {
                int j = l + ((r-l)/2);
                
                int w2_sum = P[j-1] - P[i];
                int w3_sum = P[N-1] - P[j];
                
                if ((w1_sum == w2_sum) && (w2_sum == w3_sum))
                    return true;
                    
                if (w2_sum > w1_sum) {
                    r = j - 1;
                }
                else if (w2_sum < w1_sum) {
                    l = j + 1;
                }
                else break;
            }
        }
        
        return false;
    }
}
