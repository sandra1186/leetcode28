
import java.util.*;

class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;

        long[] cost = new long[n - x + 1];

        TreeMap<Integer, Integer> left = new TreeMap<>();
        TreeMap<Integer, Integer> right = new TreeMap<>();

        long leftSum = 0;
        long rightSum = 0;
        int leftSize = 0, rightSize = 0;

        for (int i = 0; i < n; i++) {

            int val = nums[i];

            if (leftSize == 0 || val <= left.lastKey()) {
                left.merge(val, 1, Integer::sum);
                leftSum += val;
                leftSize++;
            } else {
                right.merge(val, 1, Integer::sum);
                rightSum += val;
                rightSize++;
            }

            // Balance the two halves
            while (leftSize > (x + 1) / 2) {
                int v = left.lastKey();
                remove(left, v);
                leftSum -= v;
                leftSize--;

                right.merge(v, 1, Integer::sum);
                rightSum += v;
                rightSize++;
            }

            while (leftSize < (x + 1) / 2 && rightSize > 0) {
                int v = right.firstKey();
                remove(right, v);
                rightSum -= v;
                rightSize--;

                left.merge(v, 1, Integer::sum);
                leftSum += v;
                leftSize++;
            }

            // Remove element outside the window
            if (i >= x) {
                int old = nums[i - x];

                if (left.containsKey(old)) {
                    remove(left, old);
                    leftSum -= old;
                    leftSize--;
                } else {
                    remove(right, old);
                    rightSum -= old;
                    rightSize--;
                }

                while (leftSize > (x + 1) / 2) {
                    int v = left.lastKey();
                    remove(left, v);
                    leftSum -= v;
                    leftSize--;

                    right.merge(v, 1, Integer::sum);
                    rightSum += v;
                    rightSize++;
                }

                while (leftSize < (x + 1) / 2 && rightSize > 0) {
                    int v = right.firstKey();
                    remove(right, v);
                    rightSum -= v;
                    rightSize--;

                    left.merge(v, 1, Integer::sum);
                    leftSum += v;
                    leftSize++;
                }
            }

            if (i >= x - 1) {
                long median = left.lastKey();

                cost[i - x + 1] =
                    median * leftSize - leftSum
                    + rightSum - median * rightSize;
            }
        }

        // Dynamic programming
        long INF = Long.MAX_VALUE / 4;
        long[][] dp = new long[n + 1][k + 1];

        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], INF);
        }

        for (int i = 0; i <= n; i++) {
            dp[i][0] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {

                dp[i][j] = dp[i - 1][j];

                if (i >= x && dp[i - x][j - 1] != INF) {
                    dp[i][j] = Math.min(
                        dp[i][j],
                        dp[i - x][j - 1] + cost[i - x]
                    );
                }
            }
        }

        return dp[n][k];
    }

    private void remove(TreeMap<Integer, Integer> map, int val) {
        int count = map.get(val);

        if (count == 1) {
            map.remove(val);
        } else {
            map.put(val, count - 1);
        }
    }
}
