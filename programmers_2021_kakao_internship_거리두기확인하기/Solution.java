import java.util.*;

class Solution {
    public int[] solution(String[][] places) {
        int[] socialDistancing = new int[5];
        
        for (int idx = 0; idx < 5; idx++)
            socialDistancing[idx] = checkSocialDistancing(places[idx]);
        
        return socialDistancing;
    }
    
    int checkSocialDistancing(String[] roomStrs)
    {
        char[][] room = convertToCharArr(roomStrs);
        
        for (int row = 0; row < 5; row++)
        {
            for (int col = 0; col < 5; col++)
            {
                if (room[row][col] == 'P')
                    if (!bfs2CheckSocialDistancing(room, row, col))
                        return 0;
            }
        }
        
        return 1;
    }
    
    boolean bfs2CheckSocialDistancing(char[][] room, int startRow, int startCol)
    {
        boolean[][] visit = new boolean[5][5];
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        
        Queue<Point> pointQueue = new LinkedList<>();
        
        Point startPoint = new Point(startRow, startCol);
        pointQueue.add(startPoint);
        visit[startRow][startCol] = true;
        
        Point p, nPoint;
        int nRow, nCol;
        while (!pointQueue.isEmpty())
        {
            p = pointQueue.poll();
            
            if (room[p.row][p.col] == 'P' && p != startPoint)
                return false;
            
            for (int[] dir : dirs)
            {
                nRow = p.row + dir[0];
                nCol = p.col + dir[1];
                
                if (!validateRowCol(nRow, nCol) || room[nRow][nCol] == 'X' || visit[nRow][nCol])
                    continue;
                
                nPoint = new Point(nRow, nCol);
                
                if (getManhattanDist(startPoint, nPoint) > 2)
                    continue;
                
                visit[nRow][nCol] = true;
                pointQueue.add(nPoint);
            }
        }
        
        return true;
    }
    
    boolean validateRowCol(int row, int col)
    {
        return row >= 0 && row < 5 && col >= 0 && col < 5;
    }
    
    int getManhattanDist(Point p1, Point p2)
    {
        return Math.abs(p1.row - p2.row) + Math.abs(p1.col - p2.col);
    }
    
    char[][] convertToCharArr(String[] roomStrs)
    {
        char[][] arr = new char[5][5];
        
        for (int row = 0; row < 5; row++)
            for (int col = 0; col < 5; col++)
                arr[row][col] = roomStrs[row].charAt(col);
        
        return arr;
    }
}

class Point
{
    int row;
    int col;
    
    Point (int row, int col)
    {
        this.row = row;
        this.col = col;
    }
}
