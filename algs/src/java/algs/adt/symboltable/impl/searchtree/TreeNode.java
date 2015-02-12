package algs.adt.symboltable.impl.searchtree;

/**
 * Represents minimum data to store binary tree node.
 *
 * @author Serj Sintsov
 */
public class TreeNode<Key, Value, Node extends TreeNode<Key, Value, Node>> {

    public Key key;
    public Value value;
    public Node left;
    public Node right;

    public TreeNode(Key key, Value value) {
        this.key   = key;
        this.value = value;
    }

}
