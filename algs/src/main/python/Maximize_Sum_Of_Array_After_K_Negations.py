# Given an array A of integers, we must modify the array in the following way: we choose an i and replace A[i] with -A[i], and we repeat this process K times in total.  (We may choose the same index i multiple times.)

# Return the largest possible sum of the array after modifying it in this way.

 

# Example 1:

# Input: A = [4,2,3], K = 1
# Output: 5
# Explanation: Choose indices (1,) and A becomes [4,-2,3].
# Example 2:

# Input: A = [3,-1,0,2], K = 3
# Output: 6
# Explanation: Choose indices (1, 2, 2) and A becomes [3,1,0,2].
# Example 3:

# Input: A = [2,-3,-1,5,-4], K = 2
# Output: 13
# Explanation: Choose indices (1, 4) and A becomes [2,3,-1,5,4].
 

# Note:

# 1 <= A.length <= 10000
# 1 <= K <= 10000
# -100 <= A[i] <= 100

class Solution(object):
    def largestSumAfterKNegations(self, A, K):
        """
        :type A: List[int]
        :type K: int
        :rtype: int
        """        
        if len(A) < 2:
            return A[0] if K%2 == 0 else -A[0]
        
        A.sort()
        
        summ = 0
        
        if A[0] < 0 and A[-1] < 0:
            for a in A:
                if K > 0:
                    summ += -a
                    K -= 1
                else:
                    summ += a
            
            if K > 0:
                if K%2 == 1:
                    summ += -A[-1]
                else:
                    summ += -A[0]
        elif A[0] >= 0 and A[-1] >= 0:               
            for a in A:
                summ += a
                
            if K > 0:
                if K%2 == 1:
                    summ += -2*A[0]
        else:
            i = 0
            while i < len(A):
                if A[i+1] >= 0:
                    break
                    
                if K > 0:
                    summ += -A[i]
                    K -= 1
                else:
                    summ += A[i]
                
                i += 1
                
            j = i+2
            while j < len(A):
                summ += A[j]
                j += 1
                    
            if K == 0:
                summ += A[i]
                summ += A[i+1]
            elif K%2 == 1:
                summ += -A[i]
                summ += A[i+1]
            else:
                if -A[i] > A[i+1]:
                    summ += -A[i]
                    summ += -A[i+1]
                else:
                    summ += A[i]
                    summ += A[i+1]
            
        return summ