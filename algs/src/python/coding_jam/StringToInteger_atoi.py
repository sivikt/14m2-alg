"""Implement atoi which converts a string to an integer.

The function first discards as many whitespace characters as necessary
until the first non-whitespace character is found. Then, starting from
this character, takes an optional initial plus or minus sign followed
by as many numerical digits as possible, and interprets them as a
numerical value.

The string can contain additional characters after those that form the
integral number, which are ignored and have no effect on the behavior
of this function.

If the first sequence of non-whitespace characters in str is not a valid
integral number, or if no such sequence exists because either str is empty
or it contains only whitespace characters, no conversion is performed.

If no valid conversion could be performed, a zero value is returned.

Note:
Only the space character ' ' is considered as whitespace character.
Assume we are dealing with an environment which could only store integers
within the 32-bit signed integer range: [−2^31,  2^31 − 1]. If the numerical
value is out of the range of representable values,
INT_MAX (2^31 − 1) or INT_MIN (−2^31) is returned.


Example 1:
Input: "42"
Output: 42

Example 2:
Input: "   -42"
Output: -42
Explanation: The first non-whitespace character is '-', which is the minus sign.
             Then take as many numerical digits as possible, which gets 42.

Example 3:
Input: "4193 with words"
Output: 4193
Explanation: Conversion stops at digit '3' as the next character is not a numerical digit.

Example 4:
Input: "words and 987"
Output: 0
Explanation: The first non-whitespace character is 'w', which is not a numerical
             digit or a +/- sign. Therefore no valid conversion could be performed.
Example 5:
Input: "-91283472332"
Output: -2147483648
Explanation: The number "-91283472332" is out of the range of a 32-bit signed integer.
             Thefore INT_MIN (−2^31) is returned.
"""
class Solution(object):
    def myAtoi(self, s):
        """
        :type str: str
        :rtype: int
        """
        s = s.strip()
        
        if len(s) == 0:
            return 0
        if len(s) == 1:
            if s[0].isdigit():
                return int(s[0])
            else:
                return 0
            
        max_int = (2 << 30) - 1
        m = max_int / 10
        l1 = max_int % 10
        l2 = l1 + 1
        is_neg = False
        
        if s[0].isdigit():
            num = int(s[0])
        elif s[0] == '-':
            is_neg = True
            num = 0
        elif s[0] == '+':
            num = 0
        else:
            return 0
        
        for c in s[1:]:
            if c.isdigit():
                a = int(c)
                
                if (num > m):
                    return -max_int-1 if is_neg else max_int 
                elif num == m:
                    if is_neg and a>l2:
                        return -max_int-1
                    elif not is_neg and a>l1:
                        return max_int

                num = num*10 + a 
            else:
                return -num if is_neg else num 
            
        return -num if is_neg else num 