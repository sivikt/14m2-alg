package algs.coding_jam;

// Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.

// Example 1:

// Input:
// [
//  [ 1, 2, 3 ],
//  [ 4, 5, 6 ],
//  [ 7, 8, 9 ]
// ]
// Output: [1,2,3,6,9,8,7,4,5]
// Example 2:

// Input:
// [
//   [1, 2, 3, 4],
//   [5, 6, 7, 8],
//   [9,10,11,12]
// ]
// Output: [1,2,3,4,8,12,11,10,9,5,6,7]


import java.util.List;
import java.util.LinkedList;

class SpiralMatrix {
    private static final byte RIGHT = 0;
    private static final byte LEFT = 1;
    private static final byte TOP = 2;
    private static final byte BOTTOM = 3;

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new LinkedList<>();

        int rows = matrix.length;
        if (rows == 0)
            return result;
        int cols = matrix[0].length;

        byte[][] visited = new byte[rows][cols];

        dfs(matrix, rows, cols, 0, 0, RIGHT, visited, result);

        return result;
    }

    private void dfs(int[][] matrix,
                     int rows,
                     int cols,
                     int i,
                     int j,
                     byte dir,
                     byte[][] visited,
                     List<Integer> result)
    {
        if (i >= 0 && i < rows && j >= 0 && j < cols && visited[i][j] == 0)
        {
            visited[i][j] = 1;
            result.add(matrix[i][j]);

            if (dir == RIGHT)
            {
                dfs(matrix, rows, cols, i, j+1, RIGHT, visited, result);
                dfs(matrix, rows, cols, i+1, j, BOTTOM, visited, result);
                dfs(matrix, rows, cols, i, j-1, LEFT, visited, result);
                dfs(matrix, rows, cols, i-1, j, TOP, visited, result);
            }
            else if (dir == BOTTOM)
            {
                dfs(matrix, rows, cols, i+1, j, BOTTOM, visited, result);
                dfs(matrix, rows, cols, i, j-1, LEFT, visited, result);
                dfs(matrix, rows, cols, i-1, j, TOP, visited, result);
                dfs(matrix, rows, cols, i, j+1, RIGHT, visited, result);
            }
            else if (dir == LEFT)
            {
                dfs(matrix, rows, cols, i, j-1, LEFT, visited, result);
                dfs(matrix, rows, cols, i-1, j, TOP, visited, result);
                dfs(matrix, rows, cols, i, j+1, RIGHT, visited, result);
                dfs(matrix, rows, cols, i+1, j, BOTTOM, visited, result);
            }
            else
            {
                dfs(matrix, rows, cols, i-1, j, TOP, visited, result);
                dfs(matrix, rows, cols, i, j+1, RIGHT, visited, result);
                dfs(matrix, rows, cols, i+1, j, BOTTOM, visited, result);
                dfs(matrix, rows, cols, i, j-1, LEFT, visited, result);
            }
        }
    }


    // simple Loop

    private static final byte[][] STEPS = new byte[][] {{0,+1}, {+1,0}, {0,-1}, {-1,0}};

    public List<Integer> spiralOrderLoop(int[][] matrix) {
        List<Integer> result = new LinkedList<>();

        int rows = matrix.length;
        if (rows == 0)
            return result;
        int cols = matrix[0].length;

        int top = 0;
        int left = -1;
        int right = cols;
        int bottom = rows;
        int i = 0;
        int j = -1;
        int dirCycle = 0;

        while (left < right && top < bottom)
        {
            byte dir = (byte) (dirCycle % STEPS.length);
            byte[] step = STEPS[dir];
            
            int iters = (dir == RIGHT || dir == LEFT) ? Math.abs(right - left) : Math.abs(top - bottom);
                                             
            while (--iters > 0)
            {                
                i += step[0];
                j += step[1];
                result.add(matrix[i][j]);
            }
                        
            if (dir == RIGHT)
                right--;
            else if (dir == LEFT)
                left++;
            else if (dir == TOP)
                top++;
            else
                bottom--;

            dirCycle++;
        }

        return result;
    }
}