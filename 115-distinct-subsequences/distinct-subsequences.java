class Solution {
    public int numDistinct(String s, String t) {

        int m = s.length();
        int n = t.length();

        int[][] dp = new int[m + 1][n + 1];

        // Empty string t can be formed in exactly 1 way
        dp[0][0] = 1;

        // If t is empty, there is always 1 subsequence
        for (int i = 0; i <= m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {

                if (s.charAt(i - 1) == t.charAt(j - 1)) {

                    // Two choices:
                    // 1. Use this character
                    // 2. Don't use this character
                    dp[i][j] = dp[i - 1][j - 1] + dp[i - 1][j];

                } else {

                    // Characters don't match, so skip s character
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }

        return dp[m][n];
    }
}