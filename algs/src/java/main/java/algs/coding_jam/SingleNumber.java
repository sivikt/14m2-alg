package algs.coding_jam;

// Given a non-empty array of integers, every element appears
// twice except for one. Find that single one.

// Note:

// Your algorithm should have a linear runtime complexity. 
// Could you implement it without using extra memory?

// Example 1:

// Input: [2,2,1]
// Output: 1
// Example 2:

// Input: [4,1,2,1,2]
// Output: 4


// There are 4 very important properties of XOR that we will be making use of. 
// These are formal mathematical terms but actually the concepts are very simple.

// Commutative: A ⊕ B = B ⊕ A
// This is clear from the definition of XOR: it doesn’t matter which way round you order the two inputs.

// Associative: A ⊕ ( B ⊕ C ) = ( A ⊕ B ) ⊕ C
// This means that XOR operations can be chained together and the order doesn’t matter. 
// If you aren’t convinced of the truth of this statement, try drawing the truth tables.

// Identity element: A ⊕ 0 = A
// This means that any value XOR’d with zero is left unchanged.

// Self-inverse: A ⊕ A = 0
// This means that any value XOR’d with itself gives zero.


class SingleNumber {
    public int singleNumber(int[] nums) {
        if (nums.length == 1)
            return nums[0];
        
        int n = 0;
        
        for (int i = 0; i < nums.length; i++)
            n ^= nums[i];
        
        return n;
    }
}