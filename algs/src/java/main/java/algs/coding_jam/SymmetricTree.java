package algs.coding_jam;

// Given a binary tree, check whether it is a mirror
// of itself (ie, symmetric around its center).

// For example, this binary tree [1,2,2,3,4,4,3] is symmetric:

//     1
//    / \
//   2   2
//  / \ / \
// 3  4 4  3
 

// But the following [1,2,2,null,3,null,3] is not:

//     1
//    / \
//   2   2
//    \   \
//    3    3
 

// Note:
// Bonus points if you could solve it both recursively and iteratively.


import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

class SymmetricTree {
    public void rpreorder(TreeNode root, List<Integer> visited) {
        if (root == null)
        {
            visited.add(null);
            return;
        }
        
        visited.add(root.val);
        rpreorder(root.right, visited);
        rpreorder(root.left, visited);
    }
    
    public void lpreorder(TreeNode root, List<Integer> visited) {
        if (root == null)
        {
            visited.add(null);
            return;
        }
        
        visited.add(root.val);
        lpreorder(root.left, visited);
        lpreorder(root.right, visited);
    }
    
    public boolean isSymmetric(TreeNode root) {
        List<Integer> l = new ArrayList<>();
        List<Integer> r = new ArrayList<>();
        
        if (root == null)
            return true;
        else if ((root.left == null && root.right != null) ||
                 (root.left != null && root.right == null))
            return false;
        
        lpreorder(root.left, l);
        rpreorder(root.right, r);
        
        return l.equals(r);
    }


/////


    public boolean treesEqual(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null)
            return true;
        else if (root1 != null && root2 != null)
        {
            return root1.val == root2.val &&
                treesEqual(root1.left, root2.right) &&
                treesEqual(root1.right, root2.left);
        }
        else
            return false;
    }
    
    public boolean isSymmetric2(TreeNode root) {       
        if (root == null)
            return true;
        else if (root.left == null && root.right == null)
            return true;
        
        return treesEqual(root.left, root.right);
    }


/////


    public boolean isSymmetric3(TreeNode root) {       
        if (root == null)
            return true;
        else if (root.left == null && root.right == null)
            return true;
        
        Deque<TreeNode> dq = new LinkedList<>();
        dq.addFirst(root.left);
        dq.addLast(root.right);
        
        while (!dq.isEmpty())
        {
            TreeNode l = dq.pollFirst();
            TreeNode r = dq.pollLast();
            
            if (l == null && r == null)
                continue;
            else if (l != null && r != null)
            {
                if (l.val != r.val)
                    return false;
                
                dq.addFirst(l.left);
                dq.addFirst(l.right);
                dq.addLast(r.right);
                dq.addLast(r.left);
            }
            else
                return false;
        }
        
        return true;
    }
}
