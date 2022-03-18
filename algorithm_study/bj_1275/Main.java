import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N, Q;
		N = sc.nextInt();
		Q = sc.nextInt();

		int[] nums = new int[N];
		for (int i = 0; i < N; i++)
			nums[i] = sc.nextInt();

		IndexTree idxtree = new IndexTree(nums);

		int left, right, target;
		long num;
		long[] sums = new long[Q];

		for (int i = 0; i < Q; i++)
		{
			left = sc.nextInt();
			right = sc.nextInt();
			target = sc.nextInt();
			num = sc.nextLong();

			if (left > right)
			{
				int temp = left;
				left = right;
				right = temp;
			}

			sums[i] = idxtree.subsum(1, idxtree.num_of_leaves, 1, left, right);
			idxtree.update(1, idxtree.num_of_leaves, 1, target, num - idxtree.tree[idxtree.num_of_leaves + target - 1]);
		}

		for (int i = 0; i < Q; i++)
			System.out.println(sums[i]);
	}
}

class IndexTree
{
	int num_of_leaves;
	long[] tree;

	public IndexTree(int[] leaves)
	{
		num_of_leaves = 1;
		while (num_of_leaves < leaves.length)
			num_of_leaves *= 2;

		tree = new long[num_of_leaves * 2];

		int i;
		for (i = 0; i < leaves.length; i++)
			tree[num_of_leaves + i] = leaves[i];

		for (i = num_of_leaves - 1; i >= 1; i--)
			tree[i] = tree[i * 2] + tree[i * 2 + 1];
	}

	public long subsum(int left, int right, int node, int query_left, int query_right)
	{
		if (query_right < left || query_left > right)
			return (0);
		else if (left >= query_left && right <= query_right)
			return (tree[node]);
		else
		{
			int mid = (left + right) / 2;
			long sum = 0;
			sum += subsum(left, mid, node * 2, query_left, query_right);
			sum += subsum(mid + 1, right, node * 2 + 1, query_left, query_right);
			return (sum);
		}
	}

	public void update(int left, int right, int node, int target, long diff)
	{
		if (target < left || target > right)
			return;

		tree[node] += diff;

		//when current node is internal node
		if (left != right)
		{
			int mid = (left + right) / 2;
			update(left, mid, node * 2, target, diff);
			update(mid + 1, right, node * 2 + 1, target, diff);
		}
	}
}
