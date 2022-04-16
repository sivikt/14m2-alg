package algs.coding_jam;

class GenomicRangeQuery {
    private int toImpact(String S, int i) {
        switch (S.charAt(i)) {
            case 'A': return 1;
            case 'C': return 2;
            case 'G': return 3;
            case 'T': return 4;
            default: throw new IllegalArgumentException("wrong nucleotied");
        }    
    }
    
    public int[] solution(String S, int[] P, int[] Q) 
    {
        int[] impacts = new int[P.length];
        int[][] pref_sums = new int[4][S.length()];
        
        int impact = this.toImpact(S, 0);
        pref_sums[impact-1][0] = 1;
        
        for (int i = 1; i < S.length(); i++) 
        {
            impact = this.toImpact(S, i);
            pref_sums[impact-1][i] = pref_sums[impact-1][i-1] + 1;
            
            if (impact != 1)
                pref_sums[0][i] = pref_sums[0][i-1];
                
            if (impact != 2)
                pref_sums[1][i] = pref_sums[1][i-1];
                
            if (impact != 3)
                pref_sums[2][i] = pref_sums[2][i-1];
                
            if (impact != 4)
                pref_sums[3][i] = pref_sums[3][i-1];
        }
        
        for (int i = 0; i < P.length; i++) 
        {
            for (int j = 0; j < 4; j++) 
            {
                int sum = pref_sums[j][Q[i]] - ((P[i]>0) ? pref_sums[j][P[i]-1] : 0);
                
                if (sum > 0) {
                    impacts[i] = j+1;
                    break;
                }
            }
        }
        
        return impacts;
    }
}