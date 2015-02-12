package algs.assignments.jam;

import static algs.sorting.SortUtil.print;
import static algs.sorting.SortUtil.swap;

/**
 * Dutch National Flag - red-white-blue.
 *
 *
 * Given an array of N buckets, each containing a red, white, or blue pebble, sort them by color.
 * The allowed operations are:
 *   swap(i,j): swap the pebble in bucket i with the pebble in bucket j.
 *   color(i): color of pebble in bucket i.
 *
 * The performance requirements are as follows:
 *   At most N calls to color().
 *   At most N calls to swap().
 *   Constant extra space.
 *
 * """
 * General "Dutch national flag" problem
 * The Dutch national flag problem (DNF) is a computer science programming problem proposed by
 * Edsger Dijkstra. The flag of the Netherlands consists of three colours: red, white and blue.
 * Given balls of these three colours arranged randomly in a line (the actual number of balls
 * does not matter), the task is to arrange them such that all balls of the same colour are
 * together and their collective colour groups are in the correct order.
 *
 * One algorithm is to have the top group grow down from the top of the array, the bottom group
 * grow up from the bottom, and keep the middle group just above the bottom. The algorithm indexes
 * three locations, the bottom of the top group, the top of the bottom group, and the top of the
 * middle group. Elements that are yet to be sorted fall between the middle and the top group. At
 * each step, examine the element just above the middle. If it belongs to the top group, swap it
 * with the element just below the top. If it belongs in the bottom, swap it with the element just
 * above the bottom. If it is in the middle, leave it. Update the appropriate index. Complexity
 * is Θ(n) moves and examinations.
 * """ wikipedia (c)
 *
 * @author Serj Sintsov
 */
public class DutchNationalFlag {

    private static enum Pebble {
        RED(0),
        WHITE(2),
        BLUE(1);

        private int color;

        private Pebble(int color) {
            this.color = color;
        }

        public int color() {
            return color;
        }

        @Override
        public String toString() {
            switch (this) {
                case RED:   return "red";
                case WHITE: return "white";
                case BLUE:  return "blue";
            }

            return "NA";
        }
    }

    public static void assembleFlag(Pebble[] a) {
        if (a.length < 3)
            throw new IllegalArgumentException("Flag consists of at least 3 pebbles");

        int N = a.length,
            i = 0,
            blueIdx  = N - 1,
            whiteIdx = blueIdx - 1;

        while (i <= whiteIdx && i <= blueIdx) {
            if (a[i] == Pebble.BLUE) {
                swap(a, i, blueIdx);
                blueIdx -= 1;
            }
            else if (a[i] == Pebble.WHITE) {
                swap(a, i, whiteIdx);
                whiteIdx -= 1;
            }
            else
                i += 1;

            if (blueIdx == whiteIdx)
                whiteIdx -= 1;
        }
    }

    public static void main(String[] args) {
        Pebble[] a0 = {
                Pebble.RED,
                Pebble.WHITE,
                Pebble.BLUE
        };
        assembleFlag(a0);
        System.out.println();
        print(a0);

        Pebble[] a1 = {
                Pebble.WHITE,
                Pebble.BLUE,
                Pebble.RED
        };
        assembleFlag(a1);
        System.out.println();
        print(a1);

        Pebble[] a2 = {
                Pebble.WHITE,
                Pebble.WHITE,
                Pebble.WHITE
        };
        assembleFlag(a2);
        System.out.println();
        print(a2);

        Pebble[] a3 = {
                Pebble.BLUE,
                Pebble.BLUE,
                Pebble.BLUE
        };
        assembleFlag(a3);
        System.out.println();
        print(a3);

        Pebble[] a4 = {
                Pebble.RED,
                Pebble.RED,
                Pebble.RED
        };
        assembleFlag(a4);
        System.out.println();
        print(a4);

        Pebble[] a5 = {
                Pebble.BLUE,
                Pebble.WHITE,
                Pebble.RED,
                Pebble.WHITE,
                Pebble.BLUE,
                Pebble.BLUE,
                Pebble.RED,
                Pebble.WHITE,
                Pebble.WHITE,
                Pebble.WHITE,
                Pebble.RED,
                Pebble.WHITE,
        };
        assembleFlag(a5);
        System.out.println();
        print(a5);
    }

}
