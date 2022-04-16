package algs.coding_jam;// In a deck of cards, each card has an integer written on it.

// Return true if and only if you can choose X >= 2 such that it is 
// possible to split the entire deck into 1 or more groups of cards, where:

// Each group has exactly X cards.
// All the cards in each group have the same integer.
 

// Example 1:
// Input: [1,2,3,4,4,3,2,1]
// Output: true
// Explanation: Possible partition [1,1],[2,2],[3,3],[4,4]

// Example 2:
// Input: [1,1,1,2,2,2,3,3]
// Output: false
// Explanation: No possible partition.

// Example 3:
// Input: [1]
// Output: false
// Explanation: No possible partition.

// Example 4:
// Input: [1,1]
// Output: true
// Explanation: Possible partition [1,1]

// Example 5:
// Input: [1,1,2,2,2,2]
// Output: true
// Explanation: Possible partition [1,1],[2,2],[2,2]

// Note:
// 1 <= deck.length <= 10000
// 0 <= deck[i] < 10000


import java.util.Arrays;

class XOfAKindInADeckOfCards {
    private int gcd(int a, int b) {
        if (a == 0)
            return b;
        else
            return gcd(b%a, a);
    }
    
    public boolean hasGroupsSizeX(int[] deck) {
        if (deck.length < 2)
            return false;
        
        Arrays.sort(deck);
        
        int c = 1;
        int X = -1;
        
        for (int i = 0; i < deck.length-1; i++)
        {
            if (deck[i] == deck[i+1])
                c++;
            else
            {
                X = (X == -1) ? c : gcd(X, c);
                c = 1;
            }
        }       
               
        X = (X == -1) ? c : gcd(X, c);
        
        return X >= 2;
    }

////

    public boolean hasGroupsSizeX2(int[] deck) {
        int[] count = new int[10000];
        for (int c: deck)
            count[c]++;

        int g = -1;
        for (int i = 0; i < 10000; ++i)
            if (count[i] > 0) {
                if (g == -1)
                    g = count[i];
                else
                    g = gcd(g, count[i]);
            }

        return g >= 2;
    }
}