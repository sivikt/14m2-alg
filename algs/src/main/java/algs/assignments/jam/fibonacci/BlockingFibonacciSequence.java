package algs.assignments.jam.fibonacci;

import java.math.BigInteger;

/**
 * Simple blocking implementation of thread-safe {@link LockFreeFibonacciSequence}.
 *
 * @author Serj Sintsov
 */
class BlockingFibonacciSequence implements FibonacciSequence {

    private BigInteger prev;
    private volatile BigInteger curr;

    public BlockingFibonacciSequence() {
        this.prev = null;
        this.curr = null;
    }

    @Override
    public synchronized BigInteger next() {
        if (prev == null) {
            prev = BigInteger.ZERO;
            curr = BigInteger.ONE;
            return prev;
        }

        BigInteger oldPrev = prev;
        prev = curr;
        curr = oldPrev.add(curr);
        return prev;
    }

}
