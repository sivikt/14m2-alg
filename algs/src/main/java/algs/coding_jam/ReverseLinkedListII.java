package algs.coding_jam;

/**
    Reverse a linked list from position m to n. Do it in one-pass.

    Note: 1 ≤ m ≤ n ≤ length of list.

    Example:

    Input: 1->2->3->4->5->NULL, m = 2, n = 4
    Output: 1->4->3->2->5->NULL
 */

class ReverseLinkedListII {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null)
            return null;
        
        ListNode copyHead = null;
        ListNode copy = null;
        
        for (int i = 1; i < m; i++) 
        {
            if (copy == null) 
            {
                copyHead = new ListNode(head.val);    
                copy = copyHead;
            }
            else
            {
                copy.next = new ListNode(head.val);
                copy = copy.next;
            }
            
            head = head.next;
        }
            

        ListNode rcopyHead = null;
        ListNode rcopy = null;

        if (head != null)
        {
            rcopyHead = new ListNode(head.val);
            rcopy = rcopyHead;
            head = head.next;
        }

        for (int i = 1; i <= (n-m); i++) 
        {
            ListNode j = new ListNode(head.val);
            j.next = rcopy;
            rcopy = j;
            head = head.next;
        } 

        if (copy != null)
            copy.next = rcopy;
        else
            copyHead = rcopy;

        while (head != null) 
        {
            rcopyHead.next = new ListNode(head.val);
            rcopyHead = rcopyHead.next;
            head = head.next;
        }
        
        return copyHead;
    }
}