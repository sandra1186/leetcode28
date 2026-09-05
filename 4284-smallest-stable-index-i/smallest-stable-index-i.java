class Solution {
    public int firstStableIndex(int[] nums, int k) {

        int n = nums.length;

        int[] rightMin = new int[n];

        // Minimum from the right
        rightMin[n - 1] = nums[n - 1];

        for (int i = n - 2; i >= 0; i--) {
            rightMin[i] = Math.min(nums[i], rightMin[i + 1]);
        }

        // Maximum from the left
        int leftMax = nums[0];

        for (int i = 0; i < n; i++) {

            leftMax = Math.max(leftMax, nums[i]);

            int instability = leftMax - rightMin[i];

            if (instability <= k) {
                return i;
            }
        }

        return -1;
    }
}