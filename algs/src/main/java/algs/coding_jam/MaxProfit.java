package algs.coding_jam;

class MaxProfit {
    public int solution(int[] A) {
        if (A.length < 1) 
            return 0;
        
        int[] maximums = new int[A.length];
        maximums[A.length-1] = A[A.length-1];
        
        for (int i = A.length-2; i >= 0; i--)
            maximums[i] = Math.max(A[i], maximums[i + 1]);
        
        int max_profit = Integer.MIN_VALUE;
        
        for (int i = 0; i < A.length-1; i++) {
            int profit = maximums[i+1] - A[i];
            if (max_profit < profit)
                max_profit = profit;
        }
        
        return Math.max(0, max_profit);
    }

    public int solution2(int[] A) {
        int max_profit = 0;
        int min_day = 200000;
         
        for (int day : A) {
            min_day = Math.min(min_day, day);
            max_profit = Math.max(max_profit, A[day-min_day]); // check?
        }
         
        return max_profit;
    }
}
