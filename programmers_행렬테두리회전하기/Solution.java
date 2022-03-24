class Solution {
    public int[] solution(int rows, int cols, int[][] queries) {
        
        int[][] matrix = new int[rows][cols];
        initMatrix(matrix);
        
        int[] answer = new int[queries.length];
        for (int idx = 0; idx < queries.length; idx++)
            answer[idx] = rotateMatrixAndGetMinNumber(matrix, queries[idx]);
        
        return answer;
    }
    
    int rotateMatrixAndGetMinNumber(int[][] matrix, int[] query)
    {
        int minNum;
        int temp, preNum;
        int row1, col1, row2, col2;
        
        row1 = query[0] - 1;
        col1 = query[1] - 1;
        row2 = query[2] - 1;
        col2 = query[3] - 1;
        
        preNum = matrix[row1][col1];
        minNum = matrix[row1][col1];
       
        //윗줄
        for (int col = col1 + 1; col <= col2; col++)
        {
            temp = matrix[row1][col];
            matrix[row1][col] = preNum;
            preNum = temp;
            minNum = Math.min(minNum, preNum);
        }
        
        //오른쪽 줄
        for (int row = row1 + 1; row <= row2; row++)
        {
            temp = matrix[row][col2];
            matrix[row][col2] = preNum;
            preNum = temp;
            minNum = Math.min(minNum, preNum);
        }
        
        //아랫줄
        for (int col = col2 - 1; col >= col1; col--)
        {
            temp = matrix[row2][col];
            matrix[row2][col] = preNum;
            preNum = temp;
            minNum = Math.min(minNum, preNum);
        }
        
        //왼쪽 줄
        for (int row = row2 - 1; row >= row1; row--)
        {
            temp = matrix[row][col1];
            matrix[row][col1] = preNum;
            preNum = temp;
            minNum = Math.min(minNum, preNum);
        }
        
        return minNum;
    }
    
    void initMatrix(int[][] matrix)
    {
        int num = 1;
        for (int row = 0; row < matrix.length; row++)
            for (int col = 0; col < matrix[row].length; col++)
                matrix[row][col] = num++;
    }
} 
