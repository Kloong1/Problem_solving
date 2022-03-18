import java.util.*;
import java.io.*;

class Main
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int V, E;
		st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		PriorityQueue<Edge> edges = new PriorityQueue<Edge>(E);

		int node1, node2;
		long cost;
		for (int i = 1; i <= E; i++)
		{
			st = new StringTokenizer(br.readLine());
			node1 = Integer.parseInt(st.nextToken());
			node2 = Integer.parseInt(st.nextToken());
			cost = Long.parseLong(st.nextToken());
			edges.add(new Edge(node1, node2, cost));
		}

		DisjointSet ds = new DisjointSet(V);

		cost = 0;
		int cnt = 0;
		Edge e;
		while (!edges.isEmpty() && cnt < V - 1)
		{
			e = edges.poll();
			
			if (ds.find(e.node1) != ds.find(e.node2))
			{
				ds.union(e.node1, e.node2);
				cost += e.cost;
				cnt++;
			}
		}

		System.out.println(cost);

		br.close();
	}
}

class Edge implements Comparable<Edge>
{
	int node1;
	int node2;
	long cost;

	public Edge(int node1, int node2, long cost)
	{
		this.node1 = node1;
		this.node2 = node2;
		this.cost = cost;
	}

	public int compareTo(Edge other)
	{
		if (this.cost > other.cost)
			return 1;
		else if (this.cost < other.cost)
			return -1;
		else
			return 0;
	}
}

class DisjointSet
{
	int[] parent;

	public DisjointSet(int N)
	{
		parent = new int[N + 1];
		for (int i = 1; i <= N; i++)
			parent[i] = i;
	}

	public void union(int e1, int e2)
	{
		int parent1 = find(e1);
		int parent2 = find(e2);
		parent[parent1] = parent2;
	}

	public int find(int e)
	{
		if (parent[e] == e)
			return e;
		parent[e] = find(parent[e]);
		return parent[e];
	}
}
