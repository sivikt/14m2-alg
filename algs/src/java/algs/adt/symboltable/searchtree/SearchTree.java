package algs.adt.symboltable.searchtree;

import algs.adt.symboltable.SymbolTable;

/**
 * """In computer science, a search tree is a tree data structure used for locating specific values from within a set.
 *    In order for a tree to function as a search tree, the key for each node must be greater than any keys in subtrees
 *    on the left and less than any keys in subtrees on the right.
 *
 *    The advantage of search trees is their efficient search time given the tree is reasonably balanced, which is to
 *    say the leaves at either end are of comparable depths. Various search-tree data structures exist, several of which
 *    also allow efficient insertion and deletion of elements, which operations then have to maintain tree balance.
 *
 * """ wikipedia
 *
 * @author Serj Sintsov
 */
public interface SearchTree<Key extends Comparable<? super Key>, Value> extends SymbolTable<Key, Value>, Traversable<Key>
{

}
