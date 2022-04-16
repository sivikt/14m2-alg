package algs.assignments.leetcode;

/**
 * Given a positive 32-bit integer n, you need to find the smallest 32-bit integer which has exactly
 * the same digits existing in the integer n and is greater in value than n. If no such positive 32-bit
 * integer exists, you need to return -1.
 *
 * Example 1:
 * Input: 12
 * Output: 21
 *
 *
 * Example 2:
 * Input: 21
 * Output: -1
 */
public class NextGreaterElementIII {

    public int nextGreaterElement(int n) {
        if (n == Integer.MAX_VALUE)
            return -1;

        String nstr = String.valueOf(n);

        for (int i = nstr.length()-1; i > 0; i--) {
            if (nstr.charAt(i) > nstr.charAt(i-1))
            {
                int a = (nstr.charAt(i-1) - '0')*10 + (nstr.charAt(i) - '0');
                int b = (nstr.charAt(i) - '0')*10 + (nstr.charAt(i-1) - '0');

                if (Integer.MAX_VALUE - n > b-a)
                    return Integer.parseInt(nstr.substring(0, i-1) + nstr.charAt(i) + nstr.charAt(i-1) + nstr.substring(i+1));
                else
                    return -1;
            }
        }

        return -1;
    }

}
