import java.util.*;

class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();

        for (char c : s.toCharArray()) {

            // If it is an opening bracket, push it
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            }

            // If it is a closing bracket
            else {
                // No opening bracket available
                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();

                // Check matching pair
                if (c == ')' && top != '(') {
                    return false;
                }

                if (c == '}' && top != '{') {
                    return false;
                }

                if (c == ']' && top != '[') {
                    return false;
                }
            }
        }

        // Stack must be empty at the end
        return stack.isEmpty();
    }
}