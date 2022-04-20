package algs.coding_jam;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/*Given a list of positive integers nums and an int target,
return indices of the two numbers such that they add up to a target - 30.

Conditions:

You will pick exactly 2 numbers.
You cannot pick the same element twice.
If you have muliple pairs, select the pair with the largest number.
Example 1:

Input: nums = [1, 10, 25, 35, 60], target = 90
Output: [2, 3]
Explanation:
nums[2] + nums[3] = 25 + 35 = 60 = 90 - 30
Example 2:

Input: nums = [20, 50, 40, 25, 30, 10], target = 90
Output: [1, 5]
Explanation:
nums[0] + nums[2] = 20 + 40 = 60 = 90 - 30
nums[1] + nums[5] = 50 + 10 = 60 = 90 - 30
You should return the pair with the largest number.
*/

public class FindPairWithGivenSum {

    // O(n*log(n)) time
    // O(n) memory
    public static int[] twoSum1(int[] numbers, int target) {
        Integer[] indeces = new Integer[numbers.length];

        for (int i = 0; i < numbers.length; i++)
            indeces[i] = i;


        Arrays.sort(indeces, (i1, i2) -> numbers[i1]-numbers[i2]);

        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;

        int lo = 0;
        int hi = numbers.length - 1;

        while (lo < hi)
        {
            int sum = numbers[indeces[lo]] + numbers[indeces[hi]];
            if (sum == target)
            {
                if (indeces[lo] > result[0] && indeces[hi] > result[1])
                {
                    result[0] = indeces[lo];
                    result[1] = indeces[hi];
                }
                lo++;
                hi--;
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
    public static int[] twoSum2(int[] numbers, int target) {
        Map<Integer, Integer> pairs = new HashMap<>();

        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;

        int maxNumber = Integer.MIN_VALUE;

        for (int i = 0; i < numbers.length; i++)
        {
            int a = numbers[i];
            int b = target - a;

            if (pairs.containsKey(b) && (a > maxNumber || b > maxNumber))
            {
                result[0] = pairs.get(b);
                result[1] = i;
                maxNumber = Math.max(a, b);
            }

            pairs.put(numbers[i], i);
        }

        return result;
    }

    public static void main(String[] args) {
        test(new int[] {1, 10, 25, 35, 60}, 60);
        test(new int[] {20, 50, 40, 25, 30, 10}, 60);
        test(new int[] {5, 55, 40, 20, 30, 30}, 60);
    }

    private static void test(int[] nums, int target) {
        System.out.println(Arrays.toString(twoSum2(nums, target)));
    }

}
