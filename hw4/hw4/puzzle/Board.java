package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;


public class Board implements WorldState {
    private int[][] board;
    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        int[][] b = new int[tiles.length][];
        for (int i = 0; i < tiles.length; i += 1) {
            b[i] = tiles[i].clone();
        }
        board = b;
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     * @param i
     * @param j
     * @return
     */
    public int tileAt(int i, int j) {
        if (!inBound(i, j)) {
            throw new IndexOutOfBoundsException("i, j cannot exceed [0, N)");
        }
        return board[i][j];
    }

    /**
     * Returns the board size N
     * @return
     */
    public int size() {
        return board.length;
    }

    /**
     * Returns the neighbors of the current board
     * @return
     * Copy Josh Hug's.
     */
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                //if (tileAt(rug, tug) == BLANK) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    //ili1li1[l11il][lil1il1] = BLANK;
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    //ili1li1[bug][zug] = BLANK;
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }

    /**
     * Hamming estimate described below
     * @return
     */
    public int hamming() {
        int N = size();
        int distance = 0;
        for (int i = 1; i < N * N; i += 1) {
            Position correctPosition = correctPosition(i);
            if (tileAt(correctPosition.row, correctPosition.col) != i) {
                distance += 1;
            }
        }
        return distance;
    }

    /**
     * Manhattan estimate described below
     * @return
     */
    public int manhattan() {
        int N = size();
        int distance = 0;
        for (int i = 0; i < N; i += 1) {
            for (int j = 0; j < N; j += 1) {
                int num = tileAt(i, j);
                if (num == 0) {
                    continue;
                }
                Position currentPosition = new Position(i, j);
                Position correctPosition = correctPosition(num);
                distance += correctPosition.manhattanDistances(currentPosition);
            }
        }
        return distance;
    }

    /**
     * Estimated distance to goal. This method should
     *               simply return the results of manhattan() when submitted to
     *               Gradescope.
     * @return
     */
    @Override
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (that.size() != this.size()) {
            return false;
        }
        for (int i = 0; i < size(); i += 1) {
            for (int j = 0; j < size(); j += 1) {
                if (this.tileAt(i, j) != that.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public int hashCode() {
        return board[0][0];
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    /**
     * The correct tile at position i, j.
     */
    /*private int correctTile(int i, int j) {
        int N = size();
        return 0;
    }*/

    private boolean inBound(int i) {
        return i >= 0 && i <= size() - 1;
    }

    private boolean inBound(int i, int j) {
        return inBound(i) && inBound(j);
    }

    /**
     * The correct position i, j of tile n.
     */
    private Position correctPosition(int n) {
        int N = size();
        if (n == 0) {
            return new Position(N - 1, N - 1);
        }
        int quotient = (n - 1) / N;
        int remainder = (n - 1) % N;
        return new Position(quotient, remainder);
    }

    private class Position {
        int row;
        int col;

        Position(int i, int j) {
            row = i;
            col = j;
        }

        public int manhattanDistances(Position p) {
            int rowDistance = Math.abs(row - p.row);
            int colDistance = Math.abs(col - p.col);
            return rowDistance + colDistance;
        }
    }

}
