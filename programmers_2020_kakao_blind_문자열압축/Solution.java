class Solution {
    public int solution(String s) {
        int minStringLen = s.length();
        
        for (int wordLen = 1; wordLen < s.length(); wordLen++)
            minStringLen = Math.min(minStringLen, getCompressedStringLen(s, wordLen));
        
        return minStringLen;
    }
    
    static int getCompressedStringLen(String s, int wordLen)
    {
        int strLen = 0;
        int wordIdx, nextWordIdx;
        String word, nextWord;
        int wordCnt;
        
        wordIdx = 0;
        while (wordIdx + wordLen <= s.length())
        {
            word = s.substring(wordIdx, wordIdx + wordLen);
            wordCnt = 1;
            
            for (nextWordIdx = wordIdx + wordLen; nextWordIdx + wordLen <= s.length(); nextWordIdx+= wordLen)
            {
                nextWord = s.substring(nextWordIdx, nextWordIdx + wordLen);
                
                if (!word.equals(nextWord))
                    break;
                
                wordCnt++;
            }
            
            if (wordCnt == 1)
                strLen += wordLen;
            else
                strLen += countDigits(wordCnt) + wordLen;
                
            wordIdx = nextWordIdx;
        }
        
        strLen += s.length() - wordIdx;
        
        return strLen;
    }
    
    static int countDigits(int n)
    {
        int cnt = 0;
        while (n != 0)
        {
            n /= 10;
            cnt++;
        }
        
        return cnt;
    }
} 
