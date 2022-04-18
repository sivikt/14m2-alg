package algs.search.graph;

import algs.adt.graph.AdjListGraph;
import algs.adt.graph.Graph;
import algs.adt.graph.Udigraph;

/**
 * @author Serj Sintsov
 */
public class HasCycle {

    public static boolean isCyclic(Udigraph G) {
        boolean[] marked = new boolean[G.V()];
        int[] path       = new int[G.V()];

        for (int v = 0; v < G.V(); v++) {
            if (!marked[v] && dfs(G, v, marked, path))
                return true;
        }

        return false;
    }

    private static boolean dfs(Graph G, int v, boolean[] marked, int[] path) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (!marked[w]) {
                path[w] = v;
                return dfs(G, w, marked, path);
            }
            else if (v != w && path[v] != w)
                return true;
        }
        return false;
    }

    // testing
    public static void main(String[] args) {
        Udigraph G0 = new AdjListGraph(5);
        G0.addEdge(0, 1);
        G0.addEdge(0, 2);
        G0.addEdge(0, 3);
        G0.addEdge(1, 4);
        G0.addEdge(3, 3);

        assert !isCyclic(G0);
        G0.addEdge(2, 4);
        assert isCyclic(G0);
    }

}
