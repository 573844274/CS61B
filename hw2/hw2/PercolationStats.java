package hw2;

import edu.princeton.cs.introcs.StdDraw;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

import java.util.ArrayList;

public class PercolationStats {
    // perform T independent experiments on an N-by-N grid
    private double[] simulation;
    private int N;
    private int T;

    public PercolationStats(int N, int T, PercolationFactory pf)  {
        if (N <= 0) {
            throw new IllegalArgumentException("N has to be positive");
        }
        if (T <= 0) {
            throw new IllegalArgumentException("T has to be positive");
        }
        this.N = N;
        this.T = T;
        simulation = new double[T];
        for (int i = 0; i < T; i += 1) {
            Percolation per = pf.make(N);
            while (!per.percolates()) {
                per.open(StdRandom.uniform(0, N), StdRandom.uniform(0, N));
            }
            simulation[i] = (double) per.numberOfOpenSites() / (double) (N * N);
        }
    }
    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(simulation);
    }
    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(simulation);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
