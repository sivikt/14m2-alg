package algs.search.pattern;

import java.util.Arrays;

/**
 * """Дана строка s[0...n-1]. Требуется вычислить для неё префикс-функцию, т.е. массив чисел p[0...n-1], где p[i]
 *    определяется следующим образом: это наибольшая длина наибольшего собственного суффикса подстроки s[0...i],
 *    совпадающего с её префиксом (собственный суффикс — суффикс не совпадающий со всей строкой). В частности,
 *    значение p[0] полагается равным нулю.
 *
 *    p[0] = 0
 *    p[i] =  max   {k: s[0..k-1] = s[i-k+1..i]}
 *           k=1..i
 *
 *    Например, для строки "abcabcd" префикс-функция равна: [0, 0, 0, 1, 2, 3, 0]
 *
 *    a  b  c  a  $  a  b  a  b  c  a  b  с  a  c  a  b
 *    0  0  0  1  0  1  2  1  2  3  4  2  3  4  0  1  2
 *
 * """ http://e-maxx.ru/algo/prefix_function
 *
 * @author Serj Sintsov
 */
public class PrefixFunction {

    /**
     * Реализация влоб по формуле O(n^3).
     */
    private static int[] prefixFunction_brute(String s) {
        int N = s.length();
        int[] p = new int[N];

        if (N == 0) return p;

        for (int i = 1; i < N; i++)
            for (int k = 1; k <= i; k++)
                if (s.substring(0, k).equals(s.substring(i - k + 1, i + 1)))
                    p[i] = k;

        return p;
    }

    /**
     * Значение p[i+1] не более чем на единицу превосходит значение p[i] для любого i. O(n^2)
     */
    private static int[] prefixFunction_plus(String s) {
        int N = s.length();
        int[] p = new int[N];

        if (N == 0) return p;

        for (int i = 1; i < N; i++) {
            int suffixLen = p[i - 1] + 1;
            String prefix = s.substring(0, suffixLen);
            String suffix = s.substring(i - suffixLen + 1, i + 1);

            if (prefix.equals(suffix)) {
                p[i] = p[i - 1] + 1;
                continue;
            }

            for (int k = 0; k < i / 2; k++) {
                if (s.charAt(0) == s.charAt(i - k)) {
                    if (s.substring(0, k + 1).equals(s.substring(i - k, i + 1)))
                        p[i] = k + 1;
                    break;
                }
            }
        }

        return p;
    }

    /**
     * Избавимся от явных сравнений подстрок. Для этого максимально используем информацию, вычисленную на предыдущих
     * шагах. Пусть мы вычислили значение префикс-функции p[i] для некоторого i. Теперь, если s[i+1] = s[p[i]],
     * то p[i+1] = p[i] + 1.
     */
    private static int[] prefixFunction_plusPlus(String s) {
        int N = s.length();
        int[] p = new int[N];

        if (N == 0) return p;

        for (int i = 1; i < N; i++) {
            int k = p[i-1];

            while (k > 0 && s.charAt(i) != s.charAt(k))
                k = p[k-1];

            if (s.charAt(i) == s.charAt(k))
                k += 1;

            p[i] = k;
        }

        return p;
    }

    /**
     * После i-го шага k является длиной наибольшего префикса
     *
     * """Повторение этих операций должно насторожить — казалось бы получается два вложенных цикла. Но это не так.
     *    Дело в том, что вложенный цикл длиной в k итераций уменьшает префикс-функцию в i+1-й позиции хотя бы на k-1,
     *    а для того, чтобы нарастить префикс-функцию до такого значения, нужно хотя бы k-1 раз успешно сопоставить
     *    буквы, обработав k-1 символов. То есть длина цикла соответствует промежутку между выполнением таких циклов и
     *    поэтому сложность алгоритма по прежнему линейна по длине обрабатываемой строки.
     * """ dosyas - http://habrahabr.ru/post/113266/
     */
    private static int[] prefixFunction_plusPlusPlus(String s) {
        int N = s.length();
        int[] p = new int[N];

        if (N == 0) return p;

        int k = 0;
        for (int i = 1; i < N; i++) {
            while (k > 0 && s.charAt(i) != s.charAt(k))
                k = p[k-1];

            if (s.charAt(i) == s.charAt(k))
                k += 1;

            p[i] = k;
        }

        return p;
    }

    public static int[] prefixFunction(String s) {
        return prefixFunction_plusPlusPlus(s);
    }

    public static void main(String[] args) {
        String s0 = "abcabcd";
        int[] p0  = {0, 0, 0, 1, 2, 3, 0};

        String s1 = "abcdabcabcdabcdab";
        int[] p1  = {0, 0, 0, 0, 1, 2, 3, 1, 2, 3, 4, 5, 6, 7, 4, 5, 6};

        String s2 = "abcdabca";
        int[] p2  = {0, 0, 0, 0, 1, 2, 3, 1};

        String s3 = "aaaaaaa";
        int[] p3  = {0, 1, 2, 3, 4, 5, 6};

        String s4 = "a";
        int[] p4  = {0};

        String s5 = "";
        int[] p5  = {};

        String s6 = "abacabacada";
        int[] p6  = {0, 0, 1, 0, 1, 2, 3, 4, 5, 0, 1};

        assert Arrays.equals(prefixFunction_brute(s0), p0);
        assert Arrays.equals(prefixFunction_brute(s1), p1);
        assert Arrays.equals(prefixFunction_brute(s2), p2);
        assert Arrays.equals(prefixFunction_brute(s3), p3);
        assert Arrays.equals(prefixFunction_brute(s4), p4);
        assert Arrays.equals(prefixFunction_brute(s5), p5);
        assert Arrays.equals(prefixFunction_brute(s6), p6);

        assert Arrays.equals(prefixFunction_plus(s0), p0);
        assert Arrays.equals(prefixFunction_plus(s1), p1);
        assert Arrays.equals(prefixFunction_plus(s2), p2);
        assert Arrays.equals(prefixFunction_plus(s3), p3);
        assert Arrays.equals(prefixFunction_plus(s4), p4);
        assert Arrays.equals(prefixFunction_plus(s5), p5);
        assert Arrays.equals(prefixFunction_plus(s6), p6);

        assert Arrays.equals(prefixFunction_plusPlus(s0), p0);
        assert Arrays.equals(prefixFunction_plusPlus(s1), p1);
        assert Arrays.equals(prefixFunction_plusPlus(s2), p2);
        assert Arrays.equals(prefixFunction_plusPlus(s3), p3);
        assert Arrays.equals(prefixFunction_plusPlus(s4), p4);
        assert Arrays.equals(prefixFunction_plusPlus(s5), p5);
        assert Arrays.equals(prefixFunction_plusPlus(s6), p6);

        assert Arrays.equals(prefixFunction_plusPlusPlus(s0), p0);
        assert Arrays.equals(prefixFunction_plusPlusPlus(s1), p1);
        assert Arrays.equals(prefixFunction_plusPlusPlus(s2), p2);
        assert Arrays.equals(prefixFunction_plusPlusPlus(s3), p3);
        assert Arrays.equals(prefixFunction_plusPlusPlus(s4), p4);
        assert Arrays.equals(prefixFunction_plusPlusPlus(s5), p5);
        assert Arrays.equals(prefixFunction_plusPlusPlus(s6), p6);

        assert Arrays.equals(prefixFunction(s0), p0);
        assert Arrays.equals(prefixFunction(s1), p1);
        assert Arrays.equals(prefixFunction(s2), p2);
        assert Arrays.equals(prefixFunction(s3), p3);
        assert Arrays.equals(prefixFunction(s4), p4);
        assert Arrays.equals(prefixFunction(s5), p5);
        assert Arrays.equals(prefixFunction(s6), p6);
    }
}
