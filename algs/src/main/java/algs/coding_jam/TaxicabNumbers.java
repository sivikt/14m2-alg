package algs.coding_jam;

import algs.adt.pqueue.PriorityQueue;
import algs.adt.pqueue.impl.heap.FixedArrayBinaryHeapPQ;
import algs.sorting.QuickSort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static algs.coding_jam.TaxicabNumbers.TaPair.of;
import static java.lang.String.format;
import static algs.coding_jam.TaxicabNumbers.Cubes.isTaxicab;
import static java.util.Collections.emptyList;
import static java.util.Objects.requireNonNull;

/**
 * """Taxicab numbers.
 *    A taxicab number is an integer that can be expressed as the sum of two cubes of
 *    integers in two different ways: a^3+b^3=c^3+d^3. For example, 1729=9^3+10^3=1^3+12^3.
 *    Design an algorithm to find all taxicab numbers with a, b, c, and d less than N:
 *      - Version 1: Use time proportional to N^2logN and space proportional to N^2.
 *      - Version 2: Use time proportional to N^2logN and space proportional to N.
 *
 *    Hints:
 *      - Version 1: Form the sums a^3+b^3 and sort.
 *      - Version 2: Use a min-oriented priority queue with N items.
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class TaxicabNumbers {

    public static final class Cubes implements Comparable<Cubes> {
        final int a;
        final int b;
        final long sum3;

        public Cubes(int a, int b) {
            this.a = a;
            this.b = b;
            this.sum3 = a*a*a + b*b*b;
        }
        
        @Override
        public String toString() {
            return format("<%s, %s, %s>", a, b, sum3);
        }

        public Cubes incB() {
            return new Cubes(a, b+1);
        }

        public static boolean isTaxicab(Cubes c1, Cubes c2) {
            return c1.sum3 == c2.sum3 && c1.a != c2.a && c1.b != c2.b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;

            if (!(o instanceof Cubes))
                return false;

            Cubes that = (Cubes) o;
            return (this.a == that.a && this.b == that.b) ||
                   (this.a == that.b && this.b == that.a);
        }

        @Override
        public int compareTo(Cubes that) {
            if (that == null || this.sum3 > that.sum3)
                return 1;
            else if (this.sum3 < that.sum3)
                return -1;
            else
                return 0;
        }
    }


    public static final class TaPair {

        private final Cubes first;
        private final Cubes second;

        public TaPair(Cubes c1, Cubes c2) {
            this.first = requireNonNull(c1);
            this.second = requireNonNull(c2);
        }

        public static TaPair of(Cubes first, Cubes second) {
            return new TaPair(first, second);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;

            if (this == obj)
                return true;

            if (!(obj instanceof TaPair))
                return false;

            TaPair that = (TaPair) obj;
            return ( first == that.first || first.equals(that.first) || first.equals(that.second)) &&
                   ( second == that.second || second.equals(that.second) || second.equals(that.first));
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(new Object[]{first, second});
        }

        @Override
        public String toString() {
            return String.format("(%s, %s)", first, second);
        }

    }


    public static Collection<TaPair> Ta_sorting(int N) {
        if (N < 2) return emptyList();

        int size = N*(N-1) >> 1;
        Cubes[] sums3 = new Cubes[size];

        for (int i = 1, k = 0; i <= N-1; i++)
            for (int j = i+1; j <= N; j++, k++)
                sums3[k] = new Cubes(i, j);

        QuickSort.sort(sums3);

        Collection<TaPair> result = new ArrayList<>();
        for (int i = 0; i < size-1; i++) {
            if (isTaxicab(sums3[i], sums3[i + 1]))
                result.add( of(sums3[i], sums3[i+1]) );
        }

        return result;
    }

    public static Collection<TaPair> Ta_heap(int N) {
        if (N < 2) return emptyList();

        PriorityQueue<Cubes> sums3 = new FixedArrayBinaryHeapPQ<>(N, (c1, c2) -> c2.compareTo(c1));
        Collection<TaPair> result = new ArrayList<>();

        for (int i = 1; i <= N; i++)
            sums3.enqueue(new Cubes(i, i));

        Cubes p = new Cubes(0, 0);
        while (!sums3.isEmpty()) {
            Cubes c = sums3.dequeue();

            if (isTaxicab(p, c))
                result.add(of(p, c));

            if (c.b <= N)
                sums3.enqueue(c.incB());

            p = c;
        }

        return result;
    }


    // testing 
    public static void main(String[] args) {
        Collection<TaPair> ta1 = Ta_sorting(10000);
        Collection<TaPair> ta2 = Ta_heap(10000);

        assert ta1.size() == ta2.size();

        for (TaPair p : ta1)
            assert ta1.contains(p) : "TaPair " + p + " not found in ta2";

        System.out.println(ta1);
        System.out.println(ta2);
    }
}
