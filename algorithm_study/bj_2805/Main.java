import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		long N, M;
		N = sc.nextLong();
		M = sc.nextLong();

		// for binary search
		long left, right, mid;
		long max_height;

		long[] trees = new long[(int)N];
		right = 0;
		for (int i = 0; i < N; i++)
		{
			trees[i] = sc.nextLong();
			right = max(right, trees[i]);
		}

		long trees_to_go;

		left = 0;
		max_height = 0;
		while (left <= right)
		{
			mid = (left + right) / 2;
			trees_to_go = cut_trees(trees, mid);

			if (trees_to_go > M)
			{
				max_height = mid;
				left = mid + 1;
			}
			else if (trees_to_go < M)
				right = mid - 1;
			else
			{
				max_height = mid;
				break;
			}
		}

		System.out.println(max_height);
	}

	static long max(long a, long b)
	{
		return (a > b ? a : b);
	}

	static long cut_trees(long[] trees, long height)
	{
		long sum = 0;

		for (long tree : trees)
		{
			if (tree > height)
				sum += tree - height;
		}
		
		return (sum);
	}
}
