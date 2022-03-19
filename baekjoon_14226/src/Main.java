import java.io.BufferedReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        int numOfEmoticons = readNumOfEmoticons();

        int sizeOfMinArr = Math.min(numOfEmoticons * 2 + 1, 1100);

        int[][] minArr = new int[sizeOfMinArr][sizeOfMinArr];
        setMinArr(minArr, sizeOfMinArr);

        System.out.println(getMin(minArr[numOfEmoticons]));
    }

    static int getMin(int[] arr)
    {
        return 0;
    }

    static void setMinArr(int[][] minArr, int sizeOfMinArr)
    {
        initMinArr(minArr);

        Queue<Status> statusQueue = new LinkedList<>();
        statusQueue.add(new Status(1, 0, 0));
        minArr[1][0] = 0;

        Status stat;
        while (!statusQueue.isEmpty())
        {
            stat = statusQueue.poll();

            if (minArr[stat.screenCnt][stat.clipboardCnt] <= stat.sec)
                continue;

            minArr[stat.screenCnt][stat.clipboardCnt] = stat.sec;

            pasteAndUpdateArr(stat, minArr, )
            int sec = stat.sec;
            for (int screenCnt = stat.screenCnt; screenCnt < sizeOfMinArr; screenCnt += stat.clipboardCnt)
            {
                if (sec < minArr[screenCnt][stat.clipboardCnt])
                {
                    minArr[screenCnt][stat.clipboardCnt] = sec;
                    statusQueue.add(new Status(screenCnt, stat.clipboardCnt, sec));
                }
                sec++;
            }

            sec =
        }
    }

    static void initMinArr(int[][] minArr)
    {
        for (int[] row : minArr)
            Arrays.fill(row, Integer.MIN_VALUE);
    }

    static int readNumOfEmoticons()
    {
        Scanner sc = new Scanner(System.in);
        return sc.nextInt();
    }
}

class Status
{
    int screenCnt;
    int clipboardCnt;
    int sec;

    public Status(int screenCnt, int clipboardCnt, int sec)
    {
        this.screenCnt = screenCnt;
        this.clipboardCnt = clipboardCnt;
        this.sec = sec;
    }
}