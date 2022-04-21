package algs.coding_jam;

/*
Given a non-empty array of digits representing a
non-negative integer, plus one to the integer.

The digits are stored such that the most significant
digit is at the head of the list, and each element in
the array contain a single digit.

You may assume the integer does not contain any leading
zero, except the number 0 itself.

Example 1:

Input: [1,2,3]
Output: [1,2,4]
Explanation: The array represents the integer 123.
Example 2:

Input: [4,3,2,1]
Output: [4,3,2,2]
Explanation: The array represents the integer 4321.
*/
class PlusOne {
    public int[] plusOne(int[] digits) {
        int[] result = new int[digits.length];
        
        int r = 1;
        int d = 0;
        
        for (int i = digits.length-1; i >= 0; i--)
        {
            if (r > 0)
            {
                d = r+digits[i];
                r = d/10;
                result[i] = d%10;
            }
            else result[i] = digits[i];
        }
        
        if (r > 0)
        {
            int[] tmp = new int[1+digits.length];
            tmp[0] = r;
            System.arraycopy(result, 0, tmp, 1, result.length);
            result = tmp;
        }
        
        return result;
    }
}