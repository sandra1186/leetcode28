class Solution {
    public String lexGreaterPermutation(String s, String target) {

        int[] count = new int[26];

        // Count characters in s
        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        StringBuilder prefix = new StringBuilder();

        for (int i = 0; i < target.length(); i++) {

            int t = target.charAt(i) - 'a';

            // If we can use the same character,
            // keep matching target.
            if (count[t] > 0) {
                count[t]--;
                prefix.append(target.charAt(i));
            } 
            else {

                // We cannot continue matching target.
                // Find the smallest character greater than target[i].
                for (int c = t + 1; c < 26; c++) {

                    if (count[c] > 0) {

                        prefix.append((char) ('a' + c));
                        count[c]--;

                        // Add remaining characters in sorted order
                        for (int j = 0; j < 26; j++) {
                            while (count[j] > 0) {
                                prefix.append((char) ('a' + j));
                                count[j]--;
                            }
                        }

                        return prefix.toString();
                    }
                }

                break;
            }
        }

        /*
         * We matched target completely.
         * But we need STRICTLY greater, so backtrack.
         */
        for (int i = prefix.length() - 1; i >= 0; i--) {

            // Put the character at position i back
            int current = prefix.charAt(i) - 'a';
            count[current]++;

            // Find the smallest character greater than it
            for (int c = current + 1; c < 26; c++) {

                if (count[c] > 0) {

                    StringBuilder answer = new StringBuilder();

                    // Prefix before i stays the same
                    answer.append(prefix.substring(0, i));

                    // Make position i slightly larger
                    answer.append((char) ('a' + c));
                    count[c]--;

                    // Put remaining characters in sorted order
                    for (int j = 0; j < 26; j++) {
                        while (count[j] > 0) {
                            answer.append((char) ('a' + j));
                            count[j]--;
                        }
                    }

                    return answer.toString();
                }
            }
        }

        return "";
    }
}