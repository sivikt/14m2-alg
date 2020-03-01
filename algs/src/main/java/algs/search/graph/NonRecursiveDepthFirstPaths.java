package algs.search.graph;

import algs.adt.graph.AdjListGraph;
import algs.adt.graph.Graph;
import algs.adt.graph.Udigraph;
import algs.adt.stack.Stack;
import algs.adt.stack.impl.LinkedListStack;

/**
 * Non-recursive implementation of DFS.
 *
 * @author Serj Sintsov
 */
public class NonRecursiveDepthFirstPaths implements Paths {

    private boolean[] marked;
    private int[]     paths;
    private int       from;

    public NonRecursiveDepthFirstPaths(Graph G, int from) {
        this.from  = from;
        this.marked = new boolean[G.V()];
        this.paths = new int[G.V()];

        dfs(G, from);
    }

    @Override
    public boolean hasPathTo(int v) {
        return marked[v];
    }

    @Override
    public Iterable<Integer> pathTo(int v) {
        Stack<Integer> p = new LinkedListStack<>();
        if (!hasPathTo(v)) return p;

        while (paths[v] != from) {
            p.push(v);
            v = paths[v];
        }
        p.push(from);

        return p;
    }

    private void dfs(Graph G, int src) {
        Stack<Integer> calls = new LinkedListStack<>();
        calls.push(src);

        while (!calls.isEmpty()) {
            int dest = calls.pop();

            if (marked[dest])
                continue;
            else {
                paths[dest] = src;
                src = dest;
                marked[src] = true;
            }

            for (int w : G.adj(src)) {
                if (!marked[w])
                    calls.push(w);
            }
        }
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

        NonRecursiveDepthFirstPaths path = new NonRecursiveDepthFirstPaths(G, 3);

        assert path.hasPathTo(1);
        assert path.hasPathTo(3);
        assert path.hasPathTo(5);
        assert !path.hasPathTo(10);
        assert !path.hasPathTo(7);
    }

}
