"""
You are given an integer n. There is an undirected graph with n vertices, numbered from 0 to n - 1.
You are given a 2D integer array edges where edges[i] = [ai, bi] denotes that there exists an undirected
edge connecting vertices ai and bi.

Return the number of complete connected components of the graph.

A connected component is a subgraph of a graph in which there exists a path between any two vertices,
and no vertex of the subgraph shares an edge with a vertex outside of the subgraph.

A connected component is said to be complete if there exists an edge between every pair of its vertices.

Example 1:
Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4]]
Output: 3
Explanation: From the picture above, one can see that all of the components of this graph are complete.

Example 2:
Input: n = 6, edges = [[0,1],[0,2],[1,2],[3,4],[3,5]]
Output: 1
Explanation: The component containing vertices 0, 1, and 2 is complete since there is an edge
between every pair of two vertices. On the other hand, the component containing vertices 3, 4,
and 5 is not complete since there is no edge between vertices 4 and 5. Thus, the number of
complete components in this graph is 1.

Constraints:
    1 <= n <= 50
    0 <= edges.length <= n * (n - 1) / 2
    edges[i].length == 2
    0 <= ai, bi <= n - 1
    ai != bi
    There are no repeated edges.
"""
from typing import List

from collections import deque


class Solution:
    def _find_cc(self, cc, r):
        if cc[r][0] != r:
            cc[r][0] = self._find_cc(cc, cc[r][0])
        return cc[r][0]

    def countCompleteComponents(self, n: int, edges: List[List[int]]) -> int:
        cc = {}

        for i in range(n):
            cc[i] = [i, {i}, 0]

        for e in edges:
            u0 = self._find_cc(cc, e[0])
            u1 = self._find_cc(cc, e[1])

            if u0 == u1:
                cc[u0][2] += 1
                continue
            else:
                if u0 < u1:
                    cc[e[1]][0] = u0
                    cc[u1][0] = u0
                    cc[u0][1] |= cc[u1][1]
                    cc[u1][1] = set()
                    cc[u0][2] += cc[u1][2] + 1
                    cc[u1][2] = 0
                else:
                    cc[e[0]][0] = u1
                    cc[u0][0] = u1
                    cc[u1][1] |= cc[u0][1]
                    cc[u0][1] = set()
                    cc[u1][2] += cc[u0][2] + 1
                    cc[u0][2] = 0

        r = 0
        for k in cc.keys():
            m = len(cc[k][1])

            if (cc[k][0] == k) and ((m * (m - 1) / 2) == cc[k][2]):
                r += 1

        return r

    def countCompleteComponents2(self, n: int, edges: List[List[int]]) -> int:
        graph = [[] for _ in range(n)]
        for n1, n2 in edges:
            graph[n1].append(n2)
            graph[n2].append(n1)

        def is_ccc(node):
            queue = deque([node])
            visited[node] = 1
            total_nodes = 0
            total_edges = 0
            while queue:
                cur = queue.popleft()
                total_nodes += 1
                total_edges += len(graph[cur])
                for ch in graph[cur]:
                    if not visited[ch]:
                        queue.append(ch)
                        visited[ch] = 1
            return total_edges == total_nodes * (total_nodes - 1)

        visited = [0] * n
        res = 0
        for i in range(n):
            if not visited[i] and is_ccc(i):
                res += 1
        return res