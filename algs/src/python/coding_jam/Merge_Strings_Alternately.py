"""
You are given two strings word1 and word2.
Merge the strings by adding letters in alternating order, starting with word1.
If a string is longer than the other, append the additional letters onto the end of the merged string.

Return the merged string.

Example 1:
Input: word1 = "abc", word2 = "pqr"
Output: "apbqcr"
Explanation: The merged string will be merged as so:
word1:  a   b   c
word2:    p   q   r
merged: a p b q c r

Example 2:
Input: word1 = "ab", word2 = "pqrs"
Output: "apbqrs"
Explanation: Notice that as word2 is longer, "rs" is appended to the end.
word1:  a   b
word2:    p   q   r   s
merged: a p b q   r   s

Example 3:
Input: word1 = "abcd", word2 = "pq"
Output: "apbqcd"
Explanation: Notice that as word1 is longer, "cd" is appended to the end.
word1:  a   b   c   d
word2:    p   q
merged: a p b q c   d


Constraints:
    1 <= word1.length, word2.length <= 100
    word1 and word2 consist of lowercase English letters.
"""

import itertools


class Solution:
    def mergeAlternately(self, word1: str, word2: str) -> str:
        out = bytearray(len(word1)+len(word2))

        i, w1, w2 = 0, 0, 0
        while w1 < len(word1) and w2 < len(word2):
            out[i] = ord(word1[w1])
            i += 1
            out[i] = ord(word2[w2])
            i += 1
            w1 += 1
            w2 += 1

        while w1 < len(word1):
            out[i] = ord(word1[w1])
            i += 1
            w1 += 1

        while w2 < len(word2):
            out[i] = ord(word2[w2])
            i += 1
            w2 += 1

        return out.decode(encoding='ascii')

    def mergeAlternately2(self, word1: str, word2: str) -> str:
        return ''.join([a+b for a, b in itertools.zip_longest(word1, word2, fillvalue='')])

    def mergeAlternately3(self, word1: str, word2: str) -> str:
        return ''.join([a+b for a, b in zip(word1, word2)]) + word1[len(word2):] + word2[len(word1):]

    def mergeAlternately4(self, word1: str, word2: str) -> str:
        out = ''

        for i in range(min(len(word1), len(word2))):
            out += word1[i]
            out += word2[i]

        out += word1[len(word2):]
        out += word2[len(word1):]

        return out


if __name__ == '__main__':
    assert Solution().mergeAlternately('word1', 'mama') == 'wmoarmda1'
    assert Solution().mergeAlternately('', '') == ''
    assert Solution().mergeAlternately('abba', '') == 'abba'
    assert Solution().mergeAlternately('abcd', 'pq') == 'apbqcd'
    assert Solution().mergeAlternately('ab', 'pqrs') == 'apbqrs'

    assert Solution().mergeAlternately2('word1', 'mama') == 'wmoarmda1'
    assert Solution().mergeAlternately2('', '') == ''
    assert Solution().mergeAlternately2('abba', '') == 'abba'
    assert Solution().mergeAlternately2('abcd', 'pq') == 'apbqcd'
    assert Solution().mergeAlternately2('ab', 'pqrs') == 'apbqrs'

    assert Solution().mergeAlternately3('word1', 'mama') == 'wmoarmda1'
    assert Solution().mergeAlternately3('', '') == ''
    assert Solution().mergeAlternately3('abba', '') == 'abba'
    assert Solution().mergeAlternately3('abcd', 'pq') == 'apbqcd'
    assert Solution().mergeAlternately3('ab', 'pqrs') == 'apbqrs'

    assert Solution().mergeAlternately4('word1', 'mama') == 'wmoarmda1'
    assert Solution().mergeAlternately4('', '') == ''
    assert Solution().mergeAlternately4('abba', '') == 'abba'
    assert Solution().mergeAlternately4('abcd', 'pq') == 'apbqcd'
    assert Solution().mergeAlternately4('ab', 'pqrs') == 'apbqrs'
