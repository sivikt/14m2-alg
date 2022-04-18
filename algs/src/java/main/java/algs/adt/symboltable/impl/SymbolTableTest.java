package algs.adt.symboltable.impl;

import algs.adt.symboltable.SymbolTable;
import util.Debugger;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * For {@link SymbolTable} unit testing.
 * @author Serj Sintsov
 */
public class SymbolTableTest {

    public static ItemGenerator<Integer, Integer> intGen() {
        final Random rnd = new Random();

        return () -> {
            Integer key = rnd.nextInt();
            return new SymbolTableTest.TestItem<>(key, key);
        };
    }

    public static <Key extends Comparable<? super Key>, Value> void
    testInsert(SymbolTable<Key, Value> bst,
               int count,
               ItemGenerator<Key, Value> itemGen) throws Exception {
        echo("Insertion test");

        Debugger dbg = new Debugger();
        int inserted = 0;

        try {
            dbg.startTimer("insert+search");

            while (bst.size() < count) {
                int prevSz = bst.size();
                TestItem<Key, Value> item = itemGen.generate();
                bst.put(item.getKey(), item.getVal());

                if (prevSz + 1 == bst.size()) { // count only unique inserts
                    inserted += 1;
                }

                assertTrue(bst.contains(item.getKey()));
                assertEquals(inserted, bst.size());
            }

            dbg.stopTimer();
        }
        catch (Throwable th) {
            echo(String.format("Insertion is failed! Status: %s inserted", inserted));
            System.out.println(bst.toString());
            Thread.sleep(100);
            throw th;
        }

        assertEquals(count, bst.size());
    }

    public static <Key extends Comparable<? super Key>, Value> void
    testDelete(SymbolTable<Key, Value> bst,
               int maxInserts,
               double deleteProbability,
               ItemGenerator<Key, Value> itemGen) throws Exception {
        echo("Deletion test");

        Debugger dbg = new Debugger();
        int inserted = 0;
        int deleted  = 0;

        try {
            dbg.startTimer("delete+search");

            while (inserted < maxInserts) {
                int prevSz = bst.size();
                TestItem<Key, Value> item = itemGen.generate();
                bst.put(item.getKey(), item.getVal());

                if (prevSz + 1 == bst.size()) { // count only unique inserts
                    prevSz   += 1;
                    inserted += 1;

                    if ((double) (deleted+1)/inserted <= deleteProbability) {
                        bst.delete(item.getKey());
                        deleted += 1;

                        assertFalse(bst.contains(item.getKey()));
                        assertEquals(prevSz - 1, bst.size());
                    }
                    else
                        assertTrue(bst.contains(item.getKey()));
                }
            }

            dbg.stopTimer();
        }
        catch (Throwable th) {
            echo(String.format("Deletion is failed! Status: %s inserted, %s deleted", inserted, deleted));
            System.out.println(bst.toString());
            Thread.sleep(100);
            throw th;
        }

        echo("inserted=" + inserted);
        echo("deleted=" + deleted);

        assertEquals(inserted - deleted, bst.size());
    }

    @SuppressWarnings("unchecked")
    public static <Key extends Comparable<? super Key>, Value> void
    testSearch(SymbolTable<Key, Value> bst,
               int count,
               ItemGenerator<Key, Value> itemGen) throws Exception {
        echo("Search test");

        Debugger dbg = new Debugger();
        int inserted = 0;
        Key[] keys = (Key[]) new Comparable[count];

        try {
            while (bst.size() < count) {
                int prevSz = bst.size();
                TestItem<Key, Value> item = itemGen.generate();
                bst.put(item.getKey(), item.getVal());

                if (prevSz + 1 == bst.size()) { // count only unique inserts
                    keys[inserted] = item.getKey();
                    inserted += 1;
                }

                assertEquals(inserted, bst.size());
            }

            dbg.startTimer("search");
            for (Key k : keys)
                assertTrue(bst.contains(k));
            dbg.stopTimer();
        }
        catch (Throwable th) {
            echo(String.format("Search is failed! Status: %s inserted", inserted));
            System.out.println(bst.toString());
            Thread.sleep(100);
            throw th;
        }

        assertEquals(count, bst.size());
    }

    @FunctionalInterface
    public interface ItemGenerator<Key extends Comparable<? super Key>, Value> {
        TestItem<Key, Value> generate();
    }

    public static class TestItem<Key extends Comparable<? super Key>, Value> {

        private Key   key;
        private Value val;

        public TestItem(Key key, Value val) {
            this.key = key;
            this.val = val;
        }

        public Key getKey() {
            return key;
        }

        public Value getVal() {
            return val;
        }

    }

    private static void echo(String msg) {
        System.out.println(msg);
    }

}
