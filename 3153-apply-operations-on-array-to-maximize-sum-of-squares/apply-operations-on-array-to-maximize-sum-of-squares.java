import java.util.*;

class Solution {
    public int maxSum(List<Integer> nums, int k) {
        int MOD = 1000000007;
        int[] bits = new int[32];

        for (int num : nums) {
            for (int i = 0; i < 32; i++) {
                if ((num & (1 << i)) != 0) {
                    bits[i]++;
                }
            }
        }

        long ans = 0;

        for (int j = 0; j < k; j++) {
            long num = 0;

            for (int i = 31; i >= 0; i--) {
                if (bits[i] > 0) {
                    num |= (1L << i);
                    bits[i]--;
                }
            }

            ans = (ans + num * num) % MOD;
        }

        return (int) ans;
    }
}