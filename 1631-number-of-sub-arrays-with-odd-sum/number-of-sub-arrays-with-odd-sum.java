
class Solution {
    public int numOfSubarrays(int[] arr) {
        int mod = 1000000007;

        long even = 1; // Empty prefix sum is even
        long odd = 0;
        long result = 0;

        int prefix = 0;

        for (int num : arr) {
            prefix += num;

            if (prefix % 2 == 0) {
                result = (result + odd) % mod;
                even++;
            } else {
                result = (result + even) % mod;
                odd++;
            }
        }

        return (int) result;
    }
}
