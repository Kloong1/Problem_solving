import java.util.*;

class Solution {
    public long solution(int n, int[] times) {
        
        PriorityQueue<Immigration> immigrationPQ = new PriorityQueue<>();
        
        double immigrationPerMin = 0;
        for (int time : times)
            immigrationPerMin += 1D / time;
        
        double averageWorkload = n / immigrationPerMin;
        
        long estimatedImmigrationCnt;
        for (int time : times)
        {
            estimatedImmigrationCnt = (long)(averageWorkload / time);
            immigrationPQ.offer(new Immigration(time, time * (estimatedImmigrationCnt + 1)));
            n -= estimatedImmigrationCnt;
        }
        
        Immigration immigration;
        long endImmigrationTime = (long)averageWorkload;
       for (int person = 1; person <= n; person++)
        {
            immigration = immigrationPQ.poll();
            
            endImmigrationTime = immigration.endTime;
            immigration.endTime += immigration.immigrationTime;
            immigrationPQ.add(immigration);
        }
        
        return endImmigrationTime;
    }
    
}

class Immigration implements Comparable<Immigration>
{
    int immigrationTime;
    long endTime;
    
    Immigration (int immigrationTime, long endTime)
    {
        this.immigrationTime = immigrationTime;
        this.endTime = endTime;
    }
    
    public int compareTo(Immigration o)
    {
    	return Long.compare(this.endTime, o.endTime);
    }
}
