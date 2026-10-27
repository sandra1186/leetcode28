class Solution {
    public String[] createGrid(int m, int n) {
        String[] grid = new String[m];

        for (int i = 0; i < m; i++) {
            char[] row = new char[n];
            java.util.Arrays.fill(row, '#');
            grid[i] = new String(row);
        }

        // Make the first row free.
        char[] firstRow = new char[n];
        java.util.Arrays.fill(firstRow, '.');
        grid[0] = new String(firstRow);

        // Make the last column free.
        for (int i = 1; i < m; i++) {
            char[] row = grid[i].toCharArray();
            row[n - 1] = '.';
            grid[i] = new String(row);
        }

        return grid;
    }
}