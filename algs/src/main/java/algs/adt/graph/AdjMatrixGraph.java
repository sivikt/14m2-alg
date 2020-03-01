package algs.adt.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Adjacency matrix tend be be over approach since real-world graphs tend to be sparse.
 * But matrix require amount of memory proportional to V^2.
 *
 * @author Serj Sintsov
 */
public class AdjMatrixGraph extends AbstractUdigraph {

    private boolean[][] graph;

    public AdjMatrixGraph(int V) {
        graph = new boolean[V][V];
    }

    @Override
    public void addEdge(int v, int w) {
        checkV(v);
        checkV(w);
        graph[v][w] = graph[w][v] = true;
    }

    @Override
    public void delEdge(int v, int w) {
        checkV(v);
        checkV(w);
        graph[v][w] = graph[w][v] = false;
    }

    @Override
    public int V() {
        return graph.length;
    }

    @Override
    public int E() {
        int count = 0;
        for (int v = 0; v < V(); v++)
            for (int w = 0; w <= v; w++)
                if (graph[v][w]) count += 1;
        return count;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        checkV(v);
        List<Integer> adj = new ArrayList<>();
        for (int i = 0; i < V(); i++)
            if (graph[v][i]) adj.add(i);
        return adj;
    }

    @Override
    public boolean hasAdj(int v) {
        checkV(v);
        for (int w = 0; w < V(); w++)
            if (graph[v][w])
                return true;
        return false;
    }

    @Override
    public Udigraph copy() {
        AdjListGraph copy = new AdjListGraph(V());
        for (int v = 0; v < V(); v++)
            for (int w : adj(v))
                graph[v][w] = true;

        return copy;
    }

}
