import java.io.*;
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

        int numOfIslands = findAllIslands();

        int[][] minBridgeLength = new int[numOfIslands + 1][numOfIslands + 1];
        initMinBridgeLength(minBridgeLength);
        getAllMinBridgeLength(minBridgeLength);

        DisjointSet ds = new DisjointSet(numOfIslands);
        int min = getSumOfMinBridgeLength(1, minBridgeLength, ds, numOfIslands, 0, 0);

        if (min == Integer.MAX_VALUE)
            System.out.println(-1);
        else
            System.out.println(min);
    }

    static int getSumOfMinBridgeLength(int curIsland, int[][] minBridgeLength, DisjointSet ds, int numOfIslands, int bridgeCnt, int sum)
    {
        if (curIsland > numOfIslands)
        {
            if (bridgeCnt != numOfIslands - 1)
                return Integer.MAX_VALUE;
            return sum;
        }

        int minSum;
        minSum = Integer.MAX_VALUE;

        minSum = Math.min(minSum, getSumOfMinBridgeLength(curIsland + 1, minBridgeLength, ds.copy(), numOfIslands, bridgeCnt, sum));

        for (int targetIsland = 1; targetIsland <= numOfIslands; targetIsland++)
        {
            if (minBridgeLength[curIsland][targetIsland] != Integer.MAX_VALUE)
            {
                if (ds.find(curIsland) != ds.find(targetIsland))
                {
                    DisjointSet dsCopy = ds.copy();
                    dsCopy.union(curIsland, targetIsland);
                    minSum = Math.min(minSum,
                            getSumOfMinBridgeLength(curIsland + 1, minBridgeLength, dsCopy, numOfIslands, bridgeCnt + 1, sum + minBridgeLength[curIsland][targetIsland]));
                }
            }
        }

        return minSum;
    }

    static void getAllMinBridgeLength(int[][] minBridgeLength)
    {
        getMinHorizontalBridgeLength(minBridgeLength);
        getMinVerticalBridgeLength(minBridgeLength);
    }

    static void getMinVerticalBridgeLength(int[][] minBridgeLength)
    {
        int lastIsland;
        boolean seaFlag;
        int bridgeLength;

        for (int col = 0; col < cols; col++)
        {
            lastIsland = map[0][col];
            seaFlag = false;
            bridgeLength = 0;

            for (int row = 1; row < rows; row++)
            {
                if (map[row][col] == -1)
                {
                    seaFlag = true;
                    bridgeLength++;
                }
                else
                {
                    if (seaFlag && lastIsland != -1 && bridgeLength >= 2)
                    {
                        minBridgeLength[lastIsland][map[row][col]] =
                                Math.min(minBridgeLength[lastIsland][map[row][col]], bridgeLength);
                        minBridgeLength[map[row][col]][lastIsland] =
                                Math.min(minBridgeLength[map[row][col]][lastIsland], bridgeLength);
                    }

                    seaFlag = false;
                    lastIsland = map[row][col];
                    bridgeLength = 0;
                }
            }
        }
    }

    static void getMinHorizontalBridgeLength(int[][] minBridgeLength)
    {
        int lastIsland;
        boolean seaFlag;
        int bridgeLength;

        for (int row = 0; row < rows; row++)
        {
            lastIsland = map[row][0];
            seaFlag = false;
            bridgeLength = 0;

            for (int col = 1; col < cols; col++)
            {
                if (map[row][col] == -1)
                {
                    seaFlag = true;
                    bridgeLength++;
                }
                else
                {
                    if (seaFlag && lastIsland != -1 && bridgeLength >= 2)
                    {
                        minBridgeLength[lastIsland][map[row][col]] =
                                Math.min(minBridgeLength[lastIsland][map[row][col]], bridgeLength);
                        minBridgeLength[map[row][col]][lastIsland] =
                                Math.min(minBridgeLength[map[row][col]][lastIsland], bridgeLength);
                    }

                    seaFlag = false;
                    lastIsland = map[row][col];
                    bridgeLength = 0;
                }
            }
        }
    }

    static void initMinBridgeLength(int[][] minBridgeLength)
    {
        for (int[] row : minBridgeLength)
            Arrays.fill(row, Integer.MAX_VALUE);
    }

    static int findAllIslands()
    {
        boolean[][] visit = new boolean[rows][cols];
        int mapId = 0;

        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                if (map[row][col] == 0 && !visit[row][col]) //바다 = -1, 땅 = 0
                    findLandsOfIsland(row, col, ++mapId, visit);
            }
        }

        return mapId;
    }

    static void findLandsOfIsland(int startRow, int startCol, int mapId, boolean[][] visit)
    {
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

class DisjointSet
{
    int[] parent;

    DisjointSet() {}

    DisjointSet(int numOfElements)
    {
        parent = new int[numOfElements + 1];
        for (int i = 0; i < parent.length; i++)
            parent[i] = i;
    }

    int find(int e)
    {
        if (parent[e] == e)
            return e;
        parent[e] = find(parent[e]);
        return parent[e];
    }

    void union(int e1, int e2)
    {
        int pe1 = find(e1);
        int pe2 = find(e2);
        parent[pe2] = pe1;
    }

    DisjointSet copy()
    {
        DisjointSet ds = new DisjointSet();
        ds.parent = Arrays.copyOf(this.parent, parent.length);
        return ds;
    }
}