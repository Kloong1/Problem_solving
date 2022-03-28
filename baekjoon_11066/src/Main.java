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

            int[] chapters = new int[numOfChapters];
            readChapters(chapters);

            sb.append(getMinMergingCost(chapters));
            sb.append('\n');
        }

        System.out.println(sb.toString());
    }

    static int getMinMergingCost(int[] chapters)
    {
        int[] minCost = new int[chapters.length];
        minCost[0] = chapters[0];
        minCost[1] = chapters[0] + chapters[1];
        minCost[2] = Math.min(
                minCost[1] + minCost[1] + chapters[2],
                (chapters[1] + chapters[2]) * 2 + chapters[0]);

        for (int idx = 3; idx < chapters.length; idx++)
        {
            minCost[idx] = Math.min(
                    minCost[idx - 1] + (minCost[idx - 1] + chapters[idx]),
                    (minCost[idx - 2] + (chapters[idx - 1] + chapters[idx])) * 2);
        }

        return minCost[minCost.length - 1];
    }

    static void readChapters(int[] chapters) throws IOException
    {
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < chapters.length; i++)
            chapters[i] = Integer.parseInt(st.nextToken());
    }
}
