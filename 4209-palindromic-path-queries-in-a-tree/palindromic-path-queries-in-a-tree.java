import java.util.*;

class Solution {
    private int n, timer;
    private List<Integer>[] adj;
    private int[] parent, depth, size, heavy, head, pos, inversePos;
    private int[] seg;
    private int[] bit;

    public List<Boolean> palindromePath(int n, int[][] edges, String s,
                                        String[] queries) {
        this.n = n;
        adj = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        parent = new int[n];
        depth = new int[n];
        size = new int[n];
        heavy = new int[n];
        head = new int[n];
        pos = new int[n];
        inversePos = new int[n];
        bit = new int[n];

        Arrays.fill(heavy, -1);
        parent[0] = -1;

        int[] order = new int[n];
        int[] stack = new int[n];
        int top = 0, count = 0;
        stack[top++] = 0;

        while (top > 0) {
            int u = stack[--top];
            order[count++] = u;

            for (int v : adj[u]) {
                if (v == parent[u]) continue;
                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            int u = order[i];
            size[u] = 1;
            int maxSize = 0;

            for (int v : adj[u]) {
                if (v == parent[u]) continue;

                size[u] += size[v];
                if (size[v] > maxSize) {
                    maxSize = size[v];
                    heavy[u] = v;
                }
            }
        }

        timer = 0;
        int[] chainStack = new int[n];
        top = 0;
        chainStack[top++] = 0;

        while (top > 0) {
            int start = chainStack[--top];

            for (int u = start; u != -1; u = heavy[u]) {
                head[u] = start;
                pos[u] = timer;
                inversePos[timer++] = u;
                bit[u] = 1 << (s.charAt(u) - 'a');

                for (int v : adj[u]) {
                    if (v != parent[u] && v != heavy[u]) {
                        chainStack[top++] = v;
                    }
                }
            }
        }

        seg = new int[4 * n];
        build(1, 0, n - 1);

        List<Boolean> answer = new ArrayList<>();

        for (String q : queries) {
            String[] p = q.split(" ");
            int u = Integer.parseInt(p[1]);

            if (p[0].equals("update")) {
                int c = p[2].charAt(0);
                bit[u] = 1 << (c - 'a');
                update(1, 0, n - 1, pos[u], bit[u]);
            } else {
                int v = Integer.parseInt(p[2]);
                int mask = 0;

                while (head[u] != head[v]) {
                    if (depth[head[u]] < depth[head[v]]) {
                        int temp = u;
                        u = v;
                        v = temp;
                    }

                    mask ^= rangeQuery(
                        1, 0, n - 1, pos[head[u]], pos[u]
                    );

                    u = parent[head[u]];
                }

                int left = Math.min(pos[u], pos[v]);
                int right = Math.max(pos[u], pos[v]);

                mask ^= rangeQuery(1, 0, n - 1, left, right);

                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    private void build(int node, int l, int r) {
        if (l == r) {
            seg[node] = bit[inversePos[l]];
            return;
        }

        int mid = (l + r) / 2;
        build(node * 2, l, mid);
        build(node * 2 + 1, mid + 1, r);
        seg[node] = seg[node * 2] ^ seg[node * 2 + 1];
    }

    private void update(int node, int l, int r, int idx, int value) {
        if (l == r) {
            seg[node] = value;
            return;
        }

        int mid = (l + r) / 2;

        if (idx <= mid) {
            update(node * 2, l, mid, idx, value);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, value);
        }

        seg[node] = seg[node * 2] ^ seg[node * 2 + 1];
    }

    private int rangeQuery(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return seg[node];
        }

        int mid = (l + r) / 2;
        int result = 0;

        if (ql <= mid) {
            result ^= rangeQuery(node * 2, l, mid, ql, qr);
        }

        if (qr > mid) {
            result ^= rangeQuery(node * 2 + 1, mid + 1, r, ql, qr);
        }

        return result;
    }
}