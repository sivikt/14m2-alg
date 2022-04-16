package algs.coding_jam;

// Given an array nums, write a function to move all 0's to the end
// of it while maintaining the relative order of the non-zero elements.

// Example:

// Input: [0,1,0,3,12]
// Output: [1,3,12,0,0]
// Note:

// You must do this in-place without making a copy of the array.
// Minimize the total number of operations.


class MoveZeroes {
    public void moveZeroes(int[] nums) {
        for (int i = 0, zeroi = -1; i < nums.length; i++)
        {
            if (nums[i] != 0 && zeroi >= 0)
            {
                nums[zeroi] = nums[i];
                nums[i] = 0;
                zeroi = ((i - zeroi) > 1) ? zeroi+1 : i;
            }
            else if (zeroi < 0)
                zeroi = i;
        }
    }
}