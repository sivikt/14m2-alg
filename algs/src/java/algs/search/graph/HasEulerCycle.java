package algs.search.graph;

import algs.adt.graph.AdjListGraph;
import algs.adt.graph.Udigraph;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Serj Sintsov
 */
public class HasEulerCycle {

    public static Set<Integer> hasEulerCycle(Udigraph G) {
        if (!isEvenDegree(G))
            return null;

        Udigraph cpG = G.copy();
        Set<Integer> cycle = new HashSet<>();

        for (int startVertex = 0; startVertex < G.V(); startVertex++) {
            if (G.degree(startVertex) > 0) {
                findCycle(cpG, startVertex, cycle);
                break;
            }
        }

        return cycle;
    }

    private static boolean isEvenDegree(Udigraph G) {
        // graph is connected and every vertex has even degree
        for (int v = 0; v < G.V(); v++)
            if (G.degree(v) % 2 != 0)
                return false;

        return true;
    }

    private static void findCycle(Udigraph G, int v, Set<Integer> cycleOut) {
        while (G.hasAdj(v)) {
            Iterable<Integer> adj = G.adj(v);
            int w = adj.iterator().next();
            G.delEdge(v, w);
            findCycle(G, w, cycleOut);
            cycleOut.add(v);
        }
    }

    // testing
    public static void main(String[] args) {
        Udigraph G = new AdjListGraph(10);
        assert hasEulerCycle(G) != null;

        G.addEdge(8, 1);
        assert hasEulerCycle(G) == null;

        G.addEdge(8, 4);
        G.addEdge(3, 6);
        G.addEdge(5, 6);
        G.addEdge(5, 1);
        G.addEdge(2, 6);
        G.addEdge(2, 4);
        assert hasEulerCycle(G) == null;

        G.addEdge(3, 5);
        assert hasEulerCycle(G) == null;

        G.addEdge(5, 4);
        G.addEdge(6, 4);
        Set<Integer> cycle = hasEulerCycle(G);
        assert cycle != null;
        assert cycle.size() == 7;
        assert cycle.contains(8);
        assert cycle.contains(1);
        assert cycle.contains(2);
        assert cycle.contains(3);
        assert cycle.contains(4);
        assert cycle.contains(5);
        assert cycle.contains(6);
    }

}
