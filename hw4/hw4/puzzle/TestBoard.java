package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestBoard {

    @Test
    public void verifyImmutability() {
        int r = 2;
        int c = 2;
        int[][] x = new int[r][c];
        int cnt = 0;
        for (int i = 0; i < r; i += 1) {
            for (int j = 0; j < c; j += 1) {
                x[i][j] = cnt;
                cnt += 1;
            }
        }
        Board b = new Board(x);
        assertEquals("Your Board class is not being initialized with the right values.", 0, b.tileAt(0, 0));
        assertEquals("Your Board class is not being initialized with the right values.", 1, b.tileAt(0, 1));
        assertEquals("Your Board class is not being initialized with the right values.", 2, b.tileAt(1, 0));
        assertEquals("Your Board class is not being initialized with the right values.", 3, b.tileAt(1, 1));

        x[1][1] = 1000;
        assertEquals("Your Board class is mutable and you should be making a copy of the values in the passed tiles array. Please see the FAQ!", 3, b.tileAt(1, 1));
    }

    @Test
    public void testNeighbors() {
        int N = 2;
        int[][] x = new int[N][N];
        x[0][0] = 2;
        x[0][1] = 1;
        x[1][0] = 3;
        x[1][1] = 0;
        Board b = new Board(x);
        Iterable<WorldState> neighbors = b.neighbors();
        System.out.println("Hold on!");
    }
    @Test
    public void testHamming() {
        int N = 2;
        int[][] x = new int[N][N];
        x[0][0] = 2;
        x[0][1] = 1;
        x[1][0] = 3;
        x[1][1] = 0;
        Board b = new Board(x);
        assertEquals(2, b.hamming());
        System.out.println("Hold on!");
    }
    @Test
    public void testManhattan() {
        int N = 3;
        int[][] x = new int[N][N];
        x[0][0] = 4;
        x[0][1] = 8;
        x[0][2] = 3;
        x[1][0] = 2;
        x[1][1] = 7;
        x[1][2] = 1;
        x[2][0] = 0;
        x[2][1] = 5;
        x[2][2] = 6;
        Board b = new Board(x);
        assertEquals(14, b.manhattan());
        System.out.println("Hold on!");
    }
} 
