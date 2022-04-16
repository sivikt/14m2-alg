"""
A peak element is an element that is strictly greater than its neighbors.

Given an integer array nums, find a peak element, and return its index.
If the array contains multiple peaks, return the index to any of the peaks.

You may imagine that nums[-1] = nums[n] = -∞.

You must write an algorithm that runs in O(log n) time.

Example 1:
Input: nums = [1,2,3,1]
Output: 2
Explanation: 3 is a peak element and your function should return the index number 2.

Example 2:
Input: nums = [1,2,1,3,5,6,4]
Output: 5
Explanation: Your function can return either index number 1 where the peak
element is 2, or index number 5 where the peak element is 6.

Constraints:
    1 <= nums.length <= 1000
    -231 <= nums[i] <= 231 - 1
    nums[i] != nums[i + 1] for all valid i.
"""
class Solution(object):
    def findPeakElement(self, nums):
        """
        :type nums: List[int]
        :rtype: int
        """
        max_hi = len(nums) - 1
        hi = max_hi
        lo = 0
        m = 0

        while hi >= lo:
            m = (lo + hi) / 2

            l = nums[m - 1] if m > 0 else None
            r = nums[m + 1] if m < max_hi else None

            if (l is None or (l < nums[m])) and (r is None or (nums[m] > r)):
                break
            elif r > nums[m]:
                # go right
                lo = m + 1
            else:
                # go left
                hi = m - 1

        return m
