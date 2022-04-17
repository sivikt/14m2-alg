// Given a string s, find the longest palindromic substring in s.
// You may assume that the maximum length of s is 1000.

// Example 1:

// Input: "babad"
// Output: "bab"
// Note: "aba" is also a valid answer.
// Example 2:

// Input: "cbbd"
// Output: "bb"


package algs.coding_jam;

public class LongestPalindromicSubstring {

    /* 
    	Dynamic Programming
    	O(N^2) - time
    	O(N^2) - memo 
    */
    public String longestPalindrome1(String s) {
        final int N = s.length();

        if (N == 0)
            return s;
        
        boolean[][] table = new boolean[N][N];

        // All substrings of length 1 are palindromes
        int maxLength = 1;
        for (int i = 0; i < N; ++i)
            table[i][i] = true;

        // check for sub-string of length 2.
        int start = 0;
        for (int i = 0; i < N - 1; ++i) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                table[i][i + 1] = true;
                start = i;
                maxLength = 2;
            }
        }

        // Check for lengths greater than 2. k is length
        // of substring
        for (int k = 3; k <= N; ++k)
        {
            // Fix the starting index
            for (int i = 0; i < N - k + 1; ++i)
            {
                // Get the ending index of substring from
                // starting index i and length k
                int j = i + k - 1;

                // checking for sub-string from i-th index to
                // j-th index iff s.charAt(i+1) to s.charAt(j-1) is a palindrome
                if (table[i + 1][j - 1] && s.charAt(i) == s.charAt(j)) {
                    table[i][j] = true;

                    if (k > maxLength) {
                        start = i;
                        maxLength = k;
                    }
                }
            }
        }

        return s.substring(start, start+maxLength);
    }


    /*
        Go on the left and right direction from each letter.
        O(N^2) - time
        O(1) - memo
    */
    public String longestPalindrome2(String s) {
        final int N = s.length();

        if (N == 0)
            return s;

        int maxPalindStart = 0;
        int maxPalindLength = 1;

        for (int i = 1; i < N; i++)
        {
            int lo = i - 1;
            int hi = i + 1;

            while (lo >= 0 && hi < N && s.charAt(lo) == s.charAt(hi))
            {
                lo--;
                hi++;
            }

            int start = lo + 1;
            int length = hi - start;

            //System.out.println("start1="+start+" len1="+length + "lo="+lo+" hi="+hi);
            
            if (length > maxPalindLength)
            {
                maxPalindStart = start;
                maxPalindLength = length;
            }

            //System.out.println("maxPalindStart="+start+" maxPalindLength="+length + " " + s.substring(maxPalindStart, maxPalindStart+maxPalindLength));
            
            lo = i - 1;
            hi = i;

            while (lo >= 0 && hi < N && s.charAt(lo) == s.charAt(hi))
            {
                lo--;
                hi++;
            }

            start = lo + 1;
            length = hi - start;

            //System.out.println("start2="+start+" len2="+length + " " + s.substring(maxPalindStart, maxPalindStart+maxPalindLength));
            
            if (length > maxPalindLength)
            {
                maxPalindStart = start;
                maxPalindLength = length;
            }
        }

        return s.substring(maxPalindStart, maxPalindStart+maxPalindLength);
    }


    /*
        Manacher algorithm
        O(N) - time
        O(N) - memo
    */
    public String longestPalindrome3(String s) {
        // TODO
        return null;
    }


    /*
        Suffix tree
    */
    public String longestPalindrome4(String s) {
        // TODO
        return null;
    }
}
