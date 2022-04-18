package algs.coding_jam;

class Nesting {
    public int solution(String S) {
        if (S.length() < 1) return 1;
        if (S.length() < 2) return 0;
        
        char[] stack = new char[S.length()];
        int head = 0;
        
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            
            if (c == '(') {
                stack[head] = c;
                head++;
            }
            else {
                if (head == 0)
                    return 0;
                    
                char s = stack[head-1];
                
                if ((s == '(') && (c == ')'))
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