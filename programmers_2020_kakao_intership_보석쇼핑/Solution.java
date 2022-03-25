import java.util.*;

class Solution {
    public int[] solution(String[] strGems) {
        
        int[] gems = new int[strGems.length];
        int maxGemId = getIntGems(gems, strGems);
        int numOfGemTypes = maxGemId + 1;
        
        int[] gemCnt = new int[maxGemId + 1];
        int zeroCnt = numOfGemTypes;
        
        int left, right;
        left = 0;
        right = 0;
        
        while (zeroCnt > 0)
        {
            gemCnt[gems[right]]++;
            
            if (gemCnt[gems[right]] == 1)
                zeroCnt--;
            
            right++;
        }
        
        int[] answer = {1, right};
        int minLen = answer[1];
        
        while (true)
        {
            while (zeroCnt == 0)
           {
                gemCnt[gems[left]]--;
                
                if (gemCnt[gems[left]] == 0)
                    zeroCnt++;
                
                left++;
            }
            
            int tempLen = right - left - 1;
            if (tempLen < minLen)
            {
                answer[0] = left;
                answer[1] = right;
                minLen = tempLen;
            }
            
            while (zeroCnt > 0 && right < gems.length)
            {
                gemCnt[gems[right]]++;
                
                if (gemCnt[gems[right]] == 1)
                    zeroCnt--;
                
                right++;
            }
            
            if (zeroCnt == 0)
            {
                tempLen = right - left;
                if (tempLen < minLen)
                {
                    answer[0] = left + 1;
                    answer[1] = right;
                    minLen = tempLen;
                }
            }
            
            while (zeroCnt == 0)
            {
                gemCnt[gems[left]]--;
                
                if (gemCnt[gems[left]] == 0)
                    zeroCnt++;
                
                left++;
            }
            
            tempLen = right - left - 1;
            if (tempLen < minLen)
            {
                answer[0] = left;
                answer[1] = right;
                minLen = tempLen;
            }
            
            if (right == gems.length)
                break;
        }
        
        return answer;
    }
    
    int getIntGems(int[] gems, String[] strGems)
    {
        HashMap<String, Integer> gemMap = new HashMap<>();
        
        int gemId = 0;
        for (int i = 0; i < strGems.length; i++)
        {
            if (gemMap.containsKey(strGems[i]))
                gems[i] = gemMap.get(strGems[i]);
            else
            {
                gemMap.put(strGems[i], gemId);
                gems[i] = gemId++;
            }
        }
        
        return gemId - 1;
    }
} 
