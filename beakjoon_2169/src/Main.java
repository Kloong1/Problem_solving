import java.io.*;
import java.util.*;

public class Main
{
    static int rows, cols;
    static int[][] map;

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