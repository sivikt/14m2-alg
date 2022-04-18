package algs.coding_jam;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given the array nums, for each nums[i] find out how many numbers in the array are smaller than it.
 * That is, for each nums[i] you have to count the number of valid j's such that j != i and nums[j] < nums[i].
 *
 * Return the answer in an array.
 *
 * Example 1:
 * Input: nums = [8,1,2,2,3]
 * Output: [4,0,1,1,3]
 * Explanation:
 * For nums[0]=8 there exist four smaller numbers than it (1, 2, 2 and 3).
 * For nums[1]=1 does not exist any smaller number than it.
 * For nums[2]=2 there exist one smaller number than it (1).
 * For nums[3]=2 there exist one smaller number than it (1).
 * For nums[4]=3 there exist three smaller numbers than it (1, 2 and 2).
 *
 * Example 2:
 * Input: nums = [6,5,4,8]
 * Output: [2,1,0,3]
 *
 * Example 3:
 * Input: nums = [7,7,7,7]
 * Output: [0,0,0,0]
 *
 * Constraints:
 * 2 <= nums.length <= 500
 * 0 <= nums[i] <= 100
 */
public class HowManyNumbersAreSmallerThanTheCurrentNumber {

    public int[] smallerNumbersThanCurrent(int[] nums) {
        int[] res = new int[nums.length];
        Integer[] idx = new Integer[nums.length];

        for (int i = 0; i < nums.length; i++)
            idx[i] = i;

        Arrays.sort(idx, Comparator.comparingInt(a -> nums[a]));

        for (int i = 0; i < nums.length; i++) {
            int j = i;

            while (j >= 0 && nums[idx[j]] == nums[idx[i]]) {
                j--;
            }

            res[idx[i]] = j+1;
        }

        return res;
    }

    public int[] smallerNumbersThanCurrent_v2(int[] nums) {
        int[] res = new int[nums.length];
        Integer[] idx = new Integer[nums.length];

        for (int i = 0; i < nums.length; i++)
            idx[i] = i;

        Arrays.sort(idx, (a, b) -> nums[a] - nums[b]);

        int r = 0;
        res[idx[0]] = r;

        for (int i = 1; i < nums.length; i++)
        {
            if (nums[idx[i-1]] != nums[idx[i]])
                r = i;

            res[idx[i]] = r;
        }

        return res;
    }

    public int[] smallerNumbersThanCurrent_v3(int[] nums) {
        final int MAX_NUMBER = 100;

        int[] count = new int[MAX_NUMBER + 1];
        int[] res = new int[nums.length];

        for (int num : nums)
            count[num]++;

        for (int i = 1 ; i <= MAX_NUMBER; i++)
            count[i] += count[i-1];

        for (int i = 0; i < nums.length; i++)
        {
            if (nums[i] == 0) // the smallest
                res[i] = 0;
            else
                res[i] = count[nums[i] - 1];
        }

        return res;
    }
}
