package algs.adt.symboltable.searchtree;

import algs.adt.symboltable.OrderedSymbolTable;

/**
 * This type of Search Trees support ordered operations.
 * @see algs.adt.symboltable.searchtree.SearchTree
 *
 * @author Serj Sintsov
 */
public interface OrderedSearchTree<Key extends Comparable<? super Key>, Value> extends SearchTree<Key, Value>,
        OrderedSymbolTable<Key, Value>
{

}
