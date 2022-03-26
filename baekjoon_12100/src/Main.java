import java.io.*;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int mapLen = Integer.parseInt(br.readLine());

        int[][] map = new int[mapLen][mapLen];
        readMap(map);

        pushRight(map);

        for (int[] row : map)
            System.out.println(Arrays.toString(row));

//        System.out.println(getMaxBlock(map, 0));
    }

    static int getMaxBlock(int[][] map, int pushCnt)
    {
        if (pushCnt == 5)
            return getMax(map);

        int maxBlock = 0;
        for (int dir = 0; dir < 4; dir++)
        {
            int[][] tempMap = copyMap(map);
            pushMap(tempMap, dir);
            maxBlock = Math.max(maxBlock, getMaxBlock(tempMap, pushCnt + 1));
        }

        return maxBlock;
    }

    static void pushMap(int[][] map, int dir)
    {
        if (dir == 0)
            pushUp(map);
        else if (dir == 1)
            pushDown(map);
        else if (dir == 2)
            pushLeft(map);
        else
            pushRight(map);
    }

    static void pushUp(int[][] map)
    {
        boolean[][] isCombined = new boolean[map.length][map.length];

        for (int row = 1; row < map.length; row++)
        {
            for (int col = 0; col < map.length; col++)
            {
                int originVal = map[row][col];
                int nRow = row - 1;
                while (nRow > 0)
                {
                    if (map[nRow][col] != 0)
                        break;
                    nRow--;
                }

                if (map[nRow][col] == map[row][col] && !isCombined[nRow][col])
                {
                    map[nRow][col] *= 2;
                    isCombined[nRow][col] = true;
                    map[row][col] = 0;
                }
                else
                {
                    map[row][col] = 0;
                    map[nRow + 1][col] = originVal;
                }
            }
        }
    }

    static void pushDown(int[][] map)
    {
        boolean[][] isCombined = new boolean[map.length][map.length];

        for (int row = map.length - 2; row >= 0; row--)
        {
            for (int col = 0; col < map.length; col++)
            {
                int originVal = map[row][col];
                int nRow = row + 1;
                while (nRow < map.length - 1)
                {
                    if (map[nRow][col] != 0)
                        break;
                    nRow++;
                }

                if (map[nRow][col] == map[row][col] && !isCombined[nRow][col])
                {
                    map[nRow][col] *= 2;
                    isCombined[nRow][col] = true;
                    map[row][col] = 0;
                }
                else
                {
                    map[row][col] = 0;
                    map[nRow - 1][col] = originVal;
                }
            }
        }
    }

    static void pushLeft(int[][] map)
    {
        boolean[][] isCombined = new boolean[map.length][map.length];

        for (int col = 1; col < map.length; col++)
        {
            for (int row = 0; row < map.length; row++)
            {
                int originVal = map[row][col];
                int nCol = col - 1;
                while (nCol > 0)
                {
                    if (map[row][nCol] != 0)
                        break;
                    nCol--;
                }

                if (map[row][nCol] == map[row][col])
                {
                    if (!isCombined[row][nCol])
                    {
                        map[row][nCol] *= 2;
                        isCombined[row][nCol] = true;
                        map[row][col] = 0;
                    }
                    else
                    {
                        map[row][col] = 0;
                        map[row][nCol - 1] = originVal;
                    }
                }
                else
                {
                    map[row][col] = 0;
                    map[row][nCol + 1] = originVal;
                }
            }
        }
    }

    static void pushRight(int[][] map)
    {
        boolean[][] isCombined = new boolean[map.length][map.length];

        for (int col = map.length - 2; col >= 0; col--)
        {
            for (int row = 0; row < map.length; row++)
            {
                int originVal = map[row][col];
                int nCol = col + 1;
                while (nCol < map.length - 2)
                {
                    if (map[row][nCol] != 0)
                        break;
                    nCol++;
                }

                if (map[row][nCol] == map[row][col] && !isCombined[row][nCol])
                {
                    map[row][nCol] *= 2;
                    isCombined[row][nCol] = true;
                    map[row][col] = 0;
                }
                else
                {
                    map[row][col] = 0;
                    map[row][nCol - 1] = originVal;
                }
            }
        }
    }

    static int getMax(int[][] map)
    {
        int max = 0;
        for (int row = 0; row < map.length; row++)
            for (int col = 0; col < map.length; col++)
                max = Math.max(max, map[row][col]);

        return max;
    }

    static int[][] copyMap(int[][] map)
    {
        int[][] newMap = new int[map.length][map.length];

        for (int row = 0; row < map.length; row++)
            for (int col = 0; col < map.length; col++)
                newMap[row][col] = map[row][col];

        return newMap;
    }

    static void readMap(int[][] map) throws IOException
    {
        StringTokenizer st;
        for (int row = 0; row < map.length; row++)
        {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < map.length; col++)
                map[row][col] = Integer.parseInt(st.nextToken());
        }
    }
}
