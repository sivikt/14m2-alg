package algs.assignments.jam.fibonacci;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Simple blocking implementation of thread-safe {@link algs.assignments.jam.fibonacci.LockFreeFibonacciSequence}.
 *
 * As suggested by http://codereview.stackexchange.com/users/31503/rolfl
 *
 * @author Serj Sintsov
 */
class ThreeStateLockFreeFibonacciSequence implements FibonacciSequence {

    private final AtomicReference<BigInteger> nextRef = new AtomicReference<>(BigInteger.ONE);
    private final AtomicReference<BigInteger> currRef = new AtomicReference<>(BigInteger.ONE);

    @Override
    public BigInteger next() {
        BigInteger current;

        do {
            // spin loop while someone else is updating the reference.
            // updating is indicated by a null value in the reference.
            current = currRef.getAndSet(null);
        } while (current == null);

        BigInteger next = nextRef.get();
        nextRef.set(next.add(current)); // TODO seems it's buggy and not thread-safe
        currRef.set(next);

        return current;
    }

}
