package algs.assignments.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Implementation of Percolation data structure.
 *
 * """
 *    <p><b>Percolation problem and practical application</b>. Given a
 *    composite systems comprised of randomly distributed insulating and
 *    metallic materials: what fraction of the materials need to be metallic
 *    so that the composite system is an electrical conductor? Given a porous
 *    landscape with water on the surface (or oil below), under what conditions
 *    will the water be able to drain through to the bottom (or the oil to gush
 *    through to the surface)? Scientists have defined an abstract process known
 *    as percolation to model
 *    such situations.</p>
 *
 *    <p><b>Percolation model</b>. We model a percolation system using an N-by-N
 *    grid of sites. Each site is either open or blocked. A full site is an open
 *    site that can be connected to an open site in the top row via a chain of
 *    neighboring (left, right, up, down) open sites. We say the system
 *    <b>percolates</b> if there is a full site in the bottom row. In other
 *    words, a system percolates if we fill all open sites connected to the top
 *    row and that process fills some open site on the bottom row.
 *
 *    <p>For the insulating/metallic materials example, the open sites correspond
 *    to metallic materials, so that a system that percolates has a metallic path
 *    from top to bottom, with full sites conducting. For the porous substance
 *    example, the open sites correspond to empty space through which water might
 *    flow, so that a system that percolates lets water fill open sites, flowing
 *    from top to bottom.</p>
 * """ (from materials by Robert Sedgewick)
 *
 * <p>Given Percolation data structure uses {@link WeightedQuickUnionUF}</p>
 * implementation of union-find data structure to store information about
 * opened connected sites.</p>
 *
 * @author Serj Sintov
 */
public class Percolation {

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF backwashFreeUF;
    private int N;
    private int sitesNum;
    private boolean[][] openSites;

    /**
     * Create N-by-N grid, with all sites blocked.
     * Take time proportional to N^2.
     * @param N size of the grid
     */
    public Percolation(int N) {
        if (N <= 0)
            throw new IllegalArgumentException();

        this.N = N;
        sitesNum = N * N;

        /**
         * 2D grid to 1D array plus virtual-top and virtual-bottom sites.
         * Element with index 0 - is the virtual-top; element with index
         * {@code sitesNum} - is the virtual-bottom, since UF data type store
         * elements starting with 0 index.
         */
        uf = new WeightedQuickUnionUF(sitesNum + 2);
        // the same as {@code uf} but without virtual-bottom
        backwashFreeUF = new WeightedQuickUnionUF(sitesNum + 1);

        /**
         * Track open sites (false value means the site is blocked)
         */
        this.openSites = new boolean[N][N];
    }

    private void assertRowCol(int i, int j) {
        if (i < 1 || i > N)
            throw new IndexOutOfBoundsException("Row index is out of range");

        if (j < 1 || j > N)
            throw new IndexOutOfBoundsException("Col index is out of range");
    }

    private int to1D(int i, int j) {
        return i*N - N + j;
    }

    /**
     * Open site (row i, column j) if it is not already
     * The indices i and j are integers between 1 and N, where (1, 1) is the
     * upper-left site.
     * Take constant time.
     *
     * @param i row index
     * @param j col index
     * @throws IndexOutOfBoundsException if either i or j is outside range
     */
    public void open(int i, int j) {
        assertRowCol(i, j);
        if (checkOpen(i, j)) return;

        markAsOpen(i, j);

        /**
         * Connect neighbor sites
         *          |i-1|
         *           ---
         *    |j-1|-|i,j|-|j+1|
         *           ---
         *          |i+1|
         */
        int ij1D = to1D(i, j);

        /** link top site or virtual-top */
        if (i-1 > 0) {
            if (checkOpen(i-1, j)) linkSite(ij1D, i-1, j);
        } else {
            uf.union(ij1D, 0);
            backwashFreeUF.union(ij1D, 0);
        }

        /** link bottom site or virtual-bottom */
        if (i+1 <= N) {
            if (checkOpen(i+1, j)) linkSite(ij1D, i+1, j);
        } else uf.union(ij1D, sitesNum);

        /** link left site */
        if (j-1 > 0 && checkOpen(i, j-1)) linkSite(ij1D, i, j-1);

        /** link right site */
        if (j+1 <= N && checkOpen(i, j+1)) linkSite(ij1D, i, j+1);
    }

    private void linkSite(int index1, int i, int j) {
        int index2 = to1D(i, j);
        uf.union(index1, index2);
        backwashFreeUF.union(index1, index2);
    }

    /**
     * Is site (row i, column j) open?
     * The indices i and j are integers between 1 and N, where (1, 1) is the
     * upper-left site.
     * Take constant time.
     *
     * @param i row index
     * @param j col index
     * @throws IndexOutOfBoundsException if either i or j is outside range
     */
    public boolean isOpen(int i, int j) {
        assertRowCol(i, j);
        return checkOpen(i, j);
    }

    private boolean checkOpen(int i, int j) {
        return openSites[ i-1 ][ j-1 ];
    }

    private void markAsOpen(int i, int j) {
        openSites[ i-1 ][ j-1 ] = true;
    }

    /**
     * Is site (row i, column j) full?
     * The indices i and j are integers between 1 and N, where (1, 1) is the
     * upper-left site.
     * Take constant time.
     *
     * @param i row index
     * @param j col index
     * @throws IndexOutOfBoundsException if either i or j is outside range
     */
    public boolean isFull(int i, int j) {
        assertRowCol(i, j);
        return backwashFreeUF.connected(0, to1D(i, j));
    }

    /**
     * Does the system percolate?
     * Take constant time.
     */
    public boolean percolates() {
        return uf.connected(0, sitesNum);
    }

    /**
     * Tests {@code Percolation} data structure
     */
    public static void main(String[] args) {
        Percolation perc = new Percolation(4);
        perc.open(1, 2);
        perc.open(2, 2);
        perc.open(3, 3);

        assert perc.isOpen(1, 2);
        assert perc.isOpen(2, 2);
        assert perc.isOpen(3, 3);
        assert perc.isFull(2, 2);
        assert perc.uf.connected(2, 6); // (1,2) and (2,2)
        assert !perc.uf.connected(6, 11); // (2,2) and (3,3)

        assert !perc.percolates();
    }

}
