import java.io.*;
import java.util.*;

public class Main {

    static int[][] map;

    public static void main(String[] args) throws IOException {

        Fish originBabyShark = readMap();

        System.out.println(calculateTimeBabySharkCanHunt(originBabyShark));
    }

    static int calculateTimeBabySharkCanHunt(Fish originBabyShark)
    {
        int huntTime = 0;
        int babySharkSize = 2;
        int eatFishCnt = 0;

        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        boolean[][] visit = new boolean[map.length][map.length];
        Queue<Fish> babySharkQueue = new LinkedList<>();
        ArrayList<Fish> eatableFishes = new ArrayList<>();

        while (true)
        {
            babySharkQueue.add(originBabyShark);
            map[originBabyShark.row][originBabyShark.col] = 0;
            visit[originBabyShark.row][originBabyShark.col] = true;

            int curRoundCnt, nextRoundCnt;
            curRoundCnt = 1;
            nextRoundCnt = 0;

            Fish babyShark;
            while (!babySharkQueue.isEmpty())
            {
                babyShark = babySharkQueue.poll();

                curRoundCnt--;

                if (isBabySharkWithEatableFish(babyShark.row, babyShark.col, babySharkSize))
                    eatableFishes.add(babyShark);

                for (int[] dir: dirs)
                {
                    int nextRow, nextCol;
                    nextRow = babyShark.row + dir[0];
                    nextCol = babyShark.col + dir[1];

                    if (!isValidPoint(nextRow, nextCol))
                        continue;

                    if (visit[nextRow][nextCol] || map[nextRow][nextCol] > babySharkSize)
                        continue;

                    visit[nextRow][nextCol] = true;
                    babySharkQueue.add(new Fish(nextRow, nextCol, babyShark.dist + 1));
                    nextRoundCnt++;
                }

                if (curRoundCnt == 0)
                {
                    if (!eatableFishes.isEmpty())
                        break;
                    else
                    {
                        curRoundCnt = nextRoundCnt;
                        nextRoundCnt = 0;
                    }
                }
            }

            if (eatableFishes.isEmpty())
                break;

            eatableFishes.sort((Fish f1, Fish f2) -> {
                if (f1.row == f2.row)
                    return Integer.compare(f1.col, f2.col);
                else
                    return Integer.compare(f1.row, f2.row);
            });

            Fish eatableFish = eatableFishes.get(0);

            originBabyShark.row = eatableFish.row;
            originBabyShark.col = eatableFish.col;
            originBabyShark.dist = 0;

            map[eatableFish.row][eatableFish.col] = 0;

            huntTime += eatableFish.dist;

            eatFishCnt++;
            if (eatFishCnt == babySharkSize)
            {
                babySharkSize++;
                eatFishCnt = 0;
            }

            babySharkQueue.clear();
            eatableFishes.clear();
            clearVisit(visit);
        }

        return huntTime;
    }

    static boolean isValidPoint(int row, int col)
    {
        return row >= 0 && row < map.length && col >= 0 && col < map.length;
    }

    static boolean isBabySharkWithEatableFish(int row, int col, int babySharkSize)
    {
        return map[row][col] != 0 && map[row][col] < babySharkSize;
    }

    static void clearVisit(boolean[][] visit)
    {
        for (boolean[] row: visit)
            Arrays.fill(row, false);
    }

    static Fish readMap() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int mapLen = Integer.parseInt(br.readLine());

        map = new int[mapLen][mapLen];

        Fish babyShark = null;
        StringTokenizer st;
        for (int row = 0; row < mapLen; row++)
        {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < mapLen; col++)
            {
                map[row][col] = Integer.parseInt(st.nextToken());
                if (map[row][col] == 9)
                {
                    babyShark = new Fish(row, col, 0);
                    map[row][col] = 999999;
                }
            }
        }

        return babyShark;
    }
}

class Fish {
    int row;
    int col;
    int dist;

    public Fish(int row, int col, int dist) {
        this.row = row;
        this.col = col;
        this.dist = dist;
    }
}