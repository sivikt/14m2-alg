package algs.coding_jam;

class CountDiv {
    public int solution(int A, int B, int K) {
        if (A == B && B == 0)
            return 1;
        else if (A == B)
            return (A%K == 0) ? 1 : 0;
        else if (A == 0)
            return (B/K) - (A/K) + 1;
        else
            return (B/K) - ((A-1)/K);
    }
}