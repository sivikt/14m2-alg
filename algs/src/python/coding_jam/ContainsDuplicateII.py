"""
Given an integer array nums and an integer k,
return true if there are two distinct indices i and j
in the array such that nums[i] == nums[j] and abs(i - j) <= k.

Example 1:
Input: nums = [1,2,3,1], k = 3
Output: true

Example 2:
Input: nums = [1,0,1,1], k = 1
Output: true

Example 3:
Input: nums = [1,2,3,1,2,3], k = 2
Output: false

Constraints:
    1 <= nums.length <= 10^5
    -10^9 <= nums[i] <= 10^9
    0 <= k <= 10^5
"""
class Solution:
    def containsNearbyDuplicate(self, nums: List[int], k: int) -> bool:
        indices = list(range(len(nums)))

        indices.sort(key=lambda k: nums[k])

        for i, e in enumerate(indices[:-1]):
            if nums[e] == nums[indices[i + 1]]:
                if indices[i + 1] - e <= k:
                    return True

        return False

    def containsNearbyDuplicate_v2(self, nums: List[int], k: int) -> bool:
        indices = {}

        for i, e in enumerate(nums):
            if e in indices:
                if i - indices[e] <= k:
                    return True
                else:
                    indices[e] = i
            else:
                indices[e] = i

        return False