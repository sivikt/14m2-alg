package algs.coding_jam;

// Given n pairs of parentheses, write a function to
// generate all combinations of well-formed parentheses.

// For example, given n = 3, a solution set is:

// [
//   "((()))",
//   "(()())",
//   "(())()",
//   "()(())",
//   "()()()"
// ]

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

class GenerateParentheses {
    public static final class State1 {
        String expr;
        int closed;  
        
        public State1(String expr, int closed) {
            this.expr = expr;
            this.closed = closed;
        }
        
        public String toString() {
            return "'" + this.expr + ", " + this.closed + "'";
        }
    }
    
    public List<String> generateParenthesis_DFS_like(int n) {
        List<String> result = new LinkedList<String>();
        
        Deque<State1> stack = new ArrayDeque<State1>();
        
        stack.push(new State1("(", 0));
        
        while (!stack.isEmpty())
        {
            State1 s = stack.pop();

            if (s.expr.length() == n*2)
            {
                result.add(s.expr);
                continue;
            }
            
            if (s.expr.length() - s.closed < n)
                stack.push(new State1(s.expr + "(", s.closed));
            
            if (2 * s.closed < s.expr.length())
                stack.push(new State1(s.expr + ")", s.closed+1));
            
            //System.out.println(stack);
        }
        
        return result;
    }

    /////
    public static final class State {
        public int numOpen;
        public int numClose;
        public int step;
        
        public State(int numOpen, int numClose, int step) {
            this.numOpen = numOpen;
            this.numClose = numClose;
            this.step = step;
        }
    }
    
    public List<String> generateParenthesis2(int n) {       
        Deque<State> stack = new ArrayDeque<State>();
        char[] str = new char[2*n];
        
        int i = 1;
        stack.push(new State(n, n, 0));
        
        List<String> result = new LinkedList<>();
            
        while (!stack.isEmpty())
        {
            State curr = stack.peekFirst();
            
            if (curr.numOpen == 0)
            {
                str[i] = ')';
                
                result.add(new String(str));    
            }
            else if (curr.numClose <= curr.numOpen)
            {
                // str[str.length-1] = // TBD
            }
        }
        
        return result;
    }

    /////

    public void generate(List<String> result, 
                         String str, 
                         int openCount,
                         int closeCount) 
    {
        if (openCount == 0 && closeCount == 0)
        {
            result.add(new String(str));
            return;
        }    
        
        if (openCount > 0) 
            generate(result, str+"(", openCount-1, closeCount);
        
        if (openCount < closeCount)
            generate(result, str+")", openCount, closeCount-1);
    } 
    
    public List<String> generateParenthesis1(int n) {       
        char[] str = new char[2*n];        
        List<String> result = new LinkedList<>();
        
        generate(result, "", n, n);
        
        return result;
    }
}