import java.util.*;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] freq = new int[n + 1];

        for (int[] x : items) {
            freq[x[0]]++;
        }

        int[] cnt = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j += i) {
                cnt[i] += freq[j];
            }
        }

        long minPrice = Long.MAX_VALUE;
        for (int[] x : items) {
            minPrice = Math.min(minPrice, x[1]);
        }

        long ans = 0;
        long rem = budget;

        Integer[] order = new Integer[n];
        for (int i = 0; i < n; i++) order[i] = i;

        Arrays.sort(order, (a, b) ->
            Integer.compare(items[a][1], items[b][1]));

        for (int idx : order) {
            long price = items[idx][1];

            if (price >= 2 * minPrice) break;

            long free = cnt[items[idx][0]] - 1;
            long buy = Math.min(free, rem / price);

            ans += 2 * buy;
            rem -= buy * price;
        }

        ans += rem / minPrice;

        return (int) ans;
    }
}