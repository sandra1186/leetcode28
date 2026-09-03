class Solution {
    public boolean uniformArray(int[] nums1) {
        int min = nums1[0];
        int minOdd = Integer.MAX_VALUE;

        for (int num : nums1) {
            min = Math.min(min, num);

            if (num % 2 != 0) {
                minOdd = Math.min(minOdd, num);
            }
        }

        for (int num : nums1) {
            if (num % 2 != min % 2) {
                // Need to subtract an odd number smaller than num
                if (minOdd >= num) {
                    return false;
                }
            }
        }

        return true;
    }
}