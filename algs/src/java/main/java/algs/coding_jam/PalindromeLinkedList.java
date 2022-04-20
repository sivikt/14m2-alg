package algs.coding_jam;

/**
 *  Given a singly linked list, determine if it is a palindrome.
 *
 *  Example 1:
 *
 *  Input: 1->2
 *  Output: false
 *  Example 2:
 *
 *  Input: 1->2->2->1
 *  Output: true
 *  Follow up:
 *  Could you do it in O(n) time and O(1) space?
 */
class PalindromeLinkedList {
    public boolean isPalindrome(ListNode head) {
        if (head == null)
            return true;
        
        ListNode h = head;
        ListNode copy = new ListNode(h.val);
        h = h.next;
        
        while (h != null) 
        {
            ListNode j = new ListNode(h.val);
            j.next = copy;
            copy = j;
            h = h.next;
        } 
        
        while (head != null) 
        {
            if (head.val != copy.val)
                return false;
            
            head = head.next;
            copy = copy.next;
        }
        
        return true;
    }


/////


    public ListNode reverseList(ListNode next, ListNode prev) {
        if (next == null)
            return prev;
        
        ListNode nn = next.next;
        next.next = prev;
        
        return reverseList(nn, next);
    }
    
    // O(n) time, O(1) memory
    public boolean isPalindrome_inplace(ListNode head) {
        if (head == null)
            return true;

        int size = 0;
        ListNode h = head;

        while (h != null) 
        {
            size++;
            h = h.next;
        } 
        
        
        ListNode mid = head;
        
        for (int i = 0; i < Math.ceil(size/2); i++)
            mid = mid.next;
        
        mid = reverseList(mid, null);
        h = head;
        
        while (mid != null) 
        {
            if (h.val != mid.val)
                return false;
            
            h = h.next;
            mid = mid.next;
        }
        
        return true;
    }
}