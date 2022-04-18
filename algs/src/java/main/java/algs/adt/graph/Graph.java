package algs.adt.graph;

/**
 * (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public interface Graph {

    void addEdge(int v, int w);

    void delEdge(int v, int w);

    int  V();

    int  E();

    Iterable<Integer> adj(int v);

    boolean hasAdj(int v);

    Graph copy();

}
