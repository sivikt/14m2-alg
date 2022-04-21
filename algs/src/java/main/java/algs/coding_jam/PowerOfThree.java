package algs.coding_jam;

/*
Given an integer, write a function to determine if it is a power of three.

Example 1:

Input: 27
Output: true
Example 2:

Input: 0
Output: false
Example 3:

Input: 9
Output: true
Example 4:

Input: 45
Output: false
Follow up:
Could you do it without using any loop / recursion?
*/
class PowerOfThree {
    public boolean isPowerOfThree(int n) {
        // 3^20 = 3,486,784,401 which is greater than max possible int 2^31 = 2,147,483,648
        // 3^19 = 1,162,261,467 which is therefore the greatest power of 3 possble to be stored in an int
        
        int max = 1162261467;
        
        //any number raised to 0 == 1
        if (n == 1)
            return true;
		//eliminates 0 and negative numbers
        else if (n < 1)
            return false;
        //else if n perfectly divides into the highest resultant power of 3 then it
        //is a power of 3 itself
        else if (max%n == 0)
            return true;
        else
            return false;        
    }
    

////


    // simple math formula to compute log_3(N)
    static int max_pow3 = (int) (Math.log10(Integer.MAX_VALUE) / Math.log10(3)); 
    static int max_pow3_val = (int) (Math.pow(3, max_pow3)); 
    
    public boolean isPowerOfThree(int n) {
        if (n <= 0) 
            return false;
        return max_pow3_val % n == 0;     
    }
}