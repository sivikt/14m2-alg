// A string S of lowercase letters is given. We want to partition this 
// string into as many parts as possible so that each letter appears 
// in at most one part, and return a list of integers representing the
// size of these parts.

// Example 1:
// Input: S = "ababcbacadefegdehijhklij"
// Output: [9,7,8]
// Explanation:
// The partition is "ababcbaca", "defegde", "hijhklij".
// This is a partition so that each letter appears in at most one part.
// A partition like "ababcbacadefegde", "hijhklij" is incorrect, because it splits S into less parts.
// Note:

// S will have length in range [1, 500].
// S will consist of lowercase letters ('a' to 'z') only.


class Solution {
    public List<Integer> partitionLabels(String S) {
        int[] mins = new int['z'-'a'+1];
        int[] maxs = new int['z'-'a'+1];
        
        List<Integer> result = new ArrayList<>();
        
        for (int i = 0; i < S.length(); i++)
        {
            int j = S.charAt(i)-'a';
            if (mins[j] == 0)
                mins[j] = i+1;
            
            maxs[j] = i+1;
        }
        
        Byte[] idx = new Byte[mins.length];
        for (byte i = 0; i < mins.length; i++)
            idx[i] = i;
        
        Arrays.sort(idx, (a, b) -> mins[a] - mins[b]);
        
        int i = 0;
        while (i < mins.length && mins[idx[i]] == 0)
            i++;
                
        for (int j = i+1; i < mins.length; i = j, j = i+1)
        {
            int m = maxs[idx[i]];
            
            while (j < mins.length && mins[idx[j]] <= m)
            {
                m = Math.max(m, maxs[idx[j]]);
                j++;    
            }
            
            result.add(m-mins[idx[i]]+1);
        }
        
        return result;
    }


////


    public List<Integer> partitionLabels2(String S) {
        int[] last = new int[26];

        for (int i = 0; i < S.length(); ++i)
            last[S.charAt(i) - 'a'] = i;
        
        int j = 0;
        int anchor = 0;

        List<Integer> ans = new ArrayList();

        for (int i = 0; i < S.length(); ++i) 
        {
            j = Math.max(j, last[S.charAt(i) - 'a']);

            if (i == j) 
            {
                ans.add(i - anchor + 1);
                anchor = i + 1;
            }
        }
        
        return ans;
    }
}