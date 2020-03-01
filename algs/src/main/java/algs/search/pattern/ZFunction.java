package algs.search.pattern;

import java.util.Arrays;

import static java.lang.Math.*;

/**
 * Реализация Z-функции.
 *
 * """Будем сравнивать начало самой строки с каждым из ее суффиксов. Сравнение может дойти до конца суффикса, либо
 *    оборваться на каком-то символе ввиду несовпадения. Длину совпавшей части и назовем компонентой Z-функции для данного
 *    суффикса. Т.е. z[i] — это наибольший общий префикс строки s и её i-го суффикса.
 *
 *    суффикс 	строка 		Z
 *    ababcaba 	ababcaba 	8
 *    babcaba 	ababcaba 	0
 *    abcaba 	ababcaba 	2
 *    bcaba 	ababcaba 	0
 *    caba   	ababcaba 	0
 *    aba 	    ababcaba  	3
 *    ba 	    ababcaba  	0
 *    a 	    ababcaba  	1
 *
 *    Префикс суффикса это ничто иное, как подстрока, а Z-функция — длины подстрок, которые встречаются одновременно в
 *    начале и в середине.
 *
 *    a  b  c  a  $  a  b  a  b  c  a  b  с  a  c  a  b
 *    17 0  0  1  0  2  0  4  0  0  4  0  0  1  0  2  0
 *
 *    Свойства:
 *      1) Z-функцию можно использовать для поиска образца T в строке S, с помощью алгоритма Кнута—Морриса—Пратта,
 *         использующего Z-функцию, вместо префикс-функции.
 *      2) Зная Z-функцию строки, можно однозначно восстановить префикс-функцию этой строки, и наоборот.
 * """
 *
 * @author Serj Sintsov
 */
public class ZFunction {

    /**
     * Для наглядности идеи алгоритма используются явные конструкции.
     */
    private static class ZBlock {
        public int pos;
        public int len;

        public ZBlock(int pos, int len) {
            this.pos = pos;
            this.len = len;
        }

        public int end() {
            return pos + len - 1;
        }

        public boolean inBlock(int i) {
            return i < end();
        }

        public void move(int pos, int len) {
            this.pos = pos;
            this.len = len;
        }
    }

    /**
     * O(n^2)
     */
    private static int[] zFunction_brute(String s) {
        int N   = s.length();
        int[] z = new int[N];

        if (N == 0) return z;
        z[0] = N;

        for (int i = 1; i < N; i++)
            while ( (z[i] < N-i) && (s.charAt(z[i]) == s.charAt(i+z[i])) )
                z[i] += 1;

        return z;
    }

    /**
     * Используем самый правый Z-блок для определения текущего значения Z[i].
     */
    private static int[] zFunction_plus(String s) {
        int N   = s.length();
        int[] z = new int[N];

        if (N == 0) return z;
        z[0] = N;

        ZBlock rightZBlock = new ZBlock(1, 0);

        for (int i = 1; i < N; i++) {
            if (!rightZBlock.inBlock(i)) {
                while ( (z[i] < N-i) && (s.charAt(z[i]) == s.charAt(i + z[i])) )
                    z[i]++;

                rightZBlock.move(i, z[i]);

                continue;
            }

            int hi = N - i;
            int k  = i - rightZBlock.pos; // sibling for s[i]
            if (z[k] < rightZBlock.len - k) z[i] = z[k];
            else {
                z[i] = min(rightZBlock.end() - i + 1, z[k]);

                while ( (z[i] < hi) && (s.charAt(z[i]) == s.charAt(i + z[i])) )
                    z[i]++;

                rightZBlock.move(i, z[i]);
            }
        }

        return z;
    }

    private static int[] zFunction_plusPlus(String s) {
        int N   = s.length();
        int[] z = new int[N];

        if (N == 0) return z;
        z[0] = N;

        ZBlock rightZBlock = new ZBlock(1, 0);

        for (int i = 1; i < N; i++) {
            int k  = i - rightZBlock.pos; // sibling for s[i]

            if (i < rightZBlock.end())
                z[i] = min(rightZBlock.end() - i + 1, z[k]);

            while ( (z[i] < N-i) && (s.charAt(z[i]) == s.charAt(i + z[i])) )
                z[i]++;

            if (i + z[i] - 1 > rightZBlock.end())
                rightZBlock.move(i, z[i]);
        }

        return z;
    }

    public static int[] zFunction(String s) {
        return zFunction_plusPlus(s);
    }

    public static void main(String[] args) {
        String s0 = "abcabcd";
        int[] p0  = {7, 0, 0, 3, 0, 0, 0};

        String s1 = "abcdabcabcdabcdab";
        int[] p1  = {17, 0, 0, 0, 3, 0, 0, 7, 0, 0, 0, 6, 0, 0, 0, 2, 0};

        String s2 = "abcdabca";
        int[] p2  = {8, 0, 0, 0, 3, 0, 0, 1};

        String s3 = "aaaaaaa";
        int[] p3  = {7, 6, 5, 4, 3, 2, 1};

        String s4 = "a";
        int[] p4  = {1};

        String s5 = "";
        int[] p5  = {};

        String s6 = "abacabacada";
        int[] p6  = {11, 0, 1, 0, 5, 0, 1, 0, 1, 0, 1};

        String s7 = "ababcaba";
        int[] p7  = {8, 0, 2, 0, 0, 3, 0, 1};

        String s8 = "abcdabscabcdabia";
        int[] p8  = {16, 0, 0, 0, 2, 0, 0, 0, 6, 0, 0, 0, 2, 0, 0, 1};

        assert Arrays.equals(zFunction_brute(s0), p0);
        assert Arrays.equals(zFunction_brute(s1), p1);
        assert Arrays.equals(zFunction_brute(s2), p2);
        assert Arrays.equals(zFunction_brute(s3), p3);
        assert Arrays.equals(zFunction_brute(s4), p4);
        assert Arrays.equals(zFunction_brute(s5), p5);
        assert Arrays.equals(zFunction_brute(s6), p6);
        assert Arrays.equals(zFunction_brute(s7), p7);
        assert Arrays.equals(zFunction_brute(s8), p8);

        assert Arrays.equals(zFunction_plus(s0), p0);
        assert Arrays.equals(zFunction_plus(s1), p1);
        assert Arrays.equals(zFunction_plus(s2), p2);
        assert Arrays.equals(zFunction_plus(s3), p3);
        assert Arrays.equals(zFunction_plus(s4), p4);
        assert Arrays.equals(zFunction_plus(s5), p5);
        assert Arrays.equals(zFunction_plus(s6), p6);
        assert Arrays.equals(zFunction_plus(s7), p7);
        assert Arrays.equals(zFunction_plus(s8), p8);

        assert Arrays.equals(zFunction_plusPlus(s0), p0);
        assert Arrays.equals(zFunction_plusPlus(s1), p1);
        assert Arrays.equals(zFunction_plusPlus(s2), p2);
        assert Arrays.equals(zFunction_plusPlus(s3), p3);
        assert Arrays.equals(zFunction_plusPlus(s4), p4);
        assert Arrays.equals(zFunction_plusPlus(s5), p5);
        assert Arrays.equals(zFunction_plusPlus(s6), p6);
        assert Arrays.equals(zFunction_plusPlus(s7), p7);
        assert Arrays.equals(zFunction_plusPlus(s8), p8);

        assert Arrays.equals(zFunction(s0), p0);
        assert Arrays.equals(zFunction(s1), p1);
        assert Arrays.equals(zFunction(s2), p2);
        assert Arrays.equals(zFunction(s3), p3);
        assert Arrays.equals(zFunction(s4), p4);
        assert Arrays.equals(zFunction(s5), p5);
        assert Arrays.equals(zFunction(s6), p6);
        assert Arrays.equals(zFunction(s7), p7);
        assert Arrays.equals(zFunction(s8), p8);
    }

}
