import java.io.*;
import java.util.*;

public class Main
{
    static int rows, cols;
    static int[][] map;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws  IOException
    {
        readRowsAndCols();

        map = new int[rows][cols];
        readMap();

        System.out.println(countDownhillCase());
    }

    static int countDownhillCase()
    {
        int[][] downhillCase = new int[rows][cols];

        for (int[] row : downhillCase)
            Arrays.fill(row, -1);

        downhillCase[rows - 1][cols - 1] = 1;

        return dfs2Downhill(0, 0, downhillCase);
    }

    static int dfs2Downhill(int row, int col, int[][] downhillCase)
    {
        if (downhillCase[row][col] != -1)
            return downhillCase[row][col];

        int cnt = 0;

        int nextRow, nextCol;
        for (int[] dir : dirs)
        {
            nextRow = row + dir[0];
            nextCol = col + dir[1];

            if (isDownhill(nextRow, nextCol, map[row][col]))
                cnt += dfs2Downhill(nextRow, nextCol, downhillCase);
        }

        downhillCase[row][col] = cnt;

        return cnt;
    }

    static boolean isDownhill(int row, int col, int curHeight)
    {
        return (row >= 0 && row < rows && col >= 0 && col < cols && map[row][col] < curHeight);
    }

    static void readMap() throws IOException
    {
        StringTokenizer st;
        for (int row = 0; row < rows; row++)
        {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < cols; col++)
                map[row][col] = Integer.parseInt(st.nextToken());
        }
    }

    static void readRowsAndCols() throws IOException
    {
        StringTokenizer st = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());
    }
}
