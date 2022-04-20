package algs.coding_jam;

/**
 *  Given a linked list, rotate the list to the right by k places, where k is non-negative.
 *
 *  Example 1:
 *
 *  Input: 1->2->3->4->5->NULL, k = 2
 *  Output: 4->5->1->2->3->NULL
 *  Explanation:
 *  rotate 1 steps to the right: 5->1->2->3->4->NULL
 *  rotate 2 steps to the right: 4->5->1->2->3->NULL
 *  Example 2:
 *
 *  Input: 0->1->2->NULL, k = 4
 *  Output: 2->0->1->NULL
 *  Explanation:
 *  rotate 1 steps to the right: 2->0->1->NULL
 *  rotate 2 steps to the right: 1->2->0->NULL
 *  rotate 3 steps to the right: 0->1->2->NULL
 *  rotate 4 steps to the right: 2->0->1->NULL
 */
class RotateList {
    public ListNode rotateRight(ListNode head, int k) {
        if (k == 0 || head == null)
            return head;
        
        int N = 0;
        
        ListNode h = head;
        ListNode t = head;
        ListNode y;
        
        while (h != null)
        {
            N++;
            h = h.next;
        }
        
        k = k % N;
        
        if (k > 0)
        {
            for (int i = N-k-1; i > 0; i--)
                t = t.next;

            h = head;
            head = t.next;
            y = t;

            while (y.next != null && y.next.next != null)
                y = y.next;

            y.next.next = h;
            t.next = null;
        }
        
        return head;
    }
}