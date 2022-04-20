package algs.coding_jam;

/*
 Given a singly linked list, group all odd nodes together followed
 by the even nodes. Please note here we are talking about the node
 number and not the value in the nodes.

 You should try to do it in place. The program should run in O(1)
 space complexity and O(nodes) time complexity.

 Example 1:

 Input: 1->2->3->4->5->NULL
 Output: 1->3->5->2->4->NULL
 Example 2:

 Input: 2->1->3->5->6->4->7->NULL
 Output: 2->3->6->7->1->5->4->NULL
 Note:

 The relative order inside both the even and odd groups should remain as it was in the input.
 The first node is considered odd, the second node even and so on ...
*/
class OddEvenLinkedList {
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        
        ListNode o = head;
        ListNode e = head.next;
        
        ListNode ot = o;
        ListNode et = e;
        
        head = head.next.next;
            
        while (head != null) 
        {
            ot.next = head;
            ot = ot.next;
            et.next = head.next;
            et = et.next;
            
            head = (head.next != null) ? head.next.next : null;
        }
        
        ot.next = e;
        
        return o;
    }
}