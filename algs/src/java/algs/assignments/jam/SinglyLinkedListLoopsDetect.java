package algs.assignments.jam;

import algs.adt.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains a sets of algorithms to solve cycle detection in a linked list data structure (or
 * to find a cycle in a sequence of iterated function values).
 *
 * """
 * Formal definitions:
 *     1) For any function ƒ that maps a finite set S to itself, and any
 *        initial value X0 in S, the sequence of iterated function values
 *        X0, X1=f(X0), X2=f(X1), ..., Xi=f(Xi-1), ...
 *        must eventually use the same value twice: there must be some i ≠ j
 *        such that Xi = Xj. Once this happens, the sequence must continue by
 *        repeating the cycle of values from Xi to Xj−1. Cycle detection is the
 *        problem of finding i and j, given ƒ and X0.
 *
 *     2) Let S be any finite set, ƒ be any function from S to itself, and X0 be
 *        any element of S. For any i > 0, let Xi = ƒ(Xi−1). Let μ be the smallest
 *        index such that the value Xμ reappears infinitely often within the sequence
 *        of values Xi, and let λ (the loop length) be the smallest positive integer
 *        such that Xμ = Xλ+μ. The cycle detection problem is the task of finding λ and μ.
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class SinglyLinkedListLoopsDetect {

    /**
     * Represents a node of single linked list.
     */
    private static class Node<T> {
        public T value;
        public Node<T> next;

        public Node(T value) {
            this(value, null);
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }

    /**
     * Floyd's cycle-finding algorithm, also called the "tortoise and the hare" algorithm,
     * is a pointer algorithm that uses only two pointers, which move through the sequence
     * at different speeds. The algorithm is named for Robert W. Floyd, who invented it in
     * the late 1960s.
     */
    public static <T> boolean isFloydCycle(Node<T> head) {
        Node<T> tortoise = f(head);
        Node<T> hare = f(f(head));

        while (tortoise != hare) {
            tortoise = f(tortoise);
            hare = f(f(hare));
        }

        return tortoise != null;
    }

    /**
     * Finds μ and λ using {@link #isFloydCycle(Node)} algoritm.
     * @return {@link algs.adt.Pair#getFirst()}  - μ
     *         {@link algs.adt.Pair#getSecond()} - λ
     */
    public static <T> Pair<Integer, Integer> findFloydMuLam(Node<T> head) {
        Node<T> tortoise = f(head);
        Node<T> hare = f(f(head));

        while (tortoise != hare) {
            tortoise = f(tortoise);
            hare = f(f(hare));
        }

        if (tortoise == null) // doesn't have cycle
            return Pair.of(-1, -1);

        int mu = 0;
        tortoise = head;

        while (tortoise != hare) {
            tortoise = f(tortoise);
            hare = f(hare);
            mu += 1;
        }

        int lam = 1;
        hare = f(tortoise);
        while (tortoise != hare) {
            hare = f(hare);
            lam += 1;
        }

        return Pair.of(mu, lam);
    }

    /**
     * Richard P. Brent described an alternative cycle detection algorithm that, like the
     * tortoise and hare algorithm, requires only two pointers into the sequence. However,
     * it is based on a different principle: searching for the smallest power of two 2i
     * that is larger than both λ and μ. For i = 0, 1, 2, etc., the algorithm compares X2i−1
     * with each subsequent sequence value up to the next power of two, stopping when it finds
     * a match. It has two advantages compared to the tortoise and hare algorithm: it finds
     * the correct length λ of the cycle directly, rather than needing to search for it in a
     * subsequent stage, and its steps involve only one evaluation of ƒ rather than three.
     */
    public static <T> boolean isBrentCycle(Node<T> head) {
        int power = 1,
            lam = 1;

        Node<T> tortoise = head;
        Node<T> hare = f(head);

        while (tortoise != hare) {
            if (power == lam) {
                tortoise = hare;
                power *= 2;
                lam = 0;
            }

            hare = f(hare);
            lam += 1;
        }

        return tortoise != null;
    }

    /**
     * Finds μ and λ using {@link #isBrentCycle(Node)} algoritm.
     * @return {@link algs.adt.Pair#getFirst()}  - μ
     *         {@link algs.adt.Pair#getSecond()} - λ
     */
    public static <T> Pair<Integer, Integer> findBrentCycleMuLam(Node<T> head) {
        int power = 1,
            lam = 1;

        Node<T> tortoise = head;
        Node<T> hare = f(head);

        while (tortoise != hare) {
            if (power == lam) {
                tortoise = hare;
                power *= 2;
                lam = 0;
            }

            hare = f(hare);
            lam += 1;
        }

        if (tortoise == null) // doesn't have cycle
            return Pair.of(-1, -1);

        hare = head;
        for (int i = 0; i < lam; i++)
            hare = f(hare);

        int mu = 0;
        tortoise = head;

        while (tortoise != hare) {
            tortoise = f(tortoise);
            hare = f(hare);
            mu += 1;
        }

        return Pair.of(mu, lam);
    }

    private static <T> Node<T> f(Node<T> x) {
        return x != null ? x.next : null;
    }


    private static class CachedNode<T> extends Node<T> {
        private static final Map<Object, CachedNode<?>> cache = new HashMap<>();

        public CachedNode(T value) {
            super(value, null);
            cache.put(value, this);
        }

        @SuppressWarnings("unchecked")
        public CachedNode<T> next(T n) {
            CachedNode<T> node;

            if (cache.containsKey(n))
                node = (CachedNode<T>) cache.get(n);
            else {
                node = new CachedNode<>(n);
                cache.put(n, node);
            }

            if (this.next == null) this.next = node;

            return node;
        }

        public static void reset() {
            cache.clear();
        }
    }


    public static void main(String args[]) {
        /**
         * case 0) 2 -> 0 -> 6 -> 3 -> 1 -> 6
         * case 1) 2 -> 0 -> 6 -> 3 -> 1 -> 5 -> null
         * case 2) 2 -> 2
         * case 3) 2 -> null
         * case 4) null
         */
        CachedNode.reset();
        CachedNode<Integer> head0 = new CachedNode<>(2);
        head0.next(0).next(6).next(3).next(1).next(6);
        assert isFloydCycle(head0);
        assert findFloydMuLam(head0).getFirst()  == 2;
        assert findFloydMuLam(head0).getSecond() == 3;
        assert isBrentCycle(head0);
        assert findBrentCycleMuLam(head0).getFirst()  == 2;
        assert findBrentCycleMuLam(head0).getSecond() == 3;

        CachedNode.reset();
        CachedNode<Integer> head1 = new CachedNode<>(2);
        head1.next(0).next(6).next(3).next(1).next(5);
        assert !isFloydCycle(head1);
        assert findFloydMuLam(head1).getFirst()  == -1;
        assert findFloydMuLam(head1).getSecond() == -1;
        assert !isBrentCycle(head1);
        assert findBrentCycleMuLam(head1).getFirst()  == -1;
        assert findBrentCycleMuLam(head1).getSecond() == -1;

        CachedNode.reset();
        CachedNode<Integer> head2 = new CachedNode<>(2);
        head2.next(2);
        assert isFloydCycle(head2);
        assert findFloydMuLam(head2).getFirst()  == 0;
        assert findFloydMuLam(head2).getSecond() == 1;
        assert isBrentCycle(head2);
        assert findBrentCycleMuLam(head2).getFirst()  == 0;
        assert findBrentCycleMuLam(head2).getSecond() == 1;

        CachedNode.reset();
        CachedNode<Integer> head3 = new CachedNode<>(2);
        assert !isFloydCycle(head3);
        assert findFloydMuLam(head3).getFirst()  == -1;
        assert findFloydMuLam(head3).getSecond() == -1;
        assert !isBrentCycle(head3);
        assert findBrentCycleMuLam(head3).getFirst()  == -1;
        assert findBrentCycleMuLam(head3).getSecond() == -1;

        assert !isFloydCycle(null);
        assert findFloydMuLam(null).getFirst()  == -1;
        assert findFloydMuLam(null).getSecond() == -1;
        assert !isBrentCycle(null);
        assert findBrentCycleMuLam(null).getFirst()  == -1;
        assert findBrentCycleMuLam(null).getSecond() == -1;
    }
}
