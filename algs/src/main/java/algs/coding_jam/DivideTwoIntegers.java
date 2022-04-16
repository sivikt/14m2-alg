package algs.coding_jam;

/**
 * Given two integers dividend and divisor, divide two integers without using multiplication, division and mod operator.
 *
 * Return the quotient after dividing dividend by divisor.
 *
 * The integer division should truncate toward zero.
 *
 * Example 1:
 * Input: dividend = 10, divisor = 3
 * Output: 3
 *
 * Example 2:
 * Input: dividend = 7, divisor = -3
 * Output: -2
 *
 * Note:
 * Both dividend and divisor will be 32-bit signed integers.
 * The divisor will never be 0.
 * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range:
 * [−2^31,  2^31 − 1].
 * For the purpose of this problem, assume that your function returns 2^31 − 1 when the division result overflows.
 */
public class DivideTwoIntegers {

    public long do_divide(long dividend, long divisor) {
        if (dividend < divisor)
            return 0;
        else if (dividend == divisor)
            return 1;
        else if (divisor == 1)
            return dividend;
        else if (divisor == 2)
            return dividend >> 1;

        long m = divisor;
        int r = 0;

        while (m < dividend)
        {
            m <<= 1;
            r++;
        }

        if (m > dividend)
        {
            r--;
            m >>= 1;
        }

        if (r > 0)
            return (2 << (r-1)) + do_divide(dividend-m, divisor);
        else
            return 1 + do_divide(dividend-m, divisor);
    }

    public int divide(int dividend, int divisor) {
        int sign = (dividend > 0 && divisor > 0) ? 1 : ((dividend < 0 && divisor < 0) ? 1 : -1);

        if (dividend == Integer.MIN_VALUE && divisor == -1)
            return Integer.MAX_VALUE;
        else if (dividend == Integer.MIN_VALUE && divisor == 1)
            return Integer.MIN_VALUE;
        else if (dividend == Integer.MIN_VALUE && divisor == Integer.MAX_VALUE)
            return -1;
        else if (dividend == -Integer.MAX_VALUE && divisor == Integer.MAX_VALUE)
            return -1;

        long ldividend = Math.abs((long) dividend);
        long ldivisor = Math.abs((long) divisor);

        int res = (int) do_divide(ldividend, ldivisor);

        if (sign == 1)
            return res;
        else
            return -res;
    }

    public int divide_v2(int dividend, int divisor) {
        int sign = (dividend > 0 && divisor > 0) ? 1 : ((dividend < 0 && divisor < 0) ? 1 : -1);

        if (dividend == Integer.MIN_VALUE && divisor == -1)
            return Integer.MAX_VALUE;
        else if (dividend == Integer.MIN_VALUE && divisor == 1)
            return Integer.MIN_VALUE;
        else if (dividend == Integer.MIN_VALUE && divisor == Integer.MAX_VALUE)
            return -1;
        else if (dividend == -Integer.MAX_VALUE && divisor == Integer.MAX_VALUE)
            return -1;

        long ldividend = Math.abs((long) dividend);
        long ldivisor = Math.abs((long) divisor);

        if (ldividend < ldivisor)
            return 0;
        else if (ldividend == ldivisor)
            return 1;
        else if (ldivisor == 1)
            return dividend;
        else if (ldivisor == 2)
            return dividend >> 1;

        int res = 0;

        while (ldividend >= ldivisor) {
            long m = divisor;
            int r = 0;

            while (m < dividend) {
                m <<= 1;
                r++;
            }

            if (m > dividend) {
                r--;
                m >>= 1;
            }

            ldividend -= m;

            if (r > 0)
                res += 2 << (r - 1);
            else
                res += 1;
        }

        if (sign == 1)
            return res;
        else
            return -res;
    }
}
