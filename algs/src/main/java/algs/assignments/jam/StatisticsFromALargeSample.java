// We sampled integers between 0 and 255, and stored the results in an array count:  
// count[k] is the number of integers we sampled equal to k.

// Return the minimum, maximum, mean, median, and mode of the sample respectively, 
// as an array of floating point numbers.  The mode is guaranteed to be unique.

// (Recall that the median of a sample is:

// The middle element, if the elements of the sample were sorted and the number of elements is odd;
// The average of the middle two elements, if the elements of the sample were sorted and the number of elements is even.)
 

// Example 1:
// Input: count = [0,1,3,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
// Output: [1.00000,3.00000,2.37500,2.50000,3.00000]

// Example 2:
// Input: count = [0,4,3,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
//                 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
// Output: [1.00000,4.00000,2.18182,2.00000,1.00000]
 

// Constraints:
// count.length == 256
// 1 <= sum(count) <= 10^9
// The mode of the sample that count represents is unique.
// Answers within 10^-5 of the true value will be accepted as correct.


class Solution {
    public double[] sampleStats(int[] count) {
        int min = -1;
        int max = 0;
        int mode = 0;
        double median = 0;
        double totalSum = 0;
        double totalNum = 0;
        
        for (int i = 0; i < count.length; i++)
        {
            if (count[i] == 0)
                continue;
            
            if (i > max)
                max = i;

            if (i < min || min < 0)
                min = i;

            if (count[mode] < count[i])
                mode = i;

            totalNum += count[i];
            totalSum += i*count[i];                
        }
        
        double halfNum = Math.floor(totalNum / 2);
        double currTotalNum = 0;
        int prev = 0;
        
        for (int i = 0; i < count.length; i++)
        {       
            if (count[i] == 0)
                continue;
            
            if (currTotalNum > halfNum)
            {
                median = prev;
                break;
            }

            if (currTotalNum == halfNum && (totalNum % 2 == 0)) {
                median = (i + prev)/2.0;
                break;
            }

            prev = i;
            currTotalNum += count[i];
        }
        
        double[] res = new double[5];
        res[0] = min;
        res[1] = max;
        res[2] = totalSum/totalNum;
        res[3] = median;
        res[4] = mode;
        
        return res;
    }
}
