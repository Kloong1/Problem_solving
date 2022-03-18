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

		ArrayList<ArrayList<Edge>> graph = new ArrayList<>(V + 1);
		for (int i = 0; i <= V; i++)
			graph.add(new ArrayList<Edge>());

		int node1, node2;
		long cost;
		for (int i = 1; i <= E; i++)
		{
			st = new StringTokenizer(br.readLine());
			node1 = Integer.parseInt(st.nextToken());
			node2 = Integer.parseInt(st.nextToken());
			cost = Long.parseLong(st.nextToken());

			//node를 기준으로 동작하는 알고리즘이기 때문에
			//각 node에 인접한 node와 그 cost를 추가해주는 방식
			graph.get(node1).add(new Edge(node2, cost));
			graph.get(node2).add(new Edge(node1, cost));
		}

		//MST에 추가된 node는 true
		boolean[] selected = new boolean[V + 1];

		PriorityQueue<Edge> edges = new PriorityQueue<Edge>();
		//1번 node에서 시작하기 위해
		//존재하지 않는 node에서 1번 node로 이어진 cost 0의edge가 있다고 가정하고
		//PriorityQueue에 추가함
		edges.add(new Edge(1, 0));
		
		cost = 0;
		int cnt = 0;
		Edge e;
		//cnt == V -1 이 아닌 cnt == V 일 때 끝나는 이유는
		//가상의 edge인 Edge(1,0)을 추가했기 때문!
		while (!edges.isEmpty() && cnt < V)
		{
			e = edges.poll();

			if (selected[e.node])
				continue;

			cost += e.cost;
			selected[e.node] = true;
			cnt++;

			//인접한 node 중 MST에 추가되지 않은 node를 pq에 넣는다.
			for (Edge adj : graph.get(e.node))
			{
				if (selected[adj.node] == false)
					edges.add(adj);
			}
		}

		System.out.println(cost);

		br.close();
	}
}

class Edge implements Comparable<Edge>
{
	int node;
	long cost;

	public Edge(int node, long cost)
	{
		this.node = node;
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
