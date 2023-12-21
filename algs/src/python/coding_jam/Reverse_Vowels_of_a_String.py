"""
Given a string s, reverse only all the vowels in the string and return it.
The vowels are 'a', 'e', 'i', 'o', and 'u', and they can appear in both lower and upper cases, more than once.


Example 1:
Input: s = "hello"
Output: "holle"

Example 2:
Input: s = "leetcode"
Output: "leotcede"

Constraints:
    1 <= s.length <= 3 * 10^5
    s consist of printable ASCII characters.
"""
import array

class Solution:
    def reverseVowels(self, s: str) -> str:
        if not s:
            return s

        r = s[::-1]
        a = array.array('B', [0]*len(s))
        i = 0
        j = 0

        while True:
            k = len(s)-j-1
            if k < i:
                break

            sl = s[i].lower()
            rl = r[j].lower()
            fs = (sl == 'a' or sl == 'e' or sl == 'i' or sl == 'o' or sl == 'u')
            fr = (rl == 'a' or rl == 'e' or rl == 'i' or rl == 'o' or rl == 'u')

            if not fs:
                a[i] = ord(s[i])
                i += 1

            if not fr:
                # stay on its original place
                a[k] = ord(r[j])
                j += 1

            if fs and fr:
                a[i] = ord(r[j])
                a[k] = ord(s[i])
                i += 1
                j += 1

        return a.tobytes().decode()

    def reverseVowels2(self, s: str) -> str:
        if not s:
            return s

        vws = "aeiouAEIOU"

        r = list(s)
        i = 0
        j = len(s)-1

        while j > i:
            fs = s[i] in vws
            fr = r[j] in vws

            if not fs:
                i += 1

            if not fr:
                j -= 1

            if fs and fr:
                r[i] = r[j]
                r[j] = s[i]
                i += 1
                j -= 1

        return ''.join(r)


if __name__ == '__main__':
    assert Solution().reverseVowels('') == ''
    assert Solution().reverseVowels('aeiou') == 'uoiea'
    assert Solution().reverseVowels('AEIOU') == 'UOIEA'
    assert Solution().reverseVowels('hello') == 'holle'
    assert Solution().reverseVowels('leetcode') == 'leotcede'
    assert Solution().reverseVowels('lwqrte') == 'lwqrte'

    assert Solution().reverseVowels2('') == ''
    assert Solution().reverseVowels2('aeiou') == 'uoiea'
    assert Solution().reverseVowels2('AEIOU') == 'UOIEA'
    assert Solution().reverseVowels2('hello') == 'holle'
    assert Solution().reverseVowels2('leetcode') == 'leotcede'
    assert Solution().reverseVowels2('lwqrte') == 'lwqrte'
