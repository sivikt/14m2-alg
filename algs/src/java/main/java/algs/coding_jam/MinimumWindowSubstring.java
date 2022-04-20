package algs.coding_jam;

/*
Given a string S and a string T, find the minimum window in S
which will contain all the characters in T in complexity O(n).

Example:
Input: S = "ADOBECODEBANC", T = "ABC"
Output: "BANC"

Note:
If there is no such window in S that covers all characters in T, return the empty string "".
If there is such window, you are guaranteed that there will always be only one unique minimum window in S.
*/

class MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        if (s.length() < t.length())
            return "";
        
        Set<Character> tChars = new HashSet<>();
        
        for (int i = 0; i < t.length(); i++)
            tChars.add(t.charAt(i));
        
        int w = t.length();
                    
        Set<Character> wChars = new HashSet<>();
        
        for (int i = 0; i < s.length(); i++)
            wChars.add(s.charAt(i));
                
        if (!wChars.containsAll(tChars))
            return "";       
        
        String min = s;
        int i = 0;
        
        while (i <= s.length() - w)
        {        
            wChars = new HashSet<>();
        
            String sub = s.substring(i, i+w);
            for (int k = 0; k < sub.length(); k++)
                wChars.add(sub.charAt(k));
            
            if (wChars.containsAll(tChars))
                return sub;
                
            int j = i+w;
            
            while (j < s.length())
            {
                wChars.add(s.charAt(j));
                
                if (wChars.containsAll(tChars))
                {
                    sub = s.substring(i, j+1);
                    min = (min.length() > sub.length()) ? sub : min;
                    break;
                }
                
                j++;
            }
            
            i++;
        }
        
        return min;
    }
}