import java.util.*;
import java.io.*;

class Main
{
	static int N, H;
	static BufferedReader br;

	public static void main(String[] args) throws Exception
	{
		br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());

		int[] stalagmites = new int[H];
		int[] stalactites = new int[H];

		readHeights(stalagmites, stalactites);

		int[] stgmCumulSum = getCumulSumFromLast(stalagmites);
		int[] stctCumulSum = getCumulSumFromLast(stalactites);

		reverseArray(stgmCumulSum);

		int[] obstacleCumulSum = new int[H];

		for (int i = 0; i < obstacleCumulSum.length; i++)
			obstacleCumulSum[i] = stgmCumulSum[i] + stctCumulSum[i];

		Arrays.sort(obstacleCumulSum);

		int minObstacles = obstacleCumulSum[0];
		int minCnt = 1;

		for (int i = 1; i < obstacleCumulSum.length; i++)
		{
			if (minObstacles < obstacleCumulSum[i])
				break;
			
			minCnt++;
		}

		System.out.printf("%d %d\n", minObstacles, minCnt);
	}

	static void readHeights(int[] stalagmites, int[] stalactites) throws Exception
	{
		int last = N / 2;

		for (int i = 0; i < last; i++)
		{
			stalagmites[Integer.parseInt(br.readLine()) - 1]++;
			stalactites[Integer.parseInt(br.readLine()) - 1]++;
		}
	}

	static int[] getCumulSumFromLast(int[] arr)
	{
		int[] cumulSum = new int[arr.length];

		cumulSum[0] = arr[arr.length - 1];

		for (int i = 1; i < arr.length; i++) 
			cumulSum[i] = cumulSum[i - 1] + arr[arr.length - i - 1];

		return cumulSum;
	}

	static void reverseArray(int[] arr)
	{
		int mid = arr.length / 2;
		int temp;

		for (int i = 0; i < mid; i++)
		{
			temp = arr[i];
			arr[i] = arr[arr.length - i - 1];
			arr[arr.length - i - 1] = temp;
		}
	}
}
