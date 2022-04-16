package algs.coding_jam;

// Count the number of prime numbers less than a non-negative number, n.

// Example:

// Input: 10
// Output: 4
// Explanation: There are 4 prime numbers less than 10, they are 2, 3, 5, 7.

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

class CountPrimes {

	/* Time Limit Exceeded on input 999983 */
    public int countPrimes3(int n) {
        List<Integer> sieve = new LinkedList<Integer>();
        for (int i = 2; i < n; i++)
            sieve.add(i);
        
        int d = 2;
        
        while (d > 0 && d < Math.sqrt(n)) 
        {
            int next_d = -1;
            
            Iterator<Integer> it = sieve.iterator();
            
            while (it.hasNext()) {
                int i = it.next();
                if (i == d)
                    continue;
                
                if (i%d == 0)
                    it.remove();
                else if (next_d < 0 && i > d)
                    next_d = i;
            }
                        
            d = next_d;
        }
        
        return sieve.size();
    }

    /* O(sqrt(n)*log(log(n))) */
    public int countPrimes(int n) {
        if (n <= 2)
            return 0;
        
        boolean[] sieve = new boolean[n];
        
        sieve[0] = true;
        sieve[1] = true;
        int primes = n-2;
        
        for (int i = 2; i < Math.sqrt(n); i++) 
        {    
            if (sieve[i]) 
                continue;
                        
            for (int j = i*2; j < n; j += i)
            {    
                if (!sieve[j]) 
                {
                    sieve[j] = true;
                    primes--;
                }
            }
        }
        
        return primes;
    }
}