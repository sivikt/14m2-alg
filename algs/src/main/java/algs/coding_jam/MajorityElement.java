package algs.coding_jam;

// Given an array of size n, find the majority element.
// The majority element is the element that appears 
// more than ⌊ n/2 ⌋ times.

// You may assume that the array is non-empty and 
// the majority element always exist in the array.

// Example 1:

// Input: [3,2,3]
// Output: 3
// Example 2:

// Input: [2,2,1,1,1,2,2]
// Output: 2


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class MajorityElement {
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        
        if (nums.length < 3)
            return nums[0];
                          
        int c = 1;
        int m = 0;
        int max = nums[0];
        
        for (int i = 1; i < nums.length; i++) // redundant
        {
            if (nums[i] == nums[i-1])
                c++;
            else 
            {
                if (c > m)
                {
                    max = nums[i-1];
                    m = c;
                }
                
                c = 1;
            }
        }
        
        if (c > m)
            return nums[nums.length-1];
        else        
            return max;
    }


////

    
    public int majorityElement2(int[] nums) {
        Arrays.sort(nums);
        
        if (nums.length < 3)
            return nums[0];
        else
            return nums[nums.length/2];
    }


////


    private Map<Integer, Integer> countNums(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        
        for (int num : nums) 
        {
            if (!counts.containsKey(num))
                counts.put(num, 1);
            else 
                counts.put(num, counts.get(num)+1);
        }
        
        return counts;
    }

    public int majorityElement3(int[] nums) {
        Map<Integer, Integer> counts = countNums(nums);

        Map.Entry<Integer, Integer> majorityEntry = null;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) 
        {
            if (majorityEntry == null || entry.getValue() > majorityEntry.getValue()) 
                majorityEntry = entry;
        }

        return majorityEntry.getKey();
    }


/////

    /** Randomized
        Complexity Analysis

        Time complexity : O(∞)

        It is technically possible for this algorithm to run indefinitely (if we never manage to randomly select the majority
        element), so the worst possible runtime is unbounded. However, the expected runtime is far better - linear, in fact.
        For ease of analysis, convince yourself that because the majority element is guaranteed to occupy more than half of the
        array, the expected number of iterations will be less than it would be if the element we sought occupied exactly half
        of the array. Therefore, we can calculate the expected number of iterations for this modified version of the problem
        and assert that our version is easier.


        Because the series converges, the expected number of iterations for the modified problem is constant.
        Based on an expected-constant number of iterations in which we perform linear work, the expected runtime is
        linear for the modifed problem. Therefore, the expected runtime for our problem is also linear, as the runtime
        of the modifed problem serves as an upper bound for it.

        Space complexity : O(1)O(1)

        Much like the brute force solution, the randomized approach runs with constant additional space.
     */
    private int randRange(Random rand, int min, int max) {
        return rand.nextInt(max - min) + min;
    }

    private int countOccurences(int[] nums, int num) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == num) {
                count++;
            }
        }

        return count;
    }

    public int majorityElement4(int[] nums) {
        Random rand = new Random();

        int majorityCount = nums.length/2;

        while (true) {
            int candidate = nums[randRange(rand, 0, nums.length)];
            if (countOccurences(nums, candidate) > majorityCount) {
                return candidate;
            }
        }
    }

////

    private int countInRange(int[] nums, int num, int lo, int hi) {
        int count = 0;
        for (int i = lo; i <= hi; i++) {
            if (nums[i] == num) {
                count++;
            }
        }
        return count;
    }

    private int majorityElementRec(int[] nums, int lo, int hi) {
        // base case; the only element in an array of size 1 is the majority
        // element.
        if (lo == hi) {
            return nums[lo];
        }

        // recurse on left and right halves of this slice.
        int mid = (hi-lo)/2 + lo;
        int left = majorityElementRec(nums, lo, mid);
        int right = majorityElementRec(nums, mid+1, hi);

        // if the two halves agree on the majority element, return it.
        if (left == right) {
            return left;
        }

        // otherwise, count each element and return the "winner".
        int leftCount = countInRange(nums, left, lo, hi);
        int rightCount = countInRange(nums, right, lo, hi);

        return leftCount > rightCount ? left : right;
    }

    public int majorityElement5(int[] nums) {
        return majorityElementRec(nums, 0, nums.length-1);
    }


    /** Boyer-Moore Voting Algorithm
        Intuition
        If we had some way of counting instances of the majority element as +1+1 and instances of any other element as -1−1,
        summing them would make it obvious that the majority element is indeed the majority element.

        Algorithm
        Essentially, what Boyer-Moore does is look for a suffix sufsuf of nums where suf[0]suf[0] is the majority element
        in that suffix. To do this, we maintain a count, which is incremented whenever we see an instance of our current
        candidate for majority element and decremented whenever we see anything else. Whenever count equals 0, we effectively
        forget about everything in nums up to the current index and consider the current number as the candidate for majority
        element. It is not immediately obvious why we can get away with forgetting prefixes of nums - consider the following
        examples (pipes are inserted to separate runs of nonzero count).

        [7, 7, 5, 7, 5, 1 | 5, 7 | 5, 5, 7, 7 | 7, 7, 7, 7]

        Here, the 7 at index 0 is selected to be the first candidate for majority element. count will eventually reach 0 after
        index 5 is processed, so the 5 at index 6 will be the next candidate. In this case, 7 is the true majority element, so
        by disregarding this prefix, we are ignoring an equal number of majority and minority elements - therefore, 7 will still
        be the majority element in the suffix formed by throwing away the first prefix.

        [7, 7, 5, 7, 5, 1 | 5, 7 | 5, 5, 7, 7 | 5, 5, 5, 5]

        Now, the majority element is 5 (we changed the last run of the array from 7s to 5s), but our first candidate is still 7.
        In this case, our candidate is not the true majority element, but we still cannot discard more majority elements than
        minority elements (this would imply that count could reach -1 before we reassign candidate, which is obviously false).

        Therefore, given that it is impossible (in both cases) to discard more majority elements than minority elements, we are
        safe in discarding the prefix and attempting to recursively solve the majority element problem for the suffix.
        Eventually, a suffix will be found for which count does not hit 0, and the majority element of that suffix will
        necessarily be the same as the majority element of the overall array.
    */
    public int majorityElementBoyerMoore(int[] nums) {
        int count = 0;
        Integer candidate = null;

        for (int num : nums) {
            if (count == 0) {
                candidate = num;
            }
            count += (num == candidate) ? 1 : -1;
        }

        return candidate;
    }

}	