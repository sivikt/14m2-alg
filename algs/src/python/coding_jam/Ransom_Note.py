"""
Given two strings ransomNote and magazine, return true if ransomNote can be constructed
by using the letters from magazine and false otherwise.

Each letter in magazine can only be used once in ransomNote.

Example 1:
Input: ransomNote = "a", magazine = "b"
Output: false

Example 2:
Input: ransomNote = "aa", magazine = "ab"
Output: false

Example 3:
Input: ransomNote = "aa", magazine = "aab"
Output: true

Constraints:
    1 <= ransomNote.length, magazine.length <= 105
    ransomNote and magazine consist of lowercase English letters.
"""
from collections import defaultdict


class Solution:
    def canConstruct(self, ransomNote: str, magazine: str) -> bool:
        if len(ransomNote) > len(magazine):
            return False

        ransomNote = sorted(ransomNote)
        magazine = sorted(magazine)

        m = 0
        r = 0

        while m < len(magazine):
            if r == len(ransomNote):
                return True
            elif magazine[m] < ransomNote[r]:
                m += 1
            elif magazine[m] == ransomNote[r]:
                m += 1
                r += 1
            else:
                return False

        return r == len(ransomNote)

    def canConstruct2(self, ransomNote: str, magazine: str) -> bool:
        if len(ransomNote) > len(magazine):
            return False

        ri = defaultdict(int)
        for r in ransomNote:
            ri[r] = ri[r] + 1

        for m in magazine:
            if m in ri:
                ri[m] = ri[m] - 1
                if ri[m] == 0:
                    del ri[m]

        return len(ri) == 0