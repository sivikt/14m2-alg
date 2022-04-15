// Given a singly linked list L: L0→L1→…→Ln-1→Ln,
// reorder it to: L0→Ln→L1→Ln-1→L2→Ln-2→…

// You may not modify the values in the list's nodes, only nodes itself may be changed.

// Example 1:
// Given 1->2->3->4, reorder it to 1->4->2->3.

// Example 2:
// Given 1->2->3->4->5, reorder it to 1->5->2->4->3.


/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
class Solution {
        
    public void reorderList(ListNode head) {
        if (head == null || head.next == null)
            return;
        
        int sz = 0;
        ListNode h = head;
        ListNode rest = head;
        ListNode t = null;
        
        while (h != null) {
            sz++;
            h = h.next;
        }
        
        int m = (sz/2) + 1;
            
        // find rest part next to middle element
        for (int i = 0; i < m; i++) {
            h = rest;
            rest = rest.next;
        }
        
        // create new tail
        h.next = null;
        
        // revert rest part
        h = (rest != null) ? rest.next : null;
        while (h != null) {
            t = h.next;
            h.next = rest;
            rest = h;
            h = t;
        }
        
        // mix rest part into first half of the elements
        h = head;
        
        while (m++ < sz) {
            t = h.next;
            h.next = rest;
            rest = rest.next;
            h.next.next = t;
            h = t;
        }
    }
}