package algs.coding_jam;

// Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
// Find all unique triplets in the array which gives the sum of zero.

// Note:

// The solution set must not contain duplicate triplets.

// Example:

// Given array nums = [-1, 0, 1, 2, -1, -4],

// A solution set is:
// [
//   [-1, 0, 1],
//   [-1, -1, 2]
// ]

import java.util.*;
import java.util.stream.Collectors;


public class ThreeSum {
    private static class Triple {
        final int a;
        final int b;
        final int c;

        public Triple(int a, int b, int c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public boolean equals(Object obj) {
            Triple that = (Triple) obj;
            return (this.a == that.a && this.b == that.b && this.c == that.c);
        }

        @Override
        public int hashCode() {
            return Objects.hash(a, b, c);
        }
    }

    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        Set<Triple> result = new HashSet<>();
        
        if ((nums[0] > 0) || (nums[nums.length-1] < 0))
            return new ArrayList<>();
        
        for (int i = 0; i < nums.length-2; i++)
        {
            int a = nums[i];
            
            for (int j = i+1; j < nums.length-1; j++)
            {
                int b = nums[j];
                int c = -(a+b);
                
                int cIdx = Arrays.binarySearch(nums, j+1, nums.length, c);
                //System.out.println(a + " " + b + " " + cIdx + " " + c);
                
                if (cIdx >= 0) 
                {
                    result.add(new Triple(a, b, c));
                }
            }
        }
        
        return result.stream().map(t -> Arrays.asList(t.a, t.b, t.c)).collect(Collectors.toList());
    }


    public List<List<Integer>> threeSum2(int[] nums) {
        Arrays.sort(nums);

        if ((nums.length == 0) || ((nums[0] > 0) || (nums[nums.length-1] < 0)))
            return new ArrayList<>();

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length-2; i++)
        {
            if ((i == 0) || (nums[i] != nums[i-1]))
            {
                int lo = i+1;
                int hi = nums.length-1;
                int a = nums[i];
                int antiA = -a;

                // solution for 2-sum problem
                while (lo < hi)
                {
                    int complement = nums[lo] + nums[hi];
                    if (complement == antiA)
                    {
                        result.add(Arrays.asList(a, nums[lo], nums[hi]));

                        while ((lo < hi) && (nums[lo] == nums[lo+1])) lo++;
                        while ((hi > lo) && (nums[hi] == nums[hi-1])) hi--;
                        lo++;
                        hi--;
                    }
                    else if (complement < antiA)
                        lo++;
                    else
                        hi--;
                }

            }
        }

        return result;
    }
}