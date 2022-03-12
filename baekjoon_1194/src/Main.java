import java.io.*;
import java.util.*;

public class Main
{
    static int rows, cols;
    static char[][] map;
    static boolean[][][] visit;
    static Point startPoint;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        readRowsCols();

        map = new char[rows][cols];
        readMap();

        System.out.println(Bfs2EscapeMaze());
    }

    static int Bfs2EscapeMaze()
    {
        int minMoveCnt = Integer.MAX_VALUE;
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        visit = new boolean[rows][cols][64];

        Queue<MoveStat> moveStatQueue = new LinkedList<>();
        moveStatQueue.add(new MoveStat(startPoint, 0, 0));

        MoveStat moveStat;
        int tempMinMoveCnt;
        int nextKeyBitmask;

        while (!moveStatQueue.isEmpty())
        {
            moveStat = moveStatQueue.poll();

            tempMinMoveCnt = checkCurrentMoveStat(moveStat);
            if (tempMinMoveCnt > 0)
            {
                minMoveCnt = Math.min(minMoveCnt, tempMinMoveCnt);
                continue;
            }

            for (int[] dir: dirs)
            {
                Point nextPoint = getNextPoint(moveStat.point, dir);

                if (!validatePoint(nextPoint))
                    continue;

                if (isKey(nextPoint))
                {
                    nextKeyBitmask =  generateKeyBitmask(moveStat.keyBitmask, map[nextPoint.row][nextPoint.col]);
                    if (!visit[nextPoint.row][nextPoint.col][nextKeyBitmask])
                    {
                        visit[nextPoint.row][nextPoint.col][nextKeyBitmask] = true;
                        moveStatQueue.add(new MoveStat(nextPoint, moveStat.moveCnt + 1, nextKeyBitmask));
                    }
                    continue;
                }

                if (isDoor(nextPoint) &&
                        !canOpenDoor(moveStat.keyBitmask, map[nextPoint.row][nextPoint.col]))
                    continue;

                if (visit[nextPoint.row][nextPoint.col][moveStat.keyBitmask])
                    continue;

                visit[nextPoint.row][nextPoint.col][moveStat.keyBitmask] = true;
                moveStatQueue.add(new MoveStat(nextPoint, moveStat.moveCnt + 1, moveStat.keyBitmask));
            }
        }

        if (minMoveCnt == Integer.MAX_VALUE)
            return -1;

        return minMoveCnt;
    }

    static int checkCurrentMoveStat(MoveStat moveStat)
    {
        Point curPoint = moveStat.point;

        if (map[curPoint.row][curPoint.col] == '1')
            return moveStat.moveCnt;

        return 0;
    }

    static int generateKeyBitmask(int bitmask, char key)
    {
        return bitmask | (1 << (key - 'a'));
    }

    static boolean isKey(Point p)
    {
        return map[p.row][p.col] >= 'a' && map[p.row][p.col] <= 'f';
    }

    static boolean isDoor(Point p)
    {
        return map[p.row][p.col] >= 'A' && map[p.row][p.col] <= 'F';
    }

    static boolean canOpenDoor(int keyBitmask, char door)
    {
        return (keyBitmask & (1 << (door - 'A'))) != 0;
    }

    static boolean validatePoint(Point p)
    {
        return p.row >= 0 && p.row < rows && p.col >= 0 && p.col < cols && map[p.row][p.col] != '#';
    }

    static Point getNextPoint(Point curPoint, int[] dir)
    {
        return new Point(curPoint.row + dir[0], curPoint.col + dir[1]);
    }

    static void readRowsCols() throws IOException
    {
        StringTokenizer st = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());
    }

    static void readMap() throws IOException
    {
        String line;
        for (int row = 0; row < rows; row++)
        {
            line = br.readLine();
            for (int col = 0; col < cols; col++)
            {
                map[row][col] = line.charAt(col);
                if (map[row][col] == '0')
                    startPoint = new Point(row, col);
            }
        }
    }
}

class MoveStat
{
    Point point;
    int moveCnt;
    int keyBitmask;

    public MoveStat(Point p, int moveCnt, int keyBitmask)
    {
        this.point = p;
        this.moveCnt = moveCnt;
        this.keyBitmask = keyBitmask;
    }
}

class Point
{
    int row;
    int col;

    public Point(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
}
