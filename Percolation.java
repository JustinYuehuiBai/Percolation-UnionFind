import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;                               // stores dimension
    private int numNodes;                        // stores number of spaces
    private WeightedQuickUnionUF grid;           // constructor
    private WeightedQuickUnionUF gridFull;       // constructor for full
    private boolean[] isOpen;                    // boolean of open sites

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1)
            throw new IllegalArgumentException("Invalid Size");

        this.n = n;
        numNodes = n * n + 2;
        grid = new WeightedQuickUnionUF(numNodes);
        gridFull = new WeightedQuickUnionUF(numNodes);
        isOpen = new boolean[numNodes];

        for (int i = 0; i < numNodes; i++) {
            isOpen[i] = false;
        }
        isOpen[0] = true;
        isOpen[numNodes - 1] = true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n)
            throw new IllegalArgumentException("Invalid Row");
        if (col < 1 || col > n)
            throw new IllegalArgumentException("Invalid Column");


        int convert = (row - 1) * n + col;
        isOpen[convert] = true;

        if (row == 1) {
            grid.union(0, convert);
            gridFull.union(0, convert);
        }
        else if (isOpen[convert - n]) {
            grid.union(convert - n, convert);
            gridFull.union(convert - n, convert);
        }

        if (row == n) {
            grid.union(numNodes - 1, convert);
        }
        else if (isOpen[convert + n]) {
            grid.union(convert + n, convert);
            gridFull.union(convert + n, convert);
        }

        if (col != 1 && isOpen[convert - 1]) {
            grid.union(convert - 1, convert);
            gridFull.union(convert - 1, convert);
        }

        if (col != n && isOpen[convert + 1]) {
            grid.union(convert + 1, convert);
            gridFull.union(convert + 1, convert);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n)
            throw new IllegalArgumentException("Invalid Row");
        if (col < 1 || col > n)
            throw new IllegalArgumentException("Invalid Column");

        int convert = (row - 1) * n + col;
        return (isOpen[convert]);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n)
            throw new IllegalArgumentException("Invalid Row");
        if (col < 1 || col > n)
            throw new IllegalArgumentException("Invalid Column");

        int convert = (row - 1) * n + col;
        return (grid.find(0) == grid.find(convert));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int sum = 0;
        for (int i = 1; i < numNodes - 1; i++) {
            if (isOpen[i])
                sum++;
        }
        return sum;
    }

    // does the system percolate?
    public boolean percolates() {
        return (grid.find(0) == grid.find(numNodes - 1));
    }
}
