package algs.assignments.rqueuedeque;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import edu.princeton.cs.introcs.StdRandom;

/**
 * Bonus implementation of {@link SubsetClient} without using
 * {@link algs.adt.deque.Deque} or {@link algs.adt.queue.RandomizedQueue}.
 *
 * @author Serj Sintsov
 */
public class SubsetClient2 {

    private static class SubsetImpl {
        private String[] data;
        private int N = 0;
        private int k;

        public SubsetImpl(int K) {
            this.k = K;
            this.data = new String[K];
        }

        public void scan() {
            if (k == 0) return;

            int r;
            while (!StdIn.isEmpty()) {
                String next = StdIn.readString();
                r = StdRandom.uniform(N + 1);

                if (N < k) {
                    data[N] = next;
                    swap(data, N, r);
                }
                else if (r < k) {
                    data[r] = next;
                }

                N += 1;
            }

            for (String d : data) StdOut.println(d);
        }

        private void swap(String[] a, int i, int j) {
            String tmp = a[i];
            a[i] = a[j];
            a[j] = tmp;
        }
    }

    public static void main(String[] args) {
        new SubsetImpl(Integer.parseInt(args[0])).scan();
    }

}
