// Given two binary trees, write a function to check if they are the same or not.

// Two binary trees are considered the same if they are structurally identical and the nodes have the same value.

// Example 1:

// Input:     1         1
//           / \       / \
//          2   3     2   3

//         [1,2,3],   [1,2,3]

// Output: true
// Example 2:

// Input:     1         1
//           /           \
//          2             2

//         [1,2],     [1,null,2]

// Output: false
// Example 3:

// Input:     1         1
//           / \       / \
//          2   1     1   2

//         [1,2,1],   [1,1,2]

// Output: false


/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null || q == null)
            return p == q;
        
        if (p.val != q.val)
            return false;
        
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }


////


    public boolean isSameTree_iter(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue = new LinkedList<>();
        if (p == null && q == null)
            return true;
        else if (p == null || q == null)
            return false;
        
        if (p != null && q != null) 
        {
            queue.add(p);
            queue.add(q);
        }
        
        while (!queue.isEmpty()) 
        {
            TreeNode first = queue.poll();
            TreeNode second = queue.poll();
        
            if (first == null && second == null)
                continue;
            if (first == null || second == null)
                return false;
            if (first.val != second.val)
                return false;
        
            queue.add(first.left);
            queue.add(second.left);
            queue.add(first.right);
            queue.add(second.right);
        }
        
        return true;
    }
}