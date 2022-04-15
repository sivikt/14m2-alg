// Given an integer array, you need to find one continuous subarray that if 
// you only sort this subarray in ascending order, then the whole array will
// be sorted in ascending order, too.

// You need to find the shortest such subarray and output its length.

// Example 1:
// Input: [2, 6, 4, 8, 10, 9, 15]
// Output: 5
// Explanation: You need to sort [6, 4, 8, 10, 9] in ascending order to make 
//              the whole array sorted in ascending order.

// Note:
// Then length of the input array is in range [1, 10,000].
// The input array may contain duplicates, so ascending order here means <=.


class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int[] cp = new int[nums.length];
        System.arraycopy(nums, 0, cp, 0, nums.length);
        Arrays.sort(cp);
        
        int l = 0;
        for (; l < nums.length; l++)
            if (nums[l] != cp[l])
                break;
        
        int r = nums.length - 1;
        for (; r >= 0; r--)
            if (nums[r] != cp[r])
                break;
        
        return (l < nums.length) ? r-l+1 : 0;
    }


////



}