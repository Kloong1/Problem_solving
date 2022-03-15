import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		long X, Y;
		X = sc.nextLong();
		Y = sc.nextLong();

		long winrate_prev = 100 * Y / X;

		if (winrate_prev >= 99)
		{
			System.out.println(-1);
			return;
		}

		long left, right, mid;
		long winrate_cur;
		long result;

		left = 0;
		right = X;
		result = 0;
		while (left <= right)
		{
			mid = (right + left) / 2;
			winrate_cur = 100 * (Y + mid) / (X + mid);
			if (winrate_cur > winrate_prev)
			{
				result = mid;
				right = mid - 1;
			}
			else
				left = mid + 1;
		}

		System.out.println(result);
	}
}
