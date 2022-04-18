package algs.coding_jam;

// Given an array where elements are sorted in ascending
// order, convert it to a height balanced BST.

// For this problem, a height-balanced binary tree is defined 
// as a binary tree in which the depth of the two subtrees of 
// every node never differ by more than 1.

// Example:

// Given the sorted array: [-10,-3,0,5,9],

// One possible answer is: [0,-3,9,-10,null,5], which represents 
// the following height balanced BST:

//       0
//      / \
//    -3   9
//    /   /
//  -10  5


import java.util.ArrayDeque;
import java.util.Deque;


class ConvertSortedArrayToBinarySearchTree {
    public TreeNode sortedArrayToBST(int l, int r, int[] nums) {
        if (r == l)
            return new TreeNode(nums[l]);
        else if (l < r)
        {
            int m = (l+r)/2;
            TreeNode n = new TreeNode(nums[m]);
            n.left = sortedArrayToBST(l, m-1, nums);
            n.right = sortedArrayToBST(m+1, r, nums);
            return n;
        }
        else return null;
    }

    
    public TreeNode sortedArrayToBST(int[] nums) {
        return sortedArrayToBST(0, nums.length-1,    nums);
    }


////


    public TreeNode sortedArrayToBST_2(int[] nums) {
        if (nums == null || nums.length == 0) 
            return null;
                
        int lo = 0;
        int hi = nums.length - 1;
        int mid;
        
        Deque<TreeNode> st = new ArrayDeque<>();
        TreeNode root = new TreeNode(nums[hi/2]);
        st.push(root);
        
        Deque<Integer> boundaries = new ArrayDeque<>();
        boundaries.push(hi);
        boundaries.push(lo);

        while (!st.isEmpty()) 
        {
            TreeNode cur = st.pop();
            lo = boundaries.pop();
            hi = boundaries.pop();
            
            mid = (lo + hi)/2;

            if (mid-1 >= lo)
            {
                int l = lo;
                int r = mid-1;
                int m = (l+r)/2;
                cur.left = new TreeNode(nums[m]);
                st.push(cur.left);
                boundaries.push(r);
                boundaries.push(l);
            }
                
            if (mid+1 <= hi) 
            {
                int l = mid+1;
                int r = hi;
                int m = (l+r)/2;
                cur.right = new TreeNode(nums[m]);
                st.push(cur.right);
                boundaries.push(r);
                boundaries.push(l);
            }
        }
        
        return root;
    }


////


    public TreeNode sortedArrayToBST_3(int[] nums) {
        if (nums == null || nums.length == 0) 
            return null;
        else if (nums.length == 1)
        {
            return new TreeNode(nums[0]);
        }
                
        int lo = 0;
        int hi = nums.length - 1;
        int mid;
        
        int N = (int)Math.ceil(nums.length/2.0);
        
        TreeNode[] st = new TreeNode[N];
        TreeNode root = new TreeNode(nums[hi/2]);
        int st_top = 0;
        st[st_top++] = root;
        
        int[] boundaries = new int[2*N];
        int b_top = 0;
        boundaries[b_top++] = hi;
        boundaries[b_top++] = lo;

        while (st_top > 0) 
        {
            TreeNode cur = st[--st_top];
            lo = boundaries[--b_top];
            hi = boundaries[--b_top];
            
            mid = (lo + hi)/2;

            if (mid-1 >= lo)
            {
                int l = lo;
                int r = mid-1;
                int m = (l+r)/2;
                cur.left = new TreeNode(nums[m]);
                st[st_top++] = cur.left;
                boundaries[b_top++] = r;
                boundaries[b_top++] = l;
            }
                
            if (mid+1 <= hi) 
            {
                int l = mid+1;
                int r = hi;
                int m = (l+r)/2;
                cur.right = new TreeNode(nums[m]);
                st[st_top++] = cur.right;
                boundaries[b_top++] = r;
                boundaries[b_top++] = l;
            }
        }
        
        return root;
    }
}