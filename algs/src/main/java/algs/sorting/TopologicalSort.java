package algs.sorting;

import algs.adt.graph.AdjListDigraph;
import algs.adt.graph.Digraph;
import algs.adt.stack.Stack;
import algs.adt.stack.impl.LinkedListStack;

/**
 * """In computer science, a topological sort (sometimes abbreviated topsort or toposort) or topological ordering of a
 *    directed graph is a linear ordering of its vertices such that for every directed edge uv from vertex u to vertex
 *    v, u comes before v in the ordering.
 *    For instance, the vertices of the graph may represent tasks to be performed,
 *    and the edges may represent constraints that one task must be performed before another; in this application, a
 *    topological ordering is just a valid sequence for the tasks.
 *    A topological ordering is possible if and only if the
 *    graph has no directed cycles, that is, if it is a directed acyclic graph (DAG). Any DAG has at least one
 *    topological ordering, and algorithms are known for constructing a topological ordering of any DAG in linear time.
 * """ wikipedia
 *
 * (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class TopologicalSort {

    public static Iterable<Integer> sort(Digraph G) {
        boolean[] marked = new boolean[G.V()];
        Stack<Integer> reversePost = new LinkedListStack<>();

        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v, marked, reversePost);

        return reversePost;
    }

    public static void dfs(Digraph G, int v, boolean[] marked, Stack<Integer> reversePost) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w, marked, reversePost);
        reversePost.push(v);
    }

    public static void main(String[] args) {
        Digraph G = new AdjListDigraph(7);
        G.addEdge(0, 2);
        G.addEdge(0, 5);
        G.addEdge(0, 1);
        G.addEdge(1, 4);
        G.addEdge(3, 2);
        G.addEdge(3, 4);
        G.addEdge(3, 5);
        G.addEdge(3, 6);
        G.addEdge(5, 2);
        G.addEdge(6, 0);

        assert asStr(sort(G)).equals("3 6 0 1 4 5 2 ");
    }

    private static String asStr(Iterable<Integer> it) {
        StringBuilder buf = new StringBuilder();
        for (Integer i : it) {
            buf.append(i);
            buf.append(" ");
        }
        return buf.toString();
    }
}
