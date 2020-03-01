package algs.assignments.wordnet;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DigraphGenerator;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.util.Arrays;

/**
 * @author Serj Sintsov
 */
public class SAP {

    private final Digraph G;

    /**
     * constructor takes a digraph (not necessarily a DAG)
     */
    public SAP(Digraph G) {
        if (G == null)
            throw new NullPointerException("input G is null");
        this.G = new Digraph(G);
    }

    /**
     * length of shortest ancestral path between v and w; -1 if no such path
     */
    public int length(int v, int w) {
        checkVertexIndex(v);
        checkVertexIndex(w);
        return new SapBfsPaths(G).calculate(v, w).length();
    }

    /**
     * a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
     */
    public int ancestor(int v, int w) {
        checkVertexIndex(v);
        checkVertexIndex(w);
        return new SapBfsPaths(G).calculate(v, w).ancestor();
    }

    /**
     * length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
     */
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        checkNotNull(v);
        checkNotNull(w);
        return new SapBfsPaths(G).calculate(v, w).length();
    }

    /**
     * a common ancestor that participates in shortest ancestral path; -1 if no such path
     */
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        checkNotNull(v);
        checkNotNull(w);
        return new SapBfsPaths(G).calculate(v, w).ancestor();
    }

    private void checkVertexIndex(int v) {
        if (v < 0 || v >= G.V())
            throw new IndexOutOfBoundsException("v has to be between 0 and " + G.V());
    }

    private void checkNotNull(Object o) {
        if (o == null)
            throw new NullPointerException("input is null");
    }


    private static class SapBfsPaths {

        private static final int INFINITY = Integer.MAX_VALUE;

        private final Digraph G;

        private boolean[] vMarks;
        private boolean[] wMarks;
        private int[] vDists;
        private int[] wDists;

        private int ancestor;

        public SapBfsPaths(Digraph graph) {
            G = graph;
            vMarks = new boolean[graph.V()];
            wMarks = new boolean[graph.V()];
            vDists = new int[graph.V()];
            wDists = new int[graph.V()];
            ancestor = -1;

            for (int i = 0; i < graph.V(); i++) {
                vDists[i] = INFINITY;
                wDists[i] = INFINITY;
            }
        }

        public SapBfsPaths calculate(int v, int w) {
            refreshState();

            Bag<Integer> V = new Bag<>();
            V.add(v);
            Bag<Integer> W = new Bag<>();
            W.add(w);

            searchSources(V, W);
            return this;
        }

        public SapBfsPaths calculate(Iterable<Integer> V, Iterable<Integer> W) {
            refreshState();
            searchSources(V, W);
            return this;
        }

        private void refreshState() {
            // todo refresh only changed entries of marks[] and distTo[]
        }

        private void searchSources(Iterable<Integer> V, Iterable<Integer> W) {
            BfsWave vBfsWave = new BfsWave(V, vMarks, vDists);
            BfsWave wBfsWave = new BfsWave(W, wMarks, wDists);

            vBfsWave.setNeighborWave(wBfsWave);
            wBfsWave.setNeighborWave(vBfsWave);

            while (true) {
                if (!vBfsWave.isFinished())
                    vBfsWave.nextWave(G);

                if (!wBfsWave.isFinished())
                    wBfsWave.nextWave(G);

                if (vBfsWave.isFinished() && wBfsWave.isFinished())
                    break;
            }

            if (vBfsWave.length() < wBfsWave.length())
                ancestor = vBfsWave.getClosestAncestor();
            else
                ancestor = wBfsWave.getClosestAncestor();
        }

        private int length() {
            if (ancestor == -1)
                return -1;
            else
                return vDists[ancestor] + wDists[ancestor];
        }

        private int ancestor() {
            return ancestor;
        }
    }


    private static class BfsWave {

        private static final int INFINITY = Integer.MAX_VALUE;

        private boolean[] marks;
        private int[] dists;
        private Queue<Integer> toVisit;

        private BfsWave neighborWave;
        private int ancestor;

        public BfsWave(Iterable<Integer> V, boolean[] marks, int[] dists) {
            this.marks = marks;
            this.dists = dists;
            this.toVisit = new Queue<>();
            this.ancestor = -1;

            for (int v : V) {
                toVisit.enqueue(v);
                marks[v] = true;
                dists[v] = 0;
            }
        }

        public void nextWave(Digraph G) {
            if (!toVisit.isEmpty()) {
                int s = toVisit.dequeue();

                if (isFoundMinAncestor(s))
                    return;

                for (int t : G.adj(s)) {
                    if (!marks[t]) {
                        toVisit.enqueue(t);
                        marks[t] = true;
                        dists[t] = dists[s] + 1;
                    }

                    if (isFoundMinAncestor(t))
                        return;
                }
            }
        }

        private boolean isFoundMinAncestor(int v) {
            if (!neighborWave.isVisited(v))
                return false;

            if (lengthTo(v) <= length()) {
                ancestor = v;
                return false;
            }
            else if (dists[v] > length()){
                stopWaving();
                return true;
            }

            return false;
        }

        private int lengthTo(int v) {
            return dists[v] + neighborWave.dists[v];
        }

        private boolean isVisited(int v) {
            return marks[v];
        }

        private void stopWaving() {
            toVisit = null;
        }

        public int length() {
            if (ancestor == -1)
                return INFINITY;
            else
                return dists[ancestor] + neighborWave.dists[ancestor];
        }

        public boolean isFinished() {
            return toVisit == null || toVisit.isEmpty();
        }

        public int getClosestAncestor() {
            return ancestor;
        }

        public void setNeighborWave(BfsWave wave) {
            neighborWave = wave;
        }
    }


    // do unit testing of this class
    public static void main(String[] args) {
        testFromFile(args[0]);
        testMultipleSourcesOnRandomGraphs();
        //testSingleSourcesOnRandomGraphs();
    }

    private static void testSingleSourcesOnRandomGraphs() {
        for (int i = 0; i < 100; i++) {
            Digraph G = DigraphGenerator.simple(5, 8);
            StdOut.printf("graph = %s\n", G.toString());
            SAP sap = new SAP(G);

            for (int v = 0; v < 5; v++)
                for (int w = 0; w < 5; w++) {
                    int length = sap.length(v, w);
                    int ancestor = sap.ancestor(v, w);
                    StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
                }
        }
    }

    private static void testMultipleSourcesOnRandomGraphs() {
        for (int i = 0; i < 100; i++) {
            Digraph G = DigraphGenerator.rootedInDAG(20, 100);
            StdOut.printf("graph = %s\n", G.toString());
            SAP sap = new SAP(G);

            int length = sap.length(Arrays.asList(1, 7, 11, 17, 18), Arrays.asList(0, 11, 12, 17, 19));
            int ancestor = sap.ancestor(Arrays.asList(0, 2, 4, 6, 15), Arrays.asList(2, 7, 8, 9));
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

    private static void testFromFile(String filePath) {
        In in = new In(filePath);
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);

        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length   = sap.length(v, w);
            int ancestor = sap.ancestor(v, w);

            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }

}