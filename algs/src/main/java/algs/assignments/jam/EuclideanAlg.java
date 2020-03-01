package algs.assignments.jam;

/**
 * In mathematics, the Euclidean algorithm, or Euclid's algorithm,
 * is a method for computing the greatest common divisor (GCD) of
 * two (usually positive) integers, also known as the greatest
 * common factor (GCF) or highest common factor (HCF).
 *
 * a >= b
 * a = q0 b + R0
 * b = q1 R0 + R1
 * R0 = q2 R1 + R2
 * R1 = q3 R2 + R3
 *      ...
 * Rk = qn Rk+1 + Rk+2
 *      ...
 *      while Rk+2 != 0, then Rk+1 is gcd
 *
 * @author Serj Sintsov <ssivikt@gmail.com>, 2013 Public Domain.
 */
public class EuclideanAlg {

    public static int gcd1(int a, int b) {
        while (a != b) {
            if (a < b)
                b -= a;
            else
                a -= b;
        }

        return a;
    }

    public static int gcd2(int a, int b) {
        if (b == 0)
            return a;
        else
            return gcd2(b, a%b);
    }

    public static int gcd3(int a, int b) {
        int t;
        while (b != 0) {
            t = b;
            b = a % b;
            a = t;
        }
        return a;
    }

    public static int gcd4(int a, int b) {
        while (b != 0 && a != 0) {
            if (a >= b)
                a %= b;
            else
                b %= a;
        }

        return a+b;
    }

    public static int lcm(int a, int b) {
        return a/gcd1(a, b)*b;
    }

    public static void main(String[] args) {
        assert gcd1(0, 0) == 0;
        assert gcd1(6, 8) == 2;
        assert gcd1(1, 8) == 1;
        assert gcd1(8, 8) == 8;

        assert gcd2(0, 0) == 0;
        assert gcd2(6, 8) == 2;
        assert gcd2(1, 8) == 1;
        assert gcd2(8, 8) == 8;

        assert gcd3(0, 0) == 0;
        assert gcd3(6, 8) == 2;
        assert gcd3(1, 8) == 1;
        assert gcd3(8, 8) == 8;

        assert gcd4(0, 0) == 0;
        assert gcd4(6, 8) == 2;
        assert gcd4(1, 8) == 1;
        assert gcd4(8, 8) == 8;

        assert lcm(6, 8) == 24;
    }

}
