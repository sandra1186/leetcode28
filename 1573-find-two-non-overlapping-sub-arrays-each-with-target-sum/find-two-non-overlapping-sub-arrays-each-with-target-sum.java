class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int INF = 1000000000;

        int[] best = new int[n];

        int left = 0;
        int sum = 0;
        int minLength = INF;
        int answer = INF;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target) {
                sum -= arr[left];
                left++;
            }

            if (sum == target) {
                int currentLength = right - left + 1;

                // Check for a previous non-overlapping subarray
                if (left > 0 && best[left - 1] != INF) {
                    answer = Math.min(
                        answer,
                        currentLength + best[left - 1]
                    );
                }

                minLength = Math.min(minLength, currentLength);
            }

            // Store the shortest valid subarray found so far
            if (right == 0) {
                best[right] = minLength;
            } else {
                best[right] = Math.min(best[right - 1], minLength);
            }
        }

        return answer == INF ? -1 : answer;
    }
}