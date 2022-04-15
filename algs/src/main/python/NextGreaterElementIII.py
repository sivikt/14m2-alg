"""
Given a positive integer n, find the smallest integer which has exactly the same
digits existing in the integer n and is greater in value than n.
If no such positive integer exists, return -1.

Note that the returned integer should fit in 32-bit integer,
if there is a valid answer but it does not fit in 32-bit integer, return -1.

Example 1:
Input: n = 12
Output: 21

Example 2:
Input: n = 21
Output: -1

Constraints:
    1 <= n <= 2^31 - 1
"""
def nextGreaterElement(self, n: int) -> int:
    max_n = 2 ** 31 - 1

    n_str = list(str(n))
    i = len(n_str) - 1

    while (i := i - 1) >= 0:
        if n_str[i] >= n_str[i + 1]:
            continue

        j = i + 1

        for k in range(j + 1, len(n_str)):
            if (n_str[k] < n_str[j]) and (n_str[k] > n_str[i]):
                j = k

        n_i = n_str[i]
        n_j = n_str[j]

        n_str[i] = n_j
        n_str[j] = n_i

        m_str = n_str[:i + 1] + sorted(n_str[i + 1:])
        m = int(''.join(m_str))

        return m if m <= max_n else -1

    return -1
