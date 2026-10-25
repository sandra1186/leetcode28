
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k == 0) {
            return head;
        }

        // Find the length and the last node
        int length = 1;
        ListNode tail = head;

        while (tail.next != null) {
            tail = tail.next;
            length++;
        }

        // Reduce unnecessary rotations
        k = k % length;

        if (k == 0) {
            return head;
        }

        // Connect the list into a circular list
        tail.next = head;

        // Find the new tail
        int stepsToNewTail = length - k - 1;
        ListNode newTail = head;

        for (int i = 0; i < stepsToNewTail; i++) {
            newTail = newTail.next;
        }

        // Break the circle and return the new head
        ListNode newHead = newTail.next;
        newTail.next = null;

        return newHead;
    }
}
