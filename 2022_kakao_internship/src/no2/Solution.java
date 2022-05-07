package no2;

import java.util.LinkedList;
import java.util.Queue;

public class Solution {
    public int solution(int[] queue1, int[] queue2) {
        long sum1, sum2;
        sum1 = getSumOfQueue(queue1);
        sum2 = getSumOfQueue(queue2);

        if ((sum1 + sum2) % 2 == 1)
            return -1;

        long targetSum = (sum1 + sum2) / 2;

        Queue<Integer> q1 = new LinkedList<>();
        Queue<Integer> q2 = new LinkedList<>();

        for (int element : queue1)
            q1.add(element);

        for (int element : queue2)
            q2.add(element);

        int cnt = 0;
        while (sum1 != sum2) {
            if (sum1 > targetSum) {
                int num = q1.poll();
                q2.add(num);
                sum1 -= num;
                sum2 += num;
            }
            else {
                int num = q2.poll();
                q1.add(num);
                sum2 -= num;
                sum1 += num;
            }
            cnt++;

            if (cnt >= 300_000)
                return -1;
        }

        return cnt;
    }

    private long getSumOfQueue(int[] queue) {
        long sum = 0;
        for (int element : queue)
            sum += element;
        return sum;
    }
}