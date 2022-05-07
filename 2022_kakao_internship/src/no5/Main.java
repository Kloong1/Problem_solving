package no5;

import java.util.Arrays;

class Main {
    public static void main(String[] args) {
        Solution s = new Solution();

        int[][] arr = s.solution(new int[][]{{1,2,3},{4,5,6},{7,8,9}}, new String[] {"Rotate", "s"});
        for (int[] row : arr)
            System.out.println(Arrays.toString(row));
    }
}

class Solution {

    Node startNode, lastNode;

    public int[][] solution(int[][] rc, String[] operations) {

        startNode = new Node();

        Node preNode = startNode;
        for (int[] row : rc) {
            Node node = new Node();
            node.arr = new int[row.length];
            System.arraycopy(row, 0, node.arr, 0, row.length);
            preNode.next = node;
            node.pre = preNode;
            preNode = node;
        }
        lastNode = preNode;

        int idx = 0;
        while (idx < operations.length) {
            if (operations[idx].equals("Rotate")) {
                rotate(startNode, 0);
            }
            else {
                shiftRow();
            }
            idx++;
        }

        int[][] answer = new int[rc.length][rc[0].length];
        Node node = startNode.next;
        for (int[] row : answer) {
            System.arraycopy(node.arr ,0, row, 0, row.length);
            node = node.next;
        }

        return answer;
    }

    private void shiftRow() {
        Node preNode = lastNode.pre;
        lastNode.pre = startNode;
        lastNode.next = startNode.next;
        startNode.next = lastNode;
        preNode.next = null;
        lastNode = preNode;
    }

    private void rotate(Node startNode, int cnt) {
        int topTemp, bottomTemp;

        int[] topArr = startNode.next.arr;
        topTemp = topArr[topArr.length - 1];
        System.arraycopy(topArr, 0, topArr, 1, topArr.length - 1);

        int[] bottomArr = lastNode.arr;
        bottomTemp = bottomArr[0];
        System.arraycopy(bottomArr, 1, bottomArr, 0, bottomArr.length - 2 + 1);

        Node node = startNode.next.next;
        int pre, temp;
        pre = topTemp;
        while (node != null) {
            temp = node.arr[node.arr.length - 1];
            node.arr[node.arr.length - 1] = pre;
            pre = temp;
            node = node.next;
        }

        node = lastNode;
        pre = bottomTemp;
        while (node != startNode) {
            temp = node.arr[0];
            node.arr[0] = pre;
            pre = temp;
            node = node.pre;
        }
    }
}


class Node {
    int[] arr;
    Node next;
    Node pre;
}
