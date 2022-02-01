/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] perc;
    private static final int TOP = 0;
    private final int bottom;
    private final int size;
    private int opensites;
    private final WeightedQuickUnionUF wf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        bottom = n * n + 1;
        wf = new WeightedQuickUnionUF(n * n + 2);
        perc = new boolean[size][size];
        opensites = 0;
    }

    private void checkE(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException();
        }
    }

    private int NoInArr(int row, int col) {
        return size * (row - 1) + col;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkE(row, col);
        if (isOpen(row, col)) {
            return;
        }
        perc[row - 1][col - 1] = true;
        ++opensites;
        if (row == 1) {
            wf.union(NoInArr(row, col), TOP);
        }
        if (row == size) {
            wf.union(NoInArr(row, col), bottom);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            wf.union(NoInArr(row, col), NoInArr(row - 1, col));
        }
        if (row < size && isOpen(row + 1, col)) {
            wf.union(NoInArr(row, col), NoInArr(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            wf.union(NoInArr(row, col), NoInArr(row, col - 1));
        }
        if (col < size && isOpen(row, col + 1)) {
            wf.union(NoInArr(row, col), NoInArr(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkE(row, col);
        return perc[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row > 0 && row <= size && col > 0 && col <= size) {
            return wf.find(TOP) == wf.find(NoInArr(row, col));
        }
        else throw new IllegalArgumentException();
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opensites;
    }

    // does the system percolate?
    public boolean percolates() {
        return wf.find(TOP) == wf.find(bottom);
    }
}

