import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        System.out.println(getAnswer(sc.nextInt()));
    }

    static long getAnswer(int wallSize)
    {
        long[] dp = new long[wallSize + 2]; //wallSize = 1일 때 dp[0-2] 초기화 부분 처리 때문에 +2 해줌

        dp[0] = 1;
        dp[1] = 2;
        dp[2] = 7;

        long sum = 0;
        for (int size = 3; size <= wallSize; size++)
        {
            dp[size] = 2 * dp[size - 1] + 3 * dp[size - 2];
            sum += dp[size - 3];
            dp[size] += sum * 2;
            dp[size] %= 1_000_000_007;
            sum %= 1_000_000_007;
        }

        return dp[wallSize];
    }
}