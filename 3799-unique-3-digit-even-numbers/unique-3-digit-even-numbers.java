class Solution {
    public int totalNumbers(int[] digits) {

        int count = 0;

        for (int num = 100; num <= 998; num++) {

            // Check if number is even
            if (num % 2 != 0) {
                continue;
            }

            int[] required = new int[10];

            int temp = num;

            // Get the three digits
            required[temp % 10]++;
            temp = temp / 10;

            required[temp % 10]++;
            temp = temp / 10;

            required[temp % 10]++;

            // Count available digits
            int[] available = new int[10];

            for (int digit : digits) {
                available[digit]++;
            }

            // Check if we have enough copies
            boolean possible = true;

            for (int i = 0; i < 10; i++) {
                if (required[i] > available[i]) {
                    possible = false;
                    break;
                }
            }

            if (possible) {
                count++;
            }
        }

        return count;
    }
}