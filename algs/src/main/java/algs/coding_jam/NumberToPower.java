package algs.coding_jam;

/**
 * Returns x^y.
 *
 * @author Serj Sintsov <ssivikt@gmail.com>, 2013 Public Domain.
 */
public class NumberToPower {

    public static int slow(int x, int y) {
        int p = x;
        if (y == 0) return 1;
        while (y-- > 1) p *= x;
        return p;
    }

    public static int fast(int x, int y) {
        int p = 1;
        while (y > 0) {
            if ((y & 1) == 1) {
                p *= x; // y is odd
                y -= 1;
            }
            else {
                x *= x;
                y >>= 1; // divide by 2
            }
        }
        return p;
    }

    public static void main(String[] args) {
        assert slow(0, 0) == 1;
        assert slow(5, 0) == 1;
        assert slow(5, 1) == 5;
        assert slow(5, 2) == 25;
        assert slow(2, 16) == 65536;

        assert fast(0, 0) == 1;
        assert fast(5, 0) == 1;
        assert fast(5, 1) == 5;
        assert fast(5, 2) == 25;
        assert fast(2, 16) == 65536;
    }

}
