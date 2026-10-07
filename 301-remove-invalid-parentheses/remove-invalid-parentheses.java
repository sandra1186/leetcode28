import java.util.*;

class Solution {

    public List<String> removeInvalidParentheses(String s) {

        List<String> result = new ArrayList<>();

        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {

            int size = queue.size();

            for (int i = 0; i < size; i++) {

                String current = queue.poll();

                // Check if current string is valid
                if (isValid(current)) {
                    result.add(current);
                    found = true;
                }

                // If a valid string is found,
                // don't generate strings with more removals
                if (found) {
                    continue;
                }

                // Remove one parenthesis at each position
                for (int j = 0; j < current.length(); j++) {

                    // Only remove parentheses, not letters
                    if (current.charAt(j) != '(' &&
                        current.charAt(j) != ')') {
                        continue;
                    }

                    String next = current.substring(0, j)
                                 + current.substring(j + 1);

                    if (!visited.contains(next)) {
                        visited.add(next);
                        queue.add(next);
                    }
                }
            }

            // We already found minimum-removal answers
            if (found) {
                break;
            }
        }

        return result;
    }

    // Checks whether parentheses are balanced
    private boolean isValid(String s) {

        int count = 0;

        for (char c : s.toCharArray()) {

            if (c == '(') {
                count++;
            }
            else if (c == ')') {
                count--;

                // More ')' than '('
                if (count < 0) {
                    return false;
                }
            }
        }

        // All '(' must also be closed
        return count == 0;
    }
}