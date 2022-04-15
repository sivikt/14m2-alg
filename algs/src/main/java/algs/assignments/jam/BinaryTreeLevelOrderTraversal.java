// Given a binary tree, return the level order traversal of its nodes' 
// values. (ie, from left to right, level by level).

// For example:
// Given binary tree [3,9,20,null,null,15,7],
//     3
//    / \
//   9  20
//     /  \
//    15   7
// return its level order traversal as:
// [
//   [3],
//   [9,20],
//   [15,7]
// ]


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
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        
        if (root == null)
            return result;
        
        result.add(new LinkedList<>(Arrays.asList(root.val)));
        LinkedList<TreeNode> lvl = new LinkedList<>(Arrays.asList(root)); 
        
        while (!lvl.isEmpty()) 
        {   
            int k = lvl.size();
            List<Integer> values = new LinkedList<>();
            
            while (k-- > 0)
            {
                TreeNode n = lvl.getFirst();
                
                if (n.left != null) 
                {
                    lvl.add(n.left);
                    values.add(n.left.val);
                }
                
                if (n.right != null) 
                {
                    lvl.add(n.right);
                    values.add(n.right.val);
                }
                
                lvl.remove(0);  
            }
            
            if (!values.isEmpty())
                result.add(values);
        }
        
        return result;
    }


////


    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        traverseTree(root, 0, result);
        return result;
    }
    
    private void traverseTree(TreeNode curr, int level, List<List<Integer>> result) {
        if (curr == null)
            return;
        
        if (level == result.size())
            result.add(new ArrayList<Integer>());
        
        result.get(level).add(curr.val);
        
        traverseTree(curr.left, level + 1, result);
        traverseTree(curr.right, level + 1, result);
    }
}