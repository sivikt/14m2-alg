package algs.assignments.leetcode;

class BinarySequence {

    public String solution(int U, int L, int[] C) {
        final String NO_SOLUTION = "IMPOSSIBLE";
        
        int N = C.length;
        
        int r0 = 0;
        int r1 = 0;
        
        int r0_U = 0;
        int r1_L = 0;
        
        // two integers r0 and r1. r0 & r1 -> bit i is 1 if C[i] = 2 and so on
        // let's calculate using bitwise operations 
        for (int i = 0; i < N; i++) {
            if (C[i] == 2) {
                r0 = r0 | (1 << (N-i-1)); 
                r1 = r1 | (1 << (N-i-1));
                
                r0_U++;
                r1_L++;
            }
        }

        int r0_U_zeros = U - r0_U;
        int r1_L_zeros = L - r1_L;

        if ( (r0_U_zeros < 0) || (r1_L_zeros < 0) )
            return NO_SOLUTION;
            
        for (int i = 0; i < N; i++) {
            if (C[i] == 1) {
                if (r0_U_zeros > 0) {
                    r0 = r0 | (1 << (N-i-1)); 
                    r0_U++;
                    r0_U_zeros--;
                }
                else {
                    r1 = r1 | (1 << (N-i-1));
                    r1_L++;
                    r1_L_zeros--;
                }
            }
        }
        
        if ((r0_U != U) || (r1_L != L))
            return NO_SOLUTION;
        else {
            String r0_str = "";
            String r1_str = "";
            
            for (int i = 0; i < N; i++) {
                r0_str += ((r0 >> (N-i-1)) & 1);
                r1_str += ((r1 >> (N-i-1)) & 1);
            }

            return r0_str + "," + r1_str;
        }
    }
}
