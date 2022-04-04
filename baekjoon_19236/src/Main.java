import java.io.*;
import java.util.*;

public class Main {

    static int[][] dirs = {{-1, 0}, {-1, -1}, {0, -1}, {1, -1}, {1, 0}, {1, 1}, {0, 1}, {-1, 1}};

    public static void main(String[] args) throws IOException{

        Fish[][] map = new Fish[4][4];
        Fish[] fishes = new Fish[16 + 1];

        readFishesAndMap(fishes, map);

        Fish shark = new Fish();
        int sumOfFishNum = makeSharkEatFirstFish(map, shark);

        System.out.println(getMaxSumOfFishNum(fishes, map, shark, sumOfFishNum));
    }

    static int getMaxSumOfFishNum(Fish[] fishes, Fish[][] map, Fish shark, int sumOfFishNum)
    {
        int maxSumOfFishNum = sumOfFishNum;

        moveAllFishes(fishes, map, shark);

        int[] dir = dirs[shark.dir];
        int nextRow, nextCol;
        nextRow = shark.row + dir[0];
        nextCol = shark.col + dir[1];

        while (isInMap(nextRow, nextCol))
        {
            if (map[nextRow][nextCol] == null)
            {
                nextRow += dir[0];
                nextCol += dir[1];
                continue;
            }

            Fish[] fishesClone = cloneFishes(fishes);
            Fish[][] mapClone = cloneMap(fishesClone);

            Fish nextShark = new Fish(nextRow, nextCol, mapClone[nextRow][nextCol].dir);

            int eatenFishNum = mapClone[nextRow][nextCol].num;
            fishesClone[eatenFishNum].isAlive = false;

            mapClone[nextRow][nextCol] = null;

            maxSumOfFishNum = Math.max(maxSumOfFishNum,
                    getMaxSumOfFishNum(fishesClone, mapClone, nextShark, sumOfFishNum + eatenFishNum));

            nextRow += dir[0];
            nextCol += dir[1];
        }

        return maxSumOfFishNum;
    }

    static void moveAllFishes(Fish[] fishes, Fish[][] map, Fish shark)
    {
        for (int idx = 1; idx < fishes.length; idx++)
        {
            if (!fishes[idx].isAlive)
                continue;

            Fish fish = fishes[idx];

            int[] dir = dirs[fish.dir];
            int nextRow, nextCol;

            nextRow = fish.row + dir[0];
            nextCol = fish.col + dir[1];

            while (!canFishMoveTo(nextRow, nextCol, shark))
            {
                fish.dir = (fish.dir + 1) % 8;
                dir = dirs[fish.dir];
                nextRow = fish.row + dir[0];
                nextCol = fish.col + dir[1];
            }

            if (map[nextRow][nextCol] == null)
            {
                map[nextRow][nextCol] = fish;
                map[fish.row][fish.col] = null;
                fish.row = nextRow;
                fish.col = nextCol;
                continue;
            }

            Fish otherFish = map[nextRow][nextCol];

            map[fish.row][fish.col] = otherFish;
            otherFish.row = fish.row;
            otherFish.col = fish.col;

            map[nextRow][nextCol] = fish;
            fish.row = nextRow;
            fish.col = nextCol;
        }
    }

    static boolean canFishMoveTo(int row, int col, Fish shark)
    {
        if (!(row >= 0 && row < 4 && col >= 0 && col < 4))
            return false;

        if (row == shark.row && col == shark.col)
            return false;

        return true;
    }

    static Fish[] cloneFishes(Fish[] fishes)
    {
        Fish[] fishesClone = new Fish[fishes.length];

        for (int i = 1; i < fishes.length; i++)
            fishesClone[i] = new Fish(fishes[i]);

        return fishesClone;
    }

    static Fish[][] cloneMap(Fish[] fishes)
    {
        Fish[][] map = new Fish[4][4];

        for (int i = 1; i < fishes.length; i++)
        {
            if (fishes[i].isAlive)
                map[fishes[i].row][fishes[i].col] = fishes[i];
        }

        return map;
    }

    static boolean isInMap(int row, int col)
    {
        return row >= 0 && row < 4 && col >= 0 && col < 4;
    }

    static int makeSharkEatFirstFish(Fish[][] map, Fish shark)
    {
        shark.row = 0;
        shark.col = 0;
        shark.dir = map[0][0].dir;

        int eatenFishNum = map[0][0].num;

        map[0][0].isAlive = false;
        map[0][0] = null;

        return eatenFishNum;
    }

    static void readFishesAndMap(Fish[] fishes, Fish[][] map) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int row = 0; row < map.length; row++)
        {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int col = 0; col < map.length; col++)
            {
                int fishNum = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken()) - 1;

                fishes[fishNum] = new Fish(fishNum, row, col, dir, true);
                map[row][col] = fishes[fishNum];
            }
        }
    }
}

class Fish {
    int num;
    int row;
    int col;
    int dir;
    boolean isAlive;

    public Fish() {}

    public Fish(int row, int col, int dir) {
        this.row = row;
        this.col = col;
        this.dir = dir;
    }

    public Fish(int num, int row, int col, int dir, boolean isAlive) {
        this.num = num;
        this.row = row;
        this.col = col;
        this.dir = dir;
        this.isAlive = isAlive;
    }

    public Fish(Fish o) {
        this.num = o.num;
        this.row = o.row;
        this.col = o.col;
        this.dir = o.dir;
        this.isAlive = o.isAlive;
    }
}