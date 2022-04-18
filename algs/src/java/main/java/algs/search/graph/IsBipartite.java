package algs.search.graph;

import algs.adt.graph.AdjListGraph;
import algs.adt.graph.Graph;
import algs.adt.graph.Udigraph;
import edu.princeton.cs.algs4.Queue;

/**
 * @author Serj Sintsov
 */
public class IsBipartite {

    private static final byte UNMARKED = 0;
    private static final byte ODD  = 1;
    private static final byte EVEN = 2;

    public static boolean isBipartite(Udigraph G) {
        if (G.V() <= 1)
            return true;

        byte[] marked = new byte[G.V()];
        Queue<Integer> visited = new Queue<>();

        // BFS for all vertices
        for (int v = 0; v < G.V(); v++) {
            if (marked[v] != UNMARKED)
                continue;

            visited.enqueue(v);
            marked[v] = ODD;

            while (!visited.isEmpty()) {
                int x = visited.dequeue();
                for (int t : G.adj(x)) {
                    if (marked[t] == UNMARKED) {
                        visited.enqueue(t);
                        marked[t] = marked[x] == ODD ? EVEN : ODD;
                    }
                    else if (marked[x] == marked[t])
                        return false;
                }
            }
        }

        return true;
    }


    // testing
    public static void main(String[] args) {
        Udigraph G1 = new AdjListGraph(7);
        assert isBipartite(G1);

        G1.addEdge(0, 1);
        assert isBipartite(G1);

        G1.addEdge(0, 4);
        G1.addEdge(3, 6);
        G1.addEdge(5, 6);
        G1.addEdge(5, 1);
        G1.addEdge(2, 6);
        G1.addEdge(2, 4);
        assert isBipartite(G1);

        G1.addEdge(3, 5);
        assert !isBipartite(G1);
    }

}
