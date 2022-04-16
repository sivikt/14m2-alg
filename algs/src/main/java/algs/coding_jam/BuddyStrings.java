package algs.coding_jam;

// Given two strings A and B of lowercase letters, return true if and only
// if we can swap two letters in A so that the result equals B.

// Example 1:
// Input: A = "ab", B = "ba"
// Output: true

// Example 2:
// Input: A = "ab", B = "ab"
// Output: false

// Example 3:
// Input: A = "aa", B = "aa"
// Output: true

// Example 4:
// Input: A = "aaaaaaabc", B = "aaaaaaacb"
// Output: true

// Example 5:
// Input: A = "", B = "aa"
// Output: false
 
// Note:
// 0 <= A.length <= 20000
// 0 <= B.length <= 20000
// A and B consist only of lowercase letters.


class BuddyStrings {
    public boolean buddyStrings(String A, String B) {
        if (A.length() != B.length())
            return false;
        else if (A.length() < 2) 
            return false;
        
        if (A.equals(B)) 
        {
            int[] count = new int['z'-'a'+1];
            
            for (int i = 0; i < A.length(); ++i)
                count[A.charAt(i) - 'a']++;

            for (int c: count)
                if (c > 1) return true;
            
            return false;
        }
        else
        {
            int m = -1;
            int n = -1;

            for (int i = 0; i < A.length(); i++)
            {            
                if ((A.charAt(i) - B.charAt(i)) != 0)
                {
                    if (m == -1)
                        m = i;
                    else if (n == -1)
                        n = i;
                    else
                        return false;
                }
            }

            return n != -1 && A.charAt(m) == B.charAt(n) && A.charAt(n) == B.charAt(m);
        }
    }
}