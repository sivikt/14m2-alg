// Input :
//     1
//    / \
//   2   3
// Output : 1
// Explanation: 
// Tilt of node 2 : 0
// Tilt of node 3 : 0
// Tilt of node 1 : |2-3| = 1
// Tilt of binary tree : 0 + 0 + 1 = 1

// Input :
//     4
//    / \
//   2   9
//  / \   \
// 3   5   7
// Output : 15
// Explanation: 
// Tilt of node 3 : 0
// Tilt of node 5 : 0
// Tilt of node 7 : 0
// Tilt of node 2 : |3-5| = 2
// Tilt of node 9 : |0-7| = 7
// Tilt of node 4 : |(3+5+2)-(9+7)| = 6
// Tilt of binary tree : 0 + 0 + 0 + 2 + 7 + 6 = 15


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
    static class T { 
        int tilt = 0; 
    } 
    
    public int findSum(TreeNode root, T t) {
        if (root == null)
            return 0;

        int l = findSum(root.left, t);
        int r = findSum(root.right, t);
     
        t.tilt = t.tilt + Math.abs(l - r);
        int s = root.val + l + r;
        
        return s; 
    }
    
    public int findTilt(TreeNode root) {
        if (root == null)
            return 0;
        
        T t = new T();
        int l = findSum(root.left, t);
        int r = findSum(root.right, t);
            
        return t.tilt + Math.abs(l - r); 
    }
}