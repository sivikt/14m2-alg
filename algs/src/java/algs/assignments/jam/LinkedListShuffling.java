package algs.assignments.jam;

import java.util.Random;

/**
 * """Shuffling a linked list.
 *    Given a singly-linked list containing N items, rearrange the items uniformly at random.
 *    Your algorithm should consume a logarithmic (or constant) amount of extra memory and run
 *    in time proportional to N*log(N) in the worst case.
 *
 *    Hint: design a linear-time subroutine that can take two uniformly shuffled linked lists of sizes N1
 *          and N2 and combined them into a uniformly shuffled linked lists of size N1+N2.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class LinkedListShuffling {

    public static class Node<Item> {

        public Item value;
        public Node<Item> next;

        public Node() {
            this(null, null);
        }

        public Node(Item value) {
            this(value, null);
        }

        public Node(Item value, Node<Item> next) {
            this.value = value;
            this.next = next;
        }

        public Node<Item> link(Node<Item> n) {
            next = n;
            return n;
        }

        @Override
        public String toString() {
            StringBuilder buf = new StringBuilder();
            Node<Item> next = this;

            buf.append(next.value);

            while ((next = next.next) != null) {
                buf.append("->");
                buf.append(next.value);
            }

            return buf.toString();
        }
    }

    public static <Item> Node<Item> shuffle(Node<Item> first, int N) {
        return doShuffle(first, N);
    }

    private static <Item> Node<Item> doShuffle(Node<Item> l1, int N) {
        if (N <= 1)
            return l1;

        int l1Sz = N/2;
        int l2Sz = N - l1Sz;
        Node<Item> l2 = l1;

        for (int i = 0; i < l1Sz; i++, l2 = l2.next);

        l1 = doShuffle(l1, l1Sz);
        l2 = doShuffle(l2, l2Sz);

        return merge(l1, l1Sz, l2, l2Sz);
    }

    /**
     * Merge two uniformly shuffled linked lists of different sizes.
     */
    private static <Item> Node<Item> merge(Node<Item> l1, int l1Sz, Node<Item> l2, int l2Sz) {
        Random rnd = new Random();

        Node<Item> dummy = new Node<>();
        Node<Item> tail = dummy;

        while (l1Sz > 0 && l2Sz > 0) {
            if (rnd.nextBoolean()) { // flip coin
                tail.next = l1;
                l1 = l1.next;
                l1Sz -= 1;
            }
            else {
                tail.next = l2;
                l2 = l2.next;
                l2Sz -= 1;
            }

            tail = tail.next;
        }

        while (l1Sz > 0) {
            tail.next = l1;
            l1 = l1.next;
            tail = tail.next;
            l1Sz -= 1;
        }

        while (l2Sz > 0) {
            tail.next = l2;
            l2 = l2.next;
            tail = tail.next;
            l2Sz -= 1;
        }

        tail.next = l2;

        return dummy.next;
    }


    // test it
    public static void main(String[] args) {
        test(0);
        test(1);
        test(2);
        test(3);
        test(30);
        test(101);
    }

    private static void test(int count) {
        Node<Integer> first = new Node<>(0);
        Node<Integer> tail = first;
        for (int i = 1; i < count; i++)
            tail = tail.link(new Node<>(i));

        System.out.println(shuffle(first, count));
    }
}
