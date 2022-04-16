package algs.coding_jam;

// Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.

// Note: For the purpose of this problem, we define empty string as valid palindrome.

// Example 1:

// Input: "A man, a plan, a canal: Panama"
// Output: true
// Example 2:

// Input: "race a car"
// Output: false


class ValidPalindrome {
    public boolean isPalindrome(String s) {
        int i = 0;
        int j = s.length() - 1;
        
        while (i < j) {
            if (!Character.isLetterOrDigit(s.charAt(i))) 
            {
                i++;
                continue;    
            }
            
            if (!Character.isLetterOrDigit(s.charAt(j)))
            {
                j--;
                continue;
            }
            
            if (Character.toLowerCase(s.charAt(i)) != Character.toLowerCase(s.charAt(j)))
                return false;
            else
            {
                i++;
                j--;
            }
        }
        
        return true;
    }
}