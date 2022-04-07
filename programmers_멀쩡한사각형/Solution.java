class Solution {
    public long solution(int width, int height) {
        
        if (width == 1 || height == 1)
            return 0;
        
        int gcd;
        
        if (width > height)
            gcd = getGcd(width, height);
        else
            gcd = getGcd(height, width);
        
        long squareCnt = (long)width * (long)height;
        long subWidth = width / gcd;
        long subHeight = height / gcd;
        
        return squareCnt - (subWidth + subHeight - 1) * gcd;
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
