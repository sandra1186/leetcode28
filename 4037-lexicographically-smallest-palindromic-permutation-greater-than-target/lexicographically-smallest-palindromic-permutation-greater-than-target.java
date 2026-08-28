class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] count = new int[26];

        // Count characters in s
        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }

        // A palindrome can have at most one odd-frequency character
        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        // Build the multiset for the first half
        int halfLen = n / 2;
        int[] halfCount = new int[26];

        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
        }

        // Target's first half
        String targetHalf = target.substring(0, halfLen);

        // Check whether targetHalf itself can be formed
        boolean possible = true;
        int[] temp = halfCount.clone();

        for (int i = 0; i < halfLen; i++) {
            int c = targetHalf.charAt(i) - 'a';

            if (temp[c] == 0) {
                possible = false;
                break;
            }

            temp[c]--;
        }

        // If targetHalf is a valid half, build its palindrome
        // and check whether it is already greater than target.
        if (possible) {
            String candidate = buildPalindrome(targetHalf, middle);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        // Otherwise, find the smallest permutation of the half
        // that is strictly greater than targetHalf.
        String greaterHalf = findNextHalf(targetHalf, halfCount);

        if (greaterHalf == null) {
            return "";
        }

        return buildPalindrome(greaterHalf, middle);
    }

    private String findNextHalf(String targetHalf, int[] originalCount) {
        int n = targetHalf.length();

        // prefixCount[i][c] = number of character c used
        // in targetHalf[0 ... i-1]
        int[][] prefixCount = new int[n + 1][26];

        for (int i = 0; i < n; i++) {
            for (int c = 0; c < 26; c++) {
                prefixCount[i + 1][c] = prefixCount[i][c];
            }

            prefixCount[i + 1][targetHalf.charAt(i) - 'a']++;
        }

        /*
         * We want:
         *
         * targetHalf[0..i-1] + bigger character + smallest suffix
         *
         * Try the rightmost position first.
         */
        for (int i = n - 1; i >= 0; i--) {

            // Characters remaining after using targetHalf[0..i-1]
            int[] remaining = originalCount.clone();

            boolean validPrefix = true;

            for (int j = 0; j < i; j++) {
                int c = targetHalf.charAt(j) - 'a';

                if (remaining[c] == 0) {
                    validPrefix = false;
                    break;
                }

                remaining[c]--;
            }

            if (!validPrefix) {
                continue;
            }

            int current = targetHalf.charAt(i) - 'a';

            // Try the smallest available character greater than current
            for (int c = current + 1; c < 26; c++) {
                if (remaining[c] > 0) {

                    StringBuilder result = new StringBuilder();

                    // Equal prefix
                    result.append(targetHalf.substring(0, i));

                    // First larger character
                    result.append((char) ('a' + c));

                    remaining[c]--;

                    // Fill the rest with smallest characters
                    for (int x = 0; x < 26; x++) {
                        while (remaining[x] > 0) {
                            result.append((char) ('a' + x));
                            remaining[x]--;
                        }
                    }

                    return result.toString();
                }
            }
        }

        return null;
    }

    private String buildPalindrome(String half, char middle) {
        StringBuilder result = new StringBuilder();

        result.append(half);

        if (middle != 0) {
            result.append(middle);
        }

        result.append(new StringBuilder(half).reverse());

        return result.toString();
    }
}