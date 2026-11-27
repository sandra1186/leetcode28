import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int minPrice = Integer.MAX_VALUE;

        for (int[] item : items) {
            minPrice = Math.min(minPrice, item[1]);
        }

        int[] dp = new int[budget + 1];
        Arrays.fill(dp, -1000000);
        dp[0] = 0;

        for (int i = 0; i < n; i++) {
            int factor = items[i][0];
            int price = items[i][1];

            int bonus = 0;

            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % factor == 0) {
                    bonus++;
                }
            }

            int value = 1 + bonus;

            for (int b = budget; b >= price; b--) {
                if (dp[b - price] >= 0) {
                    dp[b] = Math.max(dp[b],
                            dp[b - price] + value);
                }
            }
        }

        int ans = 0;

        for (int b = 0; b <= budget; b++) {
            if (dp[b] >= 0) {
                int total = dp[b] + (budget - b) / minPrice;
                ans = Math.max(ans, total);
            }
        }

        return ans;
    }
}