package algs.coding_jam;

/*
Given an array, rotate the array to the
right by k steps, where k is non-negative.

Example 1:

Input: [1,2,3,4,5,6,7] and k = 3
Output: [5,6,7,1,2,3,4]
Explanation:
rotate 1 steps to the right: [7,1,2,3,4,5,6]
rotate 2 steps to the right: [6,7,1,2,3,4,5]
rotate 3 steps to the right: [5,6,7,1,2,3,4]
Example 2:

Input: [-1,-100,3,99] and k = 2
Output: [3,99,-1,-100]
Explanation:
rotate 1 steps to the right: [99,-1,-100,3]
rotate 2 steps to the right: [3,99,-1,-100]
Note:

Try to come up as many solutions as you can,
there are at least 3 different ways to solve this problem.
Could you do it in-place with O(1) extra space?
*/
class RotateArray {
    public void rotate1(int[] nums, int k) {
        if (k == 0)
            return;
        
        int n = nums.length;
        k = k % n;
        int[] rotate_nums = new int[n];
        
        for (int i = 0; i < n; i++) {
            int j = (i+k) % n;
            rotate_nums[j] = nums[i];
        }
        
        System.arraycopy(rotate_nums, 0, nums, 0, n);
    }


////////////


    public void rotate2(int[] nums, int k) {
        int n = nums.length;
        if (n == 1 || nums == null)
            return;
        
        k = k % n;
        
        reverseArray(nums, 0, n - 1);
        reverseArray(nums, 0, k - 1);
        reverseArray(nums, k, n - 1);
    }
    
    public void reverseArray(int[] A, int start, int end) {
        for (int i = start, j = end; i < j; i++, j--)
        {
            int temp = A[i];
            A[i] = A[j];
            A[j] = temp;
        }
    }


/////////


    public int gcd(int a, int b) {
        if (a == 0)
            return b;
        
        if (a < b)
            return gcd(b%a, a);
        else
            return gcd(a%b, b);
    }
    
    public void rotate3(int[] nums, int k) {
        int n = nums.length;
        if (n == 1 || nums == null)
            return;
        
        k = k % n;
        int cycles = gcd(k, n);
        
        int start = n-2;
        
        while (cycles-- > 0) 
        {
            start = (start+1) % n;
            int i = start;
            int j;
            int curr = nums[i];
            int temp;
            
            do 
            {
                j = (i+k) % n;

                temp = curr;
                curr = nums[j];
                nums[j] = temp;            

                i = j;
            }
            while (i != start);
        }
    }
}