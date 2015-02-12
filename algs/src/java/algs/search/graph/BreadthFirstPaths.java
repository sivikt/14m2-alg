package algs.search.graph;

import algs.adt.graph.Graph;
import algs.adt.stack.Stack;
import algs.adt.stack.impl.LinkedListStack;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class BreadthFirstPaths implements Paths {

    private boolean[] marks;
    private int[]     paths;
    private Set<Integer> from = new HashSet<>();

    public BreadthFirstPaths(Graph G, int from) {
        this(G, new Integer[] {from});
    }

    public BreadthFirstPaths(Graph G, Integer... from) {
        this.from.addAll(Arrays.asList(from));
        this.marks = new boolean[G.V()];
        this.paths = new int[G.V()];

        bfs(G, this.from);
    }

    @Override
    public boolean hasPathTo(int v) {
        return marks[v];
    }

    @Override
    public Iterable<Integer> pathTo(int v) {
        Stack<Integer> p = new LinkedListStack<>();
        if (!hasPathTo(v)) return p;

        while (!from.contains(paths[v])) {
            p.push(v);
            v = paths[v];
        }
        p.push(paths[v]);

        return p;
    }

    private void bfs(Graph G, Set<Integer> from) {
        Queue<Integer> visited = new Queue<>();

        for (int s : from) {
            marks[s] = true;
            visited.enqueue(s);
        }

        while (!visited.isEmpty()) {
            int v = visited.dequeue();
            for (int w : G.adj(v)) {
                if (!marks[w]) {
                    visited.enqueue(w);
                    marks[w] = true;
                    paths[w] = v;
                }
            }
        }
    }

}
