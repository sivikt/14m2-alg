package algs.coding_jam;

/*
In a binary tree, the root node is at depth 0,
and children of each depth k node are at depth k+1.

Two nodes of a binary tree are cousins if they have
the same depth, but have different parents.

We are given the root of a binary tree with unique
values, and the values x and y of two different nodes
in the tree.

Return true if and only if the nodes corresponding
to the values x and y are cousins.


Example 1:
Input: root = [1,2,3,4], x = 4, y = 3
Output: false

Example 2:
Input: root = [1,2,3,null,4,null,5], x = 5, y = 4
Output: true

Example 3:
Input: root = [1,2,3,null,4], x = 2, y = 3
Output: false

Note:
The number of nodes in the tree will be between 2 and 100.
Each node has a unique integer value from 1 to 100.
*/

import java.util.Deque;
import java.util.LinkedList;

class CousinsInBinaryTree {
    static class Frame {
        int n;
        int depth;
        
        public Frame(int n, int d) {
            this.n = n;
            this.depth = d;
        }
    }
    
    public Frame getParent(TreeNode root, int x, int depth) {
        if (root == null)
            return new Frame(-1, -1);
        else if (root.val == x)
            return new Frame(-1, depth+1);
        
        Frame f1 = getParent(root.left, x, depth+1);
        
        if (f1.depth != -1) {
            if (f1.n == -1)
                f1.n = root.val;
            return f1;
        }
        
        Frame f2 = getParent(root.right, x, depth+1);  
        
        if (f2.depth != -1) {
            if (f2.n == -1)
                f2.n = root.val;
            return f2;
        }
        
        return new Frame(-1, -1);
    }
    
    public boolean isCousins(TreeNode root, int x, int y) {
        Frame p1 = getParent(root, x, -1); 
        
        if (x == y)
            return p1.depth > -1;
        
        Frame p2 = getParent(root, y, -1);            
        
        return p1.depth == p2.depth && p1.n != p2.n;
    }


////


    public boolean isCousins2(TreeNode root, int x, int y) {
        if (root == null)
            return false;
        
        Deque<TreeNode> lvlsQ = new LinkedList<>();
        lvlsQ.add(root);
        
        int depth = 0;
        int xDepth = -1;
        TreeNode xParent = null;
        int yDepth = -1;
        TreeNode yParent = null;
        
        while (!lvlsQ.isEmpty()) 
        {
            int lvlSize = lvlsQ.size();
            
            while (lvlSize-- > 0)
            {
                TreeNode n = lvlsQ.poll();
             
                if (x == y && n.val == x)
                    return true;
                                
                if (xParent != null && yParent != null)
                    break;
                
                if (n.left != null)
                {
                    if (n.left.val == x)
                    {
                        xDepth = depth;
                        xParent = n;
                    }

                    if (n.left.val == y)
                    {
                        yDepth = depth;
                        yParent = n;
                    }
                    
                    lvlsQ.add(n.left);
                }
                
                if (n.right != null)
                {
                    if (n.right.val == x)
                    {
                        xDepth = depth;
                        xParent = n;
                    }

                    if (n.right.val == y)
                    {
                        yDepth = depth;
                        yParent = n;
                    }
                    
                    lvlsQ.add(n.right);
                }
            }
            
            depth++;
        }
        
        return (xParent != null && yParent != null) && 
               (xParent != yParent && xDepth == yDepth);
    }
}