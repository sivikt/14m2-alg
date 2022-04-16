"""
Given a non-empty array nums containing only positive integers, find if the array can be
partitioned into two subsets such that the sum of elements in both subsets is equal.

Example 1:
Input: nums = [1,5,11,5]
Output: true
Explanation: The array can be partitioned as [1, 5, 5] and [11].

Example 2:
Input: nums = [1,2,3,5]
Output: false
Explanation: The array cannot be partitioned into equal sum subsets.

Constraints:
    1 <= nums.length <= 200
    1 <= nums[i] <= 100
"""
from typing import List


class Solution:
    def dp_brute_force(self, nums: List[int], hist, i, target_sum) -> bool:
        if i >= len(nums):
            return False

        if (i, target_sum) in hist:
            # do not repeat already obtained solution
            return hist[(i, target_sum)]

        if target_sum == 0:
            hist[(i, target_sum)] = True
            return True
        elif target_sum < 0:
            return False
        else:
            hist[(i, target_sum)] = self.dp_brute_force(nums, hist, i + 1, target_sum - nums[i]) or \
                                    self.dp_brute_force(nums, hist, i + 1, target_sum)
            return hist[(i, target_sum)]

    def canPartition(self, nums: List[int]) -> bool:
        if len(nums) < 2:
            return False

        total = sum(nums)
        half = total // 2

        if total % 2 != 0:
            # can not divide odd numbers equally
            return False

        # memoization
        hist = {}

        return self.dp_brute_force(nums, hist, 0, half)


class Solution2:
    def canPartition(self, nums: List[int]) -> bool:
        if len(nums) < 2:
            return False

        total = sum(nums)
        half = total // 2

        if total % 2 != 0:
            # can not divide odd numbers equally
            return False

        hist = [[0] * (half + 1) for _ in range(len(nums) + 1)]

        for i in range(1, len(nums) + 1):
            for j in range(1, half + 1):
                if nums[i - 1] <= j:
                    hist[i][j] = max(
                        hist[i - 1][j],
                        hist[i - 1][j - nums[i - 1]] + nums[i - 1]
                    )
                else:
                    hist[i][j] = hist[i - 1][j]

                if hist[i][j] == half:
                    return True

        return False


class Solution3:
    def canPartition(self, nums: List[int]) -> bool:
        if len(nums) < 2:
            return False

        total = sum(nums)
        half = total // 2

        if total % 2 != 0:
            # can not divide odd numbers equally
            return False

        hist = [[False] * (half + 1) for _ in range(len(nums) + 1)]

        hist[0][0] = True   # we want to include items as is to start a "sum"

        for i in range(len(nums) + 1):
            for j in range(1, half + 1):
                if nums[i - 1] <= j:
                    hist[i][j] = hist[i - 1][j] or hist[i - 1][j - nums[i - 1]]
                else:
                    hist[i][j] = hist[i - 1][j]

                if hist[i][half]:
                    return True

        return False


class Solution4:
    # 2^n brute-force
    def canPartition(self, nums: List[int]) -> bool:
        total = sum(nums)
        if total & 1:
            return False
        current = {0}

        for n in nums:
            now = current.copy()
            for d in current:
                now.add(d + n)
            current = now.copy()

        return total // 2 in current


class Solution5:
    # 2^n brute-force
    def canPartition(self, nums):
        dp = {0}
        for num in nums:
            dp = {num - i for i in dp} | {num + i for i in dp}
        return 0 in dp


# The best optimal
class Solution6:
    def canPartition(self, nums: List[int]) -> bool:
        if len(nums) < 2:
            return False

        total = sum(nums)
        half = total // 2

        if total % 2 != 0:
            # can not divide odd numbers equally
            return False

        hist = [False] * (half + 1)

        hist[0] = True  # we want to include items as is to start a "sum"

        # do up-to-bottom
        for num in nums:
            for j in range(half, num-1, -1):
                hist[j] = hist[j] or hist[j-num]

                if hist[half]:
                    return True

        return False


if __name__ == '__main__':
    assert not Solution2().canPartition(nums=[1])
    assert Solution2().canPartition(nums=[1,5,10,6])
    assert Solution2().canPartition(nums=[1,5,11,5])
    assert not Solution2().canPartition(nums=[2,2,3,5])
    assert Solution2().canPartition(nums=[2,2,3,3])
    assert Solution2().canPartition(nums=[14,9,8,4,3,2])
    assert not Solution2().canPartition(nums=[1,3,4,4])
    assert Solution2().canPartition(nums=[2,2,1,1])
    assert not Solution2().canPartition(nums=[100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,99,97])

    assert not Solution3().canPartition(nums=[1])
    assert Solution3().canPartition(nums=[1,5,10,6])
    assert Solution3().canPartition(nums=[1,5,11,5])
    assert not Solution3().canPartition(nums=[2,2,3,5])
    assert Solution3().canPartition(nums=[2,2,3,3])
    assert Solution3().canPartition(nums=[14,9,8,4,3,2])
    assert not Solution3().canPartition(nums=[1,3,4,4])
    assert Solution3().canPartition(nums=[2,2,1,1])
    assert not Solution3().canPartition(nums=[100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,
                                          100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,99,97])