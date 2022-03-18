import java.util.Scanner;
import java.util.ArrayList;

public class Main
{
	static int order = 1;
	static ArrayList<Integer>[] graph;
	static int[] visited;
	static boolean[] isArtic;

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int V, E;
		V = sc.nextInt();
		E = sc.nextInt();

		graph = new ArrayList[V + 1];
		for (int i = 1; i <= V; i++)
			graph[i] = new ArrayList<Integer>();

		int start, end;
		for (int i = 0; i < E; i++)
		{
			start = sc.nextInt();
			end = sc.nextInt();

			graph[start].add(end);
			graph[end].add(start);
		}

		visited = new int[V + 1];
		isArtic = new boolean[V + 1];

		for (int i = 1; i <= V; i++)
		{
			if (visited[i] == 0)
				check_artic_point(i, 0, true);
		}

		int cnt = 0;
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 1; i <= V; i++)
		{
			if (isArtic[i])
			{
				cnt++;
				result.add(i);
			}
		}

		System.out.println(cnt);
		for (Integer r : result)
			System.out.printf("%d ", r);
		System.out.println();
	}

	static int check_artic_point(int id, int parent, boolean isRoot)
	{
		int low, child_cnt;
		int sublow;

		visited[id] = order;
		order++;

		low = visited[id];
		child_cnt = 0;

		for (Integer adjid : graph[id])
		{
			if (adjid == parent)
				continue;

			if (visited[adjid] == 0)
			{
				sublow = check_artic_point(adjid, id, false);

				if (!isRoot && visited[id] <= sublow)
					isArtic[id] = true;

				low = Math.min(low, sublow);

				child_cnt++;
			}
			else
				low = Math.min(low, visited[adjid]);
		}

		if (isRoot && child_cnt >=2)
			isArtic[id] = true;

		return (low);
	}
}
