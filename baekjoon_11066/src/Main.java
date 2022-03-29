import java.io.*;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int numOfTestcases = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();
        for (int testcase = 0; testcase < numOfTestcases; testcase++)
        {
            int numOfChapters = Integer.parseInt(br.readLine());

            int[] chapters = new int[numOfChapters + 1];
            readChapters(chapters);

            sb.append(getMinMergingCost(chapters)).append('\n');
        }

        System.out.println(sb.toString());
    }

    static int getMinMergingCost(int[] chapters)
    {
        //DP에 이용할 배열. minCost[x][y]는 챕터 x부터 챕터 y를 합치는데 드는 최소 비용.
        int[][] minCost = new int[chapters.length][chapters.length];
        initMinCost(minCost, chapters); //초깃값을 넣어준다.

        //연산을 빠르게 하기 위한 누적합 배열.
        int[] chapterSumTo = new int[chapters.length];
        initChapterSumTo(chapterSumTo, chapters);

        //모든 챕터를 전부 합치는 경우를 구하기 위해서, 챕터를 합치는 범위를 챕터 2개, 3개씩 늘려간다
        for (int range = 2; range < chapters.length; range++)
        {
            for (int startIdx = 1; startIdx + range < chapters.length; startIdx++)
            {
                int endIdx = startIdx + range;
                for (int midIdx = startIdx; midIdx < endIdx; midIdx++)
                {
                    minCost[startIdx][endIdx] = Math.min(
                            minCost[startIdx][endIdx],
                            minCost[startIdx][midIdx] + minCost[midIdx + 1][endIdx] + chapterSumTo[endIdx] - chapterSumTo[startIdx - 1]
                    );
                }
            }
        }

        return minCost[1][chapters.length - 1];
    }

    static void initChapterSumTo(int[] chapterSumTo, int[] chapters)
    {
        //startIdx = 1인 경우를 위해서 chapterSumTo[0]을 0으로 초기화.
        chapterSumTo[0] = 0;
        chapterSumTo[1] = chapters[1];
        for (int i = 2; i < chapters.length; i++)
            chapterSumTo[i] = chapterSumTo[i - 1] + chapters[i];
    }

    static void initMinCost(int[][] minCost, int[] chapters)
    {
        //최솟값을 갱신해가며 저장하는 배열이기 때문에, 먼저 Integer.MAX_VALUE 로 초기화해준다.
        for (int[] row : minCost)
            Arrays.fill(row, Integer.MAX_VALUE);

        for (int i = 1; i < minCost.length - 1; i++)
        {
            minCost[i][i] = 0; //이미 존재하는 챕터는 합치는 비용이 0
            minCost[i][i + 1] = chapters[i] + chapters[i + 1]; //연속된 두 챕터를 합치는 최소 비용은 두 챕터의 크기의 합
        }

        minCost[minCost.length - 1][minCost.length - 1] = 0;
    }

    static void readChapters(int[] chapters) throws IOException
    {
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i < chapters.length; i++)
            chapters[i] = Integer.parseInt(st.nextToken());
    }
}
