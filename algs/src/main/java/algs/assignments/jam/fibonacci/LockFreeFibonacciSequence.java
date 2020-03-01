package algs.assignments.jam.fibonacci;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Lock-free thread-safe Fibonacci sequence implementation.
 *
 * @author Serj Sintsov
 */
public class LockFreeFibonacciSequence implements FibonacciSequence {

    private static class FibonacciNumber {
        protected final BigInteger prev;
        protected final BigInteger curr;

        public FibonacciNumber(BigInteger prev, BigInteger curr) {
            this.prev = prev;
            this.curr = curr;
        }

        public FibonacciNumber next() {
            return new FibonacciNumber(curr, prev.add(curr));
        }

        public BigInteger value() {
            return curr;
        }
    }

    private static final class FirstFibonacciNumber extends FibonacciNumber {
        public FirstFibonacciNumber() {
            super(null, BigInteger.ZERO);
        }

        @Override
        public FibonacciNumber next() {
            return new FibonacciNumber(curr, BigInteger.ONE);
        }
    }

    private final AtomicReference<FibonacciNumber> currentFibNumberRef;

    public LockFreeFibonacciSequence() {
        currentFibNumberRef = new AtomicReference<>(new FirstFibonacciNumber());
    }

    @Override
    public BigInteger next() {
        FibonacciNumber currFibNumber;
        do {
            currFibNumber = currentFibNumberRef.get();
        } while (!currentFibNumberRef.compareAndSet(currFibNumber, currFibNumber.next()));

        return currFibNumber.value();
    }

}


