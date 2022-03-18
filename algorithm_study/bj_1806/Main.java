import java.util.Scanner;

public class Main
{
	public static void main(String args[])
	{
		int cnt = 0;

		Scanner sc = new Scanner(System.in);
		
		// input N, S
		int N, S;
		N = sc.nextInt();
		S = sc.nextInt();

		//++right를 위해 1칸 더 여유롭게 설
		int[] arr = new int[N + 1];
		for (int i = 0; i < N; i++)
			arr[i] = sc.nextInt();

		//for 2 pointer
		int left = 0, right = 0;

		int sum, result;
		sum = arr[0];
		result = N + 1;
		while (right < N)
		{
			if (sum < S)
				sum += arr[++right];
			else
			{
				result = min(result, right - left + 1);
				sum -= arr[left++];
			}
		}

		System.out.println(result == N + 1 ? 0 : result);
	}

	static int min(int a, int b)
	{
		return (a < b ? a : b);
	}
}
