import java.util.*;
import java.io.*;

public class Main
{
	static int N;
	static int maxKthAncestor = (int)log2(100000);
	static int[][] parent;
	static int[] depth;

	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		ArrayList<ArrayList<Integer>> graph = new ArrayList<ArrayList<Integer>>(N + 1);
		for (int i = 0; i <= N; i++)
			graph.add(new ArrayList<Integer>());

		int node1, node2;
		for (int i = 0; i < N - 1; i++)
		{
			st = new StringTokenizer(br.readLine());
			node1 = Integer.parseInt(st.nextToken());
			node2 = Integer.parseInt(st.nextToken());

			graph.get(node1).add(node2);
			graph.get(node2).add(node1);
		}


		int M;
		st = new StringTokenizer(br.readLine());
		M = Integer.parseInt(st.nextToken());

		br.close();
		bw.flush();
		bw.close();
	}

	public static void getParentArr(ArrayList<ArrayList<Integer>> graph, int N)
	{
		parent = new int[N + 1][maxKthAncestor];
		
		//parent[i][K + 1] = parent[parent[i][K]][K]

	}

	public static double log2(double x)
	{
		return (Math.log(x) / Math.log(2));
	}
}
