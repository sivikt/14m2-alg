package algs.coding_jam;

/*
Determine whether an integer is a palindrome. An integer is a
palindrome when it reads the same backward as forward.

Example 1:

Input: 121
Output: true
Example 2:

Input: -121
Output: false
Explanation: From left to right, it reads -121.
From right to left, it becomes 121-. Therefore it is not a palindrome.
Example 3:

Input: 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.
Follow up:

Coud you solve it without converting the integer to a string?
*/

class PalindromNumber {

    public boolean isPalindrome1(int x) {
        if (x < 0)
            return false;
        else if ((x / 10) == 0)
            return true;
        else
        {
            String xstr = String.valueOf(x);
            
            int r = xstr.length() - 1;
            int l = 0;
                     
            while (l < r)
                if (xstr.charAt(l++) != xstr.charAt(r--))
                    return false;
                        
            return true;
        }
    }

    public boolean isPalindrome2(int x) {
        if (x < 0)
            return false;
        else if ((x / 10) == 0)
            return true;
        else
        {
            int rd = 10;
            int ld = (int)Math.pow(10, (int)(Math.log10(x)));
                                   
            //System.out.println(rd + "  " + ld);
            
            while (rd <= ld) {
                int r = x % rd;
                int l = x / ld;
                
                //System.out.println(x + "  " + r + " " + l);
                
                if (r != l)
                    return false;
                
                x %= ld;
                x /= rd;
                
                if (x == 0)
                    return true;
                
                ld /= 100;
            }
            
            return true;
        }
    }

    // fastest
    public boolean isPalindrome3(int x) {
        if (x >= 0 && x < 9) 
            return true;
        if (x < 0 || x % 10 == 0 ) 
            return false;
        
        int reverse = 0;
        int n = x;
        
        while (x > 0)
        {
            int rem = x % 10;
            reverse = reverse*10 + rem;
            x = x / 10;
        }
        
        return n ==  reverse;
    }
}