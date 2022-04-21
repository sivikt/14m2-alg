package algs.coding_jam;

/*
Remove all elements from a linked list of integers that have value val.

Example:

Input:  1->2->6->3->4->5->6, val = 6
Output: 1->2->3->4->5
*/

class RemoveLinkedListElements {
    public ListNode removeElements(ListNode head, int val) {
        ListNode h = head;
        ListNode p;
        
        if (h == null)
            return null;
        
        while (h != null && h.val == val)
        {
            p = h;
            h = h.next;
            p.next = null;
        }
        
        head = h;
        p = h;
        h = (h != null) ? h.next : null;
        
        while (h != null)
        {
            if (h.val == val)
            {
                p.next = h.next;
                h.next = null;
                h = p.next;
            }
            else
            {
                p = h;
                h = h.next;
            }
        }
        
        return head;
    }
}