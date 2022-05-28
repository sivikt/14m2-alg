"""
You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.

Merge all the linked-lists into one sorted linked-list and return it.

Example 1:
Input: lists = [[1,4,5],[1,3,4],[2,6]]
Output: [1,1,2,3,4,4,5,6]
Explanation: The linked-lists are:
[
  1->4->5,
  1->3->4,
  2->6
]
merging them into one sorted list:
1->1->2->3->4->4->5->6

Example 2:
Input: lists = []
Output: []

Example 3:
Input: lists = [[]]
Output: []

Constraints:
    k == lists.length
    0 <= k <= 10^4
    0 <= lists[i].length <= 500
    -10^4 <= lists[i][j] <= 10^4
    lists[i] is sorted in ascending order.
    The sum of lists[i].length will not exceed 10^4.
"""
import heapq
from typing import List, Optional

from listnode import ListNode


class Solution:
    def mergeKLists(self, lists: List[Optional[ListNode]]) -> Optional[ListNode]:
        lists_mins = []

        for i, l in enumerate(lists):
            if l:
                heapq.heappush(lists_mins, (l.val, (i, l)))
                lists[i] = l.next

        if not lists_mins:
            return None

        def swap_min():
            m = heapq.heappop(lists_mins)
            j = m[1][0]

            if lists[j]:
                heapq.heappush(lists_mins, (lists[j].val, (j, lists[j])))
                lists[j] = lists[j].next

            return m[1][1]

        res = swap_min()
        tail = res

        while lists_mins:
            r = swap_min()
            tail.next = r
            tail = r

        return res
