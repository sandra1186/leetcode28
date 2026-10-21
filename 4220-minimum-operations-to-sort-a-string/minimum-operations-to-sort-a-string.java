
class Solution {
    public int minOperations(String s) {
        int n = s.length();

        boolean sorted = true;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) < s.charAt(i - 1)) {
                sorted = false;
                break;
            }
        }
        if (sorted) return 0;

        if (n == 2) return -1;

        char minChar = 'z';
        char maxChar = 'a';

        for (int i = 0; i < n; i++) {
            minChar = (char) Math.min(minChar, s.charAt(i));
            maxChar = (char) Math.max(maxChar, s.charAt(i));
        }

        char first = s.charAt(0);
        char last = s.charAt(n - 1);

        if (first == minChar || last == maxChar) {
            return 1;
        }

        if (first == maxChar && last == minChar) {
            // Three operations are needed only when
            // both ends are extreme and neither can
            // be fixed directly in two operations.
            for (int i = 1; i < n - 1; i++) {
                if (s.charAt(i) == maxChar || s.charAt(i) == minChar) {
                    return 2;
                }
            }
            return 3;
        }

        return 2;
    }
}
