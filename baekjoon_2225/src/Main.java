import java.util.Scanner;

public class Main
{
    static int N, K;
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        K = sc.nextInt();

        int[][][] dp = new int[K + 1][N + 1][N + 1];

        fillDPArr(dp);

        //K번째 dp 배열의 제일 마지막 열의 합을 구하면 답이 된다.
        int cnt = 0;
        for (int i = 0; i <= N; i++)
            cnt = (cnt + dp[K][i][N]) % 1_000_000_000;

        System.out.println(cnt);
    }

    static void fillDPArr(int[][][] dp)
    {
        //Initialize dp.
        //숫자 하나로 N을 만드는 경우는 한가지 경우만 존재한다.
        for (int i = 0; i <= N; i++)
            dp[1][i][i] = 1;

        //dp[kth][i][j] => 숫자 kth개를 더해서 j를 만드는데, 마지막에 더하는 숫자가 i일 때의 경우의 수
        for (int kth = 2; kth <= K; kth++)
            fillKthDPArr(dp, kth);
    }

    static void fillKthDPArr(int[][][] dp, int Kth)
    {
        //Kth번째에 num을 더해서 숫자 sum을 만드는 경우의 수를 구한다
        for (int num = 0; num <= N; num++)
        {
            //Kth번째 더하는 숫자는 num으로 고정되어 있으므로, Kth -1 번째 숫자를 더했을 때
            //sum - num이 나오는 모든 경우의 수를 더하면 된다
            //이는 dp[Kth - 1]의 sum-num 열의 값을 전부 합하면 나오는 값이다.
            for (int sum = num; sum <= N; sum++)
                dp[Kth][num][sum] = getColSum(dp[Kth - 1], sum - num);
        }
    }

    static int getColSum(int[][] dpKth, int col)
    {
        int sum = 0;

        for (int i = 0; i <= col; i++)
            sum = (sum + dpKth[i][col]) % 1_000_000_000;

        return sum;
    }
}
