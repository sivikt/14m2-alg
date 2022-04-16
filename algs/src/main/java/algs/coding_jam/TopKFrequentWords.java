package algs.coding_jam;

// Given a non-empty list of words, return the k most frequent elements.

// Your answer should be sorted by frequency from highest to lowest. If two words have the same frequency, then the word with the lower alphabetical order comes first.

// Example 1:
// Input: ["i", "love", "leetcode", "i", "love", "coding"], k = 2
// Output: ["i", "love"]
// Explanation: "i" and "love" are the two most frequent words.
//     Note that "i" comes before "love" due to a lower alphabetical order.
// Example 2:
// Input: ["the", "day", "is", "sunny", "the", "the", "the", "sunny", "is", "is"], k = 4
// Output: ["the", "is", "sunny", "day"]
// Explanation: "the", "is", "sunny" and "day" are the four most frequent words,
//     with the number of occurrence being 4, 3, 2 and 1 respectively.
// Note:
// You may assume k is always valid, 1 ≤ k ≤ number of unique elements.
// Input words contain only lowercase letters.
// Follow up:
// Try to solve it in O(n log k) time and O(n) extra space.

import java.util.*;
import java.util.stream.Collectors;


class TopKFrequentWords {
    public List<String> topKFrequent(String[] words, int k) {
        Map<String, Integer> raiting = new HashMap<>();
                
        for (String w : words) {
            if (raiting.containsKey(w))
                raiting.put(w, raiting.get(w)+1);
            else
                raiting.put(w, 1);
        }
        
        // or use PriorityQueue to spend k*log(len(words)) time
        return raiting.entrySet().stream().sorted((e1, e2) -> {
                    Integer r1 = e1.getValue();
                    Integer r2 = e2.getValue();

                    if (r1.equals(r2))
                        return e1.getKey().compareTo(e2.getKey());
                    else
                        return r2.compareTo(r1);
              })
              .limit(k)
              .map(e -> e.getKey())
              .collect(Collectors.toList());   
    }
}
