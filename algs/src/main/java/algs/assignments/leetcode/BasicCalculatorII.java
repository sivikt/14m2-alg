package algs.assignments.leetcode;// Implement a basic calculator to evaluate a simple expression string.

import java.util.LinkedList;
import java.util.Queue;

/**
 * The expression string contains only non-negative integers, +, -, *, / operators and empty spaces.
 * The integer division should truncate toward zero.
 *
 * Example 1:
 * Input: "3+2*2"
 * Output: 7
 *
 * Example 2:
 * Input: " 3/2 "
 * Output: 1
 *
 * Example 3:
 * Input: " 3+5 / 2 "
 * Output: 5
 *
 * Note:
 * You may assume that the given expression is always valid.
 * Do not use the eval built-in library function.
 */
class BasicCalculatorII {
    public static enum TokenType {
        NUM,
        ADD,
        SUB,
        DIV,
        MUL
    }
    
    public static class Token {
        public final TokenType type;
        public final int val;
        
        public Token(TokenType type, int val) {
            this.type = type;
            this.val = val;
        }
        
        public Token(TokenType type) {
            this.type = type;
            this.val = -1;
        }
        
        public String toString() {
            return type.toString() + (type == TokenType.NUM ? " " + val : "");
        }
    }
    
    
    /* lexer, tokenizer, or scanner, though scanner 
       is also a term for the first stage of a lexer 
     */
    public class Lexer {
        
        private final String src;
        private int pos;
        private int n;
        
        private Queue<Token> buffer = new LinkedList<>();
        
        public Lexer(String src) {
            this.src = src.replace(" ", "");    
            this.n = src.length();
            this.pos = 0;
        }

        private boolean endOfSrc() {
            return pos >= n;
        }
        
        public boolean hasNext() {
            return !endOfSrc() || !buffer.isEmpty();
        }
        
        public Token lookahead() {
            if (endOfSrc())
                return null;
            
            if (buffer.isEmpty())
                buffer.add(next());
            
            return buffer.peek();    
        }
               
        public Token next() {  
            if (!buffer.isEmpty())
                return buffer.poll();
            else if (!endOfSrc())
            {
                char c = src.charAt(pos);
                
                if (Character.isDigit(c)) 
                {
                    int j = pos;
                    int num = c - '0';
                    pos++;

                    while (pos < n && Character.isDigit(src.charAt(pos)))
                    {
                        num = num*10 + (src.charAt(pos) - '0');
                        pos++;
                    }

                    return new Token(TokenType.NUM, num);
                }
                else 
                {
                    pos++;
                    
                    switch (c) 
                    {
                        case '+': return new Token(TokenType.ADD); 
                        case '-': return new Token(TokenType.SUB); 
                        case '*': return new Token(TokenType.MUL); 
                        case '/': return new Token(TokenType.DIV); 
                        default:
                            throw new IllegalArgumentException("Illegal character '" + c + "'");
                    }
                }
            }
            else
                return null;
        }    
    }
    
    /* 12ms running time */
    public int calculate(String s) {
        s = s.replace(" ", "");
        
        Lexer lexer = new Lexer(s);
               
        int acc = 0;
        int cur = 0;
        int sig = 1;
        TokenType opr = TokenType.ADD;
            
        while (lexer.hasNext()) 
        {
            Token t = lexer.next();
            
            switch (t.type)
            {
                case NUM:
                    if (opr == TokenType.ADD || opr == TokenType.SUB)
                        cur = t.val;
                    else if (opr == TokenType.MUL)
                        cur *= t.val;
                    else
                        cur /= t.val;
                    
                    break;
                case MUL:
                case DIV:
                    opr = t.type;
                    break;
                default:
                    acc += cur * sig;
                    sig = (t.type == TokenType.ADD) ? 1 : -1;
                    opr = t.type;
                    break;
            }                
        }

        return acc + cur*sig;
    }

    /* 7ms running time */
	public int calculate_v2(String s) {
	    int pre = 0;
	    int curr = 0;
	    int sign = 1;
	    int op = 0;
	    int num = 0;
	    
	    for (int i = 0; i < s.length(); i++) 
	    {
	        if (Character.isDigit(s.charAt(i))) 
	        {
	            num = num * 10 + (s.charAt(i) - '0');

	            if (i == s.length() - 1 || !Character.isDigit(s.charAt(i + 1)))
	            {
                    curr = (op == 0 ? num : (op == 1 ? curr * num : curr / num));
                }
	        } 
	        else if (s.charAt(i) == '*' || s.charAt(i) == '/') 
	        {
	            op = (s.charAt(i) == '*' ? 1 : -1);
	            num = 0;
	        } 
	        else if (s.charAt(i) == '+' || s.charAt(i) == '-') 
	        {
	            pre += sign * curr;
	            sign = (s.charAt(i) == '+' ? 1 : -1);
	            op = 0;
	            num = 0;
	        }
	    }
	    
	    return pre + sign * curr;
	}
}