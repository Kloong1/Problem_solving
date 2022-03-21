import java.util.ArrayList;

class Solution {
    public int[] solution(int[] progresses, int[] speeds) {
        int unreleasedIdx = 0;
        
        ArrayList<Integer> dailyReleaseCntList = new ArrayList<>();
        
        while (unreleasedIdx < progresses.length)
        {
            doWork(progresses, speeds, unreleasedIdx);
            
            if (progresses[unreleasedIdx] >= 100)
            	unreleasedIdx = release(progresses, unreleasedIdx, dailyReleaseCntList);
        }
        
        int[] dailyReleaseCntArr = new int[dailyReleaseCntList.size()];
        
        for (int i = 0; i < dailyReleaseCntArr.length; i++)
            dailyReleaseCntArr[i] = dailyReleaseCntList.get(i);
        
        return dailyReleaseCntArr;
    }
   
    void doWork(int[] progresses, int[] speeds, int unreleasedIdx)
    {
        for (int idx = unreleasedIdx; idx < progresses.length; idx++)
            progresses[idx] += speeds[idx];
    }
    
    int release(int[] progresses, int unreleasedIdx, ArrayList<Integer> dailyReleaseCntList)
    {
        int releaseCnt = 0;
        
        for (; unreleasedIdx < progresses.length; unreleasedIdx++)
        {
            if (progresses[unreleasedIdx] < 100)
                break;
            releaseCnt++;
        }
        
        dailyReleaseCntList.add(releaseCnt);
        
        return unreleasedIdx;
    }
    
} 
