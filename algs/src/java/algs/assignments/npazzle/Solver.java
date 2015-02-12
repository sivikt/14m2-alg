package algs.assignments.npazzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.introcs.In;
import edu.princeton.cs.introcs.StdOut;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * """Idea.
 *     Best-first search. A solution to the problem that illustrates a general artificial intelligence methodology known
 *     as the A* search algorithm. We define a search node of the game to be a board, the number of moves made to reach
 *     the board, and the previous search node. First, insert the initial search node (the initial board, 0 moves, and
 *     a null previous search node) into a priority queue. Then, delete from the priority queue the search node with
 *     the minimum priority, and insert onto the priority queue all neighboring search nodes (those that can be reached
 *     in one move from the dequeued search node). Repeat this procedure until the search node dequeued corresponds to
 *     a goal board. The success of this approach hinges on the choice of priority function for a search node. We
 *     consider two priority functions:
 *
 *       - Hamming priority function. The number of blocks in the wrong position, plus the number of moves made so far
 *                                    to get to the search node. Intuitively, a search node with a small number of blocks
 *                                    in the wrong position is close to the goal, and we prefer a search node that have
 *                                    been reached using a small number of moves.
 *       - Manhattan priority function. The sum of the Manhattan distances (sum of the vertical and horizontal distance)
 *                                      from the blocks to their goal positions, plus the number of moves made so far to
 *                                      get to the search node.
 *
 *     We make a key observation: To solve the puzzle from a given search node on the priority queue, the total number
 *     of moves we need to make (including those already made) is at least its priority, using either the Hamming or
 *     Manhattan priority function. (For Hamming priority, this is true because each block that is out of place must
 *     move at least once to reach its goal position. For Manhattan priority, this is true because each block must move
 *     its Manhattan distance from its goal position. Note that we do not count the blank square when computing the
 *     Hamming or Manhattan priorities.) Consequently, when the goal board is dequeued, we have discovered not only a
 *     sequence of moves from the initial board to the goal board, but one that makes the fewest number of moves.
 *     (Challenge for the mathematically inclined: prove this fact.)
 *
 *     Detecting unsolvable puzzles. Not all initial boards can lead to the goal board by a sequence of legal moves,
 *     including the two below:
 *
 *     1 2 3                 1  2  3  4
 *     4 5 6 => unsolvable   5  6  7  8  => unsolvable
 *     8 7 -                 9  10 11 12
 *                           13 15 14
 *
 *     To detect such situations, use the fact that boards are divided into two equivalence classes with respect to
 *     reachability: (i) those that lead to the goal board and (ii) those that lead to the goal board if we modify the
 *     initial board by swapping any pair of adjacent (non-blank) blocks in the same row. (Difficult challenge for the
 *     mathematically inclined: prove this fact.) To apply the fact, run the A* algorithm simultaneously on two puzzle
 *     instances—one with the initial board and one with the initial board modified by swapping a pair of adjacent
 *     blocks in the same row. Exactly one of the two will lead to the goal board.
 *
 * """ (from materials by Robert Sedgewick)
 *
 * @author Serj Sintsov
 */
public class Solver {

    private static class Node {
        private Board board;
        private Node  parent;
        private int moves;

        public Node(Board board) {
            this(board, null, 0);
        }

        public Node(Board board, Node parent, int moves) {
            this.board  = board;
            this.parent = parent;
            this.moves  = moves;
        }

        public int priority() {
            return board.manhattan() + moves;
        }
    }

    private static final Comparator<Node> NODES_CMP = (n1, n2) -> {
        int cmp = n1.priority() - n2.priority();

        if (cmp == 0) {
            int cmpManhattan = n1.board.manhattan() - n2.board.manhattan();
            if (cmpManhattan == 0)
                return n1.board.hamming() - n2.board.hamming();
            else
                return cmpManhattan;
        }
        else
            return cmp;
    };

    private final Node root;
    private int isSolvable = -1;
    private List<Node> solution = null;

    public Solver(Board initial) {
        this.root = new Node(initial);
    }

    /**
     * @return is the initial board solvable?
     */
    public boolean isSolvable() {
        if (isSolvable != -1)
            return isSolvable == 1;
        else {
            isSolvable = calcIsSolvable();
            return isSolvable == 1;
        }
    }

    /**
     * Use only one PQ to run the A* algorithm on the initial board and its twin.
     *
     * Possible optimization*: Use a parity argument to determine whether a puzzle is unsolvable (instead of two
     * synchronous A* searches). However, this will either break the API or will require a fragile dependence on
     * the toString() method.
     */
    private int calcIsSolvable() {
        Node twin = new Node(root.board.twin());

        MinPQ<Node> pathQueue = new MinPQ<>(NODES_CMP);
        pathQueue.insert(root);
        pathQueue.insert(twin);

        while (!pathQueue.min().board.isGoal()) {
            Node lastMove = pathQueue.delMin();
            addAllToMinQueue(pathQueue, neighbors(lastMove));
        }

        Node prevNode = pathQueue.delMin();
        while (prevNode.parent != null)
            prevNode = prevNode.parent;

        if (prevNode.board.equals(twin.board))
            return 0;
        else
            return 1;
    }

    /**
     * @return min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (isSolvable())
            return solutionList().get(solutionList().size() - 1).moves;
        else
            return -1;
    }

    /**
     * Find a solution to the initial board (using the A* algorithm).
     * @return sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        return nodeListToBoardList(solutionList());
    }

    private Iterable<Board> nodeListToBoardList(List<Node> nodes) {
        if (nodes == null)
            return null;

        List<Board> boards = new ArrayList<>(nodes.size());
        for (Node n : nodes)
            boards.add(n.board);
        return boards;
    }

    private List<Node> solutionList() {
        if (!isSolvable())
            return null;
        else if (solution != null)
            return solution;
        else {
            solution = calcSolutionList();
            return solution;
        }
    }

    private List<Node> calcSolutionList() {
        List<Node> path = new ArrayList<>();
        MinPQ<Node> pathQueue = new MinPQ<>(NODES_CMP);
        pathQueue.insert(root);

        while (!pathQueue.min().board.isGoal()) {
            Node lastMove = pathQueue.delMin();
            path.add(lastMove);
            addAllToMinQueue(pathQueue, neighbors(lastMove));
        }

        path.add(pathQueue.min());

        ((ArrayList<Node>) path).trimToSize();
        return path;
    }

    private void addAllToMinQueue(MinPQ<Node> queue, Iterable<Node> nodes) {
        for (Node n : nodes)
            queue.insert(n);
    }

    private Iterable<Node> neighbors(Node parent) {
        Iterable<Board> children = parent.board.neighbors();
        List<Node> neighbors = new ArrayList<>(3);

        for (Board b : children)
            if (allow(parent, b))
                neighbors.add(new Node(b, parent, parent.moves+1));

        ((ArrayList<Node>) neighbors).trimToSize();

        return neighbors;
    }

    private boolean allow(Node searchNode, Board neighbor) {
        // don't allow to include next search node if it equals to its grandfather
        return searchNode.parent == null || !searchNode.parent.board.equals(neighbor);
    }


    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
