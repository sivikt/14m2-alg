package algs.coding_jam;
import java.util.*;


class Dominator {
    public void merge_sort(int[] A) {
        int[] B = new int[A.length];
        System.arraycopy(A, 0, B, 0, A.length);
        merge_descent(B, 0, A.length, A);
    }
    
    public void merge_descent(int[] src, int lo, int hi, int[] dest) {
        if ((hi - lo) < 2) 
            return;
            
        int m = (hi+lo)/2;
        merge_descent(dest, lo, m, src);
        merge_descent(dest, m, hi, src);
        
        merge(src, lo, m, hi, dest);
        //System.out.println(Arrays.toString(dest));
    }
    
    public void merge(int[] src, int lo, int mid, int hi, int[] dest) {
        //System.out.println("merge " + " lo=" + lo + " mi=" + mid + " hi=" + hi);
            
        int i = lo;
        int j = mid;
        int k = lo;
        
        while ((i < mid) && (j < hi)) {
            if (src[i] > src[j]) {
                dest[k++] = src[j];
                j++;
            }    
            else if (src[i] <= src[j]) {
                dest[k++] = src[i];
                i++;
            }
        } 
        
        while (i < mid)
            dest[k++] = src[i++];
    
        while (j < hi)
            dest[k++] = src[j++];
    }

    public int solution(int[] A) {
        if (A.length == 0)
            return -1;
        if (A.length == 1)
            return 0;
        
        int leaders = 0;
        int leader = 0;

        for (int i = 0; i < A.length; i++) {
            if (leaders == 0) {
                leaders = 1;
                leader = A[i];     
            }
            else if (A[i] != leader)
                leaders--;
            else
                leaders++;
        }
        
        int result = -1;
        
        if (leaders > 0) {
            int count = 0;
            int l = 0;
            
            for (int i = 0; i < A.length; i++) {
            
                if (A[i] == leader) {
                    count++;
                    l = i;
                }
            }
            
            if (count > (A.length/2)) {
                result = l;
            }
        }
        
        return result;
    }
}
