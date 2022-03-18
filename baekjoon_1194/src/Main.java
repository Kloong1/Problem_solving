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
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //4방향으로 이동

        //3차원 visit 배열. 2^6 = 64. 열쇠 소유 상태의 경우의 수는 64개.
        visit = new boolean[rows][cols][64];

        //bfs에 쓸 queue. 출발 지점을 넣어주면서 초기화.
        Queue<MoveStat> moveStatQueue = new LinkedList<>();
        moveStatQueue.add(new MoveStat(startPoint, 0, 0));

        MoveStat moveStat;
        int tempMinMoveCnt;
        int nextKeyBitmask;

        //BFS 시작.
        while (!moveStatQueue.isEmpty())
        {
            moveStat = moveStatQueue.poll();

            //출구 '1'에 도착했으면 최소 이동 횟수 갱신.
            tempMinMoveCnt = checkCurrentMoveStat(moveStat);
            if (tempMinMoveCnt > 0)
            {
                minMoveCnt = Math.min(minMoveCnt, tempMinMoveCnt);
                continue;
            }

            //4 방향으로 움직여봄.
            for (int[] dir: dirs)
            {
                Point nextPoint = getNextPoint(moveStat.point, dir);

                //다음 지점이 맵 안에 있는지, 벽이 아닌지 확인.
                if (!validatePoint(nextPoint))
                    continue;

                //다음 지점이 열쇠인 경우
                if (isKey(nextPoint))
                {
                    //열쇠를 획득하는 경우이므로 새로운 bitmask 만들어줌
                    nextKeyBitmask =  generateKeyBitmask(moveStat.keyBitmask, map[nextPoint.row][nextPoint.col]);
                    //방문 체크후 방문 안했으면 queue에 넣어줌.. 새로운 bitmask로 체크해주는 것에 유의.
                    if (!visit[nextPoint.row][nextPoint.col][nextKeyBitmask])
                    {
                        visit[nextPoint.row][nextPoint.col][nextKeyBitmask] = true;
                        moveStatQueue.add(new MoveStat(nextPoint, moveStat.moveCnt + 1, nextKeyBitmask));
                    }
                    continue;
                }

                //다음 지점이 문인데, 현재 소유한 열쇠로 열 수 없으면 패스.
                if (isDoor(nextPoint) &&
                        !canOpenDoor(moveStat.keyBitmask, map[nextPoint.row][nextPoint.col]))
                    continue;

                //다음 지점이 빈 공간인데 이미 방문했으면 패스.
                if (visit[nextPoint.row][nextPoint.col][moveStat.keyBitmask])
                    continue;

                //queue에 넣어줌.
                visit[nextPoint.row][nextPoint.col][moveStat.keyBitmask] = true;
                moveStatQueue.add(new MoveStat(nextPoint, moveStat.moveCnt + 1, moveStat.keyBitmask));
            }
        }

        //출구 '1'에 한번도 도착하지 못한 경우.
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
                    startPoint = new Point(row, col); //출발 지점을 입력받으면서 저장해둠.
            }
        }
    }
}

class MoveStat
{
    Point point;
    int moveCnt; //현재까지의 이동 횟수
    int keyBitmask; //현재 열쇠 소유 상태를 표현하는 bitmask

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
