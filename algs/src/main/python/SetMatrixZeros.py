from typing import List
import copy


class Solution:
    def setZeroes(self, matrix: List[List[int]]) -> None:
        """
        Do not return anything, modify matrix in-place instead.
        """
        max_row = len(matrix)
        max_col = len(matrix[0])

        row_flags = [False] * max_row
        col_flags = [False] * max_col

        for r in range(len(matrix)):
            for c in range(len(matrix[0])):
                if matrix[r][c] == 0:
                    # backfill past row r cells
                    for i in range(c):
                        if (matrix[r][i] == 0) and row_flags[r]:
                            break
                        else:
                            matrix[r][i] = 0

                    # backfill past column c cells
                    for i in range(r):
                        if (matrix[i][c] == 0) and col_flags[c]:
                            break
                        else:
                            matrix[i][c] = 0

                    row_flags[r] = True
                    col_flags[c] = True

                if row_flags[r] or col_flags[c]:
                    matrix[r][c] = 0


class Solution2:
    def setZeroes(self, matrix: List[List[int]]) -> None:
        """
        Do not return anything, modify matrix in-place instead.
        """
        max_row = len(matrix)
        max_col = len(matrix[0])

        is_first_cell_zero = False

        for r in range(max_row):
            if matrix[r][0] == 0:
                is_first_cell_zero = True

            for c in range(1, max_col):
                if matrix[r][c] == 0:
                    matrix[r][0] = 0
                    matrix[0][c] = 0

        for r in range(1, max_row):
            for c in range(1, max_col):
                if matrix[r][0] == 0 or matrix[0][c] == 0:
                    matrix[r][c] = 0

        if matrix[0][0] == 0:
            for c in range(max_col):
                matrix[0][c] = 0

        if is_first_cell_zero:
            for r in range(max_row):
                matrix[r][0] = 0


if __name__ == '__main__':
    test0 = [[1,1,1],[1,0,1],[1,1,1]]
    rslt0 = [[1,0,1],[0,0,0],[1,0,1]]
    test01 = copy.deepcopy(test0)
    test02 = copy.deepcopy(test0)
    Solution().setZeroes(test01)
    Solution2().setZeroes(test02)
    assert rslt0 == test01
    assert rslt0 == test02

    test1 = [[3, 5, 5, 6, 9, 1, 4, 5, 0, 5],
             [2, 7, 9, 5, 9, 5, 4, 9, 6, 8],
             [6, 0, 7, 8, 1, 0, 1, 6, 8, 1],
             [7, 2, 6, 5, 8, 5, 6, 5, 0, 6],
             [2, 3, 3, 1, 0, 4, 6, 5, 3, 5],
             [5, 9, 7, 3, 8, 8, 5, 1, 4, 3],
             [2, 4, 7, 9, 9, 8, 4, 7, 3, 7],
             [3, 5, 2, 8, 8, 2, 2, 4, 9, 8]]
    rslt1 = [[0,0,0,0,0,0,0,0,0,0],
             [2,0,9,5,0,0,4,9,0,8],
             [0,0,0,0,0,0,0,0,0,0],
             [0,0,0,0,0,0,0,0,0,0],
             [0,0,0,0,0,0,0,0,0,0],
             [5,0,7,3,0,0,5,1,0,3],
             [2,0,7,9,0,0,4,7,0,7],
             [3,0,2,8,0,0,2,4,0,8]]
    test11 = copy.deepcopy(test1)
    test12 = copy.deepcopy(test1)
    Solution().setZeroes(test11)
    Solution2().setZeroes(test12)
    assert rslt1 == test11
    assert rslt1 == test12

    test2 = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
    rslt2 = [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
    test21 = copy.deepcopy(test2)
    test22 = copy.deepcopy(test2)
    Solution().setZeroes(test21)
    Solution2().setZeroes(test22)
    assert rslt2 == test21
    assert rslt2 == test22

    test3 = [[1, 2, 3, 4], [5, 0, 7, 8], [0, 10, 11, 12], [13, 14, 15, 0]]
    rslt3 = [[0,0,3,0],[0,0,0,0],[0,0,0,0],[0,0,0,0]]
    test31 = copy.deepcopy(test3)
    test32 = copy.deepcopy(test3)
    Solution().setZeroes(test31)
    Solution2().setZeroes(test32)
    assert rslt3 == test31
    assert rslt3 == test32
