package algs.assignments.jam;

import algs.adt.Pair;

import java.util.ArrayList;
import java.util.Collection;

import static algs.adt.Pair.of;
import static algs.shuffling.KnuthShuffle.shuffle;
import static algs.sorting.SortUtil.swap;

/**
 * """Nuts and bolts.
 *    A disorganized carpenter has a mixed pile of N nuts and N bolts. The goal is to find the corresponding pairs of nuts
 *    and bolts. Each nut fits exactly one bolt and each bolt fits exactly one nut. By fitting a nut and a bolt together,
 *    the carpenter can see which one is bigger (but the carpenter cannot compare two nuts or two bolts directly).
 *    Design an algorithm for the problem that uses Nlog(N) compares (probabilistically).
 *
 *    Remark: This research paper(http://www.cs.ucla.edu/~rafail/PUBLIC/17.pdf) gives an algorithm that runs in Nlog^4(N)
 *            time in the worst case.
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class NutsAndBolts {

    public static class Nut implements Comparable<Bolt> {
        public final int type;

        public Nut(int type) {
            this.type = type;
        }

        @Override
        public int compareTo(Bolt b) {
            return this.type - b.type;
        }

        @Override
        public String toString() {
            return Integer.toString(type);
        }
    }

    public static class Bolt implements Comparable<Nut> {
        public final int type;

        public Bolt(int type) {
            this.type = type;
        }

        @Override
        public int compareTo(Nut n) {
            return this.type - n.type;
        }

        @Override
        public String toString() {
            return Integer.toString(type);
        }
    }


    public static Collection<Pair<Nut, Bolt>> organize(Nut[] nuts, Bolt[] bolts) {
        sort(nuts, bolts, 0, nuts.length-1);

        Collection<Pair<Nut, Bolt>> result = new ArrayList<>(nuts.length);
        for (int i = 0; i < nuts.length; i++)
            result.add(of(nuts[i], bolts[i]));

        return result;
    }

    public static void sort(Nut[] nuts, Bolt[] bolts, final int lo, final int hi) {
        if (lo >= hi) return;

        int p = partition(nuts, bolts, lo, hi);

        if (p - lo > hi - p) {
            sort(nuts, bolts, p+1, hi);
            sort(nuts, bolts, lo, p-1);
        }
        else {
            sort(nuts, bolts, lo, p-1);
            sort(nuts, bolts, p+1, hi);
        }
    }

    private static int partition(Nut[] nuts, Bolt[] bolts, final int lo, final int hi) {
        int i = lo,
            j = hi;
        Nut n = nuts[lo];

        while (true) {
            while (less(bolts[i], n)) {
                if (i == hi) break;
                i++;
            }

            while (greater(bolts[j], n)) {
                if (j == lo) break;
                j--;
            }

            if (i >= j) break;

            swap(bolts, i, j);
        }

        nuts[lo] = nuts[i];
        nuts[i] = n;

        partition(nuts, bolts, i, lo, hi);

        return j;
    }

    private static void partition(Nut[] nuts, Bolt[] bolts, final int pivot, final int lo, final int hi) {
        if (pivot == lo || pivot == hi) return;

        int i = lo,
            j = hi;
        Bolt b = bolts[pivot];

        while (true) {
            while (greater(b, nuts[i])) {
                if (i == pivot) break;
                i++;
            }

            while (less(b, nuts[j])) {
                if (j == pivot) break;
                j--;
            }

            if (i >= pivot || j <= pivot) break;

            swap(nuts, i, j);
        }
    }

    private static boolean less(Bolt b, Nut n) {
        return b.compareTo(n) < 0;
    }

    private static boolean greater(Bolt b, Nut n) {
        return b.compareTo(n) > 0;
    }

    // testing
    public static void main(String[] args) {
        final int N = 3_000_000;

        Pair<Nut[], Bolt[]> pile = generate(N);
        Collection<Pair<Nut, Bolt>> matches = organize(pile.getFirst(), pile.getSecond());

        assert matches.size() == N;
        //System.out.println(matches);
        for (Pair<Nut, Bolt> p : matches)
            assert p.getFirst().compareTo(p.getSecond()) == 0 : "Error in pair " + p;
    }

    private static Pair<Nut[], Bolt[]> generate(int size) {
        Nut[] nuts = new Nut[size];
        Bolt[] bolts = new Bolt[size];

        for (int i = 0; i < size; i++) {
            nuts[i] = new Nut(i);
            bolts[i] = new Bolt(i);
        }

        shuffle(nuts);
        shuffle(bolts);

        //System.out.println(Arrays.toString(nuts));
        //System.out.println(Arrays.toString(bolts));

        return of(nuts, bolts);
    }

}
