package algs.shuffling;

import edu.princeton.cs.introcs.StdRandom;
import static algs.sorting.SortUtil.*;

/**
 * """
 * The Fisher–Yates shuffle (named after Ronald Fisher and Frank Yates), also known as the Knuth shuffle
 * (after Donald Knuth), is an algorithm for generating a random permutation of a finite set—in plain terms,
 * for randomly shuffling the set. A variant of the Fisher–Yates shuffle, known as Sattolo's algorithm, may
 * be used to generate random cycles of length n instead. The Fisher–Yates shuffle is unbiased, so that every
 * permutation is equally likely. The modern version of the algorithm is also rather efficient, requiring only
 * time proportional to the number of items being shuffled and no additional storage space.
 *
 * The Fisher–Yates shuffle, in its original form, was described in 1938 by Ronald A. Fisher and Frank Yates in
 * their book Statistical tables for biological, agricultural and medical research. Their description of the
 * algorithm used pencil and paper; a table of random numbers provided the randomness. The basic method given
 * for generating a random permutation of the numbers 1 through N goes as follows:
 *   1. Write down the numbers from 1 through N.
 *   2. Pick a random number k between one and the number of unstruck numbers remaining (inclusive).
 *   3. Counting from the low end, strike out the kth number not yet struck out, and write it down elsewhere.
 *   4. Repeat from step 2 until all the numbers have been struck out.
 *   5. The sequence of numbers written down in step 3 is now a random permutation of the original numbers.
 *
 * The modern version of the Fisher–Yates shuffle, designed for computer use, was introduced by
 * Richard Durstenfeld in 1964 and popularized by Donald E. Knuth in The Art of Computer
 * Programming as "Algorithm P". Neither Durstenfeld nor Knuth, in the first edition of his book,
 * acknowledged the work of Fisher and Yates; they may not have been aware of it. Subsequent editions
 * of The Art of Computer Programming mention Fisher and Yates' contribution.
 *
 * The algorithm described by Durstenfeld differs from that given by Fisher and Yates in a small but
 * significant way. Whereas a naive computer implementation of Fisher and Yates' method would spend
 * needless time counting the remaining numbers in step 3 above, Durstenfeld's solution is to move
 * the "struck" numbers to the end of the list by swapping them with the last unstruck number at each
 * iteration. This reduces the algorithm's time complexity to O(n), compared to O(n^2) for the naive
 * implementation. This change gives the following algorithm (for a zero-based array).
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class KnuthShuffle {

    public static <T extends Comparable<? super T>> void shuffle(T[] a) {
        for (int i = 0; i < a.length; i++) {
            int r = StdRandom.uniform(i + 1);
            swap(a, i, r);
        }
    }

    public static void main(String[] args) {
        Integer[] a = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        shuffle(a);
        assert !isSorted(a, 0, a.length-1);
        print(a);
    }

}
