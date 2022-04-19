package algs.coding_jam;

/*
Given a binary tree, find its maximum depth.

The maximum depth is the number of nodes along the
longest path from the root node down to the farthest leaf node.

Note: A leaf is a node with no children.

Example:

Given binary tree [3,9,20,null,null,15,7],

 3
/ \
9  20
 /  \
15   7
return its depth = 3.
*/
class MaximumDepthOfBinaryTree {
    public int maxDepth(TreeNode p, int max) {
        if (p == null)
            return max;
        else
        {
            int r = maxDepth(p.right, max+1);
            int l = maxDepth(p.left, max+1);
            
            return Math.max(l, r);
        }
    }
    
    public int maxDepth(TreeNode root) {
        return maxDepth(root, 0);
    }
}