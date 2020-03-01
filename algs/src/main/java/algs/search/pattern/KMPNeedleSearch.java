package algs.search.pattern;

import java.util.Arrays;

import static algs.search.pattern.PrefixFunction.*;
import static java.lang.System.*;

/**
 * """Алгоритм Кнута — Морриса — Пратта (КМП-алгоритм) — эффективный алгоритм, осуществляющий поиск подстроки в строке.
 *    Время работы алгоритма линейно зависит от объёма входных данных, то есть разработать асимптотически более
 *    эффективный алгоритм невозможно.
 *
 *    Даны образец (строка) P и строка T. Требуется определить индекс, начиная с которого образец P содержится в строке
 *    T. Если P не содержится в T — вернуть индекс, который не может быть интерпретирован как позиция в строке (например,
 *    отрицательное число). При необходимости отслеживать каждое вхождение образца в текст имеет смысл завести
 *    дополнительную функцию, вызываемую при каждом обнаружении образца.
 *
 *    Алгоритм сдвигает образец влево при поиске вхождений слева-направо. При сдвиге вполне можно ожидать, что префикс
 *    (начальные символы) образца S сойдется с каким-нибудь суффиксом (конечные символы) текста T'. Длина наиболее
 *    длинного префикса, являющегося одновременно суффиксом, есть префикс-функция от строки S для индекса j.
 *
 *    Пусть p[j] — префикс-функция от строки S[0, m - 1] для индекса j. Тогда после сдвига мы можем возобновить сравнения
 *    с места T[i + j] и S[p[j]] без потери возможного местонахождения образца.
 *
 * """ wikipedia
 *
 * @author Serj Sintsov
 */
public class KMPNeedleSearch {

    /**
     * O(|haystack|·|needle|)
     */
    public static int search(String haystack, String needle) {
        if (haystack == null)
            throw new IllegalArgumentException("haystack is null");

        if (needle == null)
            throw new IllegalArgumentException("needle is null");

        int N = haystack.length();
        int M = needle.length();

        if (M > N || M == 0 || N == 0) return -1;

        int[] p = prefixFunction(needle);
        int i = 0,
            j = 0;

        while (i < N && j < M) {
            while (j > 0 && (haystack.charAt(i) != needle.charAt(j)))
                j = p[j-1];

            if (haystack.charAt(i) == needle.charAt(j))
                j += 1;

            i += 1;
        }

        if (j == M) return i - M;
        else        return -1;
    }

    public static int[] searchAll(String haystack, String needle) {
        if (haystack == null)
            throw new IllegalArgumentException("haystack is null");

        if (needle == null)
            throw new IllegalArgumentException("needle is null");

        int N = haystack.length();
        int M = needle.length();

        if (M > N || M == 0 || N == 0) return new int[0];

        int entriesSz = 0;
        int[] entries = new int[N/M];
        int[] p = prefixFunction(needle);
        int i = 0,
            j = 0;

        while (i < N) {
            while (j > 0 && (haystack.charAt(i) != needle.charAt(j)))
                j = p[j-1];

            if (haystack.charAt(i) == needle.charAt(j))
                j += 1;

            i += 1;

            if (j == M) {
                entries[entriesSz] = i - M;
                entriesSz += 1;
                j = 0;
            }
        }

        if (entriesSz < entries.length) {
            int[] trimEntries = new int[entriesSz];
            arraycopy(entries, 0, trimEntries, 0, entriesSz);
            return trimEntries;
        }
        else return entries;
    }

    public static void main(String args[]) {
        assert search("CAAAB", "A") == 1;
        assert search("CAAAB", "B") == 4;
        assert search("CAAAB", "C") == 0;
        assert search("CAAAB", "CAAA") == 0;
        assert search("CAAAB", "AAA") == 1;
        assert search("CAAAB", "AAAB") == 1;
        assert search("CAAAB", "AAABB") == -1;
        assert search("CAAAB", "") == -1;
        assert search("", "AAABB") == -1;
        assert search("AAAB", "AAABB") == -1;
        assert search("missisipi", "mi") == 0;
        assert search("missisipi", "isi") == 4;
        assert search("missisipi", "sisi") == 3;
        assert search("missisipi", "pi") == 7;
        assert search("missisipi", "i") == 1;
        assert search("sssssss", "s") == 0;
        assert search("Hoola-Hoola girls like Hooligans", "Hooligans") == 23;

        assert Arrays.equals(searchAll("CAAAB", "A"), new int[] {1,2,3});
        assert Arrays.equals(searchAll("CAAAB", "B"), new int[] {4});
        assert Arrays.equals(searchAll("CAAAB", "C"), new int[] {0});
        assert Arrays.equals(searchAll("CAAAB", "CAAA"), new int[] {0});
        assert Arrays.equals(searchAll("CAAAB", "AAA"), new int[] {1});
        assert Arrays.equals(searchAll("CAAAB", "AAAB"), new int[] {1});
        assert Arrays.equals(searchAll("CAAAB", "AAABB"), new int[] {});
        assert Arrays.equals(searchAll("CAAAB", ""), new int[] {});
        assert Arrays.equals(searchAll("", "AAABB"), new int[] {});
        assert Arrays.equals(searchAll("AAAB", "AAABB"), new int[] {});
        assert Arrays.equals(searchAll("missisipi", "mi"), new int[] {0});
        assert Arrays.equals(searchAll("missisipi", "isi"), new int[] {4});
        assert Arrays.equals(searchAll("missisipi", "sisi"), new int[] {3});
        assert Arrays.equals(searchAll("missisipi", "pi"), new int[] {7});
        assert Arrays.equals(searchAll("missisipi", "i"), new int[] {1,4,6,8});
        assert Arrays.equals(searchAll("ssssss", "s"), new int[] {0,1,2,3,4,5});
        assert Arrays.equals(searchAll("Hoola-Hoola girls like Hooligans", "Hooligans"), new int[] {23});
    }

}
