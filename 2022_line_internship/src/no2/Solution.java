package no2;

import java.util.Arrays;

class Solution {
    public int solution(int n, int[] times) {

        int[] cuttingTimeOfLines = new int[n + 1];
        Arrays.fill(cuttingTimeOfLines, Integer.MAX_VALUE);

        cuttingTimeOfLines[1] = 0;

        for (int lines = 1; lines <= n; lines++) {
            for (int cutting = 0; cutting < lines; cutting++) {
                if (lines + cutting + 1 > n)
                    break;

                cuttingTimeOfLines[lines + (cutting + 1)] =
                        Math.min(cuttingTimeOfLines[lines + (cutting + 1)], cuttingTimeOfLines[lines] + times[cutting]);
            }
        }

        return cuttingTimeOfLines[n];
    }
}