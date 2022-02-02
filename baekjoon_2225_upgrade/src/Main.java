import java.util.Scanner;

public class Main
{
    static int N, K;
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        N = sc.nextInt();
        K = sc.nextInt();

        //dp[K][N] => 숫자 K개를 더해서 N을 만드는 경우의 수
        int[][] dp = new int[K + 1][N + 1];

		//dp 배열을 채운다
        fillDPArr(dp);

		//문제에서 요구하는 정답은 숫자 K개를 더해서 N을 만드는 경우의 수
        System.out.println(dp[K][N]);
    }

    static void fillDPArr(int[][] dp)
    {
        //Initialize dp array
        //숫자 하나로 N을 만드는 경우는 한가지 경우만 존재한다.
        for (int i = 0; i <= N; i++)
            dp[1][i] = 1;

        //dp[kth][i] => 숫자 kth개를 더해서 i를 만드는 경우의 수
        for (int kth = 2; kth <= K; kth++)
		{
			//숫자 kth개를 더해서 sum을 만드는 경우의 수는
			//kth 번째 더해지는 숫자가 0일 때의 경우의수 + ... + kth번째 더해지는 숫자가 sum일 때의 경우의 수
			//kth 번째 더해지는 숫자가 x일 때의 경우의 수는 kth - 1개의 숫자를 더했을 때 sum - x가 될 경우의 수
			//즉 dp[kth - 1][sum - x]이다.
			//따라서 dp[kth][sum]은 getDPArrSum() 함수에서 반환하는 값인 dp[kth - 1][0 ~ sum]까지의 합이다.
			for (int sum = 0; sum <= N; sum++)
				dp[kth][sum] = getDPArrSum(dp, kth - 1, sum);
		}
    }

	static int getDPArrSum(int[][] dp, int row, int colLimit)
	{
		int sum = 0;
		for (int i = 0; i <= colLimit; i++)
			sum = (sum + dp[row][i]) % 1_000_000_000;
		return sum;
	}
}
