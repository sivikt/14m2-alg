package algs.adt.symboltable.searchtree;

/**
 * Means that tree {@code Tree} can be merged with other {@code Tree}.
 *
 * @author Serj Sintsov
 */
public interface Mergable<Tree extends Mergable<Tree>> {

    Tree merge(Tree that);

}
