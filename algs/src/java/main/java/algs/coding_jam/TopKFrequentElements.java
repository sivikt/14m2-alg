package algs.coding_jam;

// Given a non-empty array of integers, return the k most frequent elements.

// Example 1:

// Input: nums = [1,1,1,2,2,3], k = 2
// Output: [1,2]
// Example 2:

// Input: nums = [1], k = 1
// Output: [1]
// Note:

// You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
// Your algorithm's time complexity must be better than O(n log n), where n is the array's size.

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;

class TopKFrequentElements {
    public List<Integer> topKFrequent1(int[] nums, int k) {
        Map<Integer, Integer> rates = new HashMap<Integer, Integer>();
        
        for (int i : nums)
        	rates.put(i, rates.getOrDefault(i, 0) + 1);
        
        Queue<Integer> orderedFrequency = new PriorityQueue<Integer>(nums.length, (a, b) -> {
            return rates.get(b) - rates.get(a);
        });
        
        orderedFrequency.addAll(rates.keySet());
            
        // return Stream.generate(orderedFrequency::poll)
        //         .limit(k)
        //         .collect(Collectors.toList());
        List<Integer> res = new LinkedList<>();
        while (k-- > 0)
            res.add(orderedFrequency.poll());
        
        return res;
    }


    public List<Integer> topKFrequent2(int[] nums, int k) {
         
         HashMap<Integer,Integer> countmap = new HashMap<>();
         int maxcount = 0;
         
         for(int i = 0;i < nums.length;i++){
             countmap.put(nums[i],countmap.getOrDefault(nums[i],0) + 1);
             maxcount = Math.max(maxcount,countmap.get(nums[i]));
         }
         
         List[] buckets = new ArrayList[maxcount + 1];
         
         for(int key:countmap.keySet()){
             int i = countmap.get(key);
             
             if(buckets[i] == null){
                 buckets[i] = new ArrayList<>();
             }
             buckets[i].add(key);
         }
         
         List<Integer> res = new ArrayList<>();
         for(int i = maxcount;i >= 0;i--){
             if(buckets[i] != null){
                 res.addAll(buckets[i]);
             }
             if(res.size() == k){
                 break;
             }
         }
         return res;
    }


    /*
      the slowest
    */
    public List<Integer> topKFrequent3(int[] nums, int k) {
        PriorityQueue<Map.Entry<Integer, Long>> maxHeap = new PriorityQueue<>(Map.Entry.comparingByValue(Collections.reverseOrder()));
        Arrays.stream(nums).boxed().collect(groupingBy(Function.identity(), Collectors.counting())).entrySet().forEach(maxHeap::offer);
        
        return IntStream.range(0, k).mapToObj(x -> maxHeap.poll().getKey()).collect(Collectors.toList());
    }
}