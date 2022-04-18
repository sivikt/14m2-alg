package algs.coding_jam;

// Given two arrays, write a function to compute their intersection.

// Example 1:

// Input: nums1 = [1,2,2,1], nums2 = [2,2]
// Output: [2]
// Example 2:

// Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
// Output: [9,4]
// Note:

// Each element in the result must be unique.
// The result can be in any order.


import java.util.Arrays;

class IntersectionOfTwoArrays {
    public int[] intersection(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        
        int[] res = nums1;
        
        if (nums1.length > nums2.length)
        {
            nums1 = nums2;
            nums2 = res;
        }
        
        res = new int[nums1.length];
        int i = 0;
        int j = 0;
        int k = 0;
        int l = -1;
        
        while (i < nums1.length && j < nums2.length)
        {
            if (nums1[i] < nums2[j])
                i++;
            else if (nums1[i] > nums2[j])
                j++;
            else
            {
                if (l == -1 || nums1[i] != nums1[l])
                {
                    l = i;
                    res[k] = nums1[i];    
                    k++;
                }
                
                i++;
                j++;
            }        
        }
        
        if (k < res.length)
        {
            int[] tmp = new int[k];
            System.arraycopy(res, 0, tmp, 0, k);
            res= tmp;
        }
        
        return res;   
    }
}