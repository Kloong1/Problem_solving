import java.util.Scanner;
import java.util.LinkedList;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N;
		N = sc.nextInt();

		IndexTree idxtree = new IndexTree();

		LinkedList<Integer> result = new LinkedList<Integer>();

		int rank, taste, cnt;
		for (int i = 0; i < N; i++)
		{
			if (sc.nextInt() == 1)
			{
				rank = sc.nextInt();
				result.add(idxtree.get_nth_candy(1, idxtree.num_of_leaves, 1, rank));
				idxtree.update_candy(1, idxtree.num_of_leaves, 1, result.getLast(), -1);
			}
			else
			{
				taste = sc.nextInt();
				cnt = sc.nextInt();
				idxtree.update_candy(1, idxtree.num_of_leaves, 1, taste, cnt);
			}
		}

		for (Integer r : result)
			System.out.println(r);
	}
}

class IndexTree
{
	int num_of_leaves;
	int[] tree;

	public IndexTree()
	{
		num_of_leaves = 1;
		while (num_of_leaves < 1000000)
			num_of_leaves *= 2;
		tree = new int[num_of_leaves * 2];
	}

	public int get_nth_candy(int left, int right, int node, int rank)
	{
		if (rank > tree[node])
			return (0);

		if (left == right)
			return (left);
		
		int taste, mid;
		mid = (left + right) / 2;

		taste = get_nth_candy(left, mid, node * 2, rank);
		if (taste == 0)
			taste = get_nth_candy(mid + 1, right, node * 2 + 1, rank - tree[node * 2]);
		
		return (taste);
	}

	public void update_candy(int left, int right, int node, int target, int diff)
	{
		if (target < left || target > right)
			return;

		tree[node] += diff;

		if (left != right)
		{
			int mid = (left + right) / 2;
			update_candy(left, mid, node * 2, target, diff);
			update_candy(mid + 1, right, node * 2 + 1, target, diff);
		}
	}
}
