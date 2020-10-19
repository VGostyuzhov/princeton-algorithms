import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private final double mean;
    private final double stddev;
    private final double confidenceLo;
    private final double confidenceHi;
    private final double cfdCriticalValue = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(final int n, final int trials) {
        double[] results;
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }

        results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int a = StdRandom.uniform(1, n + 1);
                int b = StdRandom.uniform(1, n + 1);
                percolation.open(a, b);
            }
            results[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }

        this.mean = StdStats.mean(results);
        this.stddev = StdStats.stddev(results);
        this.confidenceLo = this.mean - this.cfdCriticalValue * (this.stddev / Math.sqrt(trials));
        this.confidenceHi = this.mean + this.cfdCriticalValue * (this.stddev / Math.sqrt(trials));
    }

    // sample mean of percolation threshold
    public double mean() {
        return this.mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return this.stddev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.confidenceHi;
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch watch = new Stopwatch();
        int n = 0;
        int trials = 0;
        if (args.length > 0) {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);

            PercolationStats percStats = new PercolationStats(n, trials);
            System.out.print(String.format("%-26s = %6f\n", "mean", percStats.mean()));
            System.out.print(String.format("%-26s = %6f\n", "stddev", percStats.stddev()));
            System.out.print(String.format("%-26s = [%6f, %6f]\n", "95% confidence interval", percStats.confidenceLo(), percStats.confidenceHi()));
        } else {
            System.out.print("Provide arguments for grid size n and number of trials\n");
        }

        //System.out.print(String.format("%-26s = %6f\n", "Elapsed time", watch.elapsedTime()));
    }

}
