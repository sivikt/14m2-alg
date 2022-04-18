package algs.adt.symboltable.impl.searchtree.bst;

/**
 * Top-down implementation of insert and delete methods.
 * In T-Down insertion, the corrections are done while traversing down the tree to the insertion point.
 * T-Down insertion can be done iteratively which is generally faster.
 *
 *  TODO impl
 *
 * @see {@link RBTree}.
 *
 * @author Serj Sintsov
 */
public class TopDownRBTree<Key extends Comparable<? super Key>, Value>
        extends RBTree<Key, Value>
{

    @Override
    public void put(Key key, Value val) {

    }

    @Override
    public void delete(Key key) {
        throw new UnsupportedOperationException();
    }

}
