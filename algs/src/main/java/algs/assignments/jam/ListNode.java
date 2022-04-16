package algs.assignments.jam;

public class ListNode {
    int val;
    ListNode next;

    public ListNode(int x) {
        val = x;
    }

    public static ListNode createFrom(String listStr) {
        listStr = listStr.replaceAll("\\[", "").replaceAll("\\]", "");
        String[] items = listStr.split(",");

        ListNode head = null;
        ListNode next = null;

        for (int i = 0; i < items.length; i++) {
            int val = Integer.parseInt(items[i]);

            if (head == null) {
                head = new ListNode(val);
                next = head;
            }
            else {
                ListNode h = new ListNode(val);
                next.next = h;
                next = h;
            }
        }

        return head;
    }

    public boolean equalValuesOrder(ListNode that) {
        ListNode p = this;
        ListNode q = that;

        while (p != null && q != null) {
            if (p.val != q.val)
                return false;
            else {
                p = p.next;
                q = q.next;
            }
        }

        return p == null && q == null;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ListNode))
            return false;

        ListNode that = (ListNode) o;

        ListNode p = this;
        ListNode q = that;

        while (p != null && q != null) {
            if (p != q)
                return false;
            else {
                p = p.next;
                q = q.next;
            }
        }

        return p == null && q == null;
    }

}