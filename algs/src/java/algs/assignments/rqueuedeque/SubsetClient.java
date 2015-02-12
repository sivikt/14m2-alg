package algs.assignments.rqueuedeque;

import algs.adt.queue.impl.ArrayRandomizedQueue;
import algs.adt.queue.RandomizedQueue;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

import java.util.Iterator;

/**
 * """Write a client program Subset.java that takes a command-line integer k;
 *    reads in a sequence of N strings from standard input using StdIn.readString();
 *    and prints out exactly k of them, uniformly at random. Each item from the
 *    sequence can be printed out at most once. You may assume that k ≥ 0 and no
 *    greater than the number of string N on standard input.
 *
 *    The running time of Subset must be linear in the size of the input. You may use
 *    only a constant amount of memory plus either one Deque or RandomizedQueue object
 *    of maximum size at most N, where N is the number of strings on standard input.
 *    (For an extra challenge, use only one Deque or RandomizedQueue object of maximum
 *    size at most k.).
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class SubsetClient {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        if (k == 0) return;

        RandomizedQueue<String> rqueue = new ArrayRandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String next = StdIn.readString();
            rqueue.enqueue(next);
        }

        Iterator<String> it = rqueue.iterator();
        for (int i = 0; i < k && it.hasNext(); i++) {
            StdOut.println(it.next());
        }
    }

}
