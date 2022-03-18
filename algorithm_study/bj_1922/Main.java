import java.util.Scanner;
import java.util.PriorityQueue;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N, M;
		N = sc.nextInt();
		M = sc.nextInt();

		PriorityQueue<Edge> pq_edge = new PriorityQueue<Edge>();

		for (int i = 0; i < M; i++)
			pq_edge.add(new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt()));

		DisjointSet ds = new DisjointSet(N);

		int edge_cnt = 0;
		long cost = 0;
		Edge edge = null;
		while (!(pq_edge.isEmpty()))
		{
			if (edge_cnt == N - 1)
				break;

			edge = pq_edge.poll();

			if (ds.find_parent(edge.start) != ds.find_parent(edge.end))
			{
				ds.union(edge.start, edge.end);
				cost += edge.cost;
				edge_cnt++;
			}
		}

		System.out.println(cost);
	}
}

class Edge implements Comparable<Edge>
{
	int start;
	int end;
	int cost;

	public Edge(int start, int end, int cost)
	{
		this.start = start;
		this.end = end;
		this.cost = cost;
	}

	public int compareTo(Edge other)
	{
		return (this.cost - other.cost);
	}
}

class DisjointSet
{
	int[] parr;

	public DisjointSet(int n)
	{
		parr = new int[n + 1];
		for (int i = 0; i < parr.length; i++)
			parr[i] = i;
	}

	public void union(int e1, int e2)
	{
		int pe1 = find_parent(e1);
		int pe2 = find_parent(e2);
		parr[pe2] = pe1;
	}

	public int find_parent(int e)
	{
		if (parr[e] == e)
			return (e);
		parr[e] = find_parent(parr[e]);
		return parr[e];
	}
}
