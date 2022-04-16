"""
Given a binary tree, determine if it is height-balanced.

For this problem, a height-balanced binary tree is defined as:
    a binary tree in which the left and right subtrees of every node differ in height by no more than 1.

Example 1:
Input: root = [3,9,20,null,null,15,7]
Output: true

Example 2:
Input: root = [1,2,2,3,3,null,null,4,4]
Output: false

Example 3:
Input: root = []
Output: true


Constraints:
    The number of nodes in the tree is in the range [0, 5000].
    -10^4 <= Node.val <= 10^4
"""


# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:
    def isBalancedStep(self, node: Optional[TreeNode]) -> tuple:
        if not node:
            return 0, True

        rh, rbalanced = self.isBalancedStep(node.right)
        lh, lbalanced = self.isBalancedStep(node.left)

        return 1 + max(rh, lh), (abs(rh - lh) <= 1) and rbalanced and lbalanced

    def isBalanced(self, root: Optional[TreeNode]) -> bool:
        h, balanced = self.isBalancedStep(root)
        return balanced

    def isBalancedStep_2(self, node: Optional[TreeNode]) -> tuple:
        if not node:
            return 0, True

        rh, rbalanced = self.isBalancedStep_2(node.right)

        if not rbalanced:
            return -1, False

        lh, lbalanced = self.isBalancedStep_2(node.left)

        if not lbalanced:
            return -1, False

        return 1 + max(rh, lh), (abs(rh - lh) <= 1)

    def isBalanced_2(self, root: Optional[TreeNode]) -> bool:
        h, balanced = self.isBalancedStep_2(root)
        return balanced


class Solution2:
    def isBalanced(self, root: Optional[TreeNode]) -> bool:       
        curr = root
        stack = []
        last_terminal = None
        heights = {}
        
        while curr or stack:
            while curr:
                stack.append(curr)
                curr = curr.left
            
            leaf = stack[-1]
    
            if not leaf.right or last_terminal == leaf.right:                
                # do visit terminal node
                lh = heights[leaf.left]  if leaf.left  else 0
                rh = heights[leaf.right] if leaf.right else 0
                
                if abs(rh-lh) > 1:
                    return False
                
                heights[leaf] = 1 + max(lh, rh)
                last_terminal = leaf
                stack.pop()
            else:
                curr = leaf.right

        return True
        

class Solution3:
    def isBalanced(self, root: TreeNode) -> bool:
        stack = [root] if root else []
        heights = {None : -1}

        while stack:
            node = stack.pop()
            lh = heights.get(node.left, None)
            rh = heights.get(node.right, None)

            if lh is not None and rh is not None:
                if abs(lh - rh) > 1:
                    return False

                heights[node] = 1 + max(lh, rh)

                if node.left:  del heights[node.left]
                if node.right: del heights[node.right]
            else:
                if heights.get(node, None) is None:
                    stack.append(node)
                
                if heights.get(node.left, None) is None:
                    stack.append(node.left)
                    
                if heights.get(node.right, None) is None:
                    stack.append(node.right)

        return True
