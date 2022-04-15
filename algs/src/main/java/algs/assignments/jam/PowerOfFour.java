// Given an integer (signed 32 bits), write a function to check whether it is a power of 4.

// Example 1:
// Input: 16
// Output: true

// Example 2:
// Input: 5
// Output: false

// Follow up: Could you solve it without loops/recursion?


class Solution {
    public boolean isPowerOfFour(int n) {  
        int m = 0x55555555; // 0b01010101010101010101010101010101
        
        if (n == 1)
            return true;
        else if (n < 4)
            return false;
        else 
            return ((n & (n-1)) == 0) && ((n|m) == m);
    }


////


	// We know from Binomial Theorem that (1+X)^n = C(n,0) + C(n,1)*X + C(n,2)*X^2 + C(n,3)*X^3 +.........+ C(n,n)*X^n
	// Put X=3, we get 4^n = 1 + C(n,1)*3 + C(n,2)*3^2 + C(n,3)*3^3 +.........+ C(n,n)*3^n
	// by moving 1 left side, we get 4^n - 1 = C(n,1)*3 + C(n,2)*3^2 + C(n,3)*3^3 +.........+ C(n,n)*3^n
	// i.e (4^n - 1) = 3 * [ C(n,1) + C(n,2)*3 + C(n,3)*3^2 +.........+ C(n,n)*3^(n-1) ]
	// This implies that (4^n - 1) is multiple of 3.
    public boolean isPowerOfFour2(int n) {  
        return (n >= 0) && ((n & (n-1)) == 0) && ((n-1) % 3 == 0);
    }
}