"""
Given two sorted arrays nums1 and nums2 of size m and n respectively, return the median of the two sorted arrays.

The overall run time complexity should be O(log (m+n)).

Example 1:
Input: nums1 = [1,3], nums2 = [2]
Output: 2.00000
Explanation: merged array = [1,2,3] and median is 2.

Example 2:
Input: nums1 = [1,2], nums2 = [3,4]
Output: 2.50000
Explanation: merged array = [1,2,3,4] and median is (2 + 3) / 2 = 2.5.

Constraints:
    nums1.length == m
    nums2.length == n
    0 <= m <= 1000
    0 <= n <= 1000
    1 <= m + n <= 2000
    -10^6 <= nums1[i], nums2[i] <= 10^6
"""

from typing import List
import math


class Solution:
    def findMedian(self, nums: List[int]) -> float:
        m = len(nums) // 2
        if len(nums) % 2 == 1:
            return nums[m]
        else:
            return (nums[m] + nums[m - 1]) / 2

    def findMedianSortedArrays(self, nums1: List[int], nums2: List[int]) -> float:
        if len(nums1) == 0:
            return self.findMedian(nums2)
        elif len(nums2) == 0:
            return self.findMedian(nums1)
        else:
            # apply merge-sort approach for two sorted arrays
            N1, N2 = len(nums1), len(nums2)
            i = j = 0
            l = r = 0
            m = (N1 + N2) // 2

            while (i + j <= m) and (i < N1) and (j < N2):
                l = r
                if nums1[i] <= nums2[j]:
                    r = nums1[i]
                    i += 1
                else:
                    r = nums2[j]
                    j += 1

            if i + j <= m:
                if i == N1:
                    k = m - i
                    l = nums2[k - 1] if k > j else r
                    r = nums2[k]
                elif j == N2:
                    k = m - j
                    l = nums1[k - 1] if k > i else r
                    r = nums1[k]

            if (N1 + N2) % 2 == 1:
                return r
            else:
                return (l + r) / 2


class Solution2:
    def findMedian(self, nums: List[int]) -> float:
        m = len(nums) // 2
        if len(nums) % 2 == 1:
            return nums[m]
        else:
            return (nums[m] + nums[m - 1]) / 2

    def findMedianSortedArrays(self, nums1: List[int], nums2: List[int]) -> float:
        if len(nums1) > len(nums2):
            tmp = nums1
            nums1 = nums2
            nums2 = tmp

        N1 = len(nums1)
        N2 = len(nums2)
        M = (N1 + N2 - 2) // 2

        if N1 == 0:
            return self.findMedian(nums2)
        elif N2 == 0:
            return self.findMedian(nums1)
        else:
            lo1 = 0
            hi1 = N1

            while lo1 <= hi1:
                m1 = (lo1 + hi1) // 2
                m2 = M - m1

                x1 = -math.inf if m1 == 0 else nums1[m1 - 1]
                y1 = math.inf if m1 == N1 else nums1[m1]
                x2 = -math.inf if m2 == 0 else nums2[m2 - 1]
                y2 = math.inf if m2 == N2 else nums2[m2]

                if x1 > y2:
                    hi1 = m1 - 1
                elif x2 > y1:
                    lo1 = m1 + 1
                # (x1 <= y2 and x2 <= y1) meaning all elements below m2 are also below m1
                # and all elements below m1 are also below m2 and only m1 and m2 are candidates
                # to be medians
                else:
                    z1 = math.inf if m1 + 1 >= N1 else nums1[m1 + 1]
                    z2 = math.inf if m2 + 1 >= N2 else nums2[m2 + 1]
                    z = min(z1, z2)

                    if (N1 + N2) % 2 == 0:  # is even
                        if y1 > y2:
                            return (y2 + min(y1, z)) / 2
                        else:
                            return (y1 + min(y2, z)) / 2
                    else:
                        if y1 > y2:
                            return min(y1, z)
                        else:
                            return min(y2, z)

            raise Exception('Algorithm error')


if __name__ == '__main__':
    a = [1, 3]
    b = [2]
    assert 2.0 == Solution().findMedianSortedArrays(a,b)
    assert 2.0 == Solution2().findMedianSortedArrays(a,b)

    a = [1, 2]
    b = [3, 4]
    assert 2.5 == Solution().findMedianSortedArrays(a,b)
    assert 2.5 == Solution2().findMedianSortedArrays(a,b)

    a = [1]
    b = [2, 3, 4]
    assert 2.5 == Solution().findMedianSortedArrays(a,b)
    assert 2.5 == Solution2().findMedianSortedArrays(a,b)

    a = []
    b = [2]
    assert 2.0 == Solution().findMedianSortedArrays(a,b)
    assert 2.0 == Solution2().findMedianSortedArrays(a,b)

    a = [3]
    b = []
    assert 3.0 == Solution().findMedianSortedArrays(a,b)
    assert 3.0 == Solution2().findMedianSortedArrays(a,b)

    a = [1, 2, 3]
    b = []
    assert 2.0 == Solution().findMedianSortedArrays(a,b)
    assert 2.0 == Solution2().findMedianSortedArrays(a,b)

    a = [1, 3, 4, 4]
    b = []
    assert 3.5 == Solution().findMedianSortedArrays(a,b)
    assert 3.5 == Solution2().findMedianSortedArrays(a,b)

    a = [1, 3]
    b = [2, 6, 7]
    assert 3.0 == Solution().findMedianSortedArrays(a,b)
    assert 3.0 == Solution2().findMedianSortedArrays(a,b)

    a = [1, 2]
    b = [3, 6, 7]
    assert 3.0 == Solution().findMedianSortedArrays(a,b)
    assert 3.0 == Solution2().findMedianSortedArrays(a,b)

    a = [0, 0, 0, 0, 0]
    b = [-1, 0, 0, 0, 0, 0, 1]
    assert 0.0 == Solution().findMedianSortedArrays(a,b)
    assert 0.0 == Solution2().findMedianSortedArrays(a,b)

    a = [1,2,3,4]
    b = [20,90, 100, 100]
    assert 12 == Solution().findMedianSortedArrays(a,b)
    assert 12 == Solution2().findMedianSortedArrays(a,b)

    a = [3,4]
    b = [2,9]
    assert 3.5 == Solution().findMedianSortedArrays(a,b)
    assert 3.5 == Solution2().findMedianSortedArrays(a,b)


    a = [3,3,3,3]
    b = [3,3,3,3]
    assert 3 == Solution().findMedianSortedArrays(a,b)
    assert 3 == Solution2().findMedianSortedArrays(a,b)

    a = [3,3,3]
    b = [3,3,3]
    assert 3 == Solution().findMedianSortedArrays(a,b)
    assert 3 == Solution2().findMedianSortedArrays(a,b)

    a = [1,3,7,9,10]
    b = [0,4,5,7,8,10,11]
    assert 7 == Solution().findMedianSortedArrays(a,b)
    assert 7 == Solution2().findMedianSortedArrays(a,b)

    a = [1,3,7,9,10]
    b = [0,4,5,8,8,10,11]
    assert 7.5 == Solution().findMedianSortedArrays(a,b)
    assert 7.5 == Solution2().findMedianSortedArrays(a,b)

    a = [1,10,12,13,14]
    b = [0,4,5,10,11,15,16]
    assert 10.5 == Solution().findMedianSortedArrays(a,b)
    assert 10.5 == Solution2().findMedianSortedArrays(a,b)

    a = [1,3,5]
    b = [0,4]
    assert 3 == Solution().findMedianSortedArrays(a,b)
    assert 3 == Solution2().findMedianSortedArrays(a,b)

    a = [1]
    b = [0,4]
    assert 1 == Solution().findMedianSortedArrays(a,b)
    assert 1 == Solution2().findMedianSortedArrays(a,b)

    a = [1,3,5]
    b = [0,4]
    assert 3 == Solution().findMedianSortedArrays(a,b)
    assert 3 == Solution2().findMedianSortedArrays(a,b)

    a = [1,10,12,13]
    b = [0,4,5,10,11,15,17]
    assert 10 == Solution().findMedianSortedArrays(a,b)
    assert 10 == Solution2().findMedianSortedArrays(a,b)

    a = [1,10,12,13,14,21]
    b = [0,4,5,10,11,15,17]
    assert 11 == Solution().findMedianSortedArrays(a,b)
    assert 11 == Solution2().findMedianSortedArrays(a,b)

    a = [1]
    b = [2,3,4]
    assert 2.5 == Solution().findMedianSortedArrays(a,b)
    assert 2.5 == Solution2().findMedianSortedArrays(a,b)

    a = [2,3]
    b = [1,4,5]
    assert 3 == Solution().findMedianSortedArrays(a,b)
    assert 3 == Solution2().findMedianSortedArrays(a,b)