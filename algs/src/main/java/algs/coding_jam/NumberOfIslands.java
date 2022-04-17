package algs.coding_jam;

import java.util.Queue;
import java.util.ArrayDeque;

/**
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
 * An island is surrounded by water and is formed by connecting adjacent lands
 * horizontally or vertically.
 * You may assume all four edges of the grid are all surrounded by water.
 *
 * Example 1:
 *
 * Input:
 * 11110
 * 11010
 * 11000
 * 00000
 *
 * Output: 1
 * Example 2:
 *
 * Input:
 * 11000
 * 11000
 * 00100
 * 00011
 *
 * Output: 3
 */
class NumberOfIslands {
    public int numIslandsDFS(char[][] grid) 
    {
        if(grid == null || grid.length == 0)
            return 0;
        
        int numberOfIslands = 0;
        for(int i = 0; i < grid.length; i++)
        {
            for(int j = 0; j < grid[i].length; j++)
            {
                if(grid[i][j] == '1')
                    numberOfIslands += dfs(grid, i, j);
            }
        }
        return numberOfIslands;
    }
    
    public int dfs(char[][] grid, int i, int j)
    {
        if(i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] == '0')
            return 0;
        
        grid[i][j] = '0';
        dfs(grid, i+1, j);
        dfs(grid, i-1, j);
        dfs(grid, i, j+1);
        dfs(grid, i, j-1);
        
        return 1;
    }
    
    
    public int numIslandsBFS1(char[][] grid) {
        final char LAND = '1';
        final char WATER = '0';

        int rows = grid.length;
        if (rows == 0)
            return 0;
        int cols = grid[0].length;

        int numIslands = 0;

        Queue<Integer> bfsQueue = new ArrayDeque<>();

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (grid[i][j] == LAND)
                {
                    numIslands += 1;
                    grid[i][j] = WATER;

                    bfsQueue.add(i*cols + j);

                    while (!bfsQueue.isEmpty())
                    {
                        int xy = bfsQueue.poll();
                        int x = xy / cols;
                        int y = xy % cols;
                        
                        int k = x;
                        int l = y-1;

                        if (k >= 0 && k < rows && l >= 0 && l < cols && grid[k][l] == LAND)
                        {
                            grid[k][l] = WATER;
                            bfsQueue.add(k*cols + l);
                        }

                        k = x-1;
                        l = y;

                        if (k >= 0 && k < rows && l >= 0 && l < cols && grid[k][l] == LAND)
                        {
                            grid[k][l] = WATER;
                            bfsQueue.add(k*cols + l);
                        }

                        k = x+1;
                        l = y;

                        if (k >= 0 && k < rows && l >= 0 && l < cols && grid[k][l] == LAND)
                        {
                            grid[k][l] = WATER;
                            bfsQueue.add(k*cols + l);
                        }

                        k = x;
                        l = y+1;

                        if (k >= 0 && k < rows && l >= 0 && l < cols && grid[k][l] == LAND)
                        {
                            grid[k][l] = WATER;
                            bfsQueue.add(k*cols + l);
                        }
                    }
                }
            }
        }

        return numIslands;
    }

    public int numIslandsBFS2(char[][] grid) {
        final char LAND = '1';
        final char WATER = '0';

        int rows = grid.length;
        if (rows == 0)
            return 0;
        int cols = grid[0].length;

        int numIslands = 0;

        int[] queue = new int[rows*cols];
        int queueHead = -1;
        int queueTail = -1;

        for (int i = 0; i < rows; i++)
        {
            for (int j = 0; j < cols; j++)
            {
                if (grid[i][j] == LAND)
                {
                    numIslands += 1;
                    grid[i][j] = WATER;

                    queue[++queueTail] = i*cols + j;

                    while (queueHead != queueTail)
                    {
                        int xy = queue[++queueHead];
                        int x = xy / cols;
                        int y = xy % cols;
                        
                        int k = x;
                        int l = y-1;

                        if (l >= 0 && l < cols && grid[k][l] == LAND)
                        {
                            grid[k][l] = WATER;
                            queue[++queueTail] = k*cols + l;
                        }

                        k = x-1;
                        l = y;

                        if (k >= 0 && k < rows && grid[k][l] == LAND)
                        {
                            grid[k][l] = WATER;
                            queue[++queueTail] = k*cols + l;
                        }

                        k = x+1;
                        l = y;

                        if (k >= 0 && k < rows && grid[k][l] == LAND)
                        {
                            grid[k][l] = WATER;
                            queue[++queueTail] = k*cols + l;
                        }

                        k = x;
                        l = y+1;

                        if (l >= 0 && l < cols && grid[k][l] == LAND)
                        {
                            grid[k][l] = WATER;
                            queue[++queueTail] = k*cols + l;
                        }
                    }
                }
            }
        }

        return numIslands;
    }
}