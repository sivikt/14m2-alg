package algs.adt.symboltable.searchtree;

/**
 * """An (a,b)-tree is a search tree where all of its leaves are the same depth. Each node has at least a children and
 *    at most b children, while the root has at least 2 children and at most b children.
 * """ wikipedia
 *
 * @author Serj Sintsov
 */
public interface ABTree<Key extends Comparable<? super Key>, Value> extends SearchTree<Key, Value> {

}
