import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private boolean[][] matrix;
    private int openSites;

    private final int topSite;
    private final int bottomSite;
    private final WeightedQuickUnionUF qFind;
    private final WeightedQuickUnionUF qFindBackwash;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        this.n = n;
        this.matrix = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.matrix[i][j] = false;
            }
        }
        this.topSite = this.n * this.n;
        this.bottomSite = this.n * this.n + 1;

        // qFind is an Object, containing all elements from matrix, plus virtual top and bottom sites
        this.qFind = new WeightedQuickUnionUF(this.n * this.n + 2);
        // qFindBackWash contains only matrix + virtual top site, to mitigate a backwash problem
        this.qFindBackwash = new WeightedQuickUnionUF(this.n * this.n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(final int row, final int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException();
        }
        // By the requirements we have to use row and col indices in range [1, n]. Whereas actual array indices are in [0, n)
        // THe hack below makes the code a bit readable.
        // All the methods are using row and col in [1, n]

        if (!this.matrix[row - 1][col - 1]) {
            this.matrix[row - 1][col - 1] = true;
            this.openSites++;

            int p = this.toOneDim(row, col);
            if (this.isInBorders(row - 1)) {
                if (this.isOpen(row - 1, col)) {
                    int q = this.toOneDim(row - 1, col);
                    this.qFind.union(p, q);
                    this.qFindBackwash.union(p, q);
                }
            } else {
                this.qFind.union(p, this.topSite);
                this.qFindBackwash.union(p, this.topSite);
            }
            if (this.isInBorders(row + 1)) {
                if (this.isOpen(row + 1, col)) {
                    int q = this.toOneDim(row + 1, col);
                    this.qFind.union(p, q);
                    this.qFindBackwash.union(p, q);
                }
            } else {
                this.qFind.union(p, this.bottomSite);
            }
            if (this.isInBorders(col - 1)) {
                if (this.isOpen(row, col - 1)) {
                    int q = this.toOneDim(row, col - 1);
                    this.qFind.union(p, q);
                    this.qFindBackwash.union(p, q);
                }
            }
            if (this.isInBorders(col + 1)) {
                if (this.isOpen(row, col + 1)) {
                    int q = this.toOneDim(row, col + 1);
                    this.qFind.union(p, q);
                    this.qFindBackwash.union(p, q);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(final int row, final int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException();
        }
        return this.matrix[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(final int row, final int col) {
        if (row < 1 || col < 1 || row > this.n || col > this.n) {
            throw new IllegalArgumentException();
        }
        return this.qFindBackwash.find(this.toOneDim(row, col)) == this.qFindBackwash.find(this.topSite);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.qFind.find(this.topSite) == this.qFind.find(this.bottomSite);
    }

    private int toOneDim(final int row, final int col) {
        return (row - 1) * this.n + (col - 1);
    }

    private boolean isInBorders(final int ord) {
        return (ord > 0 && ord <= this.n);
    }

    public static void main(String[] args) {

    }
}
