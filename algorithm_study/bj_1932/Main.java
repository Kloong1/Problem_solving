import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N;
		N = sc.nextInt();

		int[][] triangle = new int[N][N];
		long[][] sum = new long[N][N];

		for (int i = 0; i < N; i++)
			for (int j = 0; j <= i; j++)
				triangle[i][j] = sc.nextInt();

		sum[0][0] = triangle[0][0];
		for (int i = 0; i < N - 1; i++)
		{
			for (int j = 0; j <= i; j++)
			{
				sum[i + 1][j] = Math.max(sum[i][j], sum[i + 1][j]);
				sum[i + 1][j + 1] = Math.max(sum[i][j], sum[i + 1][j + 1]);
			}

			for (int j = 0; j <= i + 1; j++)
				sum[i + 1][j] += triangle[i + 1][j];
		}

		long max = sum[N - 1][0];
		for (int i = 1; i < N; i++)
			max = Math.max(sum[N - 1][i], max);

		System.out.println(max);
	}
}
