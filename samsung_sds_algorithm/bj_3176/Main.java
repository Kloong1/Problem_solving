import java.util.*;
import java.io.*;

public class Main
{
	static int N;
	static int minCost, maxCost;

	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int N;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		ArrayList<ArrayList<Node>> graph = new ArrayList<ArrayList<Node>>(N + 1);

		for (int i = 0; i <= N; i++)
			graph.add(new ArrayList<Node>());

		int node1, node2, cost;
		for (int i = 0; i < N - 1; i++)
		{
			st = new StringTokenizer(br.readLine());
			node1 = Integer.parseInt(st.nextToken());
			node2 = Integer.parseInt(st.nextToken());
			cost = Integer.parseInt(st.nextToken());
			
			graph.get(node1).add(new Node(node2, cost));
			graph.get(node2).add(new Node(node1, cost));
		}

		int K;
		st = new StringTokenizer(br.readLine());
		K = Integer.parseInt(st.nextToken());

		for (int i = 0; i < K; i++)
		{
			st = new StringTokenizer(br.readLine());
			node1 = Integer.parseInt(st.nextToken());
			node2 = Integer.parseInt(st.nextToken());

			sb.append(node1 + " " + node2 + "\n");
		}

		System.out.print(sb.toString());

		br.close();
	}
}

class Node
{
	int id;
	int cost;

	public Node(int id, int cost)
	{
		this.id = id;
		this.cost = cost;
	}
}
