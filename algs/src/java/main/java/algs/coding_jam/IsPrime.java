package algs.coding_jam;

/**
 * todo implement
 * @author Serj Sintsov
 */
public class IsPrime {

    public static boolean bruteIsPrime(int p) {
        if (p <= 1)
            return false;

        for (int i = 2; i < p/2; i++)
            if (p % i == 0)
                return false;

        return true;
    }

    public static void main(String[] args) {
        int n = 16;
        int i = 4;

        while (i < 32) {
            int v = n-1;
            int w = n+1;

            for (; v > 1; v--)
                if (bruteIsPrime(v))
                    break;

            for (; w < 2*n; w++)
                if (bruteIsPrime(w))
                    break;

            System.out.println("power=" + i + "; " + v + " " + w);

            i += 1;
            n *= 2;
        }
    }

}
