class Solution {

    static class Node {
        int prod;
        int[] pref;

        Node(int k) {
            pref = new int[k];
        }
    }

    int n;
    int k;
    int[] nums;
    Node[] tree;

    // Create a leaf node
    Node makeNode(int value) {
        Node node = new Node(k);

        node.prod = value % k;

        // The single element itself is one possible prefix
        node.pref[node.prod] = 1;

        return node;
    }

    // Merge two nodes
    Node merge(Node left, Node right) {

        Node res = new Node(k);

        // Product of the whole segment
        res.prod = (left.prod * right.prod) % k;

        // Prefixes completely inside the left segment
        for (int r = 0; r < k; r++) {
            res.pref[r] += left.pref[r];
        }

        // Prefixes that contain all of left
        // and then take a prefix of right
        for (int r = 0; r < k; r++) {

            int newRemainder = (left.prod * r) % k;

            res.pref[newRemainder] += right.pref[r];
        }

        return res;
    }

    // Build segment tree
    void build(int index, int left, int right) {

        if (left == right) {
            tree[index] = makeNode(nums[left]);
            return;
        }

        int mid = left + (right - left) / 2;

        build(index * 2, left, mid);
        build(index * 2 + 1, mid + 1, right);

        tree[index] = merge(
            tree[index * 2],
            tree[index * 2 + 1]
        );
    }

    // Point update
    void update(int index, int left, int right, int pos, int value) {

        if (left == right) {
            nums[pos] = value;
            tree[index] = makeNode(value);
            return;
        }

        int mid = left + (right - left) / 2;

        if (pos <= mid) {
            update(index * 2, left, mid, pos, value);
        } else {
            update(index * 2 + 1, mid + 1, right, pos, value);
        }

        tree[index] = merge(
            tree[index * 2],
            tree[index * 2 + 1]
        );
    }

    // Query a range
    Node query(int index, int left, int right, int ql, int qr) {

        // Completely inside query range
        if (ql <= left && right <= qr) {
            return tree[index];
        }

        int mid = left + (right - left) / 2;

        // Query only right side
        if (ql > mid) {
            return query(index * 2 + 1, mid + 1, right, ql, qr);
        }

        // Query only left side
        if (qr <= mid) {
            return query(index * 2, left, mid, ql, qr);
        }

        // Query both sides
        Node leftNode = query(
            index * 2,
            left,
            mid,
            ql,
            qr
        );

        Node rightNode = query(
            index * 2 + 1,
            mid + 1,
            right,
            ql,
            qr
        );

        return merge(leftNode, rightNode);
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {

        this.nums = nums;
        this.n = nums.length;
        this.k = k;

        tree = new Node[4 * n];

        build(1, 0, n - 1);

        int[] result = new int[queries.length];

        for (int i = 0; i < queries.length; i++) {

            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // Persistent update
            update(
                1,
                0,
                n - 1,
                index,
                value
            );

            // Get nums[start ... n-1]
            Node range = query(
                1,
                0,
                n - 1,
                start,
                n - 1
            );

            // Number of prefixes with product % k == x
            result[i] = range.pref[x];
        }

        return result;
    }
}