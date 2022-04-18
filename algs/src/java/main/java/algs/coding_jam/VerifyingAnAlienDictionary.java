package algs.coding_jam;

// In an alien language, surprisingly they also use english lowercase letters,
// but possibly in a different order. The order of the alphabet is some permutation 
// of lowercase letters.

// Given a sequence of words written in the alien language, and the order of the 
// alphabet, return true if and only if the given words are sorted lexicographicaly 
// in this alien language.

 

// Example 1:
// Input: words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
// Output: true
// Explanation: As 'h' comes before 'l' in this language, then the sequence is sorted.

// Example 2:
// Input: words = ["word","world","row"], order = "worldabcefghijkmnpqstuvxyz"
// Output: false
// Explanation: As 'd' comes after 'l' in this language, then words[0] > words[1], hence the sequence is unsorted.

// Example 3:
// Input: words = ["apple","app"], order = "abcdefghijklmnopqrstuvwxyz"
// Output: false
// Explanation: The first three characters "app" match, and the second string is shorter (in size.) 
//              According to lexicographical rules "apple" > "app", because 'l' > '∅', where '∅' is defined 
//              as the blank character which is less than any other character (More info).


class VerifyingAnAlienDictionary {
    private boolean isGreater(String w1, String w2, byte[] alphabet) {
        int i = 0;
        int j = 0;
        
        while (i < w1.length() && j < w2.length())
        {
            byte r1 = alphabet[w1.charAt(i)-'a'];
            byte r2 = alphabet[w2.charAt(j)-'a'];
            
            if (r1 == r2)
            {
                i++;
                j++;
            }
            else if (r1 < r2)
                return false;
            else
                return true;
        }
        
        
        return i < w1.length();
    }
    
    public boolean isAlienSorted(String[] words, String order) {
        byte[] alphabet = new byte[order.length()];
        
        for (int i = 0; i < alphabet.length; i++)
            alphabet[order.charAt(i)-'a'] = (byte) i;
        
        for (int i = 1; i < words.length; i++)
        {
            if (isGreater(words[i-1], words[i], alphabet))
                return false;
        }
        
        return true;
    }
}