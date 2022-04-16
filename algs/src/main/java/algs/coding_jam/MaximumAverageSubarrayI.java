// Given an array consisting of n integers, find the contiguous subarray of 
// given length k that has the maximum average value.
// And you need to output the maximum average value.


// Example 1:
// Input: [1,12,-5,-6,50,3], k = 4
// Output: 12.75
// Explanation: Maximum average is (12-5-6+50)/4 = 51/4 = 12.75
 

// Note:
// 1 <= k <= n <= 30,000.
// Elements of the given array will be in the range [-10,000, 10,000].


class Solution {
    public double findMaxAverage(int[] nums, int k) {
        int N = nums.length;
        
        int[] prefs = new int[N];
        prefs[0] = nums[0];
        
        for (int i = 1; i < N; i++)
            prefs[i] = prefs[i-1] + nums[i];
        
        double max = prefs[k-1];
        
        for (int i = k; i < N; i++)
        {
            int s = prefs[i] - prefs[i-k];
            max = Math.max(max, s);
        }
        
        return max/k;
    }


/////


    public double findMaxAverage2(int[] nums, int k) {
        int s = 0;
        
        for (int i = 0; i < k; i++)
            s += nums[i];
        
        int max = s;
        
        for (int i = k; i < nums.length; i++)
        {
            s += nums[i] - nums[i-k];
            max = Math.max(max, s);
        }
        
        return (double)max/k;
    }
}