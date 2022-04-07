class Solution {
    public long solution(int w, int h) {
        
        if (w == 1 || h == 1)
            return 0;
        
        long originSquareCnt = (long)w * (long)h;
        
        int gcd;
        
        if (w > h)
            gcd = getGcd(w, h);
        else
            gcd = getGcd(h, w);
        
        return originSquareCnt - (getCutSquareCnt(w / gcd, h / gcd) * gcd);
    }
    
    private long getCutSquareCnt(int w, int h) {
        
        long cnt = 0;
        double inclination = (double)h / w;
        double preY, curY;
        
        preY = 0;
        for (int x = 1; x <= w; x++)
        {
            curY = preY + inclination;
            cnt += (int)curY - (int)preY + 1;
            preY = curY;
        }
        
       return cnt - 1;
    }
    
    private int getGcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        else {
            return getGcd(b, a%b);
        }
    }
} 
