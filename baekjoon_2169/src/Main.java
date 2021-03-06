import java.io.*;
import java.util.*;

public class Main
{
    static int rows, cols;
    static int[][] map;
<<<<<<< HEAD
=======
    static boolean[][] visit;
    static int[][][] benefits;

    static final int FROM_LEFT = 0;
    static final int FROM_RIGHT = 1;
    static final int FROM_UP = 2;

    static int[][] dirs = {{0, 1, FROM_LEFT}, {0, -1, FROM_RIGHT}, {1, 0, FROM_UP}};
>>>>>>> 7c8fff4005b107b05d380beb0c4e8877448ff102

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
<<<<<<< HEAD
        int[] fromLeftUpDP = new int[cols];
        int[] fromRightUpDP = new int[cols];
        int[] upperRowDP = new int[cols];

        upperRowDP[0] = map[0][0];
        for (int col = 1; col < cols; col++)
            upperRowDP[col] = upperRowDP[col - 1] + map[0][col];

        for (int row = 1; row < rows; row++)
        {
            fromLeftUpDP[0] = upperRowDP[0] + map[row][0];
            for (int col = 1; col < cols; col++)
                fromLeftUpDP[col] = Math.max(fromLeftUpDP[col - 1], upperRowDP[col]) + map[row][col];

            fromRightUpDP[cols - 1] = upperRowDP[cols - 1] + map[row][cols - 1];
            for (int col = cols - 2; col >= 0; col--)
                fromRightUpDP[col] = Math.max(fromRightUpDP[col + 1], upperRowDP[col]) + map[row][col];

            for (int col = 0; col < cols; col++)
                upperRowDP[col] = Math.max(fromLeftUpDP[col], fromRightUpDP[col]);
        }

        return upperRowDP[cols - 1];
=======
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
>>>>>>> 7c8fff4005b107b05d380beb0c4e8877448ff102
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