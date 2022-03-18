import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N;
		N = sc.nextInt();

		int[] stair = new int[N + 1];
		int[] sum = new int[N + 1];

		for (int i = 1; i <= N; i++)
			stair[i] = sc.nextInt();

		sum[1] = stair[1];
		if (N >= 2)
			sum[2] = stair[1] + stair[2];

		for (int i = 3; i <= N; i++)
		{
			int onestep = sum[i - 3] + stair[i - 1];
			int twostep = sum[i - 2];

			sum[i] = onestep > twostep ? onestep : twostep;
			sum[i] += stair[i];
		}

		System.out.println(sum[N]);
	}
}
