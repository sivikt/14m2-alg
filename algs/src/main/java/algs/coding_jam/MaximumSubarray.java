package algs.coding_jam;

/*
Given an integer array nums, find the contiguous subarray
(containing at least one number) which has the largest sum and return its sum.

Example:

Input: [-2,1,-3,4,-1,2,1,-5,4],
Output: 6
Explanation: [4,-1,2,1] has the largest sum = 6.
Follow up:

If you have figured out the O(n) solution, try coding another solution using
the divide and conquer approach, which is more subtle.
*/
class MaximumSubarray {
    public int maxOnPart(int l, int r, int[] nums) {
        if (l == r)
            return nums[l];
        
        int lmax = Integer.MIN_VALUE;
        int rmax = Integer.MIN_VALUE;
        int m = (l + r)/2;
        
        for (int sum = 0, i = m; i >= l; i--)
        {
            sum += nums[i];
            lmax = Math.max(sum, lmax);
        }
        
        for (int sum = 0, i = m+1; i <= r; i++)
        {
            sum += nums[i];
            rmax = Math.max(sum, rmax);
        }
        
        return Math.max(
            maxOnPart(l, m, nums), 
            Math.max(maxOnPart(m+1, r, nums), rmax+lmax)
        );
    }
    
    // divide and conquer
    public int maxSubArray3(int[] nums) {
        if (nums.length == 0)
            return 0;
        else if (nums.length == 1)
            return nums[0];
        else if (nums.length == 2)
            return Math.max(Math.max(nums[0], nums[1]), nums[0]+nums[1]);
       
        return maxOnPart(0, nums.length-1, nums);
    }


////


    public int maxSubArray(int[] nums) {
        if (nums.length == 0)
            return 0;
        else if (nums.length == 1)
            return nums[0];
        else if (nums.length == 2)
            return Math.max(Math.max(nums[0], nums[1]), nums[0]+nums[1]);
       
        int max = nums[0];
    
        for (int i = 1; i < nums.length; i++)
        {
            nums[i] = Math.max(nums[i], nums[i]+nums[i-1]);
            max = Math.max(nums[i], max);
        }
        
        return max;
    }


////


    public int maxSubArray2(int[] nums) {
        if (nums.length == 0)
            return 0;
        else if (nums.length == 1)
            return nums[0];
        else if (nums.length == 2)
            return Math.max(Math.max(nums[0], nums[1]), nums[0]+nums[1]);
       
        int max = nums[0];
        int pre = max;
    
        for (int i = 1; i < nums.length; i++)
        {
            pre = Math.max(nums[i], nums[i]+pre);
            max = Math.max(pre, max);
        }
        
        return max;
    }
}