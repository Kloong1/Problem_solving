import java.io.*;
import java.util.*;

public class Main
{
	static int V, E;

	//graph를 표현하기 위한 2차원 ArrayList
	static ArrayList<ArrayList<Integer>> nodes;

	//각 node의 방문 순서를 의미하는 order. 그 order를 기록할 visited 배열
	static int order;
	static int[] visited;

	//정답을 기록할 PriorityQueue. 사전순으로 출력해야하므로 PriorityQueue 사용.
	static PriorityQueue<Bridge> bridges;

	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		//V와 E 입력
		st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		nodes = new ArrayList<ArrayList<Integer>>(V + 1);
		for (int i = 0; i <= V; i++)
			nodes.add(new ArrayList<Integer>());

		//그래프 입력. 양방향 간선임에 주의.
		int node1, node2;
		for (int i = 0; i < E; i++)
		{
			st = new StringTokenizer(br.readLine());
			node1 = Integer.parseInt(st.nextToken());
			node2 = Integer.parseInt(st.nextToken());

			nodes.get(node1).add(node2);
			nodes.get(node2).add(node1);
		}

		//order는 1부터 시작함.
		order = 1;
		visited = new int[V + 1];
		bridges = new PriorityQueue<Bridge>();

		//입력 조건에서 connected graph라는 조건이 있으므로 한 번만 호출하면 됨.
		//시작 node는 부모가 없으므로 의미 없는 node id인 0 을 넘겨줌.
		check_bridge(1, 0);

		StringBuilder sb = new StringBuilder();
		Bridge b = null;

		//정답을 사전순으로 출력.
		sb.append(bridges.size() + "\n");
		while (!bridges.isEmpty())
		{
			b = bridges.poll();
			sb.append(b.node1 + " " + b.node2 + "\n");
		}

		bw.write(sb.toString());

		bw.flush();
		br.close();
		bw.close();
	}

	static int check_bridge(int id, int parent)
	{
		int low, subtree_low;

		//order를 기록함. return할 low의 초깃값은 order임.
		visited[id] = order;
		low = order;
		order++;

		for (int adj : nodes.get(id))
		{
			//부모 노드로 다시 올라가지 않음.
			if (adj == parent)
				continue;

			//방문한 적이 없는 인접 node인 경우 재귀 호출.
			if (visited[adj] == 0)
			{
				//재귀 호출 후 반환받은 값으로 low 갱신.
				subtree_low = check_bridge(adj, id);
				low = Math.min(low, subtree_low);

				//다시 돌아올 방법이 없는 경우에 bridge
				if (visited[id] < subtree_low)
					bridges.add(new Bridge(id, adj));
			}
			//방문한 적이 있는 node인 경우, 돌아갈 방법이 있다는 것이므로 bridge인 것을 고려할 필요가 없음.
			//low만 갱신해준다.
			else
				low = Math.min(low, visited[adj]);
		}

		return low;
	}
}

class Bridge implements Comparable<Bridge>
{
	int node1;
	int node2;

	public Bridge(int node1, int node2)
	{
		//출력 조건에 있는 A < B 를 맞춰주기 위함
		if (node1 > node2)
		{
			this.node1 = node2;
			this.node2 = node1;
		}
		else
		{
			this.node1 = node1;
			this.node2 = node2;
		}
	}

	//출력 조건에 있는 사전순 출력을 맞추기 위해 구현.
	public int compareTo(Bridge other)
	{
		if (this.node1 > other.node1)
			return 1;
		else if (this.node1 == other.node1)
			return this.node2 - other.node2;
		else
			return -1;
	}
}
