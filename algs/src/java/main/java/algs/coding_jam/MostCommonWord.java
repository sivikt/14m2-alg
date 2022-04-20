package algs.coding_jam;

/*
Given a paragraph and a list of banned words, return the most frequent
word that is not in the list of banned words.  It is guaranteed there
is at least one word that isn't banned, and that the answer is unique.

Words in the list of banned words are given in lowercase, and free of
punctuation.  Words in the paragraph are not case sensitive.
The answer is in lowercase.



Example:

Input:
paragraph = "Bob hit a ball, the hit BALL flew far after it was hit."
banned = ["hit"]
Output: "ball"
Explanation:
"hit" occurs 3 times, but it is a banned word.
"ball" occurs twice (and no other word does), so it is the most frequent non-banned word in the paragraph.
Note that words in the paragraph are not case sensitive,
that punctuation is ignored (even if adjacent to words, such as "ball,"),
and that "hit" isn't the answer even though it occurs more because it is banned.


Note:

1 <= paragraph.length <= 1000.
0 <= banned.length <= 100.
1 <= banned[i].length <= 10.
The answer is unique, and written in lowercase (even if its occurrences in paragraph
may have uppercase symbols, and even if it is a proper noun.)
paragraph only consists of letters, spaces, or the punctuation symbols !?',;.
There are no hyphens or hyphenated words.
Words only consist of letters, never apostrophes or other punctuation symbols.
*/

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class MostCommonWord {
    private String leaderK = "";
    private int leaderV = -1;

    public String mostCommonWord(String paragraph, String[] banned) {
        Set<String> bannedSet = Stream.of(banned).collect(Collectors.toSet());
        String[] words = paragraph.toLowerCase().replaceAll("\\p{Punct}", " ").split(" ");
        Map<String, Integer> nonBanned = new HashMap<>();

        System.out.println(Arrays.toString(words));

        for (String word : words)
        {
            word = word.trim();
            if (word.length() > 0 && !bannedSet.contains(word))
                nonBanned.put(word, nonBanned.getOrDefault(word, 0) + 1);
        }

        nonBanned.forEach((k, v) -> { if (v > this.leaderV) { this.leaderK = k; this.leaderV = v; } });

        return leaderK;
    }


    public String mostCommonWordFaster(String paragraph, String[] banned) {
        Set<String> bannedSet = new HashSet();
        
        for (String ban : banned)
            bannedSet.add(ban);
        
        String[] words = paragraph.replaceAll("\\p{Punct}", " ").split(" ");
        Map<String, Integer> nonBanned = new HashMap<>();
               
        for (String word : words)
        {
            word = word.trim().toLowerCase();
            
            if (word.length() > 0 && !bannedSet.contains(word))
                nonBanned.put(word, nonBanned.getOrDefault(word, 0) + 1);
        }
        
        String leaderK = "";
        int leaderV = -1;
        
        for (Map.Entry<String, Integer> e : nonBanned.entrySet()) 
        {
            if (e.getValue() > leaderV) { 
                leaderK = e.getKey(); 
                leaderV = e.getValue(); 
            }    
        }
        
        
        return leaderK;
    }
}


