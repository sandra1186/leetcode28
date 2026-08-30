class Solution {
    public int minimumDeletions(int[] nums) {
        int n = nums.length;

        int minIndex = 0;
        int maxIndex = 0;

        // Find minimum and maximum indices
        for (int i = 1; i < n; i++) {
            if (nums[i] < nums[minIndex]) {
                minIndex = i;
            }

            if (nums[i] > nums[maxIndex]) {
                maxIndex = i;
            }
        }

        // Case 1: Remove both from the front
        int front = Math.max(minIndex, maxIndex) + 1;

        // Case 2: Remove both from the back
        int back = n - Math.min(minIndex, maxIndex);

        // Case 3: Remove one from front and one from back
        int both = Math.min(minIndex, maxIndex) + 1
                 + n - Math.max(minIndex, maxIndex);

        return Math.min(front, Math.min(back, both));
    }
}