package algs.assignments.jam;

import algs.sorting.QuickSort;

import static java.lang.String.format;
import static algs.assignments.jam.TaxicabNumbers.Cubes.texicab;

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

    static class Cubes implements Comparable<Cubes> {
        final int a;
        final int b;
        final int sum3;

        public Cubes(int a, int b) {
            this.a = a;
            this.b = b;
            this.sum3 = a*a*a + b*b*b;
        }
        
        @Override
        public String toString() {
            return format("<%s, %s, %s>", a, b, sum3);
        }

        public static boolean texicab(Cubes c1, Cubes c2) {
            return c1.sum3 == c2.sum3 && c1.a != c2.a && c1.b != c2.b;
        }
        
        @Override
        public int compareTo(Cubes that) {
            return (that != null) ? (this.sum3 - that.sum3) : 1;
        }
    }


    public static void version1(int N) {
        int size = N*(N-1) >> 1;
        Cubes[] sums3 = new Cubes[size];

        int k = 0;
        for (int i = 1; i <= N-1; i++)
            for (int j = i+1; j <= N; j++, k++)
                sums3[k] = new Cubes(i, j);

        QuickSort.sort(sums3);

        for (int i = 0; i < size-1; i++)
            if (texicab(sums3[i], sums3[i+1])) {
                System.out.print(sums3[i] + " & " + sums3[i + 1]);
            }
    }

    
    // testing 
    public static void main(String[] args) {
        version1(12);
    }
}
