class Solution {
    public int solution(int[] numbers, int target) {
       
        int maxBitmask = 1 << numbers.length;
        
        int cnt = 0;
        for (int bitmask = 0; bitmask < maxBitmask; bitmask++)
            if (generateNumber(numbers, bitmask) == target)
                cnt++;
        
        return cnt;
    }
    
    int generateNumber(int[] numbers, int bitmask)
    {
        int number = 0;
        
        for (int idx = 0; idx < numbers.length; idx++)
        {
            if ((bitmask & (1 << idx)) == 0)
                number -= numbers[idx];
            else
                number += numbers[idx];
        }
        
        return number;
    }
}
