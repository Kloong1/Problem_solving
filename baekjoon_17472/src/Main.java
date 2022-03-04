import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int rows, cols;
    static int[][] map;
    static int[][] dirs =

    public static void main(String[] args) throws IOException
    {
        readRowsCols();

        map = new int[rows][cols];
        readMap();

        for (int[] row : map)
            System.out.println(Arrays.toString(row));

        ArrayList<Island> islands = new ArrayList<>();
        findAllIslands(islands);
    }

    static void findAllIslands(ArrayList<Island> islands)
    {
        boolean[][] visit = new boolean[rows][cols];
        int mapId = 1;

        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                if (map[row][col] == 0 && !visit[row][col]) //바다 = -1, 땅 = 0
                {
                    ArrayList<Land> lands = findBorderlandsOfIsland(row, col, mapId++, visit);
                    islands.add(new Island(lands));
                }
            }
        }
    }

    static ArrayList<Land> findBorderlandsOfIsland(int startRow, int startCol, int mapId, boolean[][] visit)
    {
        ArrayList<Land> borderlands = new ArrayList<>();
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        Queue<Land> bfsQ = new LinkedList<>();
        bfsQ.add(new Land(startRow, startCol));
        Land land;

        int newRow, newCol;

        while (!bfsQ.isEmpty())
        {
            land = bfsQ.poll();

            map[land.row][land.col] = mapId;
            visit[land.row][land.col] = true;

            if (isBorderland(land.row, land.col, dirs))
                borderlands.add(land);

            for (int[] dir : dirs)
            {
                newRow = land.row + dir[0];
                newCol = land.col + dir[1];

                if (!isInMap(newRow, newCol) || map[newRow][newCol] != 0)
                    continue;

                bfsQ.add(new Land(newRow, newCol));
            }
        }

        return borderlands;
    }

    static boolean isBorderland(int row, int col, int[][] dirs)
    {
        int newRow, newCol;
        for (int[] dir : dirs)
        {
            newRow = row + dir[0];
            newCol = col + dir[1];

            if (isInMap(newRow, newCol) || map[newRow][newCol] == -1)
                return true;
        }

        return false;
    }

    static boolean isInMap(int row, int col)
    {
        return (row >= 0 && row < rows && col >= 0 && col < cols);
    }

    static void readMap() throws IOException
    {
        StringTokenizer st;

        for (int row = 0; row < rows; row++)
        {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < cols; col++)
                map[row][col] = Integer.parseInt(st.nextToken()) - 1; //바다 = -1, 땅 = 0
        }
    }

    static void readRowsCols() throws IOException
    {
        StringTokenizer st = new StringTokenizer(br.readLine());

        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());
    }
}

class Island
{
    ArrayList<Land> lands;
    int[] bridgeLengths;

    public Island(ArrayList<Land> lands)
    {
        this.lands = lands;
    }
}

class Land
{
    int row;
    int col;

    Land(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
}