package algs.adt.unionfind.impl;

import algs.adt.unionfind.UF;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

import java.io.File;

/**
 * """Also known as "eager approach"
 * """ (from materials by Robert Sedgewick)
 */
public class QuickFindUF implements UF {

    private int count;
    private int[] ids;

    public QuickFindUF(int N) {
        ids = new int[N];
        count = N;
        for (int i = 0; i < N; i++) ids[i] = i;
    } 
    
    @Override
    public void union(int p, int q) {
        if (connected(p, q)) return;

        int pid = ids[p];

        for (int i = 0; i < ids.length; i++)
	        if (ids[i] == pid) ids[i] = ids[q];

        count--;
    }

    @Override
    public int find(int p) {
        return ids[p];
    }

    @Override
    public boolean connected(int p, int q) {
        return ids[p] == ids[q];
    }

    @Override
    public int count() {
        return count;
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
        QuickFindUF uf = new QuickFindUF(N);

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
