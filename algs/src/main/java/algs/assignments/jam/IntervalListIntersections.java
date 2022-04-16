// Given two lists of closed intervals, each list of 
// intervals is pairwise disjoint and in sorted order.

// Return the intersection of these two interval lists.

// (Formally, a closed interval [a, b] (with a <= b) 
// denotes the set of real numbers x with a <= x <= b.
// The intersection of two closed intervals is a set of 
// real numbers that is either empty, or can be represented 
// as a closed interval.  For example, the intersection 
// of [1, 3] and [2, 4] is [2, 3].)

 
// Example 1:
// Input: A = [[0,2],[5,10],[13,23],[24,25]], 
//        B = [[1,5],[8,12],[15,24],[25,26]]
// Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
// Reminder: The inputs and the desired output are lists of Interval objects, and not arrays or lists.
 

// Note:
// 0 <= A.length < 1000
// 0 <= B.length < 1000
// 0 <= A[i].start, A[i].end, B[i].start, B[i].end < 10^9

// NOTE: input types have been changed on April 15, 2019. 
//       Please reset to default code definition to get new method signature.


class Solution {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        int[][] res = new int[2*Math.max(A.length, B.length)][2];
        int size = 0;
        
        int i = 0;
        int j = 0;
        
        while (i < A.length && j < B.length)
        {
            int l1 = A[i][0];
            int r1 = A[i][1];

            int l2 = B[j][0];
            int r2 = B[j][1];
            
            if (l2 > r1)
            {
                i++;
            }
            else if (l1 <= l2 && l2 <= r1 && l1 <= r2 && r2 <= r1)
            {
                res[size][0] = l2;
                res[size][1] = r2;
                size++;
                j++;
            }
            else if (l2 <= l1 && l1 <= r2 && l2 <= r1 && r1 <= r2)
            {
                res[size][0] = l1;
                res[size][1] = r1;
                size++;
                i++;
            }
            else
            {
                int lo = Math.max(l1, l2);
                int hi = Math.min(r1, r2);
                
                if (lo <= hi)
                {
                    res[size][0] = lo;
                    res[size][1] = hi;
                    size++;    
                }
                
                if (r1 < r2)
                    i++;
                else
                    j++;
            }
        }
        
        int[][] tmp = new int[size][2];
        for (i = 0; i < size; i++)
        {
            tmp[i][0] = res[i][0];
            tmp[i][1] = res[i][1];
        }
        
        res = tmp;
        
        return res;
    }


////


    public int[][] intervalIntersection2(int[][] A, int[][] B) {
        int[][] res = new int[2*Math.max(A.length, B.length)][2];
        int size = 0;
        
        int i = 0;
        int j = 0;
        
        while (i < A.length && j < B.length)
        {
            int l1 = A[i][0];
            int r1 = A[i][1];

            int l2 = B[j][0];
            int r2 = B[j][1];
            
            if (l2 > r1)
                i++;
            else
            {
                int lo = Math.max(l1, l2);
                int hi = Math.min(r1, r2);
                
                if (lo <= hi)
                {
                    res[size][0] = lo;
                    res[size][1] = hi;
                    size++;    
                }
                
                if (r1 < r2)
                    i++;
                else
                    j++;
            }
        }
        
        int[][] tmp = new int[size][2];
        System.arraycopy(res, 0, tmp, 0, size);        
        res = tmp;
        
        res = tmp;
        
        return res;
    }
}