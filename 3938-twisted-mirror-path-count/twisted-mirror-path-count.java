
class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int MOD = 1_000_000_007;

        long[][] right = new long[m][n];
        long[][] down = new long[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                long r = right[i][j];
                long d = down[i][j];

                // Starting position
                if (i == 0 && j == 0) {
                    r = 1;
                }

                // Mirror cell
                if (grid[i][j] == 1) {

                    // Entered while moving right:
                    // Reflect downward
                    if (r > 0 && i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + r) % MOD;
                    }

                    // Entered while moving down:
                    // Reflect right
                    if (d > 0 && j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + d) % MOD;
                    }

                } else {

                    // Empty cell: both directions are possible
                    long ways = (r + d) % MOD;

                    // Move right
                    if (j + 1 < n) {
                        right[i][j + 1] =
                            (right[i][j + 1] + ways) % MOD;
                    }

                    // Move down
                    if (i + 1 < m) {
                        down[i + 1][j] =
                            (down[i + 1][j] + ways) % MOD;
                    }
                }
            }
        }

        return (int)((right[m - 1][n - 1]
                    + down[m - 1][n - 1]) % MOD);
    }
}
