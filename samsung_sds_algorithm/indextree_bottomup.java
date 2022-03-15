/**
 * @author : kloong
 * @created : 2021-07-22
**/

public class IndexTree
{
	int num_of_leaves;
	int[] tree;

	public void update(int target, int value)
	{
		int node = num_of_leaves + target - 1;

		tree[node] = value;

		node /= 2;
		while (node >= 1)
		{
			tree[node] = tree[node * 2] + tree[node * 2 + 1];
			node /= 2;
		}
	}

	public long subsum(int query_left, int query_right)
	{
		int left, right;
		left = num_of_leaves + query_left - 1;
		right = num_of_leaves + query_right - 1;

		long sum = 0;
		while (left <= right)
		{
			if (left % 2 == 1)
				sum += tree[left++];

			if (right % 2 == 0)
				sum += tree[right--];

			left /= 2;
			right /= 2;
		}
	}
}
