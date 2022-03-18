import java.util.*;
import java.io.*;

class Main
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N, P;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		P = Integer.parseInt(st.nextToken());

		int[] countryCost = new int[N + 1];
		int minCountryCost = Integer.MAX_VALUE;
		for (int i = 1; i <= N; i++)
		{
			countryCost[i] = Integer.parseInt((new StringTokenizer(br.readLine())).nextToken());
			minCountryCost = Math.min(minCountryCost, countryCost[i]);
		}

		PriorityQueue<Edge> edges = new PriorityQueue<Edge>(P);

		int node1, node2, cost;
		for (int i = 0; i < P; i++)
		{
			st = new StringTokenizer(br.readLine());
			node1 = Integer.parseInt(st.nextToken());
			node2 = Integer.parseInt(st.nextToken());
			cost = Integer.parseInt(st.nextToken());

			cost = cost * 2 + countryCost[node1] + countryCost[node2];
			edges.add(new Edge(node1, node2, cost));
		}

		DisjointSet ds = new DisjointSet(N);

		long tripCost = 0;
		int cnt = 0;
		Edge e;
		while (!edges.isEmpty() && cnt < N - 1)
		{
			e = edges.poll();

			if (ds.find(e.node1) != ds.find(e.node2))
			{
				ds.union(e.node1, e.node2);
				tripCost += e.cost;
				cnt++;
			}
		}

		System.out.println(tripCost + minCountryCost);
	}
}

class Edge implements Comparable<Edge>
{
	int node1;
	int node2;
	int cost;

	public Edge(int node1, int node2, int cost)
	{
		this.node1 = node1;
		this.node2 = node2;
		this.cost = cost;
	}

	public int compareTo(Edge other)
	{
		if (this.cost > other.cost)
			return (1);
		else if (this.cost == other.cost)
			return (0);
		else
			return (-1);
	}
}

class DisjointSet
{
	int parent[];

	public DisjointSet(int N)
	{
		parent = new int[N + 1];
		for (int i = 1; i <= N; i++)
			parent[i] = i;
	}

	public int find(int e)
	{
		if (parent[e] == e)
			return (e);
		parent[e] = find(parent[e]);
		return (parent[e]);
	}

	public void union(int e1, int e2)
	{
		int par1 = find(e1);
		int par2 = find(e2);
		parent[par1] = par2;
	}
}
