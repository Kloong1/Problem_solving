import java.util.*;
import java.io.*;

class Main
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		int[] arrA = new int[N + 1];
		int maxId = 0;

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++)
		{
			arrA[i] = Integer.parseInt(st.nextToken());
			maxId = Math.max(maxId, arrA[i]);
		}

		int[] pairIdx = new int[maxId + 1];
		int machineId;

		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++)
		{
			machineId = Integer.parseInt(st.nextToken());
			pairIdx[machineId] = i;
		}

		IndexedTree idxTree = new IndexedTree(N);
		
		long cnt = 0;
		int queryLeft, queryRight;
		for (int i = 1; i <= N; i++)
		{
			queryLeft = pairIdx[arrA[i]] + 1;
			queryRight = idxTree.numOfLeaves;
			cnt += idxTree.getPrefixSum(1, idxTree.numOfLeaves, queryLeft, queryRight, 1);
			idxTree.update(1, idxTree.numOfLeaves, pairIdx[arrA[i]], 1, 1);
		}

		System.out.println(cnt);

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
