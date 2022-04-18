package algs.assignments.wordnet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.introcs.In;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Serj Sintsov
 */
public class WordNet {

    private final Map<String, Set<Integer>> synonyms;
    private Map<Integer, Synset> synsets;
    private final SAP saPaths;

    /**
     * constructor takes the name of the two input files
     */
    public WordNet(String synsetsPath, String hypernymsPath) {
        WordNetReader wnRd = new WordNetReader(synsetsPath, hypernymsPath);
        synonyms = wnRd.getSynonyms();
        synsets  = wnRd.getSynsets();
        Digraph hypernymsG = buildHypernymsDAG(wnRd);
        checkIsRooted(hypernymsG);
        saPaths = new SAP(hypernymsG);
    }

    /**
     * returns all WordNet nouns
     */
    public Iterable<String> nouns() {
        return synonyms.keySet();
    }

    /**
     * is the word a WordNet noun?
     */
    public boolean isNoun(String word) {
        checkNotNull(word);
        return synonyms.containsKey(word);
    }

    /**
     * distance between nounA and nounB
     */
    public int distance(String nounA, String nounB) {
        checkIsNoun(nounA);
        checkIsNoun(nounB);
        return saPaths.length(synonyms.get(nounA), synonyms.get(nounB));
    }

    /**
     * a synset (second field of synsets.txt) that is the common
     * ancestor of nounA and nounB in a shortest ancestral path
     */
    public String sap(String nounA, String nounB) {
        checkIsNoun(nounA);
        checkIsNoun(nounB);
        int ancestor = saPaths.ancestor(synonyms.get(nounA), synonyms.get(nounB));

        if (ancestor == -1)
            return null;
        else
            return synsets.get(ancestor).joinSynonyms();
    }

    private Digraph buildHypernymsDAG(WordNetReader wnRd) {
        Digraph G = new Digraph(wnRd.getSynsetsCount());
        for (int hyponymId : wnRd.getHyponyms().keySet())
            for (int hypernymId : wnRd.getHyponyms().get(hyponymId).getHypernyms())
                G.addEdge(hyponymId, hypernymId);
        return G;
    }

    private void checkIsNoun(String word) {
        if (!isNoun(word))
            throw new IllegalArgumentException("Word is not noun");
    }

    private void checkIsRooted(Digraph G) {
        int rootCount = 0;
        for (int v = 0; v < G.V(); v++)
            if (!G.adj(v).iterator().hasNext())
                rootCount += 1;

        if (rootCount == 0 || rootCount > 1)
            throw new IllegalArgumentException("DAG is not rooted");
    }

    private static class WordNetReader {

        private Map<String, Set<Integer>> synonyms = new HashMap<>();
        private Map<Integer, Synset> synsets = new HashMap<>();
        private Map<Integer, Hyponym> hyponyms = new HashMap<>();

        public WordNetReader(String synsetsPath, String hypernymsPath) {
            checkNotNull(synsetsPath);
            checkNotNull(hypernymsPath);

            In synsetsIn = new In(synsetsPath);
            while (!synsetsIn.isEmpty()) {
                Synset synset = Synset.parseFrom(synsetsIn.readLine());
                storeSynset(synset);
            }

            In hypernymsIn = new In(hypernymsPath);
            while (!hypernymsIn.isEmpty()) {
                Hyponym hyponym = Hyponym.parseFrom(hypernymsIn.readLine());
                storeHyponym(hyponym);
            }
        }

        private void storeSynset(Synset synset) {
            synsets.put(synset.getId(), synset);

            for (String syn : synset.getSynonyms()) {
                if (synonyms.containsKey(syn))
                    synonyms.get(syn).add(synset.getId());
                else {
                    Set<Integer> ids = new HashSet<>();
                    ids.add(synset.getId());
                    synonyms.put(syn, ids);
                }
            }
        }

        private void storeHyponym(Hyponym hyponym) {
            if (hyponyms.containsKey(hyponym.getId()))
                hyponyms.get(hyponym.getId()).addHypernyms(hyponym.getHypernyms());
            else
                hyponyms.put(hyponym.getId(), hyponym);
        }

        public int getSynsetsCount() {
            return getSynsets().size();
        }

        public Map<String, Set<Integer>> getSynonyms() {
            return synonyms;
        }

        public Map<Integer, Synset> getSynsets() {
            return synsets;
        }

        public Map<Integer, Hyponym> getHyponyms() {
            return hyponyms;
        }
    }

    private static class Synset {
        private int id;
        private String[] synonyms;

        public Synset(int id, String... synonyms) {
            this.id = id;
            this.synonyms = synonyms;
        }

        public int getId() {
            return id;
        }

        public String[] getSynonyms() {
            return synonyms;
        }

        public String joinSynonyms() {
            StringBuilder buf = new StringBuilder();
            int n = synonyms.length-1;

            for (int i = 0; i <= n; i++) {
                buf.append(synonyms[i]);
                if (i < n)
                    buf.append(" ");
            }

            return buf.toString();
        }

        public static Synset parseFrom(String synset) {
            String[] synParts = synset.split(",");
            int synonymId = Integer.parseInt(synParts[0].trim());
            String[] synonyms = synParts[1].split(" ");
            return new Synset(synonymId, synonyms);
        }
    }

    private static class Hyponym {
        private int id;
        private Set<Integer> hypernyms = new HashSet<>(0);

        public Hyponym(int id, int... hypernyms) {
            this.id = id;
            this.hypernyms = new HashSet<>(hypernyms.length);
            addHypernyms(hypernyms);
        }

        public int getId() {
            return id;
        }

        public Set<Integer> getHypernyms() {
            return hypernyms;
        }

        public void addHypernyms(int... hypernyms) {
            for (int h : hypernyms)
                this.hypernyms.add(h);
        }

        public void addHypernyms(Set<Integer> hypernyms) {
            this.hypernyms.addAll(hypernyms);
        }

        public static Hyponym parseFrom(String hypernym) {
            String[] hypParts = hypernym.split(",");
            int hyponymId = Integer.parseInt(hypParts[0].trim());
            int[] hypernymIds = new int[Math.max(0, hypParts.length - 1)];

            for (int i = 0; i < hypernymIds.length; i++)
                hypernymIds[i] = Integer.parseInt(hypParts[i+1].trim());

            return new Hyponym(hyponymId, hypernymIds);
        }
    }

    private static void checkNotNull(Object o) {
        if (o == null)
            throw new NullPointerException("input is null");
    }


    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        assert wn.isNoun("a");
        assert wn.isNoun("b");

        assert wn.distance("a", "b") == 1;
        assert wn.sap("a", "b").equals("b");
    }
}
