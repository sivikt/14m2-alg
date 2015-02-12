package algs.adt;

import java.util.Arrays;

/**
 * Holds <first, second> tuple.
 */
public class Pair<A, B> {

    private final A first;
    private final B second;

    public Pair(A key, B value) {
        this.first = key;
        this.second = value;
    }

    public static <A, B> Pair<A, B> of (A first, B second) {
        return new Pair<>(first, second);
    }

    public A getFirst() {
        return first;
    }

    public B getSecond() {
        return second;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;

        if (this == obj)
            return true;

        if (!(obj instanceof Pair<?, ?>))
            return false;

        Pair<?, ?> that = (Pair<?, ?>) obj;
        return (first == that.first || (first != null && first.equals(that.first))) &&
               (second == that.second || (second != null && second.equals(that.second)));
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[] {first, second});
    }

    @Override
    public String toString() {
        return String.format("(%s, %s)", first, second);
    }

}
