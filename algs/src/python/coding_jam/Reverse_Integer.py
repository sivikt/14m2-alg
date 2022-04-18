"""
Given a 32-bit signed integer, reverse digits of an integer.

Example 1:

Input: 123
Output: 321
Example 2:

Input: -123
Output: -321
Example 3:

Input: 120
Output: 21
Note:
Assume we are dealing with an environment which could only store integers within
the 32-bit signed integer range: [−231,  231 − 1]. For the purpose of this problem,
assume that your function returns 0 when the reversed integer overflows.
"""

class Solution(object):
    def reverse(self, x):
        """
        :type x: int
        :rtype: int
        """
        if x == 0:
            return 0
        
        max_int = (2 << 30) - 1
        
        is_neg = x<0
        x = abs(x)
        
        m = max_int / 10
        l1 = max_int % 10
        l2 = l1 + 1
        
        r = 0
        while x > 0:
            a = x%10
            x = x/10
            
            if (r > m):
                return 0
            elif r == m and x != 0:
                return 0
            elif r == m and x == 0:
                if is_neg and a>l2:
                    return 0
                elif not is_neg and a>l1:
                    return 0
            
            r = r*10 + a
            
            
            
        return -r if is_neg else r