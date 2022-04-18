// Given an array of integers nums, sort the array in ascending order.

 
// Example 1:
// Input: nums = [5,2,3,1]
// Output: [1,2,3,5]

// Example 2:
// Input: nums = [5,1,1,2,0,0]
// Output: [0,0,1,1,2,5]
 
// Constraints:
// 1 <= nums.length <= 50000
// -50000 <= nums[i] <= 50000


class Solution {
    public void insertionSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int j = i;
            
            while (j > 0) {
                if (nums[j] < nums[j-1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j-1];
                    nums[j-1] = tmp;
                    j--;
                }
                else break;
            }
        }
    }
 

////


    public void mergeSorBottomUp(int[] nums) {
        int N = nums.length;        
        int[] tmp = new int[N];
            
        for (int h = 1; h < N; h <<= 1) 
        {
            for (int t = 0; t < N; t += 2*h) 
            {
                int i = t;
                int j = t + h;
                // merge two runs i+h and j+h
                int m = Math.min(N, j);
                int n = 2*h - Math.max(0, j+h-N);
                int r = t + n;
                int k = 0;
                
                while (k < n)
                {
                    if ((i < m) && (j >= r || nums[i] < nums[j])) {
                        tmp[k++] = nums[i];
                        i++;
                    }
                    else {
                        tmp[k++] = nums[j];
                        j++;
                    }
                }
                
                System.arraycopy(tmp, 0, nums, t, n);
            }
        }
    }


////


    public void insertionSort(int[] nums, int from, int to) {
        for (int i = from+1; i < to; i++) {
            int j = i;
            
            while (j > from) {
                if (nums[j] < nums[j-1]) {
                    int tmp = nums[j];
                    nums[j] = nums[j-1];
                    nums[j-1] = tmp;
                    j--;
                }
                else break;
            }
        }
    }
    
    public void mergeSorBottomUp_insertionSort(int[] nums) {
        int N = nums.length;        
        int threshold = 4;
        
        if (N < threshold) {
            insertionSort(nums, 0, N);
            return;
        }          
        
        int[] tmp = new int[N];
        
        for (int i = 0; i < N; i += threshold)
            insertionSort(nums, i, Math.min(i+threshold, N));
                
        for (int h = threshold; h < N; h <<= 1) 
        {
            for (int t = 0; t < N; t += 2*h) 
            {
                int i = t;
                int j = t + h;
                // merge two runs i+h and j+h
                int m = Math.min(N, j);
                int n = 2*h - Math.max(0, j+h-N);
                int r = t + n;
                int k = 0;
                
                while (k < n)
                {
                    if ((i < m) && (j >= r || nums[i] < nums[j])) {
                        tmp[k++] = nums[i];
                        i++;
                    }
                    else {
                        tmp[k++] = nums[j];
                        j++;
                    }
                }
                
                System.arraycopy(tmp, 0, nums, t, n);
            }
        }
    }


    public List<Integer> sortArray(int[] nums) {
        insertionSort(nums);
        return Arrays.stream(nums).boxed().collect(Collectors.toList());
    }
}