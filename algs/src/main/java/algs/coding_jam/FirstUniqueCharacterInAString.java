package algs.coding_jam;

// Given a string, find the first non-repeating character
// in it and return it's index. If it doesn't exist, return -1.

// Examples:

// s = "leetcode"
// return 0.

// s = "loveleetcode",
// return 2.
// Note: You may assume the string contain only lowercase letters.


class FirstUniqueCharacterInAString {
    public int firstUniqChar(String s) {
        if (s.length() == 0)
            return -1;
        
        final int ALPHABET_SIZE = 26;
        
        int[] firstPost = new int[ALPHABET_SIZE];
        int[] lastPos  = new int[ALPHABET_SIZE];
        
        for (int i = 0; i < s.length(); i++)
        {
            int l = 'z'-s.charAt(i);
            if (firstPost[l] == 0)
            {
                firstPost[l] = i+1;
                lastPos[l] = i+1;
            }
            else lastPos[l] = i+1;
        }
        
        
        int first = Integer.MAX_VALUE;
    
        for (int i = 0; i < firstPost.length; i++)
            if (firstPost[i] > 0 && firstPost[i] == lastPos[i])
                first = Math.min(first, firstPost[i]);
        
        return first > 0 && first < Integer.MAX_VALUE ? first-1 : -1;
    }
}