package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.awt.*;
import java.lang.*;
import java.util.ArrayList;

public class Percolation {
    private int N;
    private int[][] sites;
    private WeightedQuickUnionUF sitesUnion;
    private WeightedQuickUnionUF isFullSitesUnion;
    private int numberOfOpenSites;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N has to be positive");
        }
        this.N = N;
        sites = new int [N][N];
        sitesUnion = new WeightedQuickUnionUF(N * N + 2);
        isFullSitesUnion = new WeightedQuickUnionUF(N * N + 1);
        numberOfOpenSites = 0;
    }
    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) {
            return;
        }
        if (!isInBoundN(row) || !isInBoundN(col)) {
            throw new IndexOutOfBoundsException("The index is out of bound.");
        }
        if (row == 0) {
            sitesUnion.union(unionIndex(row, col), N * N);
            isFullSitesUnion.union(unionIndex(row, col), N * N);
        }
        if (row == N - 1) {
            sitesUnion.union(unionIndex(row, col), N * N + 1);
        }

        sites[row][col] = 1;
        numberOfOpenSites += 1;
        getAdjacentConnected(row, col);
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isInBoundN(row) || !isInBoundN(col)) {
            throw new IndexOutOfBoundsException("The index is out of bound.");
        }
        return sites[row][col] == 1;
    }
    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isInBoundN(row) || !isInBoundN(col)) {
            throw new IndexOutOfBoundsException("The index is out of bound.");
        }
        return isFullSitesUnion.connected(unionIndex(row, col), N * N);
    }
    // number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }
    // does the system percolate?
    public boolean percolates() {
        return sitesUnion.connected(N * N, N * N + 1);
    }
    // use for unit testing (not required)
    public static void main(String[] args) {
        Percolation per = new Percolation(10);
        int[][] sites = per.sites;
        System.out.println(sites[3][3]);
    }

    // check if n >= 0 and n <= N - 1
    private boolean isInBoundN(int n) {
        return n >= 0 && n <= N - 1;
    }

    private boolean isInBoundN(int row, int col) {
        return isInBoundN(row) && isInBoundN(col);
    }
    // The index of (row, col) in the sitesUnion
    private int unionIndex(int row, int col) {
        return row * N + col;
    }

    // return indexes of adjacent open sites. If such site does not exist, return -1.
    private ArrayList<Integer> adjacentOpenIndexes(int row, int col) {
        ArrayList<Integer> adjacentIndexes = new ArrayList<Integer>();
        if (isInBoundN(row, col + 1)) {
            if (isOpen(row, col + 1)) {
                adjacentIndexes.add(unionIndex(row, col + 1));
            }
        }
        if (isInBoundN(row, col - 1)) {
            if (isOpen(row, col - 1)) {
                adjacentIndexes.add(unionIndex(row, col - 1));
            }
        }
        if (isInBoundN(row + 1, col)) {
            if (isOpen(row + 1, col)) {
                adjacentIndexes.add(unionIndex(row + 1, col));
            }
        }
        if (isInBoundN(row - 1, col)) {
            if (isOpen(row - 1, col)) {
                adjacentIndexes.add(unionIndex(row - 1, col));
            }
        }
        return adjacentIndexes;
    }
    // When (row, col) is opened, we need to connect the adjacent areas
    public void getAdjacentConnected(int row, int col) {
        ArrayList<Integer> adjacentIndexes = adjacentOpenIndexes(row, col);
        for (int index : adjacentIndexes) {
            sitesUnion.union(unionIndex(row, col), index);
            isFullSitesUnion.union(unionIndex(row, col), index);
        }
    }
}
