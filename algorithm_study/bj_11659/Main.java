import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N, M;
		N = sc.nextInt();
		M = sc.nextInt();

		int[] arr = new int[N + 1];
		long[] sum = new long[N + 1];

		for (int i = 1; i <= N; i++)
			arr[i] = sc.nextInt();

		for (int i = 1; i <= N; i++)
			sum[i] += sum[i - 1] + arr[i];

		long[] result = new long[M];
		
		int start, end;
		for (int i = 0; i < M; i++)
		{
			start = sc.nextInt();
			end = sc.nextInt();
			result[i] = sum[end] - sum[start - 1];
		}

		for (int i = 0; i < M; i++)
			System.out.println(result[i]);
	}
}
