package algs.search.pattern;

/**
 * Условие, при котором паттерн найден (i - индекс первого вхождения):
 * R(i) = ∀j: 0 <= j < M : p(j) = s(i+j) = ~(∃j: 0 <= j < M : p(j) != s(i+j))
 *
 * Условие первого вхождения образца:
 * Q(i) = ∀k: 0 <= k < i : ~R(k)
 *
 * Этот алгоритм реализует "наивный" способ проверки вхождения образца (слова) в
 * текст. Поиск происходит слева-направо. Двигаются два индексных указателя i -
 * для текста и j - для образца. При совпадении символов p(0) = s(i) последовательно
 * проверяется равенство символов p(j) = s(i+j), где 1<=j<M, 0<=i<N-M. Образца в
 * тексте нет, если i>N-M. Образец в тексте есть, если j>=M.
 *
 * Показано, что примитивный алгоритм отрабатывает в среднем 2h сравнений.
 *
 * @author Serj Sintsov
 */
public class BruteForceNeedleSearch {

    public static int search(String haystack, String needle) {
        if (haystack == null)
            throw new IllegalArgumentException("haystack is null");

        if (needle == null)
            throw new IllegalArgumentException("needle is null");

        int N = haystack.length();
        int M = needle.length();

        if (M > N || M == 0 || N == 0) return -1;

        int i = 0,
            j = 0;

        while (true) {
            if ( (i <= N-M) && (j < M) && (needle.charAt(j) == haystack.charAt(i+j)) ) {
                j += 1;
            }
            else if ( (i <= N-M) && (j < M) ) {
                i += 1;
                j = 0;
            }
            else break;
        }

        if (j == M) return i;
        else        return -1;
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
    }

}
