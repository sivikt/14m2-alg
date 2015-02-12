package algs.adt.symboltable.impl.searchtree;

/**
 * @author Serj Sintsov
 */
@FunctionalInterface
public interface TraverseVisitor<Key, Value, Node extends TreeNode<Key, Value, Node>> {

    void visit(Node n);

}
