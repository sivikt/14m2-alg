package algs.assignments.jam;

import java.util.HashMap;
import java.util.Map;
import static algs.assignments.jam.LinkedListCloning.CachedNode.node;
import static algs.assignments.jam.LinkedListCloning.CachedNode.reset;
import static java.lang.String.format;

/**
 * From algs4partI task description:
 * <p>Clone a linked structure with two pointers per node.
 * Suppose that you are given a reference to the first node of a linked structure
 * where each node has two pointers: one pointer to the next node in the sequence
 * (as in a standard singly-linked list) and one pointer to an arbitrary node.
 *
 * Goal:
 * Design a linear-time algorithm to create a copy of the doubly-linked structure.
 * You may modify the original linked structure, but you must end up with two copies
 * of the original.
 *
 * @author Serj Sintsov
 */
public class LinkedListCloning {

    public static class Node<T> {
        public T value;
        public Node<T> next;
        public Node<T> random;

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }

        public static <T> Node<T> f(Node<T> x) {
            return x != null ? x.next : null;
        }

        private void insertClones() {
            Node<T> orig = this;

            while (orig != null) {
                Node<T> next = f(orig);
                orig.next = new Node<>(orig.value, next); // clone
                orig = next;
            }
        }

        private void linkClones() {
            Node<T> orig  = this;

            while (orig != null) {
                f(orig).random = f(orig.random); // next to origin random located its clone
                orig = f(f(orig)); // double step to next origin node
            }
        }

        private Node<T> ejectClones() {
            Node<T> orig  = this;
            Node<T> clone = f(this);
            Node<T> cloneHead = clone;

            do {
                orig.next  = clone.next;
                clone.next = f(orig.next);
                orig  = orig.next;
                clone = clone.next;
            } while (orig != null);

            return cloneHead;
        }

        /**
         * Target task implementation.
         * It takes time proportional to 3N, where N is a number of
         * list items. And, at that,  ω(N) == Θ(N) == O(N).
         *
         * Algorithm is based on modification of the original list.
         * On the first step algorithm clone all "next" nodes without
         * deep cloning (only value) and link them just after original nodes.
         * On the second step algorithm init node.random link from the cloned nodes.
         * And at the end algorithm backs revert original list and makes a separate
         * list from the cloned nodes.
         *
         * @return clone of the current list.
         */
        public Node<T> cloneList() {
            insertClones();
            linkClones();
            return ejectClones();
        }
    }

    public static class CachedNode<T> extends Node<T> {
        private static final Map<Object, CachedNode<?>> cache = new HashMap<>();

        private CachedNode(T value, Node<T> random) {
            super(value, null);
            this.random = random;
            cache.put(value, this);
        }

        public static <T> CachedNode<T> node(T value) {
            return new CachedNode<>(value, null);
        }

        public CachedNode<T> random(T n) {
            this.random = getCached(n);
            return this;
        }

        @SuppressWarnings("unchecked")
        public CachedNode<T> next(T n) {
            CachedNode<T> node = getCached(n);
            if (this.next == null) this.next = node;
            return node;
        }

        @SuppressWarnings("unchecked")
        public CachedNode<T> next(T n, Node<T> random) {
            CachedNode<T> node = getCached(n);
            if (this.next == null)   this.next   = node;
            if (this.random == null) this.random = random;
            return node;
        }

        @SuppressWarnings("unchecked")
        private CachedNode<T> getCached(T n) {
            if (cache.containsKey(n))
                return (CachedNode<T>) cache.get(n);

            CachedNode<T> node = new CachedNode<>(n, random);
            cache.put(n, node);
            return node;
        }

        public static void reset() {
            cache.clear();
        }
    }

    private static <T> T value(Node<T> x) {
        return x != null ? x.value : null;
    }

    private static <T> Integer hash(Node<T> x) {
        return x != null ? x.hashCode() : null;
    }

    private static <T> String asString(Node<T> head) {
        StringBuilder buf = new StringBuilder();

        while (head != null) {
            buf.append(format("<value=%s; ", head.value));
            buf.append(format("[next=%s, %s]; ", value(head.next), hash(head.next)));
            buf.append(format("[rnd=%s, %s]; ", value(head.random), hash(head.random)));
            buf.append(format("hash=%s>, ", hash(head)));

            head = Node.f(head);
        }

        return buf.toString();
    }


    public static void main(String[] args) {
        /**
         * case 0) 1 --> 2 --> 3 --> 4 --> 5 --> null
         *         |     |     |           |
         *         |->3  |->2  |->4        |->1
         *
         * case 1) 1 --> 2 --> 3 --> 4 --> 5 --> null
         *         |     |     |     |      |
         *         |->2  |->3  |->4  |->5   |->null
         *
         * case 2) 1 --> 2 --> 3 --> 4 --> 5 --> null
         *
         * case 3) 1 --> 2 --> null
         *         |
         *         |->2
         *
         * case 4) 1 --> 2 --> null
         *         |     |
         *         |->2  |->2
         *
         * case 5) 1 --> null
         *         |
         *         |->1
         *
         * case 6) 1 --> null
         */
        reset();
        CachedNode<Integer> head0 = node(1).random(3);
        head0.next(2).random(2)
                     .next(3).random(4)
                             .next(4).next(5).random(1);

        String head0StrOrig = asString(head0);
        Node<Integer> head0Clone = head0.cloneList();
        String head0StrMod  = asString(head0);
        String head0CloneStr = asString(head0Clone);
        assert head0StrOrig.equals(head0StrMod);
        assert !head0StrOrig.equals(head0CloneStr);


        reset();
        CachedNode<Integer> head1 = node(1).random(2);
        head1.next(2).random(3)
                .next(3).random(4)
                        .next(4).random(5)
                                .next(5);

        String head1StrOrig = asString(head1);
        Node<Integer> head1Clone = head1.cloneList();
        String head1StrMod  = asString(head1);
        String head1CloneStr = asString(head1Clone);
        assert head1StrOrig.equals(head1StrMod);
        assert !head1StrOrig.equals(head1CloneStr);


        reset();
        CachedNode<Integer> head2 = node(1);
        head2.next(2).next(3).next(4).next(5);

        String head2StrOrig = asString(head2);
        Node<Integer> head2Clone = head2.cloneList();
        String head2StrMod  = asString(head2);
        String head2CloneStr = asString(head2Clone);
        assert head2StrOrig.equals(head2StrMod);
        assert !head2StrOrig.equals(head2CloneStr);


        reset();
        CachedNode<Integer> head3 = node(1).random(2);
        head3.next(2);

        String head3StrOrig = asString(head3);
        Node<Integer> head3Clone = head3.cloneList();
        String head3StrMod  = asString(head3);
        String head3CloneStr = asString(head3Clone);
        assert head3StrOrig.equals(head3StrMod);
        assert !head3StrOrig.equals(head3CloneStr);


        reset();
        CachedNode<Integer> head4 = node(1).random(2);
        head4.next(2).random(2);

        String head4StrOrig = asString(head4);
        Node<Integer> head4Clone = head4.cloneList();
        String head4StrMod  = asString(head4);
        String head4CloneStr = asString(head4Clone);
        assert head4StrOrig.equals(head4StrMod);
        assert !head4StrOrig.equals(head4CloneStr);


        reset();
        CachedNode<Integer> head5 = node(1).random(1);

        String head5StrOrig = asString(head5);
        Node<Integer> head5Clone = head5.cloneList();
        String head5StrMod  = asString(head5);
        String head5CloneStr = asString(head5Clone);
        assert head5StrOrig.equals(head5StrMod);
        assert !head5StrOrig.equals(head5CloneStr);


        reset();
        CachedNode<Integer> head6 = node(1);

        String head6StrOrig = asString(head6);
        Node<Integer> head6Clone = head6.cloneList();
        String head6StrMod  = asString(head6);
        String head6CloneStr = asString(head6Clone);
        assert head6StrOrig.equals(head6StrMod);
        assert !head6StrOrig.equals(head6CloneStr);
    }
}
