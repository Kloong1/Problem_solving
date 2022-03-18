import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N;
		N = sc.nextInt();

		int[] arr = new int[N + 1];
		for (int i = 1; i <= N; i++)
			arr[i] = sc.nextInt();

		int[] len = new int[N + 1];
		len[0] = 1;

		for (int i = 1; i <= N; i++)
		{
			if (len[i] == 0)
				len[i] = len[i - 1];

			for (int j = i + 1; j <= N && arr[j] > arr[i]; j++)
				len[j]++;
		}
	}
}
