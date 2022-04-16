"""
A substring is a contiguous (non-empty) sequence of characters within a string.

A vowel substring is a substring that only consists of vowels ('a', 'e', 'i', 'o', and 'u')
and has all five vowels present in it.

Given a string word, return the number of vowel substrings in word.


Example 1:
Input: word = "aeiouu"
Output: 2
Explanation: The vowel substrings of word are as follows (underlined):
- "aeiouu"
- "aeiouu"

Example 2:
Input: word = "unicornarihan"
Output: 0
Explanation: Not all 5 vowels are present, so there are no vowel substrings.

Example 3:
Input: word = "cuaieuouac"
Output: 7
Explanation: The vowel substrings of word are as follows (underlined):
- "cuaieuouac"
- "cuaieuouac"
- "cuaieuouac"
- "cuaieuouac"
- "cuaieuouac"
- "cuaieuouac"
- "cuaieuouac"

Constraints:
    1 <= word.length <= 100
    word consists of lowercase English letters only.
"""
class Solution:
    def countVowelSubstrings(self, word: str) -> int:
        """Bruteforce"""
        vowels = {'a', 'e', 'i', 'o', 'u'}

        vowel_sub_str_cnt = 0

        i = -1

        while i < len(word) - len(vowels):
            i += 1

            if word[i] not in vowels:
                continue

            vowel_sub_str_uniques = set(word[i])

            j = i
            while (j := j + 1) < len(word):
                if word[j] in vowels:
                    vowel_sub_str_uniques.add(word[j])
                    if len(vowel_sub_str_uniques) == len(vowels):
                        vowel_sub_str_cnt += 1
                else:
                    break

        return vowel_sub_str_cnt


class Solution3:
    def countVowelSubstrings(self, word: str) -> int:
        vowels = {'a', 'e', 'i', 'o', 'u'}

        consonants = list(range(len(word)))

        for i in reversed(range(len(word) - 1)):
            if word[i + 1] in vowels:
                consonants[i] = consonants[i + 1]

        i = -1
        j = 0
        result = 0
        vowel_sub_str_uniques = {}

        while (i := i + 1) < len(word):
            if word[i] not in vowels:
                j = i + 1
                vowel_sub_str_uniques = {}
                continue

            vowel_sub_str_uniques[word[i]] = vowel_sub_str_uniques.get(word[i], 0) + 1

            while len(vowel_sub_str_uniques) == len(vowels):
                result += consonants[i] - i + 1
                vowel_sub_str_uniques[word[j]] -= 1

                if vowel_sub_str_uniques[word[j]] == 0:
                    del vowel_sub_str_uniques[word[j]]

                j += 1

        return result


if __name__ == '__main__':
    test0 = "aeiouu"
    assert 2 == Solution().countVowelSubstrings(test0)
    assert 2 == Solution2().countVowelSubstrings(test0)

    test1 = "unicornarihan"
    assert 0 == Solution().countVowelSubstrings(test1)
    assert 0 == Solution2().countVowelSubstrings(test1)

    test2 = "cuaieuouac"
    assert 7 == Solution().countVowelSubstrings(test2)
    assert 7 == Solution2().countVowelSubstrings(test2)

    test3 = "poazaeuioauoiioaouuouaui"
    assert 31 == Solution().countVowelSubstrings(test3)
    assert 31 == Solution2().countVowelSubstrings(test3)
