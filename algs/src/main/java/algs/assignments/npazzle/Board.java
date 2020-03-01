package algs.assignments.npazzle;

import java.util.Collections;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Game board at the current moment.
 *
 * {@link Board} is immutable but doesn't thread-safe.
 *
 * @author Serj Sintsov
 */
public class Board {

    private static final Comparator<Board> DISTANCE_ASC_CMP = (b1, b2) -> {
        int cmp = b1.hamming() - b2.hamming();
        if (cmp == 0)
            return b1.manhattan() - b2.manhattan();
        else
            return cmp;
    };

    /**
     * Use char[] to save memory and speed up performance.
     * Don not scary of type overflow since the size (dimension) of
     * Board is 2 ≤ size < 128.
     *
     * Other possible improvement is to use bitmap.
     */
    private final char[] blocks;
    private final int size;
    private final int hole;

    // cached values
    private int hamming   = -1;
    private int manhattan = -1;
    private int goal      = -1;

    /**
     * Construct a board from an N-by-N array of blocks
     * @param blocks [i][j] = block in row i, column j
     */
    public Board(int[][] blocks) {
        if (blocks == null) throw new NullPointerException("blocks must be not null");
        this.size = blocks.length;
        this.blocks = new char[size*size];
        this.hole = inlineBlocks(blocks);
    }

    /**
     * Clone constructor.
     */
    private Board(char[] blocks, int size, int hole) {
        this.blocks = new char[blocks.length];
        this.size = size;
        this.hole = hole;
        System.arraycopy(blocks, 0, this.blocks, 0, blocks.length);
    }

    /**
     * board dimension N
     */
    public int dimension() {
        return size;
    }

    /**
     * Used for Hamming priority function. The number of blocks in the wrong position, plus the number of moves made so
     * far to get to the search node. Intuitively, a search node with a small number of blocks in the wrong position is
     * close to the goal, and we prefer a search node that have been reached using a small number of moves.
     *
     * @return number of blocks out of place
     */
    public int hamming() {
        if (hamming > -1)
            return hamming;
        else {
            hamming = calcHamming();
            return hamming;
        }
    }

    private int calcHamming() {
        int N = blocks.length,
                i = 0,
                j = 0,
                distance = 0;

        while (i < N-1) {
            while ((j < size) && (i+j < N-1)) {
                if (blocks[i + j] != i + j + 1)
                    distance += 1;

                j += 1;
            }

            i += j;
            j = 0;
        }

        return distance;
    }

    /**
     * Used for Manhattan priority function. The sum of the Manhattan distances (sum of the vertical and horizontal
     * distance) from the blocks to their goal positions, plus the number of moves made so far to get to the search node.
     *
     * @return sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        if (manhattan > -1)
            return manhattan;
        else {
            manhattan = calcManhattan();
            return manhattan;
        }
    }

    private int calcManhattan() {
        int N = blocks.length,
                i = 0,
                j = 0,
                distance = 0;

        while (i < N-1) {
            while (j < size) {

                int block = i+j;
                if ((blocks[block] != 0) && (blocks[block] != block + 1)) { // j is current column
                    int row = i / size,
                            goalRow = (blocks[block] - 1) / size,
                            goalCol = (blocks[block] - 1) % size;

                    distance += Math.abs(goalRow - row) + Math.abs(goalCol - j);
                }

                j += 1;
            }

            i += j;
            j = 0;
        }

        return distance;
    }

    /**
     * Is this board the goal board?
     */
    public boolean isGoal() {
        if (goal != -1)
            return goal == 1;
        else {
            goal = calcIsGoal();
            return goal == 1;
        }
    }

    private int calcIsGoal() {
        int N = blocks.length,
                i = 0,
                j = 0;

        while (i < N-1) {
            while ((j < size) && (i+j < N-1)) {
                if (blocks[i + j] != i + j + 1) {
                    return 0;
                }
                j += 1;
            }

            i += j;
            j = 0;
        }

        assert N == 0 || blocks[N-1] == 0;

        return 1;
    }

    /**
     * A board that is obtained by exchanging two adjacent blocks in the same row.
     */
    public Board twin() {
        Board twin = new Board(blocks, size, hole);
        if (hole >= 0 && hole <= size-1)
            twin.swapBlocks(size, size + 1); // swap blocks in the second row
        else
            twin.swapBlocks(0, 1);           // swap blocks in the first row

        return twin;
    }

    /**
     * All neighboring boards.
     */
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new ArrayList<>(4);

        int left  = hole - 1,
                right = hole + 1,
                top   = hole - size,
                down  = hole + size,
                maxRight = size*(hole/size) + size - 1,
                minLeft  = size*(hole/size);

        if (left >= minLeft) {
            Board leftBoard = new Board(blocks, size, left);
            leftBoard.swapBlocks(hole, left);
            neighbors.add(leftBoard);
        }

        if (right <= maxRight) {
            Board rightBoard = new Board(blocks, size, right);
            rightBoard.swapBlocks(hole, right);
            neighbors.add(rightBoard);
        }

        if (top >= 0) {
            Board topBoard = new Board(blocks, size, top);
            topBoard.swapBlocks(hole, top);
            neighbors.add(topBoard);
        }

        if (down < blocks.length) {
            Board downBoard = new Board(blocks, size, down);
            downBoard.swapBlocks(hole, down);
            neighbors.add(downBoard);
        }

        ((ArrayList) neighbors).trimToSize();
        // Optimize the order in which neighbors() returns the neighbors of a board.
        Collections.sort(neighbors, DISTANCE_ASC_CMP);

        return neighbors;
    }

    @Override
    public boolean equals(Object y) {
        if (!(y instanceof Board))
            return false;

        if (this == y)
            return true;

        Board that = (Board) y;

        if (this.dimension() != that.dimension())
            return false;

        for (int i = 0; i < this.blocks.length; i++)
            if (this.blocks[i] != that.blocks[i])
                return false;

        return true;
    }

    /**
     * Prints the Board as
     * 3
     * 0 1 3
     * 4 2 5
     * 7 8 6
     */
    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder();
        buf.append(size);
        buf.append("\n");

        int i = 0, j,
                N = blocks.length;

        // print row
        while (i < N) {
            j = 0;

            // print row.col
            while (j < size) {
                buf.append((int) blocks[i + j]);
                j += 1;
                if (j != size)
                    buf.append(" ");
                else if (i + j != N)
                    buf.append("\n");
            }

            i += j;
        }

        return buf.toString();
    }

    private void swapBlocks(int i, int j) {
        char tmp = blocks[i];
        blocks[i] = blocks[j];
        blocks[j] = tmp;
    }

    /**
     * @return the hole blocks2D index
     */
    private int inlineBlocks(int[][] blocks2D) {
        int zeroBlock = -1;

        for (int i = 0; i < size; i++) {
            if (blocks2D[i].length != size)
                throw new NullPointerException("size of blocks2D[" + i + "] must be " + size);

            for (int j = 0; j < size; j++)
                if (blocks2D[i][j] == 0)
                    zeroBlock = i*size + j;
                else
                    this.blocks[i*size + j] = (char) blocks2D[i][j];
        }

        if (size > 0 && zeroBlock == -1)
            throw new NullPointerException("board does not have zeroBlock block");

        return zeroBlock;
    }


    /**
     * Test it!
     */
    @SuppressWarnings("AssertWithSideEffects")
    public static void main(String[] args) {
        int[][] boardIn = {
                {2, 1, 3},
                {4, 0, 5},
                {7, 8, 6}
        };

        int[][] boardIn1 = {
                {2, 1, 3},
                {4, 0, 5},
                {7, 6, 8}
        };

        int[][] boardIn2 = {
                {8, 1, 3},
                {4, 0, 2},
                {7, 6, 5}
        };

        int[][] boardIn3 = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };

        int[][] boardIn4 = {
                {2, 0, 3},
                {4, 1, 5},
                {7, 8, 6}
        };

        int[][] boardIn5 = {
                {1, 0},
                {2, 3}
        };

        Board board = new Board(boardIn);
        Board emptyBoard = new Board(new int[][]{});

        assert emptyBoard.dimension() == 0;
        assert board.dimension() == 3;

        assert board.equals(new Board(boardIn));
        assert !board.equals(new Board(boardIn1));
        assert !emptyBoard.equals(board);

        assert emptyBoard.isGoal();
        assert !board.isGoal();
        assert new Board(boardIn3).isGoal();

        String boardAsStr = "3\n2 1 3\n4 0 5\n7 8 6";
        assert board.toString().equals(boardAsStr);

        String twinBoardAsStr = "3\n1 2 3\n4 0 5\n7 8 6";
        assert board.twin().toString().equals(twinBoardAsStr);

        String twinBoard4AsStr = "3\n2 0 3\n1 4 5\n7 8 6";
        assert new Board(boardIn4).twin().toString().equals(twinBoard4AsStr);

        assert new Board(boardIn2).hamming() == 5;
        assert new Board(boardIn3).hamming() == 0;

        assert new Board(boardIn2).manhattan() == 10;
        assert new Board(boardIn3).manhattan() == 0;
        assert new Board(boardIn5).manhattan() == 3;

        // ----------------------------------------------
        Board orig = new Board(boardIn);
        assert orig.toString().equals("3\n2 1 3\n4 0 5\n7 8 6");
        List<Board> neighbors = asList(orig.neighbors());

        assert neighbors.size() == 4;
        assert neighbors.get(0).toString().equals("3\n2 1 3\n4 5 0\n7 8 6");
        assert neighbors.get(1).toString().equals("3\n2 0 3\n4 1 5\n7 8 6");
        assert neighbors.get(2).toString().equals("3\n2 1 3\n0 4 5\n7 8 6");
        assert neighbors.get(3).toString().equals("3\n2 1 3\n4 8 5\n7 0 6");
        //
        orig = new Board(boardIn3);
        assert orig.toString().equals("3\n1 2 3\n4 5 6\n7 8 0");
        neighbors = asList(orig.neighbors());

        assert neighbors.size() == 2;
        assert neighbors.get(0).toString().equals("3\n1 2 3\n4 5 6\n7 0 8");
        assert neighbors.get(1).toString().equals("3\n1 2 3\n4 5 0\n7 8 6");
        //
        orig = new Board(boardIn4);
        assert orig.toString().equals("3\n2 0 3\n4 1 5\n7 8 6");
        neighbors = asList(orig.neighbors());

        assert neighbors.size() == 3;
        assert neighbors.get(0).toString().equals("3\n0 2 3\n4 1 5\n7 8 6");
        assert neighbors.get(1).toString().equals("3\n2 1 3\n4 0 5\n7 8 6");
        assert neighbors.get(2).toString().equals("3\n2 3 0\n4 1 5\n7 8 6");
        //
        orig = new Board(boardIn5);
        assert orig.toString().equals("2\n1 0\n2 3");
        neighbors = asList(orig.neighbors());

        assert neighbors.size() == 2;
        assert neighbors.get(0).toString().equals("2\n1 3\n2 0");
        assert neighbors.get(1).toString().equals("2\n0 1\n2 3");
    }

    private static List<Board> asList(Iterable<Board> it) {
        List<Board> list = new ArrayList<>();
        for (Board b : it)
            list.add(b);
        return list;
    }

}
