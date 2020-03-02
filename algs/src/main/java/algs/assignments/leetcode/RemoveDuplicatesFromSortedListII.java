package algs.assignments.leetcode;

/**
 * Given a sorted linked list, delete all nodes that have duplicate numbers,
 * leaving only distinct numbers from the original list.
 *
 * Example 1:
 * Input: 1->2->3->3->4->4->5
 * Output: 1->2->5
 *
 * Example 2:
 * Input: 1->1->1->2->3
 * Output: 2->3
 */
public class RemoveDuplicatesFromSortedListII {

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return null;

        ListNode nhead = null;
        ListNode ntail = null;

        ListNode p = head;
        ListNode q = p.next;

        while (q != null) {
            if (p.val == q.val)
            {
                q = q.next;

                while (q != null && p.val == q.val) {
                    q = q.next;
                }

                p = q;
                q = (q != null) ? q.next : null;
            }
            else
            {
                if (nhead == null) {
                    nhead = p;
                    ntail = p;
                }
                else {
                    ntail.next = p;
                    ntail = p;
                }

                p = p.next;
                q = q.next;
            }
        }

        if (ntail != null) {
            ntail.next = p;
            ntail = p;

            if (ntail != null)
                ntail.next = null;
        }
        else {
            nhead = p;

            if (nhead != null)
                nhead.next = null;
        }

        return nhead;
    }

}