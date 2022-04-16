package algs.coding_jam;

import algs.adt.unionfind.UF;

import static java.lang.Math.max;

/**
 * """Union-find with specific canonical element.
 *    Add a method find() to the union-find data type so that find(i) returns the largest
 *    element in the connected component containing i. The operations, union(), connected(),
 *    and find() should all take logarithmic time or better.
 *
 *    For example, if one of the connected components is {1,2,6,9}, then the find() method
 *    should return 9 for each of the four elements in the connected components because 9
 *    is larger 1, 2, and 6.
 *
 * """ (from materials by Robert Sedgewick)
 */
public class UFWithCanonicalElement implements UF {

    private int count;
    private int[] ids;
    private int[] sizes;
    private int[] maximums;

    public UFWithCanonicalElement(int N) {
        ids = new int[N];
        sizes = new int[N];
        maximums = new int[N];

        for (int i = 0; i < N; i++) {
            ids[i] = i;
            sizes[i] = 1;
            maximums[i] = i;
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

        if (sizes[rP] <= sizes[rQ]) {
            ids[rP] = rQ;
            sizes[rQ] += sizes[rP];
        }
        else {
            ids[rQ] = rP;
            sizes[rP] += sizes[rQ];
        }

        maximums[rQ] = max(maximums[rP], maximums[rQ]);
        maximums[rP] = maximums[rQ];
        count -= 1;
    }

    @Override
    public int find(int p) {
        return maximums[root(p)];
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
        UFWithCanonicalElement uf = new UFWithCanonicalElement(10);
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

        assert uf.find(0) == 4;
        assert uf.find(6) == 9;
        assert uf.find(7) == 7;

        uf.union(2, 1);
        uf.union(9, 0);

        assert uf.count() == 4;

        uf.union(8, 4);
        uf.union(3, 5);
        uf.union(8, 5);

        assert uf.count() == 1;
        for (int i = 0; i < 10; i ++)
            assert uf.find(i) == 9 : "error at element " + i;
    }

}
