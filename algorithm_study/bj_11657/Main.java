import java.util.Scanner;

public class Main
{
	static final long INF = Integer.MAX_VALUE;

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N, M;
		N = sc.nextInt();
		M = sc.nextInt();

		//dist는 long이어야 한다. cost가 전부 음수인 경우를 생각해보자.
		//V * E * cost 하면 INT_MIN 보다 작다.
		long[] dist = new long[N + 1];
		Edge[] edges = new Edge[M];

		for (int i = 1; i <= N; i++)
			dist[i] = INF;

		for (int i = 0; i < M; i++)
			edges[i] = new Edge(sc.nextInt(), sc.nextInt(), sc.nextInt());

		//1번 도시에서 출발
		dist[1] = 0;

		for (int i = 1; i < N; i++)
		{
			for (int j = 0; j < M; j++)
			{
				Edge e = edges[j];

				if (dist[e.start] == INF)
					continue;

				long next_dist = dist[e.start] + e.cost;
				if (next_dist < dist[e.end])
					dist[e.end] = next_dist;
			}
		}

		boolean has_minus_cycle = false;

		for (int j = 0; j < M; j++)
		{
			Edge e = edges[j];

			if (dist[e.start] == INF)
				continue;

			if (dist[e.start] + e.cost < dist[e.end])
			{
				has_minus_cycle = true;
				break;
			}
		}

		if (has_minus_cycle)
			System.out.println(-1);
		else
		{
			for (int i = 2; i <= N; i++)
			{
				if (dist[i] == INF)
					System.out.println(-1);
				else
					System.out.println(dist[i]);
			}
		}
	}
}

class Edge
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
}
