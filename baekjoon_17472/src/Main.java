import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int rows, cols;
    static int[][] map;

    public static void main(String[] args) throws IOException
    {
        readRowsCols();

        map = new int[rows][cols];
        readMap();

        ArrayList<Island> islands = findAllIslands();

        for (int[] row : map)
            System.out.println(Arrays.toString(row));
    }

    static ArrayList<Island> findAllIslands()
    {
        ArrayList<Island> islands = new ArrayList<>();

        boolean[][] visit = new boolean[rows][cols];
        int mapId = 1;

        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                if (map[row][col] == 0 && !visit[row][col]) //바다 = -1, 땅 = 0
                {
                    ArrayList<Land> lands = findLandsOfIsland(row, col, mapId++, visit);
                    islands.add(new Island(lands));
                }
            }
        }

        return islands;
    }

    static ArrayList<Land> findLandsOfIsland(int startRow, int startCol, int mapId, boolean[][] visit)
    {
        ArrayList<Land> lands = new ArrayList<>();
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        Queue<Land> bfsQ = new LinkedList<>();
        bfsQ.add(new Land(startRow, startCol));
        visit[startRow][startCol] = true;

        Land land;
        int targetRow, targetCol;

        while (!bfsQ.isEmpty())
        {
            land = bfsQ.poll();
            map[land.row][land.col] = mapId;
            lands.add(land);

            for (int[] dir : dirs)
            {
                targetRow = land.row + dir[0];
                targetCol = land.col + dir[1];

                if (!isLand(targetRow, targetCol))
                    continue;

                if (visit[targetRow][targetCol])
                    continue;

                visit[targetRow][targetCol] = true;
                bfsQ.add(new Land(targetRow, targetCol));
            }
        }

        return lands;
    }

    static boolean isLand(int row, int col)
    {
        return (row >= 0 && row < rows && col >= 0 && col < cols && map[row][col] == 0);
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