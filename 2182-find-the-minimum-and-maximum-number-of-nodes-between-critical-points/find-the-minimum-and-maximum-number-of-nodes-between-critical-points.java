class Solution {
    public int[] nodesBetweenCriticalPoints(ListNode head) {

        int[] result = {-1, -1};

        ListNode prev = head;
        ListNode curr = head.next;

        int position = 1;

        int firstCritical = -1;
        int lastCritical = -1;
        int minDistance = Integer.MAX_VALUE;

        while (curr.next != null) {

            ListNode next = curr.next;

            // Check if curr is a critical point
            boolean isMax = curr.val > prev.val && curr.val > next.val;
            boolean isMin = curr.val < prev.val && curr.val < next.val;

            if (isMax || isMin) {

                // First critical point
                if (firstCritical == -1) {
                    firstCritical = position;
                }

                // We already found a previous critical point
                if (lastCritical != -1) {
                    minDistance = Math.min(
                        minDistance,
                        position - lastCritical
                    );
                }

                lastCritical = position;
            }

            prev = curr;
            curr = next;
            position++;
        }

        // Fewer than 2 critical points
        if (firstCritical == -1 || firstCritical == lastCritical) {
            return result;
        }

        int maxDistance = lastCritical - firstCritical;

        result[0] = minDistance;
        result[1] = maxDistance;

        return result;
    }
}