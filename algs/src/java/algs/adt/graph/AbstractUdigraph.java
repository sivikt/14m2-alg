package algs.adt.graph;

import java.util.Iterator;

/**
 * (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public abstract class AbstractUdigraph implements Udigraph {

    @Override
    public int degree(int v) {
        int degree = 0;
        for (Iterator<Integer> it = adj(v).iterator();  it.hasNext(); it.next())
            degree += 1;
        return degree;
    }

    @Override
    public int maxDegree() {
        int max = 0;
        for (int v = 0; v < V(); v++) {
            int degree = degree(v);
            if (degree > max) max = degree;
        }
        return max;
    }

    @Override
    public double averageDegree() {
        return 2.0 * E() / V();
    }

    @Override
    public int numberOfSelfLoops() {
        int count = 0;
        for (int v = 0; v < V(); v++)
            for (Integer w : adj(v))
                if (v == w) count += 1;
        return count/2;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        for (int v = 0; v < V(); v++) {
            for (Integer w : adj(v)) {
                buf.append(v);
                buf.append(" - ");
                buf.append(w);
                buf.append("  ");
            }
            buf.append("\n");
        }
        return buf.toString();
    }

    protected void checkV(int v) {
        if (v < 0 || v >= V()) throw new IllegalArgumentException("V has to be between 0 and " + V());
    }
}
