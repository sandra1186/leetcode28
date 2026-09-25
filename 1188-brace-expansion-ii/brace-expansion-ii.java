import java.util.*;

class Solution {

    private String expression;
    private int index;

    public List<String> braceExpansionII(String expression) {
        this.expression = expression;
        this.index = 0;

        Set<String> result = parse();

        return new ArrayList<>(result);
    }

    // Parses concatenated expressions
    private Set<String> parse() {
        Set<String> result = new TreeSet<>();
        result.add("");

        while (index < expression.length() && expression.charAt(index) != '}'
                && expression.charAt(index) != ',') {

            Set<String> current;

            if (expression.charAt(index) == '{') {
                index++; // skip '{'
                current = parseUnion();
                index++; // skip '}'
            } else {
                current = new TreeSet<>();
                current.add(String.valueOf(expression.charAt(index)));
                index++;
            }

            result = concatenate(result, current);
        }

        return result;
    }

    // Parses expressions separated by commas
    private Set<String> parseUnion() {
        Set<String> result = new TreeSet<>();

        while (true) {
            Set<String> current = parse();
            result.addAll(current);

            if (expression.charAt(index) == ',') {
                index++; // skip ','
            } else {
                break;
            }
        }

        return result;
    }

    // Cartesian product + string concatenation
    private Set<String> concatenate(Set<String> a, Set<String> b) {
        Set<String> result = new TreeSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}