package algs.coding_jam;

// Given two non-negative integers num1 and num2 represented as strings,
// return the product of num1 and num2, also represented as a string.

// Example 1:

// Input: num1 = "2", num2 = "3"
// Output: "6"
// Example 2:

// Input: num1 = "123", num2 = "456"
// Output: "56088"
// Note:

// The length of both num1 and num2 is < 110.
// Both num1 and num2 contain only digits 0-9.
// Both num1 and num2 do not contain any leading zero, except the number 0 itself.
// You must not use any built-in BigInteger library or convert the inputs to integer directly.


class MultiplyStrings {
    public int[] mul(int[] a, int m, int pad) {  
        if (m == 0)
            return new int[1];
        
        int[] result = new int[a.length+pad];
         
        int r = 0;
        int d = 0;
        
        for (int i = a.length-1; i >= 0; i--)
        {
            d = r+(a[i]*m);
            r = d/10;
            result[i] = d%10;
        }
        
        if (r > 0)
        {
            int[] tmp = new int[1+result.length];
            tmp[0] = r;
            System.arraycopy(result, 0, tmp, 1, result.length);
            result = tmp;
        }
        
        return result;
    }
    
    public int[] add(int[] a, int[] b) {     
        if (b.length == 1 && b[0] == 0)
            return a;
        
        int r = 0;
        int d = 0;
        int h = a.length - b.length;
            
        for (int i = b.length-1; i >= 0; i--)
        {
            d = r+(a[i+h]+b[i]);
            r = d/10;
            a[i+h] = d%10;
        }
        
        for (int i = h-1; i >= 0; i--)
        {
            d = r+a[i];
            r = d/10;
            a[i] = d%10;
        }
        
        if (r > 0)
        {
            int[] tmp = new int[1+a.length];
            tmp[0] = r;
            System.arraycopy(a, 0, tmp, 1, a.length);
            a = tmp;
        }
        
        return a;
    }
    
    public int[] mul(int[] a, int[] b) {        
        int[] sum = null;
         
        for (int i = 0; i < b.length; i++)
        {
            int[] r = mul(a, b[i], b.length-i-1);
                        
            if (sum == null) 
                sum = r;
            else if (sum.length > r.length)
                sum = add(sum, r);
            else
                sum = add(r, sum);
        }
         
        return sum;
    }
    
    public String multiply(String num1, String num2) {
        int[] a = new int[num1.length()];
        int[] b = new int[num2.length()];
        
        for (int i = 0; i < a.length; i++)
            a[i] = Character.getNumericValue(num1.charAt(i));
        
        for (int i = 0; i < b.length; i++)
            b[i] = Character.getNumericValue(num2.charAt(i));
        
        int[] result;
            
        if (a.length > b.length)
            result = mul(a, b);
        else
            result = mul(b, a);
        
        char[] cresult = new char[result.length];
        for (int i = 0; i < result.length; i++)
            cresult[i] = Character.forDigit(result[i], 10);
        
        return new String(cresult);
    }


//////


    /*
            999
		    999
		    ---
		   8991
		  89910
		 899100 

		pos = [000000]
     */
    public String multiply2(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();
        
        int[] pos = new int[m + n];

        for (int i = m - 1; i >= 0; i--) 
        {
            for (int j = n - 1; j >= 0; j--) 
            {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0'); 
                int p1 = i + j;
                int p2 = i + j + 1;
                int sum = mul + pos[p2];

                pos[p1] += sum / 10;
                pos[p2] = (sum) % 10;
            }
        }  

        
        int i = 0;
        for (; i < pos.length; i++)
            if (pos[i] != 0)
                break;
        
        if (pos.length > i)
        {
            char[] cresult = new char[pos.length-i];

            for (int j = 0; i < pos.length; i++, j++)
                cresult[j] = Character.forDigit(pos[i], 10);
            
            return new String(cresult);
        }
        else 
            return "0";
    }


//////


    public String multiply3(String num1, String num2) {
        int m = num1.length();
        int n = num2.length();
        int zero = 0;
        
        int[] a = new int[m];
        int[] c = new int[m+n];
        
        for (int i = 0, k = m; i < m; i++)
            // reverse the first number
            a[--k] = num1.charAt(i)-'0';  
        
        for (int i = n-1; i>=0; i--)
            // multiply each digits of num2 to num1
            add(c, a, num2.charAt(i)-'0', zero++);
        
        // handle all carry operation together
        carry(c);            
        
        int i=m+n;
        
        // find the highest digit
        while (i>0 && c[--i] == 0);  
        
        i++;
        
        StringBuilder ret = new StringBuilder(i);
        
        while (i>0) 
            ret.append((char)(c[--i]+'0'));
        
        return ret.toString();
    }
    
    public void carry(int[] a) {
        int i;
        for (int k = 0, d = 0; k < a.length; k++)
        {
            i = a[k]+d;
            a[k] = i%10;
            d = i/10;
        }
    }
    
    public void add(int[] c, int[] a, int b, int zero) {
        for (int i = zero, j = 0; j < a.length; j++, i++)
            c[i] += a[j]*b;
    }
}

