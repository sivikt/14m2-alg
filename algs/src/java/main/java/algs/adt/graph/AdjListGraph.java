package algs.adt.graph;

import algs.adt.list.LinkedList;
import algs.adt.list.List;
import edu.princeton.cs.algs4.Bag;

/**
 * @author Serj Sintsov
 */
public class AdjListGraph extends AbstractUdigraph {

    private List<Integer>[] graph;

    @SuppressWarnings("unchecked")
    public AdjListGraph(int V) {
        graph = (List<Integer>[]) new List[V];
        for (int v = 0; v < V; v++)
            graph[v] = new LinkedList<>();
    }

    @Override
    public AdjListGraph copy() {
        AdjListGraph copy = new AdjListGraph(V());
        for (int v = 0; v < V(); v++)
            for (int w : adj(v))
                copy.graph[v].add(w);

        return copy;
    }

    @Override
    public void addEdge(int v, int w) {
        checkV(v);
        checkV(w);
        graph[v].add(w);
        graph[w].add(v);
    }

    @Override
    public void delEdge(int v, int w) {
        checkV(v);
        checkV(w);
        graph[v].delete(w);
        graph[w].delete(v);
    }

    @Override
    public int V() {
        return graph.length;
    }

    @Override
    public int E() {
        int count = 0;
        for (int v = 0; v < V(); v++)
            count += graph[v].size();
        return count/2;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        Bag<Integer> copy = new Bag<>();
        for (Integer w : graph[v])
            copy.add(w);
        return copy;
    }

    @Override
    public boolean hasAdj(int v) {
        checkV(v);
        return !graph[v].isEmpty();
    }


    // testing
    public static void main(String[] args) {
        AdjListGraph G = new AdjListGraph(7);
        G.addEdge(0, 4);
        G.addEdge(3, 6);
        G.addEdge(5, 6);
        G.addEdge(5, 1);
        G.addEdge(2, 6);
        G.addEdge(2, 4);
        G.addEdge(3, 5);

        AdjListGraph G1 = G.copy();
        assert G.toString().equals(G1.toString());
    }

}
