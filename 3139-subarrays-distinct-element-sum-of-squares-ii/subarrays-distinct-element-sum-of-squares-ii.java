
class Solution {
    private static final int MOD = 1_000_000_007;

    private long[] sum;
    private long[] sq;
    private long[] lazy;
    private int n;

    public int sumCounts(int[] nums) {
        n = nums.length;
        sum = new long[4 * n];
        sq = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        java.util.Arrays.fill(last, -1);

        long ans = 0;

        for (int i = 0; i < n; i++) {
            int x = nums[i];
            int prev = last[x];

            // Increase distinct counts for subarrays ending at i
            // whose starting positions are in [prev + 1, i].
            update(1, 0, n - 1, prev + 1, i);

            last[x] = i;

            // Sum of squared distinct counts for subarrays ending at i.
            ans = (ans + query(1, 0, n - 1, 0, i)) % MOD;
        }

        return (int) ans;
    }

    private void apply(int node, int l, int r, long v) {
        long len = r - l + 1;

        sq[node] = (sq[node]
                + 2L * v % MOD * sum[node] % MOD
                + len % MOD * v % MOD * v % MOD) % MOD;

        sum[node] = (sum[node] + len % MOD * v) % MOD;
        lazy[node] = (lazy[node] + v) % MOD;
    }

    private void push(int node, int l, int r) {
        if (lazy[node] == 0 || l == r) {
            return;
        }

        int mid = (l + r) / 2;

        apply(node * 2, l, mid, lazy[node]);
        apply(node * 2 + 1, mid + 1, r, lazy[node]);

        lazy[node] = 0;
    }

    private void update(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) {
            return;
        }

        if (ql <= l && r <= qr) {
            apply(node, l, r, 1);
            return;
        }

        push(node, l, r);

        int mid = (l + r) / 2;

        update(node * 2, l, mid, ql, qr);
        update(node * 2 + 1, mid + 1, r, ql, qr);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        sq[node] = (sq[node * 2] + sq[node * 2 + 1]) % MOD;
    }

    private long query(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) {
            return 0;
        }

        if (ql <= l && r <= qr) {
            return sq[node];
        }

        push(node, l, r);

        int mid = (l + r) / 2;

        long left = query(node * 2, l, mid, ql, qr);
        long right = query(node * 2 + 1, mid + 1, r, ql, qr);

        return (left + right) % MOD;
    }
}
