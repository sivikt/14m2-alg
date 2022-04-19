package algs.coding_jam;

/*
Merge two sorted linked lists and return it as a new list.
The new list should be made by splicing together the nodes of the first two lists.

Example:

Input: 1->2->4, 1->3->4
Output: 1->1->2->3->4->4
*/

class MergeTwoSortedLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {        
        if (l1 == null && l2 == null)
            return null;
        else if (l1 == null)
            return l2;
        else if (l2 == null)
            return l1;
        else
        {
            ListNode l3 = new ListNode(0);
            ListNode t3 = l3;
            
            while (l1 != null && l2 != null)
            {               
                if (l1.val >= l2.val)
                {
                    t3.next = new ListNode(l2.val);
                    t3 = t3.next;                    
                    l2 = l2.next;
                }
                else
                {
                    t3.next = new ListNode(l1.val);
                    t3 = t3.next;                   
                    l1 = l1.next;
                }
            }
            
            while (l1 != null) {
                t3.next = new ListNode(l1.val);
                t3 = t3.next;
                l1 = l1.next;
            }
            
            while (l2 != null) {
                t3.next = new ListNode(l2.val);
                t3 = t3.next;
                l2 = l2.next;
            }
            
            return l3.next;
        }
    }


	public ListNode mergeTwoListsRecursion(ListNode l1, ListNode l2) {
        if(l1 == null) return l2;
        if(l2==null)  return l1; 
        if(l1.val <= l2.val){
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }
        else{
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }       
    }
}