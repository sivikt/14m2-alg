package algs.coding_jam;

/**
 * Given a sorted linked list, delete all duplicates such that each element appear only once.
 *
 * Example 1:
 * Input: 1->1->2
 * Output: 1->2
 *
 * Example 2:
 * Input: 1->1->2->3->3
 * Output: 1->2->3
 */
public class RemoveDuplicatesFromSortedList {

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return null;
        
        ListNode p = head;
        ListNode q = p.next;
        
        while (q != null) {
            if (p.val == q.val) {
                p.next = q.next;
                q = q.next;
            }
            else {
                p = p.next;
                q = q.next;
            }
        }
        
        return head;
    }

}