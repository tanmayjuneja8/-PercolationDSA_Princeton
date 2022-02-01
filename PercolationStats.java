import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */
public class PercolationStats {
    private int count;
    private final int size;
    private final double[] mean1;
    private double x;
    private double y;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        size = trials;
        mean1 = new double[size];
        int i = 0;
        while (i < size) {
            Percolation p = new Percolation(n);
            int opensites = 0;
            while (!p.percolates()) {
                int j = StdRandom.uniform(1, n + 1);
                int k = StdRandom.uniform(1, n + 1);
                if (!p.isOpen(j, k)) {
                    p.open(j, k);
                    opensites++;
                }
            }
            mean1[i++] = (double) opensites / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(mean1);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(mean1);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double z = ((1.96 * stddev()) / Math.sqrt(size));
        return mean() - z;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double z = ((1.96 * stddev()) / Math.sqrt(size));
        return mean() + z;
    }

    // test client (see below)
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(x, y);
        StdOut.println(ps.mean());
        StdOut.println(ps.stddev());
        StdOut.print("[" + ps.confidenceLo() + " , " + ps.confidenceHi() + "]");
    }

}
