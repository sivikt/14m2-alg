package algs.assignments.leetcode;// Implement int sqrt(int x).

/**
 *  Compute and return the square root of x, where
 *  x is guaranteed to be a non-negative integer.
 *
 *  Since the return type is an integer, the decimal digits are
 *  truncated and only the integer part of the result is returned.
 *
 *  Example 1:
 *  Input: 4
 *  Output: 2
 *
 *  Example 2:
 *  Input: 8
 *  Output: 2
 *  Explanation: The square root of 8 is 2.82842..., and since
 *               the decimal part is truncated, 2 is returned.
 */
class Sqrtx {
    public int mySqrt(int x) {
        if (x < 2)
            return x;
        
        int l = 0;
        int r = x/2;
        
        while (l < r) 
        {
            int mid = l + (r-l+1)/2;
            int mid2 = x/mid;
                       
            if (mid2 == mid)
                return mid;
            else if (mid2 > mid)
                l = mid;
            else
                r = mid-1;
        }
        
        return l;
    }

    // Newton's method, also known as the Newton–Raphson method (or tangent of the graph method)
    public int mySqrt_v2(int x) {
        if (x < 2)
            return x;

        double x0 = 1;
        double x1 = x/2.0;
        double eps = 0.1;

        while (Math.abs(x1 - x0) > eps)
        {
            x0 = x1;
            x1 = (x0 + x/x0)/2; //x1 = x0 - (x0*x0 - x) / (2*x0);
        }

        return (int)x1;
    }


    /**
     * slightly changed (variables rearranging) to be Babylonian method
     * https://en.wikipedia.org/wiki/Methods_of_computing_square_roots#Babylonian_method
     */
    public int mySqrt_v3(int x) {
        if (x < 2)
            return x;

        double v = x;
        double eps = 1E-15;

        while (Math.abs(v - x/v) > eps*v)
            v = (v + x/v)/2;

        return (int)v;
    }
}
