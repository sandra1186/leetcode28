import java.util.*;

class Solution {
    public long maxAlternatingSum(int[] nums) {
        long[] a = new long[nums.length];
        for (int i = 0; i < nums.length; i++)
            a[i] = (long) nums[i] * nums[i];

        Arrays.sort(a);
        long ans = 0;
        for (int i = 0; i < a.length; i++)
            ans += (i < (a.length + 1) / 2 ? a[a.length - 1 - i] : 0);

        for (int i = 0; i < a.length / 2; i++)
            ans -= a[i];

        return ans;
    }
}