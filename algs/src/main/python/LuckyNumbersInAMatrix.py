"""
Given an m x n matrix of distinct numbers, return all lucky numbers in the matrix in any order.

A lucky number is an element of the matrix such that it is the minimum element in its row and maximum in its column.


Example 1:
Input: matrix = [[3,7,8],[9,11,13],[15,16,17]]
Output: [15]
Explanation: 15 is the only lucky number since it is the minimum in its row and the maximum in its column.

Example 2:
Input: matrix = [[1,10,4,2],[9,3,8,7],[15,16,17,12]]
Output: [12]
Explanation: 12 is the only lucky number since it is the minimum in its row and the maximum in its column.

Example 3:
Input: matrix = [[7,8],[1,2]]
Output: [7]
Explanation: 7 is the only lucky number since it is the minimum in its row and the maximum in its column.

Constraints:
    m == mat.length
    n == mat[i].length
    1 <= n, m <= 50
    1 <= matrix[i][j] <= 10^5.
    All elements in the matrix are distinct.
"""

from typing import List


class Solution:
    def luckyNumbers(self, matrix: List[List[int]]) -> List[int]:
        res = []

        for i in range(len(matrix)):
            c_min = 0
            for j in range(1, len(matrix[0])):
                if matrix[i][j] < matrix[i][c_min]:
                    c_min = j

            r_max = 0
            for j in range(len(matrix)):
                if matrix[j][c_min] > matrix[r_max][c_min]:
                    r_max = j

            if r_max == i:
                res.append(matrix[r_max][c_min])

        return res


if __name__ == '__main__':
    matrix = [[3, 7, 8], [9, 11, 13], [15, 16, 17]]
    assert [15] == Solution().luckyNumbers(matrix)

    matrix = [[1,10,4,2],[9,3,8,7],[15,16,17,12]]
    assert [12] == Solution().luckyNumbers(matrix)

    matrix = [[7,8],[1,2]]
    assert [7] == Solution().luckyNumbers(matrix)
