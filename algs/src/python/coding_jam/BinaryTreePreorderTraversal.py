"""
Given the root of a binary tree, return the preorder traversal of its nodes' values.

Example 1:
Input: root = [1,null,2,3]
Output: [1,2,3]

Example 2:
Input: root = []
Output: []

Example 3:
Input: root = [1]
Output: [1]

Constraints:
    The number of nodes in the tree is in the range [0, 100].
    -100 <= Node.val <= 100

Follow up: Recursive solution is trivial, could you do it iteratively?
"""

# Definition for a binary tree node.
# class TreeNode:
#     def __init__(self, val=0, left=None, right=None):
#         self.val = val
#         self.left = left
#         self.right = right
class Solution:

    def preorderTraversalStep(self, node: Optional[TreeNode], trav: List[int]):
        if node is not None:
            trav.append(node.val)
            self.preorderTraversalStep(node.left, trav)
            self.preorderTraversalStep(node.right, trav)

    def preorderTraversal(self, root: Optional[TreeNode]) -> List[int]:
        traversal_res = []
        self.preorderTraversalStep(root, traversal_res)
        return traversal_res


class Solution2:

    def preorderTraversal(self, root: Optional[TreeNode]) -> List[int]:
        if root is None:
            return []
        else:
            stack = [root]
            traversal_res = []

            while stack:
                n = stack.pop()
                traversal_res.append(n.val)

                if n.right is not None:
                    stack.append(n.right)

                if n.left is not None:
                    stack.append(n.left)

        return traversal_res
