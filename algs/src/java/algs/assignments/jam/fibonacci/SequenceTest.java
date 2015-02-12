package algs.assignments.jam.fibonacci;

import java.math.BigInteger;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Serj Sintsov
 */
public class SequenceTest {

    public static void main(String[] args) throws InterruptedException {
        //testBlocking();
        echo();
        testLockFree();
        echo();
        //testThreeStateLockFree();
        // todo add thread-safety test
    }

    private static void testBlocking() throws InterruptedException {
        echo("test blocking");
        testMultiThreadsTimingsFor(BlockingFibonacciSequence::new);
//        testFibonacciNumberCorrectnessFor(BlockingFibonacciSequence::new);
//        testSingleThreadTimingsFor(BlockingFibonacciSequence::new);
    }

    private static void testLockFree() throws InterruptedException {
        echo("test lock-free");
        testMultiThreadsTimingsFor(LockFreeFibonacciSequence::new);
//        testFibonacciNumberCorrectnessFor(LockFreeFibonacciSequence::new);
//        testSingleThreadTimingsFor(LockFreeFibonacciSequence::new);
    }

    private static void testThreeStateLockFree() throws InterruptedException {
        echo("test 3-state lock-free");
        testMultiThreadsTimingsFor(ThreeStateLockFreeFibonacciSequence::new);
//        testFibonacciNumberCorrectnessFor(ThreeStateLockFreeFibonacciSequence::new);
//        testSingleThreadTimingsFor(ThreeStateLockFreeFibonacciSequence::new);
    }

    private static void testFibonacciNumberCorrectnessFor(SequenceBuilder seqBuilder) {
        int[] some = {
            0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144,
            233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946,
            17711, 28657, 46368, 75025, 121393, 196418, 317811
        };

        FibonacciSequence fibSeq = seqBuilder.build();

        for (int num : some)
            assert num == fibSeq.next().intValue();
    }

    private static void testSingleThreadTimingsFor(SequenceBuilder seqBuilder) throws InterruptedException {
        final int PROBE_COUNT = 37;
        LinkedList<Double> timings = new LinkedList<>();
        SingleTestClient client = new SingleTestClient(seqBuilder, 500_000, timings);

        for (int i = 0; i < PROBE_COUNT; i++)
            client.run();

        echo("average time " + average(timings) + " sec");
    }

    private static void testMultiThreadsTimingsFor(SequenceBuilder seqBuilder) throws InterruptedException {
        final int CLI_COUNT = 10;
        final int NUMBERS_COUNT = 50_000;

        FibonacciSequence fibSeq = seqBuilder.build();
        Thread[] clients = new Thread[CLI_COUNT];

        for (int i = 0; i < CLI_COUNT; i++)
            clients[i] = new Thread(new MultiTestClient(""+i, fibSeq, NUMBERS_COUNT));

        for (int i = 0; i < CLI_COUNT; i++)
            clients[i].start();

        for (int i = 0; i < CLI_COUNT; i++)
            clients[i].join();
    }

    private static class SingleTestClient implements Runnable {
        private final int loopCount;
        private final Deque<Double> timings;
        private final SequenceBuilder seqBuilder;

        private BigInteger currNumber;

        public SingleTestClient(SequenceBuilder seqBuilder, int loopCount, Deque<Double> timings) {
            this.loopCount = loopCount;
            this.timings = timings;
            this.seqBuilder = seqBuilder;
        }

        @Override
        public void run() {
            final FibonacciSequence fibSeq = seqBuilder.build();

            long start = System.currentTimeMillis();

            for (int i = 0; i < loopCount; i++)
                currNumber = fibSeq.next();

            long end = System.currentTimeMillis();
            timings.add((double)(end - start) / 1000);

            echo("ended in " + timings.getLast() + " sec");
        }
    }

    private static class MultiTestClient implements Runnable {
        private final String name;
        private final int loopCount;
        private final FibonacciSequence fibSeq;

        private BigInteger currNumber;

        public MultiTestClient(String name, FibonacciSequence fibSeq, int loopCount) {
            this.name = name;
            this.loopCount = loopCount;
            this.fibSeq = fibSeq;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();

            for (int i = 0; i < loopCount; i++)
                currNumber = fibSeq.next();

            long end = System.currentTimeMillis();

            echo(name + " ended in " + (double)(end - start) / 1000 + " sec");
        }
    }

    @FunctionalInterface
    private interface SequenceBuilder {
        FibonacciSequence build();
    }

    private static double average(List<Double> values) {
        Collections.sort(values);
        return values.stream().mapToDouble(a -> a).average().getAsDouble();
    }

    private static void echo() {
        echo("");
    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

}
