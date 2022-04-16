package algs.coding_jam;

// you can also use imports, for example:
// import java.util.*;

// you can write to stdout for debugging purposes, e.g.
// System.out.println("this is a debug message");

class Brackets {
    public int solution(String S) {
        if (S.length() < 1) return 1;
        if (S.length() < 2) return 0;
        
        char[] stack = new char[S.length()];
        int head = 0;
        
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            
            if (c == '{' || c == '(' || c == '[') {
                stack[head] = c;
                head++;
            }
            else {
                if (head == 0)
                    return 0;
                    
                char s = stack[head-1];
                
                if ( ((s == '{') && (c == '}')) ||
                     ((s == '(') && (c == ')')) ||
                     ((s == '[') && (c == ']')) ) 
                {
                    head--;         
                }    
                else return 0;
            }
        }
        
        if (head != 0)
            return 0;
        else
            return 1;
    }
}