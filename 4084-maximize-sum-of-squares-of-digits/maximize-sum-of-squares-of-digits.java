class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > 9 * num) return "";

        StringBuilder s = new StringBuilder();

        for (int i = 0; i < num; i++) {
            int d = Math.min(9, sum);
            s.append(d);
            sum -= d;
        }

        return s.toString();
    }
}