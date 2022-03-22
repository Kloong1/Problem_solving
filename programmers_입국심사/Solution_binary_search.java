import java.util.*;

class Solution {
    public long solution(int n, int[] times) {
        
        long minTime = 0;
        long leftTime, rightTime, midTime;
        
        leftTime = 0;
        rightTime = getLimitTime(n, times);
        midTime = (leftTime + rightTime) / 2;
        
        while (leftTime <= rightTime)
        {
            midTime = (leftTime + rightTime) / 2;
            
            long immigrationCnt = countImmigrationInTime(midTime, times);
            
            if (immigrationCnt >= n)
            {
                minTime = midTime;
                rightTime = midTime - 1;
            }
            else if (immigrationCnt < n)
                leftTime = midTime + 1;
        }
        
        return minTime;
    }
    
    long countImmigrationInTime(long limitTime, int[] times)
    {
        long cnt = 0;
        for (int time : times)
            cnt += limitTime / time;
        
        return cnt;
    }
    
    long getLimitTime(int n, int[] times)
    {
        long minTime = Integer.MAX_VALUE;
        for (int time : times)
            minTime = Math.min(minTime, time);
        
        return minTime * n;
    }
    
}
