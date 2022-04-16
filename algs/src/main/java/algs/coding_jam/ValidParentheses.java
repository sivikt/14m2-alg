package algs.coding_jam;

// Given a string containing just the characters '(', ')', '{',
// '}', '[' and ']', determine if the input string is valid.

// An input string is valid if:

// Open brackets must be closed by the same type of brackets.
// Open brackets must be closed in the correct order.
// Note that an empty string is also considered valid.

// Example 1:

// Input: "()"
// Output: true
// Example 2:

// Input: "()[]{}"
// Output: true
// Example 3:

// Input: "(]"
// Output: false
// Example 4:

// Input: "([)]"
// Output: false
// Example 5:

// Input: "{[]}"
// Output: true


class ValidParentheses {
    public boolean isValid(String s) {
        if (s.length() == 0)
            return true;
        else if (s.length() % 2 != 0)
            return false;
        
        char[] stack = new char[(int)s.length()];
        int top = 0;
        
        stack[top++] = s.charAt(0);
        
        
        for (int i = 1; i < s.length(); i++)
        {
            char c = s.charAt(i);
            
            switch (c) 
            {
                case '(': case '{': case '[': 
                    stack[top++] = c;
                    break;
                default:
                    char l = stack[--top];
                    if (!((l == '(' && c == ')') || 
                          (l == '[' && c == ']') || 
                          (l == '{' && c == '}')))
                        return false;
            }
        }
        
        return top == 0;
    }
}