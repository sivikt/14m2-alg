package algs.coding_jam;

// Given a binary tree, return the inorder traversal of its nodes' values.

// Example:

// Input: [1,null,2,3]
//    1
//     \
//      2
//     /
//    3

// Output: [1,3,2]
// Follow up: Recursive solution is trivial, could you do it iteratively?


import algs.coding_jam.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

class BinaryTreeInorderTraversal {
    public void inorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null)
            return;
        
        inorderTraversal(root.left, result);
        result.add(root.val);
        inorderTraversal(root.right, result);
    }
    
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        inorderTraversal(root, result);
        return result;
    }


////


    public static class F {
        TreeNode n;
        byte visit;
        
        public F(TreeNode n) {
            this.n = n;
            this.visit = 0;
        }
    }
    
    public List<Integer> inorderTraversal_iter(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        if (root == null)
            return result;
        
        Deque<F> dq = new LinkedList<>();
        dq.push(new F(root));
        
        while (!dq.isEmpty()) 
        {
            F f = dq.peek();
            
            if (f.n == null)
            {
                dq.pop();
                continue;
            }
            
            if (f.visit == 0)
            {
                dq.push(new F(f.n.left));
                f.visit++;
            }
            else
            {
                result.add(f.n.val);
                dq.pop();
                dq.push(new F(f.n.right));
            }
        }
        
        return result;
    }


/////


    public List<Integer> inorderTraversal_iter2(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        
        if (root == null)
            return result;
        
        Deque<TreeNode> dq = new LinkedList<>();
        TreeNode cur = root;
        
        while (cur != null || !dq.isEmpty()) 
        {
            while (cur != null)
            {
                dq.push(cur);  
                cur = cur.left;
            }
                
            cur = dq.pop();
            result.add(cur.val);
            cur = cur.right;
        }
        
        return result;
    }


/////


    // Morris Traversal, https://en.wikipedia.org/wiki/Threaded_binary_tree
    /*
        In this method, we have to use a new data structure-Threaded Binary Tree, 
        and the strategy is as follows:

		Step 1: Initialize current as root
		Step 2: While current is not NULL,

		If current does not have left child
		    a. Add current’s value
		    b. Go to the right, i.e., current = current.right

		Else
		    a. In current's left subtree, make current the right child of the rightmost node
		    b. Go to this left child, i.e., current = current.left

		For example:


		          1
		        /   \
		       2     3
		      / \   /
		     4   5 6

		First, 1 is the root, so initialize 1 as current, 1 has 
		left child which is 2, the current's left subtree is

		         2
		        / \
		       4   5
		So in this subtree, the rightmost node is 5, then make 
		the current(1) as the right child of 5. 
		Set current = cuurent.left (current = 2). The tree now looks like:

		         2
		        / \
		       4   5
		            \
		             1
		              \
		               3
		              /
		             6
		For current 2, which has left child 4, we can continue 
		with thesame process as we did above

		        4
		         \
		          2
		           \
		            5
		             \
		              1
		               \
		                3
		               /
		              6
		              
		then add 4 because it has no left child, then add 2, 5, 1, 3 one by one, for 
		node 3 which has left child 6, do the same as above. 
		Finally, the inorder traversal is [4,2,5,1,6,3].
     */
    public List<Integer> inorderTraversal_iter3(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        
        if (root == null)
            return result;
        
        TreeNode cur = root;
        TreeNode pre = null;
        TreeNode tmp = null;
        
        while (cur != null) 
        {
            if (cur.left == null)
            {
                result.add(cur.val);
                cur = cur.right;
            }
            else
            {
                pre = cur.left;
                
                while (pre.right != null)
                    pre = pre.right;
                
                pre.right = cur;
                tmp = cur;
                cur = cur.left;
                tmp.left = null;
            }
        }
        
        return result;
    }
}