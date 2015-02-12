package algs.adt.symboltable.searchtree;

/**
 * """In computer science, a binary search tree (BST), sometimes also called an ordered or sorted binary tree, is a
 *    node-based binary tree data structure where each node has a comparable key (and an associated value) and satisfies
 *    the restriction that the key in any node is larger than the keys in all nodes in that node's left sub-tree and
 *    smaller than the keys in all nodes in that node's right sub-tree. Each node has no more than two child nodes.
 *    Each child must either be a leaf node or the root of another binary search tree. The left sub-tree contains only
 *    nodes with keys less than the parent node; the right sub-tree contains only nodes with keys greater than the
 *    parent node. BSTs are also dynamic data structures, and the size of a BST is only limited by the amount of free
 *    memory in the operating system. The main advantage of binary search trees is that it remains ordered, which
 *    provides quicker search times than many other data structures.
 *
 *    The common properties of binary search trees are as follows:
 *      - The left subtree of a node contains only nodes with keys less than the node's key.
 *      - The right subtree of a node contains only nodes with keys greater than the node's key.
 *      - The left and right subtree each must also be a binary search tree.
 *      - Each node can have up to two successor nodes.
 *      - There must be no duplicate nodes.
 *      - A unique path exists from the root to every other node.
 *
 *    Generally, the information represented by each node is a record rather than a single data element. However, for
 *    sequencing purposes, nodes are compared according to their keys rather than any part of their associated records.
 *
 *    The major advantage of binary search trees over other data structures is that the related sorting algorithms and
 *    search algorithms such as in-order traversal can be very efficient. The other advantages are:
 *      - Binary Search Tree is fast in insertion and deletion etc. when balanced.
 *      - Very efficient and its code is easier than other data structures.
 *      - Stores keys in the nodes in a way that searching, insertion and deletion can be done efficiently.
 *      - Implementation is very simple in Binary Search Trees.
 *      - Nodes in tree are dynamic in nature.
 *
 *    Binary search trees are a fundamental data structure used to construct more abstract data structures such as sets,
 *    multisets, and associative arrays. Some of their disadvantages are as follows:
 *      - The shape of the binary search tree totally depends on the order of insertions, and it can be degenerated.
 *      - When inserting or searching for an element in binary search tree, the key of each visited node has to be
 *        compared with the key of the element to be inserted or found, i.e., it takes a long time to search an element
 *        in a binary search tree.
 *      - The keys in the binary search tree may be long and the run time may increase.
 *      - After a long intermixed sequence of random insertion and deletion, the expected height of the tree approaches
 *        square root of the number of keys which grows much faster than log(n).
 *
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public interface BinarySearchTree<Key extends Comparable<? super Key>, Value> extends SearchTree<Key, Value> {

}
