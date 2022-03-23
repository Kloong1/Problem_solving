class Solution {
    public int solution(int[][] triangle) {
        
        for (int row = 1; row < triangle.length; row++)
        {
            for (int col = 1; col < triangle[row].length - 1; col++)
                triangle[row][col] += Math.max(triangle[row - 1][col - 1], triangle[row - 1][col]);
            
            triangle[row][0] += triangle[row - 1][0];
            triangle[row][triangle[row].length - 1] += triangle[row - 1][triangle[row - 1].length - 1];
        }
        
        int maxSum = 0;
        for (int sum : triangle[triangle.length - 1])
            maxSum = Math.max(maxSum, sum);
        
        return maxSum;
    }
}
