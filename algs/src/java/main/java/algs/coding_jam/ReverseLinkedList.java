package algs.coding_jam;

/*
Reverse a singly linked list.

Example:

Input: 1->2->3->4->5->NULL
Output: 5->4->3->2->1->NULL
Follow up:

A linked list can be reversed either iteratively or recursively. Could you implement both?
*/
class ReverseLinkedList {
    public ListNode reverseList(ListNode head) {
        if (head == null)
            return null;
        
        ListNode copy = new ListNode(head.val);
        head = head.next;
        
        while (head != null) 
        {
            ListNode j = new ListNode(head.val);
            j.next = copy;
            copy = j;
            head = head.next;
        } 
                
        return copy;
    }


///////

    
    public ListNode reverseList(ListNode next, ListNode prev) {
        if (next == null)
            return prev;
        
        ListNode copy = new ListNode(next.val);
        copy.next = prev;
        
        return reverseList(next.next, copy);
    }
    
    public ListNode reverseList_recursive(ListNode head) {                
        return reverseList(head, null);
    }


///////


    public ListNode reverseList_inplace(ListNode next, ListNode prev) {
        if (next == null)
            return prev;
        
        ListNode nn = next.next;
        next.next = prev;
        
        return reverseList_inplace(nn, next);
    }
    
    public ListNode reverseList_inplace(ListNode head) {                
        return reverseList_inplace(head, null);
    }
}