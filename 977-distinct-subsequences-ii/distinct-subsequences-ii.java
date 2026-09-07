class Solution {
    public int distinctSubseqII(String s) {
        final long MOD = 1_000_000_007L;

        long[] last = new long[26];

        long dp = 1;
        char[] c = s.toCharArray();
        for (int i=0;i<c.length;i++ ) {
            int idx = c[i] - 'a';

            long newDp = (2 * dp % MOD - last[idx] + MOD) % MOD;

            last[idx] = dp;

            dp = newDp;
        }
        return (int) ((dp - 1 + MOD) % MOD);
    }
}