package algs.coding_jam;

// Write a function to find the longest common prefix string amongst an array of strings.

// If there is no common prefix, return an empty string "".

// Example 1:

// Input: ["flower","flow","flight"]
// Output: "fl"
// Example 2:

// Input: ["dog","racecar","car"]
// Output: ""
// Explanation: There is no common prefix among the input strings.
// Note:

// All given inputs are in lowercase letters a-z.


class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0)
            return "";
        else if (strs.length == 1)
            return strs[0];
        
        int i = 0;
        
        while (true)
        {
            for (int j = 0; j < strs.length-1; j++)
            {
                if (i == strs[j].length() || i == strs[j+1].length() || strs[j].charAt(i) != strs[j+1].charAt(i))
                    return strs[j].substring(0, i);
            }
            
            i++;
        }
    }
}