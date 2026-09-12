import java.util.*;

class Solution {

    public int[] maximumWeight(List<List<Integer>> intervals) {

        int n = intervals.size();

        // [left, right, weight, originalIndex]
        int[][] arr = new int[n][4];

        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        // Sort by right endpoint
        Arrays.sort(arr, (a, b) -> {
            if (a[1] != b[1]) {
                return Integer.compare(a[1], b[1]);
            }

            return Integer.compare(a[0], b[0]);
        });

        // Find previous non-overlapping interval
        int[] prev = new int[n];

        for (int i = 0; i < n; i++) {

            int left = arr[i][0];

            int low = 0;
            int high = i - 1;
            int answer = -1;

            while (low <= high) {

                int mid = low + (high - low) / 2;

                // Strictly less because touching boundaries overlap
                if (arr[mid][1] < left) {
                    answer = mid;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            prev[i] = answer;
        }

        // dp[k][i] = best answer using first i intervals
        State[][] dp = new State[5][n + 1];

        // Initialize all states
        for (int k = 0; k <= 4; k++) {
            for (int i = 0; i <= n; i++) {
                dp[k][i] = new State(0L, new int[0]);
            }
        }

        // DP
        for (int k = 1; k <= 4; k++) {

            for (int i = 1; i <= n; i++) {

                // Option 1: Don't take current interval
                State skip = dp[k][i - 1];

                // Option 2: Take current interval
                int current = i - 1;

                State before = dp[k - 1][prev[current] + 1];

                long score = before.score + arr[current][2];

                int[] indices =
                    Arrays.copyOf(
                        before.indices,
                        before.indices.length + 1
                    );

                indices[indices.length - 1] = arr[current][3];

                // Sort original indices
                Arrays.sort(indices);

                State take = new State(score, indices);

                dp[k][i] = better(skip, take);
            }
        }

        return dp[4][n].indices;
    }

    // Choose better state
    private State better(State a, State b) {

        // Higher score wins
        if (a.score > b.score) {
            return a;
        }

        if (b.score > a.score) {
            return b;
        }

        // Same score -> lexicographically smaller
        if (lexicographicallySmaller(a.indices, b.indices)) {
            return a;
        }

        return b;
    }

    // Check which array is lexicographically smaller
    private boolean lexicographicallySmaller(int[] a, int[] b) {

        int len = Math.min(a.length, b.length);

        for (int i = 0; i < len; i++) {

            if (a[i] < b[i]) {
                return true;
            }

            if (a[i] > b[i]) {
                return false;
            }
        }

        // If one is a prefix, shorter is smaller
        return a.length < b.length;
    }

    static class State {

        long score;       // IMPORTANT: long
        int[] indices;

        State(long score, int[] indices) {
            this.score = score;
            this.indices = indices;
        }
    }
}