import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        int wallWidth = sc.nextInt();

        System.out.println(getAnswer(wallWidth));
    }

    static long getAnswer(int wallWidth)
    {
        if (wallWidth % 2 == 1)
            return 0;

        long[] dp = new long[wallWidth + 1];
        dp[2] = 3;

        for (int width = 4; width <= wallWidth; width += 2)
        {
            dp[width] += dp[width - 2] * 3;

            //idx 너비를 제외하고 나머지를 특이 모양으로 채우는 경우
            for (int idx = 4; width - idx >= 2; idx += 2)
                dp[width] += dp[width - idx] * 2;

            //특이 모양으로 width만큼 전부 채우는 경우
            dp[width] += 2;
        }

        return dp[wallWidth];
    }
}
