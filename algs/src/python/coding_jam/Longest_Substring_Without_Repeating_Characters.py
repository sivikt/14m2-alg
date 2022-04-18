# Given a string, find the length of the longest substring without repeating characters.

# Example 1:

# Input: "abcabcbb"
# Output: 3 
# Explanation: The answer is "abc", with the length of 3. 
# Example 2:

# Input: "bbbbb"
# Output: 1
# Explanation: The answer is "b", with the length of 1.
# Example 3:

# Input: "pwwkew"
# Output: 3
# Explanation: The answer is "wke", with the length of 3. 
#              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
             
class Solution(object):
    def lengthOfLongestSubstring(self, s):
        """
        :type s: str
        :rtype: int
        """
        if len(s) == 0:
            return 0
        elif len(s) == 1:
            return 1
        
        wnd_i = 0
        wnd_j = 1
        max_sz = 1
        
        c = {s[wnd_i]: wnd_i}
        
        while wnd_j < len(s):               
            if s[wnd_j] in c:
                wnd_i = c[s[wnd_j]] + 1
                wnd_j = wnd_i + max_sz
                
                c = {}
                for i,j in enumerate(s[wnd_i:wnd_j]): 
                    if j in c:
                        wnd_j = wnd_i + i 
                    else:
                        c[j] = wnd_i + i
            else:
                sz = wnd_j - wnd_i + 1
                if sz > max_sz:
                    max_sz = sz
                    
                c[s[wnd_j]] = wnd_j
                wnd_j += 1
                  
        return max_sz