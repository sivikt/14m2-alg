package algs.coding_jam;

/*
 Given a non-empty 2D array grid of 0's and 1's, an island is a group of 1's
 (representing land) connected 4-directionally (horizontal or vertical.)
 You may assume all four edges of the grid are surrounded by water.

 Find the maximum area of an island in the given 2D array. (If there is no island, the maximum area is 0.)

 Example 1:

 [[0,0,1,0,0,0,0,1,0,0,0,0,0],
  [0,0,0,0,0,0,0,1,1,1,0,0,0],
  [0,1,1,0,1,0,0,0,0,0,0,0,0],
  [0,1,0,0,1,1,0,0,1,0,1,0,0],
  [0,1,0,0,1,1,0,0,1,1,1,0,0],
  [0,0,0,0,0,0,0,0,0,0,1,0,0],
  [0,0,0,0,0,0,0,1,1,1,0,0,0],
  [0,0,0,0,0,0,0,1,1,0,0,0,0]]
 Given the above grid, return 6. Note the answer is not 11, because the island must be connected 4-directionally.
 Example 2:

 [[0,0,0,0,0,0,0,0]]
 Given the above grid, return 0.
 Note: The length of each dimension in the given grid does not exceed 50.
*/

class MaxAreaOfIsland {
    /*
      Possible solutions:
       1) recursive DFS
       2) iterative DFS
       3) union-find structure
       4) DFS without revisiting visited nodes (? if possible)
       5) BFS
     */

    private static final int VISITED = 2;
       
    public int dfs(int[][] grid, 
                   int rows, int cols, 
                   int i, int j) 
    {
        if (i>=0 && i<rows && j>=0 && j<cols && grid[i][j] != VISITED)
        {
            if (grid[i][j] == 0) 
            {
                grid[i][j] = VISITED;
                return 0;
            }
            else
            {
                grid[i][j] = VISITED;
                int area = 1;
                
                area += dfs(grid, rows, cols, i+1, j);
                area += dfs(grid, rows, cols, i-1, j);
                area += dfs(grid, rows, cols, i, j+1);
                area += dfs(grid, rows, cols, i, j-1);
                
                return area; 
            }
        }
        else return 0;
    }
    
    public int maxAreaOfIsland(int[][] grid) {
        int rows = grid.length;
        if (rows == 0)
            return 0;
        int cols = grid[0].length;
        
        int maxArea = 0;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1)
                    maxArea = Math.max(maxArea, dfs(grid, rows, cols, i, j));    
            }
        }
        
        return maxArea;
    }

}