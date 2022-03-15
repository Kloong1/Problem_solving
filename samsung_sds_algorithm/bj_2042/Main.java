import java.util.*;
import java.io.*;

class Main
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		int N, M, K;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		IndexedTree idxTree = new IndexedTree(N);
		for (int i = idxTree.numOfLeaves; i < idxTree.numOfLeaves + N; i++)
			idxTree.tree[i] = Long.parseLong(br.readLine());

		idxTree.init();

		LinkedList<Long> result = new LinkedList<Long>();

		int command;
		long b, c;
		for (int i = 0; i < M + K; i++)
		{
			st = new StringTokenizer(br.readLine());
			command = Integer.parseInt(st.nextToken());

			if (command == 1)
			{
				int target = Integer.parseInt(st.nextToken());
				long diff = Long.parseLong(st.nextToken()) - idxTree.tree[idxTree.numOfLeaves + target - 1];
				idxTree.update(1, idxTree.numOfLeaves, target, diff, 1);
			}
			else
			{
				int left = Integer.parseInt(st.nextToken());
				int right = Integer.parseInt(st.nextToken());
				result.add(idxTree.getPrefixSum(1, idxTree.numOfLeaves, left, right, 1));
			}
		}

		for (Long r : result)
		    bw.write(r + "\n");

		bw.flush();
		bw.close();
		br.close();
	}
}

class IndexedTree
{
	long[] tree;
	int numOfLeaves;

	public IndexedTree(int N)
	{
		numOfLeaves = 1;
		while (numOfLeaves < N)
			numOfLeaves *= 2;

		//1 ~ (numOfLeaves * 2 - 1) 범위
		tree = new long[numOfLeaves * 2];
	}

	public void init()
	{
		for (int i = numOfLeaves - 1; i >= 1; i--)
			tree[i] = tree[i * 2] + tree[i * 2 + 1];
	}

	public long getPrefixSum(int left, int right, int queryLeft, int queryRight, int node)
	{
		if (right < queryLeft || left > queryRight)
			return 0;

		else if (left >= queryLeft && right <= queryRight)
			return tree[node];
		
		else
		{
			long sum = 0;
			int mid = (left + right) / 2;

			sum += getPrefixSum(left, mid, queryLeft, queryRight, node * 2);
			sum += getPrefixSum(mid + 1, right, queryLeft, queryRight, node * 2 + 1);

			return sum;
		}
	}

	public void update(int left, int right, int target, long diff, int node)
	{
		if (right < target || left > target)
			return;

		if (left <= target && right >= target)
			tree[node] += diff;

		if (left != right)
		{
			int mid = (left + right) / 2;

			update(left, mid, target, diff, node * 2);
			update(mid + 1, right, target, diff, node * 2 + 1);
		}
	}
}
