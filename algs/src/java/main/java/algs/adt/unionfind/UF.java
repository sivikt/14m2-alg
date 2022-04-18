package algs.adt.unionfind;

public interface UF {

    /**
     * Add connection between p and q
     */
    void union(int p, int q);

    /**
     * Component identifier for p (0 <= p < N)
     */
    int find(int p);

    /**
     * Are the two objects p and q in the same component?
     */
    boolean connected(int p, int q);

    /**
     * Return the number of components (1..N)
     */
    int count();

}
