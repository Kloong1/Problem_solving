import java.io.*;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int numOfTestcases = Integer.parseInt(br.readLine());

        long[][][] dp = new long[21][21][21];
        setDpArr(dp);

        System.out.println(getAnswer(dp, numOfTestcases));
    }

    static String getAnswer(long[][][] dp, int numOfTestcases) throws IOException
    {
        StringBuilder answer = new StringBuilder();

        StringTokenizer st;
        for (int testcase = 0; testcase < numOfTestcases; testcase++)
        {
            st = new StringTokenizer(br.readLine());

            int numOfSticks = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());

            answer.append(dp[numOfSticks][left][right]).append('\n');
        }

        return answer.toString();
    }

    static void setDpArr(long[][][] dp)
    {
        dp[1][1][1] = 1;

        for (int numOfSticks = 2; numOfSticks <= 20; numOfSticks++)
        {
            for (int left = 1; left <= numOfSticks; left++)
            {
                for (int right = 1; right <= numOfSticks; right++)
                {
                    dp[numOfSticks][left][right] += dp[numOfSticks - 1][left - 1][right];
                    dp[numOfSticks][left][right] += dp[numOfSticks - 1][left][right - 1];
                    dp[numOfSticks][left][right] += (numOfSticks - 2) * dp[numOfSticks - 1][left][right];
                }
            }
        }
    }
}
