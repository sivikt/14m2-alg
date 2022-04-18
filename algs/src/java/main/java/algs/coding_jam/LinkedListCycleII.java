package algs.coding_jam;

// Given a linked list, return the node where the cycle begins. If there is no cycle, return null.

// To represent a cycle in the given linked list, we use an integer pos which represents the 
// position (0-indexed) in the linked list where tail connects to. If pos is -1, then there 
// is no cycle in the linked list.

// Note: Do not modify the linked list.


// Example 1:
// Input: head = [3,2,0,-4], pos = 1
// Output: tail connects to node index 1
// Explanation: There is a cycle in the linked list, where tail connects to the second node.


// Example 2:
// Input: head = [1,2], pos = 0
// Output: tail connects to node index 0
// Explanation: There is a cycle in the linked list, where tail connects to the first node.


// Example 3:
// Input: head = [1], pos = -1
// Output: no cycle
// Explanation: There is no cycle in the linked list.


// Follow-up:
// Can you solve it without using extra space?


public class LinkedListCycleII {
	/*
	  Floyd's Tortoise and Hare
	  The key insight in the algorithm is as follows. If there is a cycle, then, for any 
	  integers i ≥ μ and k ≥ 0, xi = xi + kλ, where λ is the length of the loop to be found 
	  and μ is the index of the first element of the cycle. Based on this, it can then be 
	  shown that i = kλ ≥ μ for some k if and only if xi = x2i. Thus, the algorithm only 
	  needs to check for repeated values of this special form, one twice as far from the 
	  start of the sequence as the other, to find a period ν of a repetition that is a 
	  multiple of λ. Once ν is found, the algorithm retraces the sequence from its start 
	  to find the first repeated value xμ in the sequence, using the fact that λ divides 
	  ν and therefore that xμ = xμ + v. Finally, once the value of μ is known it is trivial 
	  to find the length λ of the shortest repeating cycle, by searching for the first 
	  position μ + λ for which xμ + λ = xμ.
	 */
    public ListNode detectCycle(ListNode head) {
       if (head == null)
            return null;
        
        ListNode p1 = head.next;
        ListNode p2 = (p1 != null) ? p1.next : null;
        
        while (p1 != null && p2 != null && p1 != p2)
        {
            p1 = p1.next;
            p2 = (p2.next != null) ? p2.next.next : null;
        }
        
        p1 = head;
        while (p2 != null && p1 != p2) 
        {
            p1 = p1.next;
            p2 = p2.next;
        }
        
        return p2;  
    }

    /*
      Brent's algorithm
      Richard P. Brent described an alternative cycle detection algorithm that, 
      like the tortoise and hare algorithm, requires only two pointers into the 
      sequence.[8] However, it is based on a different principle: searching for 
      the smallest power of two 2i that is larger than both λ and μ. 
      For i = 0, 1, 2, ..., the algorithm compares x2i−1 with each subsequent 
      sequence value up to the next power of two, stopping when it finds a match. 
      It has two advantages compared to the tortoise and hare algorithm: it finds 
      the correct length λ of the cycle directly, rather than needing to search 
      for it in a subsequent stage, and its steps involve only one evaluation of 
      f rather than three.
     */
    // TODO
}