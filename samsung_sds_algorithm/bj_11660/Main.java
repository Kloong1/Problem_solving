import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N, M;
		N = sc.nextInt();
		M = sc.nextInt();

		int[][] arr = new int[N + 1][N + 1];
		long[][] sum = new long[N + 1][N + 1];

		for (int i = 1; i <= N; i++)
			for (int j = 1; j <= N; j++)
				arr[i][j] = sc.nextInt();

		for (int i = 1; i <= N; i++)
			for (int j = 1; j <= N; j++)
				sum[i][j] = sum[i - 1][j] + sum[i][j - 1] - sum[i - 1][j - 1] + arr[i][j];

		long[] result = new long[M];

		int x1, y1, x2, y2;
		int start, end;
		for (int i = 0; i < M; i++)
		{
			x1 = sc.nextInt();
			y1 = sc.nextInt();
			x2 = sc.nextInt();
			y2 = sc.nextInt();

			result[i] = sum[x2][y2] - sum[x1 - 1][y2] - sum[x2][y1 - 1] + sum[x1 - 1][y1 - 1];
		}

		for (int i = 0; i < M; i++)
			System.out.println(result[i]);
	}
}
