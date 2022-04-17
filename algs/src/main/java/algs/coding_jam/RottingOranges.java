package algs.coding_jam;

/* In a given grid, each cell can have one of three values:

 the value 0 representing an empty cell;
 the value 1 representing a fresh orange;
 the value 2 representing a rotten orange.
 Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.

 Return the minimum number of minutes that must elapse until no cell has a fresh orange.  If this is impossible, return -1 instead.

 Input: [[2,1,1],[1,1,0],[0,1,1]]
 Output: 4
 Example 2:

 Input: [[2,1,1],[0,1,1],[1,0,1]]
 Output: -1
 Explanation:  The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
 Example 3:

 Input: [[0,2]]
 Output: 0
 Explanation:  Since there are already no fresh oranges at minute 0, the answer is just 0.
 

 Note:

 1 <= grid.length <= 10
 1 <= grid[0].length <= 10
 grid[i][j] is only 0, 1, or 2.
 */

import java.util.*;

class RottingOranges {
    private static class Coord {
        final int x;
        final int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    private final Coord[] steps = new Coord[] { 
        new Coord(0, -1), 
        new Coord(0, 1),
        new Coord(-1, 0),
        new Coord(1, 0) 
    };
    
    public int orangesRotting(int[][] grid) {
        final int EMPTY = 0;
        final int FRESH = 1;
        final int ROTEN = 2;

        final int rows = grid.length;
        final int cols = grid[0].length;
        
        boolean[][] visited = new boolean[rows][cols];
        
        Queue<Coord> rottenOranges = new LinkedList<>();
        int freshNum = 0;
        
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                if (grid[r][c] == ROTEN) 
                {
                    rottenOranges.add(new Coord(r, c));
                    visited[r][c] = true;
                }
                else if (grid[r][c] == FRESH) 
                    freshNum++;
            }
        }
        
        int mins = 0;

        while (true)
        {
            int rottenWaveCount = rottenOranges.size();
            int prevFreshNum = freshNum;
            
            if (rottenWaveCount == 0)
                break;
            
            while (rottenWaveCount > 0) 
            {
                Coord coord = rottenOranges.poll();
                rottenWaveCount--;
                
                for (Coord step : steps)
                {
                    int r = coord.x + step.x;
                    int c = coord.y + step.y;
                    
                    if (r >= 0 && r < rows && c >= 0 && c < cols) 
                    {
                        if (grid[r][c] == FRESH && !visited[r][c])
                        {
                            freshNum--;
                            visited[r][c] = true;
                            rottenOranges.add(new Coord(r, c));
                        }
                    }
                }
            }
            
            if (prevFreshNum == freshNum)
                break;
            
            mins++;
        }

        return (freshNum == 0) ? mins : -1;
    }
}

class RottingOranges2 {
    
    private static class Coord {
        final int x;
        final int y;

        public Coord(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            Coord that = (Coord) obj;
            return this.x == that.x && this.y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    private final Coord[] steps = new Coord[] {
        new Coord(0, -1),
        new Coord(0, 1),
        new Coord(-1, 0),
        new Coord(1, 0)
    };

    public int orangesRotting(int[][] grid) {
        final int FRESH = 1;
        final int ROTEN = 2;

        final int rows = grid.length;
        final int cols = grid[0].length;
        
        Queue<Coord> rottenOranges = new LinkedList<>();
        int freshNum = 0;

        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < cols; c++)
            {
                if (grid[r][c] == ROTEN)
                    rottenOranges.add(new Coord(r, c));
                else if (grid[r][c] == FRESH)
                    freshNum++;
            }
        }

        int mins = 0;

        while (true)
        {
            int prevFreshNum = freshNum;

            if (rottenOranges.isEmpty())
                break;

            Set<Coord> newWave = new HashSet<>();
            
            while (!rottenOranges.isEmpty())
            {
                Coord coord = rottenOranges.poll();

                for (Coord step : steps)
                {
                    int r = coord.x + step.x;
                    int c = coord.y + step.y;

                    if (r >= 0 && r < rows && c >= 0 && c < cols)
                    {
                        if (grid[r][c] == FRESH)
                            newWave.add(new Coord(r, c));
                    }
                }
            }

            freshNum -= newWave.size();
            
            if (prevFreshNum == freshNum)
                break;

            for (Coord coord : newWave) 
            {
                grid[coord.x][coord.y] = ROTEN;
                rottenOranges.add(coord);
            }
            
            mins++;
        }

        return (freshNum == 0) ? mins : -1;
    }
    
}