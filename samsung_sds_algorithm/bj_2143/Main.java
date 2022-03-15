import java.util.Scanner;
import java.util.Arrays;
import java.util.Collections;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int T;
		T = sc.nextInt();
		
		int N, M;
		int[] arr1, arr2;

		N = sc.nextInt();
		arr1 = new int[N];
		for (int i = 0; i < N; i++)
			arr1[i] = sc.nextInt();

		M = sc.nextInt();
		arr2 = new int[M];
		for (int i = 0; i < M; i++)
			arr2[i] = sc.nextInt();

		Long[] subsum1, subsum2;

		subsum1 = get_subsum_arr(arr1, N);
		subsum2 = get_subsum_arr(arr2, M);

		Arrays.sort(subsum1);
		Arrays.sort(subsum2, Collections.reverseOrder());

		long cnt = 0;

		int p1 = 0, p2 = 0;
		long pivot;
		long target;
		while (p1 < subsum1.length && p2 < subsum2.length)
		{
			pivot = subsum1[p1];
			target = T - pivot;

			if (subsum2[p2] > target)
				p2++;
			else if (subsum2[p2] < target)
				p1++;
			else
			{
				long cntA = 0, cntB = 0;

				for (; p1 < subsum1.length && subsum1[p1] == pivot; p1++)
					cntA++;
				for (; p2 < subsum2.length && subsum2[p2] == target; p2++)
					cntB++;

				// multiplication!!
				cnt += cntA * cntB;
			}
		}

		System.out.println(cnt);
	}

	static Long[] get_subsum_arr(int[] arr, int len)
	{
		Long[] subsum_arr;
		int subsum_arr_len;

		subsum_arr_len = len * (len + 1) / 2;
		subsum_arr = new Long[subsum_arr_len];

		int k = 0;
		long sum = 0;
		for (int i = 0; i < len; i ++)
		{
			sum = 0;
			for (int j = i; j < len; j++)
			{
				sum += arr[j];
				subsum_arr[k++] = sum;
			}
		}

		return (subsum_arr);
	}
}
