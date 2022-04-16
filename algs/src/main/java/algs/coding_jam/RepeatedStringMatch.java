// Given two strings A and B, find the minimum number of times A 
// has to be repeated such that B is a substring of it. 
// If no such solution, return -1.

// For example, with A = "abcd" and B = "cdabcdab".

// Return 3, because by repeating A three times (“abcdabcdabcd”), 
// B is a substring of it; and B is not a substring of A repeated 
// two times ("abcdabcd").

// Note:
// The length of A and B will be between 1 and 10000.

import java.math.BigInteger;


class Solution {
    public int repeatedStringMatch(String A, String B) {
        int q = 1;
        String S = A;
        
        for (; S.length() < B.length(); q++)
            S += A;
        
        if (S.indexOf(B) >= 0)
            return q;
        else if ((S+A).indexOf(B) >= 0)
            return q+1;
        else
            return -1;
    }


////


    // Rabin-Karp (Rolling Hash) approach using polynomial rolling hash
    public boolean isEqual(int at, String A, String B) {
        for (int i = 0; i < B.length(); i++)
            if (B.charAt(i) != A.charAt((i + at)% A.length()))
                return false;
        
        return true;
    }
    
    public int repeatedStringMatch(String A, String B) {
        int q = (B.length() - 1) / A.length() + 1;
        int p = 113; // prime
        int M = 1_000_000_007; // prime
        int invP = BigInteger.valueOf(p).modInverse(BigInteger.valueOf(M)).intValue();
        
        long bHash = 0;
        long power = 1;
        
        for (int i = 0; i < B.length(); i++)
        {
            bHash += power * B.codePointAt(i);
            bHash %= M;
            power = (power*p) % M;
        }
        
        long aHash = 0;
        power = 1;
        
        for (int i = 0; i < B.length(); i++)
        {
            aHash += power * A.codePointAt(i%A.length());
            aHash %= M;
            power = (power*p) % M;
        }
        
        if (aHash == bHash && isEqual(0, A, B))
            return q;
        
        // else, calculate rolling hash
        power = (power * invP) % M;
        
        for (int i = B.length(); i < (q+1)*A.length(); i++)
        {
            aHash -= A.codePointAt((i-B.length()) % A.length());
            aHash *= invP;
            aHash += power*A.codePointAt(i % A.length());
            aHash %= M;
            
            if (aHash == bHash && isEqual(i-B.length()+1, A, B))
                return (i < q*A.length()) ? q : q+1;
        }
        
        return -1;
    }
}