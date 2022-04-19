package algs.coding_jam;

/*
There are two sorted arrays nums1 and nums2 of size m and n respectively.

Find the median of the two sorted arrays.
The overall run time complexity should be O(log (m+n)).

You may assume nums1 and nums2 cannot be both empty.

Example 1:
nums1 = [1, 3]
nums2 = [2]

The median is 2.0

Example 2:
nums1 = [1, 2]
nums2 = [3, 4]

The median is (2 + 3)/2 = 2.5
*/
class MedianOfTwoSortedArrays {
    public double findMedian(int[] nums, int l, int r) {
        int m = (r+l)/2;
        
        if ((r-l) % 2 == 0)
            return nums[m];
        else
            return (nums[m] + nums[m+1])/2.0;
    }

    public double findMedianSortedArrays(int[] nums1, int l1, int r1,
                                         int[] nums2, int l2, int r2)
    {
        System.out.println("l1="+l1 + " r1="+r1);
        double m1 = findMedian(nums1, l1, r1);
        System.out.println("m1="+m1);
        System.out.println("l2="+l2 + " r2="+r2);
        double m2 = findMedian(nums2, l2, r2);
        System.out.println("m2="+m2);
        
        if (m1 == m2)
            return m1;
        else if (m1 < m2)
            return findMedianSortedArrays(nums1, (l1+r1+2)/2, r1, nums2, 0, (l2+r2+2)/2);
        else
            return findMedianSortedArrays(nums1, 0, (l1+r1+2)/2, nums2, (l2+r2+2)/2, r2);
        
    }
    
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        
        if (m > n) { // to ensure m<=n
            int[] temp = nums1; 
            nums1 = nums2;
            nums2 = temp;
            int tmp = m; 
            m = n; 
            n = tmp;
        }
        
        if (m == 0)
            return findMedian(nums2, 0, n-1);
        else if (n == 0)
            return findMedian(nums1, 0, m-1);
        else if (nums1[m-1] <= nums2[0])
            return (m == n) ? (nums1[m-1] + nums2[0])/2.0 : nums2[(n+m)/2 - m];
        else if (nums2[n-1] <= nums1[0])
            return (m == n) ? (nums2[n-1] + nums1[0])/2.0 : nums2[(n+m)/2];
        
        return findMedianSortedArrays(nums1, 0, m-1, nums2, 0, n-1);
    }
}