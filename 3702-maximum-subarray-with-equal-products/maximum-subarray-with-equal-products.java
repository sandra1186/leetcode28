
class Solution {
    public int maxLength(int[] nums) {
        int n = nums.length;
        int ans = 1;

        for (int i = 0; i < n; i++) {
            long prod = 1;
            long lcm = 1;
            int gcd = 0;

            for (int j = i; j < n; j++) {
                prod *= nums[j];

                gcd = gcd(gcd, nums[j]);

                lcm = lcm(lcm, nums[j]);

                if (prod == lcm * gcd) {
                    ans = Math.max(ans, j - i + 1);
                }
            }
        }

        return ans;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private long lcm(long a, long b) {
        return (a / gcd((int)a, (int)b)) * b;
    }
}
