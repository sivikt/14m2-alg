package algs.coding_jam;

// Given an array of integers, find if the array contains any duplicates.

// Your function should return true if any value appears at least twice 
// in the array, and it should return false if every element is distinct.

// Example 1:

// Input: [1,2,3,1]
// Output: true
// Example 2:

// Input: [1,2,3,4]
// Output: false
// Example 3:

// Input: [1,1,1,3,3,4,3,2,4,2]
// Output: true


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class ContainsDuplicate {
    public boolean containsDuplicate(int[] nums) {
        if (nums.length != 0)
        {
            Arrays.sort(nums);
            for (int i = 1; i < nums.length; i++)
            {
                if (nums[i-1] == nums[i])
                    return true;
            }
        }
        
        return false;
    }

////

	public boolean containsDuplicate2(int[] nums) {
	    Set<Integer> set = new HashSet<>(nums.length);
	    for (int x: nums) 
	    {
	        if (set.contains(x)) return true;
	        set.add(x);
	    }
	    return false;
	}
}