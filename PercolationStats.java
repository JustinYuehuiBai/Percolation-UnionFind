import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] trialStats;            // array of averages
    private double trialCount;              // count of trials

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1)
            throw new IllegalArgumentException("invalid size");
        if (trials < 1)
            throw new IllegalArgumentException("invalid number of trials");

        trialStats = new double[trials];
        trialCount = trials;

        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);
            while (!test.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                test.open(row, col);
            }
            trialStats[i] = (double) test.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialStats);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialStats);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trialCount));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trialCount));
    }

    // test client (see below)
    public static void main(String[] args) {
        int dimension = 2;
        int numTrials = 10000;
        PercolationStats a = new PercolationStats(dimension, numTrials);

        String confidence = a.confidenceLo() + ", " + a.confidenceHi();
        System.out.println("mean =                     " + a.mean());
        System.out.println("stddev =                   " + a.stddev());
        System.out.println("95% confidence interval =  [" + confidence + "]");


    }


}
