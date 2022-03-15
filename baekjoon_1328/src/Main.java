import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int numOfBuildings;
        int leftCnt, rightCnt;

        st = new StringTokenizer(br.readLine());
        numOfBuildings = Integer.parseInt(st.nextToken());
        leftCnt = Integer.parseInt(st.nextToken());
        rightCnt = Integer.parseInt(st.nextToken());

        long[][][] dp = new long[numOfBuildings + 1][numOfBuildings + 1][numOfBuildings + 1];
        setDpArr(dp, numOfBuildings);

        System.out.println(dp[numOfBuildings][leftCnt][rightCnt]);
    }

    static void setDpArr(long[][][] dp, int numOfBuildings)
    {
        dp[1][1][1] = 1;

        for (int building = 2; building <= numOfBuildings; building++)
        {
            for (int left = 1; left <= building; left++)
            {
                for (int right = 1; right <= building; right++)
                {
                    dp[building][left][right] = dp[building - 1][left - 1][right];
                    dp[building][left][right] += dp[building - 1][left][right - 1];
                    dp[building][left][right] += (building - 2) * dp[building - 1][left][right];
                    dp[building][left][right] %= 1_000_000_007;
                }
            }
        }
    }
}
