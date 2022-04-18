package algs.coding_jam;

// Winter is coming! Your first job during the contest is to design
// a standard heater with fixed warm radius to warm all the houses.

// Now, you are given positions of houses and heaters on a horizontal 
// line, find out minimum radius of heaters so that all houses could 
// be covered by those heaters.

// So, your input will be the positions of houses and heaters seperately, 
// and your expected output will be the minimum radius standard of heaters.

// Note:
// Numbers of houses and heaters you are given are non-negative and will not exceed 25000.
// Positions of houses and heaters you are given are non-negative and will not exceed 10^9.
// As long as a house is in the heaters' warm radius range, it can be warmed.
// All the heaters follow your radius standard and the warm radius will the same.
 

// Example 1:
// Input: [1,2,3],[2]
// Output: 1
// Explanation: The only heater was placed in the position 2, and if we use the radius 
//              1 standard, then all the houses can be warmed.
 

// Example 2:
// Input: [1,2,3,4],[1,4]
// Output: 1
// Explanation: The two heater was placed in the position 1 and 4. We need to use 
//              radius 1 standard, then all the houses can be warmed.


import java.util.Arrays;
import java.util.TreeSet;

class Heaters {
    public int findRadius(int[] houses, int[] heaters) {
        Arrays.sort(heaters);
            
        int radius = -1;
        
        for (int i = 0; i < houses.length; i++) 
        {
            int e = houses[i];
            int p = Arrays.binarySearch(heaters, e);
            
            if (p < 0)
            {
                p = -p - 1;
                
                if (p == heaters.length)
                    radius = Math.max(e-heaters[heaters.length-1], radius);
                else if (p == 0)
                    radius = Math.max(heaters[p]-e, radius);
                else
                {
                    int r = Math.min(heaters[p]-e, e-heaters[p-1]);
                    radius = Math.max(r, radius);
                }
            }
        }
        
        
        return (radius < 0) ? 0 : radius;
    }

////

    /* two pointer */
    public int findRadius_two_pointer(int[] houses, int[] heaters) {
        Arrays.sort(houses);
        Arrays.sort(heaters);

        int max=0;

        for(int i = 0; i < houses.length; i++)
        {
            int index = Arrays.binarySearch(heaters,houses[i]);
            
            if (index < 0) 
            {
                index = -(index+1);
                if(index == 0) 
                    max = Math.max(max,heaters[0]-houses[i]);
                else 
                {
                    if (index == heaters.length) 
                        max = Math.max(max, houses[i]-heaters[heaters.length-1]);
                    else 
                        max = Math.max(max, Math.min(houses[i]-heaters[index-1], heaters[index]-houses[i]));
                }
            }
        }

        return max;
    }


////

    /* slowest */
    public int findRadius_slowest(int[] houses, int[] heaters) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        
        for (int h : heaters)
            treeSet.add(h);
        
        int max=0;

        for (int h: houses) {
            Integer l = treeSet.floor(h); 
            Integer r = treeSet.higher(h);

            max = Math.max(
                max, 
                Math.min((l == null) ? Integer.MAX_VALUE : h-l, (r == null) ? Integer.MAX_VALUE : r-h)
            );
        }

        return max;
    }


////


    /* fastes */
    public int findRadius_fastes(int[] houses, int[] heaters) {
        
        Arrays.sort(houses);
        Arrays.sort(heaters);
                
        int index = 0;
        int radius = 0;
        int left = -heaters[index];
        int right = heaters[index];   
                
        for (int house : houses) 
        {
            while (house > right) 
            {
                left = right;
                right = (index < heaters.length - 1) ? heaters[++index] : Integer.MAX_VALUE;
            }
                        
            if ((left < house - radius) && (house + radius < right)) 
            {
                if (left < 0) 
                    radius = Math.abs(house - right);
                else if (right == Integer.MAX_VALUE)
                    radius = Math.abs(house - left);
                else 
                    radius = Math.min(Math.abs(house - right), Math.abs(house - left));
            }
        }   
                
        return radius;
    }
    
}