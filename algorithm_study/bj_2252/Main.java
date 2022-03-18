import java.util.Scanner;
import java.util.LinkedList;
import java.util.Queue;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N, M;
		N = sc.nextInt();
		M = sc.nextInt();

		Node[] graph = new Node[N + 1];
		for (int i = 1; i <= N; i++)
			graph[i] = new Node(i);

		int small, tall;
		for (int i = 0; i < M; i++)
		{
			small = sc.nextInt();
			tall = sc.nextInt();

			graph[small].adjacent.add(tall);
			graph[tall].indeg += 1;
		}

		Queue<Node> queue = new LinkedList<Node>();

		for (int i = 1; i <= N; i++)
			if (graph[i].indeg == 0)
				queue.offer(graph[i]);

		LinkedList<Integer> result = new LinkedList<Integer>();

		while (!(queue.isEmpty()))
		{
			Node shortest = queue.poll();

			for (Integer adj_id : shortest.adjacent)
			{
				graph[adj_id].indeg -= 1;
				if (graph[adj_id].indeg == 0)
					queue.offer(graph[adj_id]);
			}

			result.add(shortest.id);
		}

		for (Integer r : result)
			System.out.printf("%d ", r);
		System.out.println();
	}
}

class Node
{
	int id;
	int indeg;
	LinkedList<Integer> adjacent;

	public Node(int id)
	{
		this.id = id;
		indeg = 0;
		adjacent = new LinkedList<Integer>();
	}
}
