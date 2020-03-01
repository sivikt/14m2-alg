package algs.assignments.jam;

import algs.adt.unionfind.UF;

/**
 * """Union-by-height.
 *    Develop a union-find implementation that uses the same basic strategy as
 *    weighted quick-union but keeps track of tree height and always links the
 *    shorter tree to the taller one. Prove a lgN upper bound on the height of
 *    the trees for N sites with your algorithm.
 *
 * """ (from materials by Robert Sedgewick)
 */
public class UFByHeight implements UF {

    private int count;
    private int[] ids;
    private int[] height;

    public UFByHeight(int N) {
        ids = new int[N];
        height = new int[N];

        for (int i = 0; i < N; i++) {
            ids[i] = i;
            height[i] = 1;
        }

        count = N;
    }

    private int root(int p) {
        while (p != ids[p]) {
            ids[p] = ids[ids[p]];
            p = ids[p];
        }
        return p;
    }

    @Override
    public void union(int p, int q) {
        int rP = root(p);
        int rQ = root(q);

        if (rP == rQ) return;

        if (height[rP] <= height[rQ]) {
            ids[rP] = rQ;
            height[rQ] += 1;
        }
        else {
            ids[rQ] = rP;
            height[rP] += 1;
        }

        count -= 1;
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

    // testing
    public static void main(String[] args) {
        UFByHeight uf = new UFByHeight(10);
        uf.union(0, 1);
        uf.union(1, 4);
        uf.union(2, 7);
        uf.union(6, 9);

        assert uf.connected(0, 1);
        assert uf.connected(0, 4);
        assert !uf.connected(1, 6);
        assert !uf.connected(6, 7);
        assert uf.connected(6, 9);
        assert uf.count() == 6;

        assert uf.find(0) == 1;
        assert uf.find(6) == 9;
        assert uf.find(7) == 7;

        uf.union(2, 1);
        assert uf.find(2) == 1;
        assert uf.find(7) == 1;

        uf.union(9, 0);

        assert uf.count() == 4;

        uf.union(8, 4);
        uf.union(3, 5);
        uf.union(8, 5);
    }

}
