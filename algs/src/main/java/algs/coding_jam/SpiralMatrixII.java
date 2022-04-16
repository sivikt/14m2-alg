package algs.coding_jam;

// Given a positive integer n, generate a square matrix filled with elements from 1 to n^2 in spiral order.

// Example:

// Input: 3
// Output:
// [
//  [ 1, 2, 3 ],
//  [ 8, 9, 4 ],
//  [ 7, 6, 5 ]
// ]


class SpiralMatrixII {
    // simple Loop
    private static final byte RIGHT = 0;
    private static final byte BOTTOM = 1;
    private static final byte LEFT = 2;
    private static final byte TOP = 3;

    private static final byte[][] STEPS = new byte[][] {{0,+1}, {+1,0}, {0,-1}, {-1,0}};

    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];

        int elem = 1;
        int top = 0;
        int left = -1;
        int right = n;
        int bottom = n;
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
                matrix[i][j] = elem++;
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

        return matrix;
    }    


    public int[][] generateMatrixSimpler(int n) {
        int[][] matrix = new int[n][n];

        int i = 0;
        int j = -1;
        int dirCycle = 0;
        
        for (int elem = 1; elem <= n*n; )
        {
            int dir = dirCycle % STEPS.length;
            i += STEPS[dir][0];
            j += STEPS[dir][1];
            
            if ((j < n) && (j >= 0) && (i < n) && (i >= 0) && (matrix[i][j] == 0))
            {        
                matrix[i][j] = elem++;
            }
            else 
            {
                i -= STEPS[dir][0];
                j -= STEPS[dir][1];
                dirCycle++;
            }
            
        }

        return matrix;
    }    
}