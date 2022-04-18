package algs.assignments.percolation;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

/**
 * This data structure uses to estimate the value of the
 * percolation threshold via Monte Carlo simulation.
 *
 * @see Percolation
 * @author Serj Sintsov
 */
public class PercolationStats {

    private double mean;
    private double stddev;
    private double confidenceLo;
    private double confidenceHi;

    /**
     * Perform T independent computational experiments on an N-by-N grid
     * @param N size of a grid
     * @param T number of independent experiments
     */
    public PercolationStats(int N, int T) {
        assertNT(N, T);
        compute(N, T);
    }

    private void assertNT(int N, int T) {
        if (N <= 0)
            throw new IllegalArgumentException("Size of grid has to be > 0");

        if (T <= 0)
            throw new IllegalArgumentException("Number of experiments has to be > 0");
    }

    /**
     * Sample mean of percolation threshold
     */
    public double mean() {
        return mean;
    }

    /**
     * Sample standard deviation of percolation threshold
     */
    public double stddev() {
        return stddev;
    }

    /**
     * Returns lower bound of the 95% confidence interval
     */
    public double confidenceLo() {
        return confidenceLo;
    }

    /**
     * Returns upper bound of the 95% confidence interval
     */
    public double confidenceHi() {
        return confidenceHi;
    }

    private strictfp void compute(int N, int T) {
        int[] openSitesCounts = new int[T];
        int totalSites = N*N;

        for (int i = 0; i < T; i++) {
            Percolation perc = new Percolation(N);

            do {
                openSite(N, perc);
                openSitesCounts[i]++;
            } while (!perc.percolates());
        }

        double[] xt = new double[T];
        for (int i = 0; i < T; i++)
            xt[i] = (double) openSitesCounts[i]/totalSites;

        this.mean = StdStats.mean(xt);
        this.stddev = StdStats.stddev(xt);

        double delta = 1.96 * this.stddev / Math.sqrt(T);
        this.confidenceLo = this.mean - delta;
        this.confidenceHi = this.mean + delta;
    }

    private void openSite(int N, Percolation perc) {
        int i, j;

        while (true) {
            i = StdRandom.uniform(1, N+1);
            j = StdRandom.uniform(1, N+1);

            if (perc.isOpen(i, j)) continue;
            else { perc.open(i, j); break; }
        }
    }

    /**
     * Test client, described below
     * @param args contains two command-line arguments N and T
     */
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(N, T);
        printKeyValue("mean", percStats.mean());
        printKeyValue("stddev", percStats.stddev());
        printKeyValues("95% confidence interval",
                percStats.confidenceLo(),
                percStats.confidenceHi()
        );
    }

    private static void printKeyValue(String key, double value) {
        System.out.printf("%-25s = %s\n", key, value);
    }

    private static void printKeyValues(String key, double val1, double val2) {
        System.out.printf("%-25s = %s, %s\n", key, val1, val2);
    }

}