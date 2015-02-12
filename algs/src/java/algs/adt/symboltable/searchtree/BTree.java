package algs.adt.symboltable.searchtree;

/**
 * """B-trees are generalizations of binary search trees in that they can have a variable number of subtrees at each
 *    node. While child-nodes have a pre-defined range, they will not necessarily be filled with data, meaning B-trees
 *    can potentially waste some space. The advantage is that B-trees do not need to be re-balanced as frequently as
 *    other self-balancing trees.
 *
 *    Due to the variable range of their node length, B-trees are optimized for systems that read large blocks of data.
 *    They are also commonly used in databases.
 * """ wikipedia
 *
 * @author Serj Sintsov
 */
public interface BTree<Key extends Comparable<? super Key>, Value> extends SearchTree<Key, Value> {

}
