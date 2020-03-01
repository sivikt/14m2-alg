package algs.adt.symboltable.searchtree;

/**
 * """In computer science, a Cartesian tree is a binary tree derived from a sequence of numbers; it can be uniquely
 *    defined from the properties that it is heap-ordered and that a symmetric (in-order) traversal of the tree returns
 *    the original sequence. Introduced by Vuillemin (1980) in the context of geometric range searching data structures,
 *    Cartesian trees have also been used in the definition of the treap and randomized binary search tree data structures
 *    for binary search problems. The Cartesian tree for a sequence may be constructed in linear time using a stack-based
 *    algorithm for finding all nearest smaller values in a sequence.
 *
 *    Cartesian trees may be used as part of an efficient data structure for range minimum queries, a range searching
 *    problem involving queries that ask for the minimum value in a contiguous subsequence of the original sequence.
 *    In a Cartesian tree, this minimum value may be found at the lowest common ancestor of the leftmost and rightmost
 *    values in the subsequence.
 *
 *    Cartesian trees were introduced and named by Vuillemin (1980). The name is derived from the Cartesian coordinate
 *    system for the plane: in Vuillemin's version of this structure, as in the two-dimensional range searching
 *    application discussed above, a Cartesian tree for a point set has the sorted order of the points by their
 *    x-coordinates as its symmetric traversal order, and it has the heap property according to the y-coordinates of the
 *    points. Gabow, Bentley & Tarjan (1984) and subsequent authors followed the definition here in which a Cartesian tree
 *    is defined from a sequence; this change generalizes the geometric setting of Vuillemin to allow sequences other than
 *    the sorted order of x-coordinates, and allows the Cartesian tree to be applied to non-geometric problems as well.
 * """ wikipedia
 *
 * @author Serj Sintsov
 */
public interface CartesianTree<Key extends Comparable<? super Key>, Value> extends BinarySearchTree<Key, Value> {
}
