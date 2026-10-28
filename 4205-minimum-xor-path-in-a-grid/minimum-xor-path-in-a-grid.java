class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int K = 1024;

        boolean[][] dp = new boolean[n][K];
        dp[0][grid[0][0]] = true;

        for (int i = 0; i < m; i++) {
            boolean[][] curr = new boolean[n][K];

            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) {
                    curr[j][grid[0][0]] = true;
                    continue;
                }

                int val = grid[i][j];

                if (i > 0) {
                    for (int x = 0; x < K; x++) {
                        if (dp[j][x]) {
                            curr[j][x ^ val] = true;
                        }
                    }
                }

                if (j > 0) {
                    for (int x = 0; x < K; x++) {
                        if (curr[j - 1][x]) {
                            curr[j][x ^ val] = true;
                        }
                    }
                }
            }

            dp = curr;
        }

        for (int x = 0; x < K; x++) {
            if (dp[n - 1][x]) {
                return x;
            }
        }

        return 0;
    }
}