package algs.adt.graph;

import algs.adt.list.LinkedList;
import algs.adt.list.List;
import edu.princeton.cs.algs4.Bag;

/**
 * @author Serj Sintsov
 */
public class AdjListDigraph implements Digraph {

    private List<Integer>[] graph;

    @SuppressWarnings("unchecked")
    public AdjListDigraph(int V) {
        graph = (List<Integer>[]) new List[V];
        for (int v = 0; v < V; v++)
            graph[v] = new LinkedList<>();
    }

    @Override
    public void addEdge(int v, int w) {
        checkV(v);
        checkV(w);
        graph[v].add(w);
    }

    @Override
    public void delEdge(int v, int w) {
        checkV(v);
        checkV(w);
        graph[v].delete(w);
    }

    @Override
    public int V() {
        return graph.length;
    }

    @Override
    public int E() {
        int count = 0;
        for (int v = 0; v < V(); v++) count += graph[v].size();
        return count;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        Bag<Integer> copy = new Bag<>();
        for (Integer w : graph[v]) copy.add(w);
        return copy;
    }

    @Override
    public boolean hasAdj(int v) {
        checkV(v);
        return !graph[v].isEmpty();
    }

    @Override
    public Digraph copy() {
        AdjListDigraph copy = new AdjListDigraph(V());
        for (int v = 0; v < V(); v++)
            for (int w : adj(v))
                copy.graph[v].add(w);

        return copy;
    }

    @Override
    public Digraph reverse() {
        AdjListDigraph dg = new AdjListDigraph(V());
        for (int v = 0; v < V(); v++)
            for (Integer w : graph[v])
                dg.addEdge(w, v);
        return dg;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (int v = 0; v < V(); v++) {
            for (Integer w : adj(v)) {
                buf.append(v);
                buf.append(" -> ");
                buf.append(w);
                buf.append("  ");
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    private void checkV(int v) {
        if (v < 0 || v >= V()) throw new IllegalArgumentException("V has to be between 0 and " + V());
    }

}
