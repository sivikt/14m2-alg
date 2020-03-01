package algs.search.pattern;

/**
 * """
 *
 * """ wikipedia
 * todo implement
 * @author Serj Sintsov
 */
public class BMNeedleSearch {

    public static int search(String haystack, String needle) {
        return -1;
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
    }

}
