package algs.adt.unionfind.impl;

import algs.adt.unionfind.UF;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

import java.io.File;

/**
 * Array represents a tree
 */
public class QuickUnionUF implements UF {

    private int[] ids;
    private int count;

    public QuickUnionUF(int N) {
        ids = new int[N];
        count = N;
        for (int i = 0; i < N; i++)
            ids[i] = i;
    } 

    @Override
    public void union(int p, int q) {
        int rP = root(p);
        int rQ = root(q);

        if (rP == rQ) return;  // components already connected
        ids[rP] = rQ;
        count--;
    }

    @Override
    public int find(int p) {
        return root(p);
    }

    @Override
    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    @Override
    public int count() {
        return count;
    }

    private int root(int p) {
        while (p != ids[p])
            p = ids[p];
        return p;
    }


    /**
     * Test method for this implementation of Union find data structure.
     *
     * Reads in a sequence of pairs of integers (between 0 and N-1) from test data file,
     * where each integer represents some object;
     * if the objects are in different components, merge the two components
     * and print the pair to standard output.
     *
     *  @author Robert Sedgewick
     *  @author Kevin Wayne
     */
    public static void main(String[] args) {
        In in = new In(new File(args[0]));

        int N = in.readInt();
        QuickUnionUF uf = new QuickUnionUF(N);

        while (!in.isEmpty()) {
            int p = in.readInt();
            int q = in.readInt();

            if (uf.connected(p, q)) continue;

            uf.union(p, q);
            StdOut.println(p + " " + q);
        }

        StdOut.println(uf.count() + " components");
    }

}
