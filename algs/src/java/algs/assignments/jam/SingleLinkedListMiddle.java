package algs.assignments.jam;

import static algs.assignments.jam.SingleLinkedListMiddle.Node.f;
import static java.lang.String.format;

/**
 * Finds the middle of single-linked-list in one pass
 */
public class SingleLinkedListMiddle {

    public static class Node<Item> {
        public Item value;
        public Node<Item> next;

        public Node(Item value) {
            this.value = value;
        }

        public Node<Item> link(Node<Item> n) {
            this.next = n;
            return n;
        }

        public static <T extends Comparable<? super T>> Node<T> f(Node<T> n) {
            return n.next;
        }

        @Override
        public String toString() {
            return value.toString();
        }
    }

    public static <T extends Comparable<? super T>> Node<T> middle(Node<T> l) {
        Node<T> mid = null;
        boolean odd = true;

        while (l != null) {
            if (mid == null)
                mid = l;
            else if (odd)
                mid = f(mid);

            l = f(l);
            odd = !odd;
        }

        return mid;
    }


    // testing
    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++)
            checkForSize(i);
    }

    private static void checkForSize(int size) {
        if (size == 0) {
            assert middle(null) == null;
            return;
        }

        Node<Integer> head = new Node<>(0);
        Node<Integer> tail = head;
        Node<Integer> midd = head;
        int midIndex = (size % 2 == 0) ? size/2 : size/2+1;
        midIndex -= 1;

        for (int i = 1; i < size; i++) {
            tail = tail.link(new Node<>(i));
            if (i == midIndex)
                midd= tail;
        }

        Node<Integer> actualMid = middle(head);
        assert actualMid == midd : format("Failed for size=%s. Return %s but expected %s", size, actualMid, midd);
    }
}
