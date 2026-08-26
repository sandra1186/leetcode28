class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        int[] ones = new int[n];
        int count = 0;

        // Store positions of all 1s
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                ones[count++] = i;
            }
        }

        // Not enough 1s
        if (count < k) {
            return "";
        }

        int minLen = Integer.MAX_VALUE;
        String answer = "";

        // Consider every group of k consecutive 1s
        for (int i = 0; i <= count - k; i++) {
            int start = ones[i];
            int end = ones[i + k - 1];

            int len = end - start + 1;

            String current = s.substring(start, end + 1);

            if (len < minLen) {
                minLen = len;
                answer = current;
            } else if (len == minLen && current.compareTo(answer) < 0) {
                answer = current;
            }
        }

        return answer;
    }
}