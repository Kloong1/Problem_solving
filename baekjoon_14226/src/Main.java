import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        int numOfEmoticons = readNumOfEmoticons();

        System.out.println(getMinTime(numOfEmoticons));
    }

    static int getMinTime(int numOfEmoticons)
    {
        int sizeOfVisitArr = Math.min(numOfEmoticons * 2 + 1, 1100);

        boolean[][] visit = new boolean[sizeOfVisitArr][sizeOfVisitArr];

        Queue<Status> statusQueue = new LinkedList<>();
        statusQueue.add(new Status(1, 0, 0));
        visit[1][0] = true;

        Status stat;
        while (!statusQueue.isEmpty())
        {
            stat = statusQueue.poll();

            if (stat.screenCnt == numOfEmoticons)
                return stat.sec;

            copyScreenAndEnqueue(stat, statusQueue, visit);

            if (stat.screenCnt > 0)
                deleteEmoticonAndEnqueue(stat, statusQueue, visit);

            if (stat.screenCnt + stat.clipboardCnt < sizeOfVisitArr)
                pasteClipboardAndEnqueue(stat, statusQueue, visit);
        }

        //정상 입력이 아닌 경우
        return -1;
    }

    static void pasteClipboardAndEnqueue(Status stat, Queue<Status> statusQueue, boolean[][] visit)
    {
        if (!visit[stat.screenCnt + stat.clipboardCnt][stat.clipboardCnt])
        {
            visit[stat.screenCnt + stat.clipboardCnt][stat.clipboardCnt] = true;
            statusQueue.add(new Status(stat.screenCnt + stat.clipboardCnt, stat.clipboardCnt, stat.sec + 1));
        }
    }

    static void deleteEmoticonAndEnqueue(Status stat, Queue<Status> statusQueue, boolean[][] visit)
    {
        if (!visit[stat.screenCnt - 1][stat.clipboardCnt])
        {
            visit[stat.screenCnt - 1][stat.clipboardCnt] = true;
            statusQueue.add(new Status(stat.screenCnt - 1, stat.clipboardCnt, stat.sec + 1));
        }
    }

    static void copyScreenAndEnqueue(Status stat, Queue<Status> statusQueue, boolean[][] visit)
    {
        if (!visit[stat.screenCnt][stat.screenCnt])
        {
            visit[stat.screenCnt][stat.screenCnt] = true;
            statusQueue.add(new Status(stat.screenCnt, stat.screenCnt, stat.sec + 1));
        }
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