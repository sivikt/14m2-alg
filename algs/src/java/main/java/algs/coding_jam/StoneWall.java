package algs.coding_jam;

class StoneWall {
    public int solution(int[] H) {
        int N = H.length;
        int[] stack = new int[N+1];
        int shead = 0;

        stack[shead] = 0;
        shead++;

        int result = 0;
        
        for (int i = 0; i <= N; i++) {
            int h = 0;
            
            if (i < N) {
                h = H[i];    
            }
            
            if (stack[shead-1] == h) {
                // do nothing
            }  
            else if (stack[shead-1] < h) {
                stack[shead] = h;
                shead++;
            }
            else {
                while ((shead > 1) && (stack[shead-1] > h)) {
                    result++;
                    shead--;
                }
                
                if (stack[shead-1] != h) {
                    stack[shead] = h;
                    shead++;
                }
            }
            
            //System.out.println(Arrays.toString(stack) + " " + shead + " " + result);
        }
        
        return result;
    }
}
