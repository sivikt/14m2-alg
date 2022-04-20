package algs.coding_jam;

/**
    Write a function that takes an unsigned integer and return
    the number of '1' bits it has (also known as the Hamming weight (http://en.wikipedia.org/wiki/Hamming_weight)).

    Example 1:
    Input: 00000000000000000000000000001011
    Output: 3
    Explanation: The input binary string 00000000000000000000000000001011 has a total of three '1' bits.

    Example 2:
    Input: 00000000000000000000000010000000
    Output: 1
    Explanation: The input binary string 00000000000000000000000010000000 has a total of one '1' bit.

    Example 3:
    Input: 11111111111111111111111111111101
    Output: 31
    Explanation: The input binary string 11111111111111111111111111111101 has a total of thirty one '1' bits.


    Note:
    Note that in some languages such as Java, there is no unsigned integer type.
    In this case, the input will be given as signed integer type and should not
    affect your implementation, as the internal binary representation of the integer
    is the same whether it is signed or unsigned.
    In Java, the compiler represents the signed integers using 2's complement notation.
    Therefore, in Example 3 above the input represents the signed integer -3.
*/

public class NumberOf1Bits {
    // you need to treat n as an unsigned value
    public int hammingWeight(int n) {
        int c = 0;

        while (n != 0)
        {
            c += (n & 1);
            n >>>= 1;
        }    
        
        return c;
    }


    static int[] arr = 
    {
        0, 1, 1, 2,
        1, 2, 2, 3,
        1, 2, 2, 3,
        2, 3, 3, 4
    };
    
    public int hammingWeight2(int n) {
        int cnt = 0;
        cnt += arr[n & 0xF];
        cnt += arr[(n >>> 4) & 0xF];
        cnt += arr[(n >>> 8) & 0xF];
        cnt += arr[(n >>> 12) & 0xF];
        cnt += arr[(n >>> 16) & 0xF];
        cnt += arr[(n >>> 20) & 0xF];
        cnt += arr[(n >>> 24) & 0xF];
        cnt += arr[(n >>> 28) & 0xF];
        return cnt;
    }

    public int hammingWeight3(int n) {
        return arr[n >>> 28] + 
            arr[n << 4 >>> 28] +
            arr[n << 8 >>> 28] +
            arr[n << 12 >>> 28] +
            arr[n << 16 >>> 28] +
            arr[n << 20 >>> 28] +
            arr[n << 24 >>> 28] +
            arr[n << 28 >>> 28];
    }
}