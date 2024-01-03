"""
Given two 0-indexed integer arrays nums1 and nums2, return a list answer of size 2 where:

    answer[0] is a list of all distinct integers in nums1 which are not present in nums2.
    answer[1] is a list of all distinct integers in nums2 which are not present in nums1.

Note that the integers in the lists may be returned in any order.

Example 1:
Input: nums1 = [1,2,3], nums2 = [2,4,6]
Output: [[1,3],[4,6]]
Explanation:
For nums1, nums1[1] = 2 is present at index 0 of nums2, whereas nums1[0] = 1 and nums1[2] = 3 are not present in nums2.
Therefore, answer[0] = [1,3].
For nums2, nums2[0] = 2 is present at index 1 of nums1, whereas nums2[1] = 4 and nums2[2] = 6 are not present in nums2.
Therefore, answer[1] = [4,6].

Example 2:
Input: nums1 = [1,2,3,3], nums2 = [1,1,2,2]
Output: [[3],[]]
Explanation:
For nums1, nums1[2] and nums1[3] are not present in nums2. Since nums1[2] == nums1[3],
their value is only included once and answer[0] = [3].
Every integer in nums2 is present in nums1. Therefore, answer[1] = [].


Constraints:
    1 <= nums1.length, nums2.length <= 1000
    -1000 <= nums1[i], nums2[i] <= 1000
"""

class Solution:
    def findDifference(self, nums1: List[int], nums2: List[int]) -> List[List[int]]:
        t = {}
        r = [[],[]]

        for n in nums1:
            if n not in t:
                t[n] = 1

        for n in nums2:
            if n not in t:
                t[n] = 2
            elif t[n] == 1:
                t[n] = 3

        for k, v in t.items():
            if v == 1:
                r[0].append(k)
            elif v == 2:
                r[1].append(k)

        return r

   def findDifference2(self, nums1: List[int], nums2: List[int]) -> List[List[int]]:
        r = [[],[]]

        nums1.sort()
        nums2.sort()

        i, j = 0, 0

        while i < len(nums1) and j < len(nums2):
            if nums1[i] < nums2[j]:
                if not r[0] or r[0][-1] != nums1[i]:
                    r[0].append(nums1[i])
                i += 1
            elif nums2[j] < nums1[i]:
                if not r[1] or r[1][-1] != nums2[j]:
                    r[1].append(nums2[j])
                j += 1
            else:
                i += 1
                j += 1
                while i < len(nums1) and nums1[i] == nums1[i-1]:
                    i += 1
                while j < len(nums2) and nums2[j] == nums2[j-1]:
                    j += 1

        while i < len(nums1):
            if nums2[-1] != nums1[i] and (not r[0] or r[0][-1] != nums1[i]):
                r[0].append(nums1[i])
            i += 1

        while j < len(nums2):
            if nums1[-1] != nums2[j] and (not r[1] or r[1][-1] != nums2[j]):
                r[1].append(nums2[j])
            j += 1

        return r
