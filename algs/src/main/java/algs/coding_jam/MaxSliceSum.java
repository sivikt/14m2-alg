package algs.coding_jam;

class MaxSliceSum {
    public int solution(int[] A) {
        boolean has_positives = false;
        int max = Integer.MIN_VALUE;

        for (int j : A) {
            if (j >= 0) {
                has_positives = true;
                break;
            }

            if (max < j)
                max = j;
        }
        
        if (has_positives) {
            int max_end = 0;
            max = 0;

            for (int j : A) {
                max_end = Math.max(0, max_end + j);
                max = Math.max(max_end, max);
            }
        }
        
        return max;
    }
}