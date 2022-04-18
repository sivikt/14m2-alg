package algs.coding_jam;

// Write an algorithm to determine if a number is "happy".

// A happy number is a number defined by the following process: 
// Starting with any positive integer, replace the number by the 
// sum of the squares of its digits, and repeat the process until 
// the number equals 1 (where it will stay), or it loops endlessly 
// in a cycle which does not include 1. Those numbers for which 
// this process ends in 1 are happy numbers.

// Example: 

// Input: 19
// Output: true
// Explanation: 
// 12 + 92 = 82
// 82 + 22 = 68
// 62 + 82 = 100
// 12 + 02 + 02 = 1


class HappyNumber {
    public boolean isHappy(int n) {
        if (n == 0)
            return false;
        
        int x = n;
        int y = n;
        
        do
        {
            x = sumsquares(x);
            y = sumsquares(y);
            y = sumsquares(y);
            
            if (x != 1 && x == y)
                return false;
        }
        while (x != y);
            
        return x == 1;
    }
    
    public int sumsquares(int n) {
        int x = 0;

        while (n != 0) 
        {
            x += Math.pow(n%10, 2);
            n = n/10;
        }
        
        return x;
    }
}