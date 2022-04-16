package algs.coding_jam;

//
// Given an array of integers, return indices of the two
// numbers such that they add up to a specific target.

// You may assume that each input would have exactly one
// solution, and you may not use the same element twice.

// Example:

// Given nums = [2, 7, 11, 15], target = 9,

// Because nums[0] + nums[1] = 2 + 7 = 9,
// return [0, 1].
//

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class TwoSum {

    // O(n*log(n)) time
    // O(n) memory
    public int[] twoSum(int[] numbers, int target) {
        Integer[] indeces = new Integer[numbers.length];
        
        for (int i = 0; i < numbers.length; i++) 
            indeces[i] = i;
            
        
        Arrays.sort(indeces, (i1, i2) -> numbers[i1]-numbers[i2]);
        
        int[] result = new int[2];

        int lo = 0;
        int hi = numbers.length - 1;

        while (lo < hi)
        {
            int sum = numbers[indeces[lo]] + numbers[indeces[hi]];
            if (sum == target)
            {
                result[0] = indeces[lo];
                result[1] = indeces[hi];
                break;
            }
            else if (sum < target)
                lo++;
            else
                hi--;
        }

        return result;
    }


    // O(n) time
    // O(n) memory
    public int[] twoSum2(int[] numbers, int target) {
        Map<Integer, Integer> pairs = new HashMap<>();
                  
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        
        for (int i = 0; i < numbers.length; i++) 
        {
            int b = target - numbers[i];
            
            if (pairs.containsKey(b) )
            {
                result[0] = pairs.get(b);
                result[1] = i;
                break;
            }
            
            pairs.put(numbers[i], i);    
        }        

        return result;
    }

}