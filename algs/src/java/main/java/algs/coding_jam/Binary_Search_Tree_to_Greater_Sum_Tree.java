package algs.coding_jam;

/**
 * Given the root of a binary search tree with distinct values, modify it so that every node has a new value equal to the sum of the values of the  * original tree that are greater than or equal to node.val.
 * 
 * As a reminder, a binary search tree is a tree that satisfies these constraints:
 * 
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 * 
 * Input: [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]
 * Output: [30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]
 * 
 * Definition for a binary tree node.
 */

public class Binary_Search_Tree_to_Greater_Sum_Tree {
    private int calcLeftSum(TreeNode p, int rightSum) {
        if (p == null)
            return rightSum;
        
        p.val = p.val + this.calcRightSum(p.right, rightSum);
        return this.calcLeftSum(p.left, p.val);
    }
    
    private int calcRightSum(TreeNode p, int rightSum) {
        if (p == null)
            return rightSum;
        
        p.val = p.val + this.calcRightSum(p.right, rightSum);
        return this.calcLeftSum(p.left, p.val);
    }
    
    public TreeNode bstToGst(TreeNode root) {       
        root.val = root.val + this.calcRightSum(root.right, 0);
        this.calcLeftSum(root.left, root.val);
        return root;
    }
}