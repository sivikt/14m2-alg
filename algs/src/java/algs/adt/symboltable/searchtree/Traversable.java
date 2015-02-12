package algs.adt.symboltable.searchtree;

/**
 * Provide possibility to traverse tree in different ways.
 *
 * @author Serj Sintsov
 */
public interface Traversable<Key> {

    /**
     * Depth-first traversal. We visit parent node before going to its children.
     * From left to right.
     */
    Iterable<Key> preorder();

    /**
     * Depth-first traversal. We visit left subtree, than visit its left most node and than we visit next subtree which
     * is considered to be right subtree.
     * From left to right.
     */
    Iterable<Key> inorder();

    /**
     * Depth-first traversal. We visit all children before visiting its parent.
     * From left to right.
     */
    Iterable<Key> postorder();

    /**
     * Breadth-first traversal. We visit every node on a level before going to a lower level.
     * From left to right.
     */
    Iterable<Key> lvlorder();

}
