// Given an array of integers A sorted in non-decreasing order, 
// return an array of the squares of each number, also in sorted 
// non-decreasing order.

// In-place merging "Practical In-Place Merging" by BING-CHAO HUANG and MICHAEL A. LANGSTON 
// http://akira.ruc.dk/~keld/teaching/algoritmedesign_f04/Artikler/04/Huang88.pdf 
// --
// Katajainen, Jyrki; Pasanen, Tomi; Teuhola, Jukka (1996). "Practical in-place mergesort". Nordic Journal of Computing.
// https://web.archive.org/web/20110807033704/http://www.diku.dk/hjemmesider/ansatte/jyrki/Paper/mergesort_NJC.ps

// Example 1:
// Input: [-4,-1,0,3,10]
// Output: [0,1,9,16,100]

// Example 2:
// Input: [-7,-3,2,3,11]
// Output: [4,9,9,49,121]
 

// Note:
// 1 <= A.length <= 10000
// -10000 <= A[i] <= 10000
// A is sorted in non-decreasing order.


class Solution {
    public int[] sortedSquares(int[] A) {
        if (A.length == 0)
            return A;
        else if (A.length == 1) {
            A[0] = A[0]*A[0];
            return A;
        }
        else if (A[0] >= 0) {
            for (int i = 0; i < A.length; i++)
                A[i] = A[i]*A[i];
            
            return A;
        }
         
        int[] B = new int[A.length];
        
        int i = 0;
        int j = A.length - 1;
        int b = A.length;
        
        while (i <= j) {
            int m = A[i];
            m = m*m;

            int n = A[j];
            n = n*n;

            if (n >= m) {
                B[--b] = n;
                j--;
            }
            else if (m > n) {
                B[--b] = m;
                i++;
            }
        }
        
        return B;
    }
}