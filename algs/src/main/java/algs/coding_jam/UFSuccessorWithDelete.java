package algs.coding_jam;

/**
 * """Successor with delete.
 *    Given a set of N integers S={0,1,...,N−1} and a
 *    sequence of requests of the following form:
 *      - Remove x from S;
 *      - Find the successor of x: the smallest y in S such that y≥x.
 *
 *    design a data type so that all operations (except construction)
 *    should take logarithmic time or better.
 *
 * """ (from materials by Robert Sedgewick)
 */
public class UFSuccessorWithDelete {

    private int count;
    private int[] ids;
    private int[] sizes;
    private int[] successors;
    private boolean[] removed;

    public UFSuccessorWithDelete(int N) {
        ids = new int[N];
        sizes = new int[N];
        removed = new boolean[N];
        successors = new int[N];

        for (int i = 0; i < N; i++) {
            ids[i] = i;
            sizes[i] = 1;
            successors[i] = i;
            removed[i] = false;
        }

        count = N;
    }

    public int successor(int x) {
        return successors[root(x)];
    }

    public void remove(int x) {
        if (removed[x]) return;

        int last = ids.length-1;

        if (x == last) {
            successors[x] = -1;
        }

        if (x < last) {
            if (removed[x+1])
                union(x, x+1);
            else
                successors[x] = x+1;
        }

        if (x > 0) {
            if (removed[x-1]) {
                int suc = successor(x);
                union(x-1, x);
                successors[root(x)] = suc;
            }
        }

        removed[x] = true;
        count -= 1;
    }

    private void union(int p, int q) {
        int rp = root(p);
        int rq = root(q);

        if (rp == rq) return;

        if (sizes[rp] <= sizes[rq]) {
            ids[rp] = rq;
            sizes[rq] += sizes[rp];
        }
        else {
            ids[rq] = rp;
            sizes[rp] += sizes[rq];
        }
    }

    public int count() {
        return count;
    }

    private int root(int p) {
        while (p != ids[p]) {
            ids[p] = ids[ids[p]];
            p = ids[p];
        }
        return p;
    }

    // testing
    public static void main(String[] args) {
        UFSuccessorWithDelete uf = new UFSuccessorWithDelete(10);

        uf.remove(5);
        uf.remove(9);

        assert uf.count() == 8;
        assert uf.successor(9) == -1;
        assert uf.successor(4) == 4;
        assert uf.successor(5) == 6;

        uf.remove(0);
        uf.remove(9);

        assert uf.successor(0) == 1;

        uf.remove(3);
        uf.remove(4);

        assert uf.successor(5) == 6;
        assert uf.successor(4) == 6;
        assert uf.successor(3) == 6;

        uf.remove(7);
        uf.remove(6);
        uf.remove(8);

        assert uf.successor(8) == -1;
        assert uf.successor(7) == -1;
        assert uf.successor(6) == -1;
        assert uf.successor(5) == -1;
        assert uf.successor(4) == -1;
        assert uf.successor(3) == -1;

        uf.remove(1);

        assert uf.successor(0) == 2;
        assert uf.count() == 1;
    }

}
