package algs.coding_jam;

// Given an array with n integers, your task is to check if it
// could become non-decreasing by modifying at most 1 element.

// We define an array is non-decreasing if array[i] <= array[i + 1] holds for every i (1 <= i < n).

// Example 1:
// Input: [4,2,3]
// Output: True
// Explanation: You could modify the first 4 to 1 to get a non-decreasing array.

// Example 2:
// Input: [4,2,1]
// Output: False
// Explanation: You can't get a non-decreasing array by modify at most one element.
// Note: The n belongs to [1, 10,000].


class NonDecreasingArray {
    public boolean checkPossibility(int[] nums) {
        if (nums.length < 3)
            return true;
        
        int d = 0;
        int i = 1;
        
        for (; i < nums.length-1; i++)
        {
            int a = nums[i-1];
            int b = nums[i];
            int c = nums[i+1];
                        
            if (a <= b && b <= c) // 123
                continue;
            else if (a > b) // 321, 312
            {
                if (b > c)
                    return false;
                else
                    nums[i-1] = b;
            }
            else if (a < b) // 231, 132
            {
                if (a > c)
                    nums[i+1] = b;
                else
                    nums[i] = c;    
            }
            else if (a == b) // 332
            {
                nums[i+1] = b;
            }
            
            d++;
            
            if (d > 1)
                return false;
        }
        
        return d <= 1 && nums[i-1] <= nums[i];
    }
}