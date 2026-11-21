
import java.util.*;

class Solution {
    public int[] findSubtreeSizes(int[] parent, String s) {
        int n = parent.length;

        List<Integer>[] tree = new ArrayList[n];
        List<Integer>[] newTree = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            tree[i] = new ArrayList<>();
            newTree[i] = new ArrayList<>();
        }

        // Build original tree
        for (int i = 1; i < n; i++) {
            tree[parent[i]].add(i);
        }

        int[] last = new int[26];
        Arrays.fill(last, -1);

        int[] newParent = new int[n];
        newParent[0] = -1;

        int[] oldLast = new int[n];

        // Iterative DFS on original tree
        int[] stack = new int[2 * n];
        int top = 0;

        stack[top++] = 0;

        while (top > 0) {
            int node = stack[--top];

            if (node < 0) {
                node = ~node;
                last[s.charAt(node) - 'a'] = oldLast[node];
                continue;
            }

            int c = s.charAt(node) - 'a';

            oldLast[node] = last[c];

            if (node != 0) {
                newParent[node] = last[c] == -1
                        ? parent[node] : last[c];
            }

            last[c] = node;

            stack[top++] = ~node;

            for (int child : tree[node]) {
                stack[top++] = child;
            }
        }

        // Build modified tree
        for (int i = 1; i < n; i++) {
            newTree[newParent[i]].add(i);
        }

        // Calculate subtree sizes
        int[] order = new int[n];
        top = 0;

        stack[top++] = 0;
        int count = 0;

        while (top > 0) {
            int node = stack[--top];
            order[count++] = node;

            for (int child : newTree[node]) {
                stack[top++] = child;
            }
        }

        int[] answer = new int[n];
        Arrays.fill(answer, 1);

        // Process children before parents
        for (int i = count - 1; i >= 0; i--) {
            int node = order[i];

            if (newParent[node] != -1) {
                answer[newParent[node]] += answer[node];
            }
        }

        return answer;
    }
}
