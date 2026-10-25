import java.util.*;

class Solution {
    public List<String> restoreIpAddresses(String s) {
        List<String> result = new ArrayList<>();
        backtrack(s, 0, 0, new ArrayList<>(), result);
        return result;
    }

    private void backtrack(String s, int index, int parts,
                           List<String> current, List<String> result) {
        if (parts == 4) {
            if (index == s.length()) {
                result.add(String.join(".", current));
            }
            return;
        }

        for (int len = 1; len <= 3 && index + len <= s.length(); len++) {
            String segment = s.substring(index, index + len);

            // Leading zeros are not allowed
            if (len > 1 && segment.charAt(0) == '0') {
                break;
            }

            // Each segment must be between 0 and 255
            int value = Integer.parseInt(segment);
            if (value > 255) {
                break;
            }

            current.add(segment);
            backtrack(s, index + len, parts + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
}