// Given a non-negative integer c, your task is to decide whether 
// there're two integers a and b such that a^2 + b^2 = c.

// Example 1:
// Input: 5
// Output: True
// Explanation: 1 * 1 + 2 * 2 = 5
 
// Example 2:
// Input: 3
// Output: False


// Approach 2: Better Brute Force
// We can improve the last solution, if we make the following observation. 
// For any particular aa chosen, the value of bb required to satisfy the equation a^2 + b^2 =c will be such that b^2 = c - a^2.
// Thus, we need to traverse over the range (0, sqrt(c)) only for considering the various values of a.
// For every current value of aa chosen, we can determine the corresponding b^2 value and check if it is a 
// perfect square or not. If it happens to be a perfect square, cc is a sum of squares of two integers, otherwise not.
// Now, to determine, if the number c - a^2 is a perfect square or not, we can make use of the following theorem:
// The square of n-th positive integer can be represented as a sum of first n odd positive integers.

// Or in mathematical terms:

// n^2 = 1 + 3 + 5 + ... + (2*n-1)

// To look at the proof of this statement, look at the L.H.S. of the above statement.
// 1+3+5+…+(2*n−1)=(2*1−1)+(2*2−1)+(2*3−1)+…+(2*n−1)=2*(1+2+3+....+n)−(1+1+…+1)=n^2

// This completes the proof of the above statement.


class Solution {
    public boolean judgeSquareSum(int c) {
        for (long a = 0; a*a<=c; a++) {
            long b = c - a*a;
            int i = 1;
            long sum = 0;
            
            while (sum < b) {
                sum += i;
                i += 2;
            }
            
            if (sum == b)
                return true;
        }   
        
        return false;
    }


////


    public boolean judgeSquareSum2(int c) {
        for (long a = 0; a*a <= c; a++) {
            double b = Math.sqrt(c - (int)(a*a));
            if (b == (int)b)
                return true;
        }   
        
        return false;
    }


////


    public boolean judgeSquareSum3(int c) {
        for (long a = 0; a*a <= c; a++) {
            int b = c - (int)(a*a);
            long l = 0;
            long r = b;
            
            while (l <= r) {
                long m = (l+r)/2;
                long s = m*m;
                if (s == b)
                    return true;
                else if (s < b)
                    l = m+1;
                else
                    r = m-1;
            }
        }   
        
        return false;
    }


////


// Fermat's Theorem:
// Any positive number n is expressible as a sum of two squares if and only 
// if the prime factorization of n, every prime of the form (4k+3) occurs
// an even number of times.

    public boolean judgeSquareSum4(int c) {
        for (long i = 2; i*i <= c; i++) {
            int count = 0;
            
            if (c % i == 0) {
                while (c % i == 0) {
                    count++;
                    c /= i;
                }    
                
                if ((i%4 == 3) && (count % 2 != 0))
                    return false;
            }
        }   
        
        return c % 4 != 3;
    }
}

