class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // Valid parentheses string must have even length
        if ((m + n - 1) % 2 != 0) {
            return false;
        }

        // dp[i][j][balance]
        boolean[][][] dp = new boolean[m][n][m + n];

        // Starting cell
        if (grid[0][0] == '(') {
            dp[0][0][1] = true;
        } else {
            return false;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                // Skip starting cell
                if (i == 0 && j == 0) {
                    continue;
                }

                for (int balance = 0; balance < m + n; balance++) {

                    // If current cell is '('
                    if (grid[i][j] == '(') {
                        if (i > 0 && balance > 0) {
                            dp[i][j][balance] =
                                dp[i][j][balance] ||
                                dp[i - 1][j][balance - 1];
                        }

                        if (j > 0 && balance > 0) {
                            dp[i][j][balance] =
                                dp[i][j][balance] ||
                                dp[i][j - 1][balance - 1];
                        }
                    }

                    // If current cell is ')'
                    else {
                        if (i > 0 && balance + 1 < m + n) {
                            dp[i][j][balance] =
                                dp[i][j][balance] ||
                                dp[i - 1][j][balance + 1];
                        }

                        if (j > 0 && balance + 1 < m + n) {
                            dp[i][j][balance] =
                                dp[i][j][balance] ||
                                dp[i][j - 1][balance + 1];
                        }
                    }
                }
            }
        }

        // Valid path must finish with balance 0
        return dp[m - 1][n - 1][0];
    }
}