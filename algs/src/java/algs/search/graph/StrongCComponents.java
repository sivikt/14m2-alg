package algs.search.graph;

import algs.adt.graph.Digraph;
import algs.adt.stack.Stack;
import algs.adt.stack.impl.LinkedListStack;

/**
 * (from materials by Robert Sedgewick)
 *
 * Two-pass linear-time Kosaraju-Sharir algorithm (1980s).
 *
 * @author Serj Sintsov
 */
public class StrongCComponents {

    private boolean[] marked;
    private int[] ids;
    private int count;

    public StrongCComponents(Digraph G) {
        marked = new boolean[G.V()];
        ids    = new int[G.V()];

        Iterable<Integer> reversePost = sort(G.reverse());
        for (int v : reversePost) {
            if (!marked[v]) {
                dfs(G, v);
                count++;
            }
        }
    }

    private Iterable<Integer> sort(Digraph G) {
        boolean[] marked = new boolean[G.V()];
        Stack<Integer> reversePost = new LinkedListStack<>();

        for (int v = 0; v < G.V(); v++)
            if (!marked[v]) dfs(G, v, marked, reversePost);

        return reversePost;
    }

    private void dfs(Digraph G, int v, boolean[] marked, Stack<Integer> reversePost) {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w, marked, reversePost);
        reversePost.push(v);
    }

    private void dfs(Digraph G, int v) {
        marked[v] = true;
        ids[v]    = count;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w);
    }

    public boolean stronglyConnected(int v, int w) {
        return ids[v] == ids[w];
    }

    public int id(int v) {
        return ids[v];
    }

    public int count() {
        return count;
    }

}
