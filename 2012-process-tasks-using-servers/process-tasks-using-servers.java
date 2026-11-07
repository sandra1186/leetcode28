
import java.util.*;

class Solution {
    public int[] assignTasks(int[] servers, int[] tasks) {

        int n = servers.length;
        int m = tasks.length;

        int[] ans = new int[m];

        // [weight, index]
        PriorityQueue<int[]> free = new PriorityQueue<>(
            (a, b) -> {
                if (a[0] != b[0])
                    return Integer.compare(a[0], b[0]);
                return Integer.compare(a[1], b[1]);
            }
        );

        // [availableTime, weight, index]
        PriorityQueue<long[]> busy = new PriorityQueue<>(
            (a, b) -> Long.compare(a[0], b[0])
        );

        for (int i = 0; i < n; i++) {
            free.offer(new int[]{servers[i], i});
        }

        long currentTime = 0;

        for (int j = 0; j < m; j++) {

            currentTime = Math.max(currentTime, j);

            // Release all available servers
            while (!busy.isEmpty() &&
                   busy.peek()[0] <= currentTime) {

                long[] s = busy.poll();

                free.offer(new int[]{
                    (int) s[1],
                    (int) s[2]
                });
            }

            // Wait until a server becomes free
            if (free.isEmpty()) {

                currentTime = busy.peek()[0];

                while (!busy.isEmpty() &&
                       busy.peek()[0] <= currentTime) {

                    long[] s = busy.poll();

                    free.offer(new int[]{
                        (int) s[1],
                        (int) s[2]
                    });
                }
            }

            // Assign the task
            int[] server = free.poll();

            ans[j] = server[1];

            // Server becomes free after task completion
            busy.offer(new long[]{
                currentTime + tasks[j],
                server[0],
                server[1]
            });
        }

        return ans;
    }
}
