// you can also use imports, for example:
// import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Solution {
    public int solution(int N) {
        int B = (int)Math.ceil(Math.sqrt(N));
        
        if (N % B == 0)
            return 2*((N/B) + B);
        else
            return 2*(1 + N);
    }
}