package algs.coding_jam;

// Sort a linked list in O(n log n) time using constant space complexity.

// Example 1:

// Input: 4->2->1->3
// Output: 1->2->3->4
// Example 2:

// Input: -1->5->3->4->0
// Output: -1->0->3->4->5


public class SortList {
    public ListNode sortList(ListNode head) {
        int N = 0;
        ListNode tmp = head;
        
        while (tmp != null)
        {
            N++;
            tmp = tmp.next;
        }
                
        for (int h = 1; h < N/2; h *= 2)
        {
            for (int i = 0; i < N; i += 2*h)
            {                
                ListNode l = head;
                ListNode prel = null;
                head = null;
                ListNode r = l;
                
                int m = h;
                int n = i;
            
                while (n-- > 0)
                    r = r.next;
                
                System.out.println("l=" + l.val + " r=" + r.val + " i="+i);
                
                n = h;
                
                while (m > 0 && n > 0 && r != null)
                {
                    if (l.val <= r.val)
                    {
                        prel = l;
                        l = l.next;
                        m--;
                    }
                    else
                    {
                        tmp = r.next;
                        
                        if (prel == null)
                        {
                            prel = r;
                            prel.next = l;
                        }
                        else
                        {
                            prel.next = r;
                            r.next = l;
                        }
                        
                        r = tmp;
                        n--;
                    }
                    
                    if (head == null)
                        head = prel;
                }
            }
        }
        
        return head;
    }
}