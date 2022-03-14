import java.io.*;
import java.util.*;

public class Main
{
    static int rows, cols;
    static int[][] map;
    static boolean[][] visit;
    static int[][][] benefits;

    static final int FROM_LEFT = 0;
    static final int FROM_RIGHT = 1;
    static final int FROM_UP = 2;

    static int[][] dirs = {{0, 1, FROM_LEFT}, {0, -1, FROM_RIGHT}, {1, 0, FROM_UP}};

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        readRowsCols();

        map = new int[rows][cols];
        readMap();

        System.out.println(getMaxBenefitSum());
    }

    static int getMaxBenefitSum()
    {
        visit = new boolean[rows][cols];
        benefits = new int[3][rows][cols];

        for (int[][] arr : benefits)
            for (int[] row : arr)
                Arrays.fill(row, -1_000_000_000);

        return dfs2getMaxBenefitSum(0, 0, 0);
    }

    static int dfs2getMaxBenefitSum(int row, int col, int fromDir)
    {
        if (isDestination(row, col))
            return map[row][col];

        if (benefits[fromDir][row][col] != -1_000_000_000)
            return benefits[fromDir][row][col];

        visit[row][col] = true;

        int nextRow, nextCol;
        int maxBenefitSum = -1_000_000_000;

        for (int[] dir : dirs)
        {
            nextRow = row + dir[0];
            nextCol = col + dir[1];

            if (!validateArea(nextRow, nextCol))
                continue;

            if (visit[nextRow][nextCol])
                continue;

            maxBenefitSum = Math.max(
                    maxBenefitSum, dfs2getMaxBenefitSum(nextRow, nextCol, dir[2]));
        }

        benefits[fromDir][row][col] = maxBenefitSum + map[row][col];
        visit[row][col] = false;

        if (benefits[fromDir][row][col] != Integer.MIN_VALUE)
            System.out.printf("(%d, %d, %d) = %d\n", fromDir, row, col, benefits[fromDir][row][col]);

        return benefits[fromDir][row][col];
    }

    static boolean validateArea(int row, int col)
    {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    static boolean isDestination(int row, int col)
    {
        return row == rows - 1 && col == cols - 1;
    }

    static void readRowsCols() throws IOException
    {
        StringTokenizer st = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());
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
}