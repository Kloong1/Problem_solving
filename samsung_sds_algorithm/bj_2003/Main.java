import java.util.Scanner;

public class Main
{
	public static void main(String args[])
	{
		int cnt = 0;

		Scanner sc = new Scanner(System.in);
		
		// input N, M
		int N, M;
		N = sc.nextInt();
		M = sc.nextInt();

		//++right를 위해 1칸 더 여유롭게 설
		int[] arr = new int[N + 1];
		for (int i = 0; i < N; i++)
			arr[i] = sc.nextInt();

		//for 2 pointer
		int left = 0, right = 0;

		int sum;
		sum = arr[0];
		while (right < N)
		{
			if (sum < M)
				sum += arr[++right];
			else if (sum > M)
				sum -= arr[left++];
			else
			{
				cnt++;
				sum -= arr[left++];
			}
		}

		System.out.println(cnt);
	}
}
