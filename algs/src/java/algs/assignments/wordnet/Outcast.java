package algs.assignments.wordnet;

/**
 * @author Serj Sintsov
 */
public class Outcast {

    private WordNet wn;

    /**
     * constructor takes a WordNet object
     */
    public Outcast(WordNet wordnet) {
        checkNotNull(wordnet);
        this.wn = wordnet;
    }

    /**
     * given an array of WordNet nouns, return an outcast
     */
    public String outcast(String[] nouns) {
        checkNotNull(nouns);

        String outcast  = nouns[0];
        int maxDistanceSum = 0;

        for (int i = 0; i < nouns.length; i++) {
            int distSum = distanceSum(i, nouns);

            if (distSum > maxDistanceSum) {
                maxDistanceSum = distSum;
                outcast = nouns[i];
            }
        }

        return outcast;
    }

    private int distanceSum(int i, String[] nouns) {
        int dist = 0;
        for (int j = 0; j < nouns.length; j++)
            if (i != j)
                dist += wn.distance(nouns[i], nouns[j]);
        return dist;
    }

    private static void checkNotNull(Object o) {
        if (o == null)
            throw new NullPointerException("input is null");
    }

}

