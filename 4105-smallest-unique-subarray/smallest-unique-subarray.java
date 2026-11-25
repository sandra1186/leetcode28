
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int N = n + 1;

        int[] a = new int[N];
        for (int i = 0; i < n; i++) a[i] = nums[i];

        int[] sa = new int[N];
        int[] rank = new int[N];
        int[] temp = new int[N];
        int[] cnt = new int[Math.max(N, 100002)];

        for (int x : a) cnt[x]++;
        for (int i = 1; i < cnt.length; i++)
            cnt[i] += cnt[i - 1];

        for (int i = N - 1; i >= 0; i--)
            sa[--cnt[a[i]]] = i;

        int classes = 1;
        rank[sa[0]] = 0;

        for (int i = 1; i < N; i++) {
            if (a[sa[i]] != a[sa[i - 1]]) classes++;
            rank[sa[i]] = classes - 1;
        }

        for (int len = 1; len < N; len *= 2) {
            int[] p = new int[N];

            for (int i = 0; i < N; i++) {
                p[i] = sa[i] - len;
                if (p[i] < 0) p[i] += N;
            }

            Arrays.fill(cnt, 0);

            for (int x : p) cnt[rank[x]]++;

            for (int i = 1; i < classes; i++)
                cnt[i] += cnt[i - 1];

            for (int i = N - 1; i >= 0; i--)
                sa[--cnt[rank[p[i]]]] = p[i];

            temp[sa[0]] = 0;
            int newClasses = 1;

            for (int i = 1; i < N; i++) {
                int x = sa[i];
                int y = sa[i - 1];

                if (rank[x] != rank[y] ||
                    rank[(x + len) % N] != rank[(y + len) % N])
                    newClasses++;

                temp[x] = newClasses - 1;
            }

            int[] swap = rank;
            rank = temp;
            temp = swap;

            classes = newClasses;

            if (classes == N) break;
        }

        int[] lcp = new int[N];
        int k = 0;

        for (int i = 0; i < n; i++) {
            int r = rank[i];

            if (r == 0) {
                k = 0;
                continue;
            }

            int j = sa[r - 1];

            while (i + k < n && j + k < n &&
                   a[i + k] == a[j + k])
                k++;

            lcp[r] = k;

            if (k > 0) k--;
        }

        int ans = n;

        for (int r = 0; r < N; r++) {
            int i = sa[r];

            if (i == n) continue;

            int common = lcp[r];

            if (r + 1 < N)
                common = Math.max(common, lcp[r + 1]);

            if (common < n - i)
                ans = Math.min(ans, common + 1);
        }

        return ans;
    }
}
