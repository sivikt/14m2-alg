package algs.search.graph;

/**
 * (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public interface Paths {

    boolean hasPathTo(int v);

    Iterable<Integer> pathTo(int v);

}
