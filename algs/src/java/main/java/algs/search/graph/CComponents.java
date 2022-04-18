package algs.search.graph;

import algs.adt.graph.AdjListGraph;
import algs.adt.graph.Udigraph;

/**
 * (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class CComponents {

    private boolean[] marked;
    private int[] ids;
    private int count;

    public CComponents(Udigraph G) {
        marked = new boolean[G.V()];
        ids    = new int[G.V()];
        for (int v = 0; v < G.V(); v++) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    public boolean connected(int v, int w) {
        return ids[v] == ids[w];
    }

    public int id(int v) {
        return ids[v];
    }

    public int count() {
        return count;
    }

    private void dfs(Udigraph G, int v) {
        marked[v] = true;
        ids[v]    = count;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    public static void main(String[] args) {
        Udigraph G = new AdjListGraph(13);
        G.addEdge(0, 1);
        G.addEdge(0, 2);
        G.addEdge(0, 5);
        G.addEdge(0, 6);
        G.addEdge(3, 4);
        G.addEdge(3, 5);
        G.addEdge(4, 5);
        G.addEdge(4, 6);
        G.addEdge(7, 8);
        G.addEdge(9, 10);
        G.addEdge(9, 11);
        G.addEdge(9, 12);
        G.addEdge(11, 12);

        CComponents cc = new CComponents(G);

        assert cc.connected(2, 5);
        assert !cc.connected(9, 8);
        assert cc.count() == 3;
        assert cc.id(1) == 0;
    }

}
