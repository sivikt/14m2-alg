"""
Given an array of characters chars, compress it using the following algorithm:

Begin with an empty string s. For each group of consecutive repeating characters in chars:

    If the group's length is 1, append the character to s.
    Otherwise, append the character followed by the group's length.

The compressed string s should not be returned separately, but instead, be stored in the
input character array chars. Note that group lengths that are 10 or longer will be split
into multiple characters in chars.

After you are done modifying the input array, return the new length of the array.
You must write an algorithm that uses only constant extra space.

Example 1:
Input: chars = ["a","a","b","b","c","c","c"]
Output: Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]
Explanation: The groups are "aa", "bb", and "ccc". This compresses to "a2b2c3".

Example 2:
Input: chars = ["a"]
Output: Return 1, and the first character of the input array should be: ["a"]
Explanation: The only group is "a", which remains uncompressed since it's a single character.

Example 3:
Input: chars = ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
Output: Return 4, and the first 4 characters of the input array should be: ["a","b","1","2"].
Explanation: The groups are "a" and "bbbbbbbbbbbb". This compresses to "ab12".


Constraints:
    1 <= chars.length <= 2000
    chars[i] is a lowercase English letter, uppercase English letter, digit, or symbol.
"""
from typing import List


class Solution:
    def compress(self, chars: List[str]) -> int:
        if len(chars) < 2:
            return len(chars)

        counter = 1
        i = 0
        j = 0

        while i < len(chars) - 1:
            if chars[i] == chars[i + 1]:
                counter += 1
            else:
                chars[j] = chars[i]
                j += 1

                if counter >= 2:
                    for c in str(counter):
                        chars[j] = c
                        j += 1

                    counter = 1

            i += 1

        chars[j] = chars[-1]
        j += 1

        if counter >= 2:
            for c in str(counter):
                chars[j] = c
                j += 1

        return j


if __name__ == '__main__':
    chars = []
    assert Solution().compress(chars) == 0
    assert chars == []

    chars = ["a"]
    assert Solution().compress(chars) == 1
    assert chars == ["a"]

    chars = ["a", "b"]
    assert Solution().compress(chars) == 2
    assert chars == ["a", "b"]

    chars = ["a", "a", "b"]
    assert Solution().compress(chars) == 3
    assert chars == ["a", "2", "b"]

    chars = ["a", "b", "b"]
    assert Solution().compress(chars) == 3
    assert chars == ["a", "b", "2"]

    chars = ["a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a"]
    assert Solution().compress(chars) == 3
    assert chars == ["a", "1", "1", "a", "a", "a", "a", "a", "a", "a", "a"]

    chars = ["a", "a", "b", "b", "c", "c", "c"]
    assert Solution().compress(chars) == 6
    assert chars == ["a", "2", "b", "2", "c", "3", "c"]

    chars = ["a","b","b","b","b","b","b","b","b","b","b","b","b"]
    assert Solution().compress(chars) == 4
    assert chars == ["a","b","1","2","b","b","b","b","b","b","b","b","b"]
