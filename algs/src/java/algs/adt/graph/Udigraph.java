package algs.adt.graph;

/**
 * (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public interface Udigraph extends Graph {

    int degree(int v);

    int maxDegree();

    double averageDegree();

    int numberOfSelfLoops();

    Udigraph copy();

}
