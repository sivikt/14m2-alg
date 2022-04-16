package algs.coding_jam;

class Fish {
    public int solution(int[] A, int[] B) {
        int N = A.length;
        int[] stack = new int[N];
        int head = 0;
        
        for (int i = 0; i < N; i++) {
            stack[head] = i;
            head++;

            while ((head > 1) && 
                   (B[stack[head-1]] == 0) &&
                   B[stack[head-2]] == 1) 
           {
                if (A[stack[head-1]] > A[stack[head-2]]) {
                    stack[head-2] = stack[head-1];
                }
                
                head--;
            }
        }
        
        return head;
    }
}
