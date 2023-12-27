"""
The Tribonacci sequence Tn is defined as follows:
T0 = 0, T1 = 1, T2 = 1, and Tn+3 = Tn + Tn+1 + Tn+2 for n >= 0.
Given n, return the value of Tn.

Example 1:
Input: n = 4
Output: 4
Explanation:
T_3 = 0 + 1 + 1 = 2
T_4 = 1 + 1 + 2 = 4

Example 2:
Input: n = 25
Output: 1389537

Constraints:
    0 <= n <= 37
    The answer is guaranteed to fit within a 32-bit integer, ie. answer <= 2^31 - 1.
"""
class Solution:
    def tribonacci(self, n: int) -> int:
        if n == 0:
            return 0
        elif n < 3:
            return 1

        a = 0
        b = 1
        c = 1

        while n >= 3:
            t = a + b + c
            a = b
            b = c
            c = t
            n -= 1

        return c

    dict = {0: 0, 1: 1, 2: 1}

    def tribonacci2(self, n: int) -> int:
        if n < 3:
            return self.dict[n]
        elif n in self.dict:
            return self.dict[n]
        self.dict[n] = self.tribonacci(n - 3) + self.tribonacci(n - 2) + self.tribonacci(n - 1)
        return self.dict[n]
