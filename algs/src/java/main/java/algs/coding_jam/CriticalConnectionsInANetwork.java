package algs.coding_jam;

/**
 *  Amazon | OA 2019 | Critical Routers
 *  You are given an undirected connected graph. An articulation point (or cut vertex)
 *  is defined as a vertex which, when removed along with associated edges, makes the
 *  graph disconnected (or more precisely, increases the number of connected components
 *  in the graph). The task is to find all articulation points in the given graph.
 *
 *  Input:
 *  The input to the function/method consists of three arguments:
 *
 *  numNodes, an integer representing the number of nodes in the graph.
 *  numEdges, an integer representing the number of edges in the graph.
 *  edges, the list of pair of integers - A, B representing an edge between the nodes A and B.
 *  Output:
 *  Return a list of integers representing the critical nodes.
 *
 *  Example:
 *
 *  Input: numNodes = 7, numEdges = 7, edges = [[0, 1], [0, 2], [1, 3], [2, 3], [2, 5], [5, 6], [3, 4]]
 *  Output: [2, 3, 5]
 *
 *
 *  ----- Another problem with the same solution
 *
 *
 *  There are n servers numbered from 0 to n-1 connected by undirected server-to-server
 *  connections forming a network where connections[i] = [a, b] represents a connection
 *  between servers a and b. Any server can reach any other server directly or indirectly
 *  through the network.
 *
 *  A critical connection is a connection that, if removed, will make some server unable
 *  to reach some other server.
 *
 *  Return all critical connections in the network in any order.
 *
 *
 *
 *  Example 1:
 *  Input: n = 4, connections = [[0,1],[1,2],[2,0],[1,3]]
 *  Output: [[1,3]]
 *  Explanation: [[3,1]] is also accepted.
 *
 *
 *  ----- Another problem with the same solution
 *
 *  Given an underected connected graph with n nodes labeled 1..n. A bridge (cut edge)
 *  is defined as an edge which, when removed, makes the graph disconnected (or more
 *  	precisely, increases the number of connected components in the graph).
 *  Equivalently, an edge is a bridge if and only if it is not contained in any cycle.
 *  The task is to find all bridges in the given graph. Output an empty list if there
 *  are no bridges.
 *
 *  Input:
 *
 *  n, an int representing the total number of nodes.
 *  edges, a list of pairs of integers representing the nodes connected by an edge.
 *  Example 1:
 *
 *  Input: n = 5, edges = [[1, 2], [1, 3], [3, 4], [1, 4], [4, 5]]
 *
 *  Output: [[1, 2], [4, 5]]
 *  Explanation:
 *  There are 2 bridges:
 *  1. Between node 1 and 2
 *  2. Between node 4 and 5
 *  If we remove these edges, then the graph will be disconnected.
 *  If we remove any of the remaining edges, the graph will remain connected.
 *  Example 2:
 *
 *  Input: n = 6, edges = [[1, 2], [1, 3], [2, 3], [2, 4], [2, 5], [4, 6], [5, 6]]
 *
 *  Output: []
 *  Explanation:
 *  We can remove any edge, the graph will remain connected.
 *
 *  Example 3:
 *
 *  Input: n = 9, edges = [[1, 2], [1, 3], [2, 3], [3, 4], [3, 6], [4, 5], [6, 7], [6, 9], [7, 8], [8, 9]]
 *  Output: [[3, 4], [3, 6], [4, 5]]
 */
import java.util.*;

class CriticalConnectionsInANetwork {

    private int time = 0;

    public void dfs(Map<Integer, List<Integer>> graph,
                    byte[] visited,
                    int[] in, int[] up,
                    int v,
                    int parent,
                    List<List<Integer>> bridges)
    {
        visited[v] = 1;
        in[v] = up[v] = time++;

        if (!graph.containsKey(v))
            return;

        for (Integer t : graph.get(v))
        {
            if (t == parent)
                continue;
            else if (visited[t] == 1)
            {
                up[v] = Math.min(up[v], in[t]);
            }
            else
            {
                dfs(graph, visited, in, up, t, v, bridges);
                up[v] = Math.min(up[v], up[t]);

                if (up[t] > in[v])
                    bridges.add(Arrays.asList(v, t));
            }
        }
    }

    public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
        List<List<Integer>> bridges = new LinkedList<>();

        Map<Integer, List<Integer>> graph = new HashMap<>();
        byte[] visited = new byte[n];
        int[] in = new int[n];
        int[] up = new int[n];

        for (List<Integer> edge : connections) 
        {
            graph.computeIfAbsent(edge.get(0), k -> new LinkedList<>()).add(edge.get(1));
            graph.computeIfAbsent(edge.get(1), k -> new LinkedList<>()).add(edge.get(0));
        }

        for (Integer v : graph.keySet())
        {
            if (visited[v] == 0)
                dfs(graph, visited, in, up, v, -1, bridges);
        }

        return bridges;
    }


    // Iterative

    private static final class Frame {
        final int v;
        final int p;
        int i;
        
        public Frame(int v, int p, int i) {
            this.v = v;
            this.p = p;
            this.i = i;
        }            
        
        public String toString() {
            return "(v="+v+" p="+p+" i="+i+")";
        }
    }
    
    public void dsf(Map<Integer, List<Integer>> graph, 
                    int[] in, 
                    int[] up, 
                    byte[] visited, 
                    int v, 
                    List<List<Integer>> bridges) 
    {       
        Deque<Frame> stack = new ArrayDeque<Frame>();
        
        stack.push(new Frame(v, -1, -1));

        while (!stack.isEmpty())
        {
            Frame f = stack.peekFirst();

            if (visited[f.v] == 0)
            {
                visited[f.v] = 1;
                in[f.v] = up[f.v] = this.time++;
            }
            else if (visited[f.v] == 1 && f.i == -1)
            {
                stack.pop();
                continue;  
            }
            
            if (!graph.containsKey(f.v)) {
                stack.pop();
                continue;
            }
            
            if (f.i >= 0)
            {
                stack.pop();
                
                if (f.p >= 0)
                {
                    up[f.p] = Math.min(up[f.p], up[f.v]);

                    if (up[f.v] > in[f.p])
                        bridges.add(Arrays.asList(f.v, f.p));             
                }
            }
            else
            {
                int i = 0;
                for (Integer t : graph.get(f.v))
                {            
                    if (t == f.p)
                        continue;
                    else if (visited[t] == 1)
                    {
                        up[f.v] = Math.min(up[f.v], in[t]);
                    }
                    else
                    {
                        i++;
                        stack.push(new Frame(t, f.v, -1));
                    }
                }
                
                f.i = i;
            }
        }
    }
    
    public List<List<Integer>> criticalConnections_iterative(int n, List<List<Integer>> connections) {
        List<List<Integer>> bridges = new LinkedList<>();

        Map<Integer, List<Integer>> graph = new HashMap<>();
        
        for (List<Integer> edge : connections) 
        {
            graph.computeIfAbsent(edge.get(0), k -> new LinkedList<>()).add(edge.get(1));
            graph.computeIfAbsent(edge.get(1), k -> new LinkedList<>()).add(edge.get(0));
        }
        
        
        byte[] visited = new byte[n];
        int[] in = new int[n];
        int[] up = new int[n];
        this.time = 0;
        
        for (Integer v : graph.keySet())
        {
            if (visited[v] == 0) 
                dsf(graph, in, up, visited, v, bridges);
        }

        return bridges;
    }
}