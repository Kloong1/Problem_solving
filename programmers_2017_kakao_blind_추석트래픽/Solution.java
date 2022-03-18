class Solution {
    public int solution(String[] lines) {
        
        Time[] times = convertLogsToTimes(lines);
        int maxCnt, tempCnt;
        
        maxCnt = 1;
        for (int idx = 0; idx < times.length; idx++)
        {
            int limitTime = times[idx].end + 999;
            tempCnt = 0;
            
            for (int limitIdx = idx; limitIdx < times.length; limitIdx++)
                if (limitTime >= times[limitIdx].start)
                    tempCnt++;
            
            maxCnt = Math.max(maxCnt, tempCnt);
        }
        
        return maxCnt;
    }
    
    Time[] convertLogsToTimes(String[] lines)
    {
        Time[] times = new Time[lines.length];
        
        for (int i = 0; i < lines.length; i++)
       {
            String[] splited = lines[i].split(" ");
            times[i] = new Time(splited[1], splited[2]);
        }
        
        return times;
    }
    
}

class Time
{
    int start;
    int end;
    
    Time (String responseTime, String processTime)
    {
        String[] hourMinSec = responseTime.split("\\:");
        
        this.end += Integer.parseInt(hourMinSec[0]) * 3600 + Integer.parseInt(hourMinSec[1]) * 60;
        this.end *= 1000;
        this.end += (int)(Double.parseDouble(hourMinSec[2]) * 1000); 
        
        int pTime = (int)(Double.parseDouble(processTime.substring(0, processTime.length() - 1)) * 1000);
        
        this.start = end - pTime + 1;
    }
} 
