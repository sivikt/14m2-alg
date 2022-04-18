package algs.coding_jam;

// Given a linked list, determine if it has a cycle in it.

// To represent a cycle in the given linked list, we use an 
// integer pos which represents the position (0-indexed) in 
// the linked list where tail connects to. If pos is -1, 
// then there is no cycle in the linked list.

 

// Example 1:
// Input: head = [3->2->0->-4->2], pos = 1
// Output: true
// Explanation: There is a cycle in the linked list, where tail connects to the second node.


// Example 2:
// Input: head = [1->2->1], pos = 0
// Output: true
// Explanation: There is a cycle in the linked list, where tail connects to the first node.


// Example 3:
// Input: head = [1->nil], pos = -1
// Output: false
// Explanation: There is no cycle in the linked list.


// Follow up:
// Can you solve it using O(1) (i.e. constant) memory?

public class LinkedListCycle {
    public boolean hasCycle(ListNode head) {
        if (head == null)
            return false;
        
        ListNode p1 = head;
        ListNode p2 = (p1.next != null) ? p1.next.next : null;
        
        while (p1 != null && p2 != null && p1 != p2)
        {
            p1 = p1.next;
            p2 = (p2.next != null) ? p2.next.next : null;
        }
        
        return p1 != null && p1 == p2; 
    }
}