import java.io.*;
import java.util.*;

public class Main
{
    static int N;
    static int[][] bamboos;
    static int[][] maxMovesArr;
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        readN();

        bamboos = new int[N][N];
        readBamboos();

        System.out.println(getMaxMoves());
    }

    static int getMaxMoves()
    {
        int maxMoves = 0;

        maxMovesArr = new int[N][N];

        for (int row = 0; row < N; row++)
        {
            for (int col = 0; col < N; col++)
                maxMoves = Math.max(maxMoves, dfs2GetMaxMoves(row, col));
        }

        return maxMoves;
    }

    static int dfs2GetMaxMoves(int startRow, int startCol)
    {
        if (maxMovesArr[startRow][startCol] != 0)
            return maxMovesArr[startRow][startCol];

        int maxMoves = 1;

        int nextRow, nextCol;
        for (int[] dir : dirs)
        {
            nextRow = startRow + dir[0];
            nextCol = startCol + dir[1];

            if (!isInMap(nextRow, nextCol))
                continue;

            if (bamboos[nextRow][nextCol] <= bamboos[startRow][startCol])
                continue;

            maxMoves = Math.max(maxMoves, dfs2GetMaxMoves(nextRow, nextCol) + 1);
        }

        maxMovesArr[startRow][startCol] = maxMoves;

        return maxMoves;
    }

    static boolean isInMap(int row, int col)
    {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static void readN() throws IOException
    {
        N = Integer.parseInt(br.readLine());
    }

    static void readBamboos() throws IOException
    {
        StringTokenizer st;

        for (int row = 0; row < N; row++)
        {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < N; col++)
                bamboos[row][col] = Integer.parseInt(st.nextToken());
        }
    }
}
