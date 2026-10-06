class Solution {
    public int minAddToMakeValid(String s) {
        int balance = 0;
        int additions = 0;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                balance++;
            } else {
                balance--;

                // Too many closing brackets
                if (balance < 0) {
                    additions++;
                    balance = 0;
                }
            }
        }

        // Remaining opening brackets need closing brackets
        additions += balance;

        return additions;
    }
}