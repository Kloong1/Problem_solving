import java.util.Scanner;
import java.util.LinkedList;

public class Main
{
	static int W, H, G, E;
	static int[][] map;
	static long[][] cost;
	static LinkedList<Edge> edges;
	static LinkedList<String> result;

	static final int GRASS = 0;
	static final int GRAVE = 1;
	static final int HOLE = 2;
	static final int EXIT = 3;

	static final long INF = Integer.MAX_VALUE;

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		result = new LinkedList<String>();

		while (true)
		{
			W = sc.nextInt();
			H = sc.nextInt();

			if (W == 0 && H == 0)
				break;

			//초깃값 0 은 GRASS를 의미
			map = new int[H][W];

			//출구 표시
			map[H - 1][W - 1] = EXIT;

			G = sc.nextInt();

			//묘비 입력 받음
			int row, col;
			for (int i = 0; i < G; i++)
			{
				col = sc.nextInt();
				row = sc.nextInt();
				map[row][col] = GRAVE;
			}

			E = sc.nextInt();

			//벨만 포드에 쓰일 edge list
			edges = new LinkedList<Edge>();

			//귀신 구멍을 입력받는 동시에 귀신 구멍 간선 추가
			int row1, col1, row2, col2, t;
			for (int i = 0; i < E; i++)
			{
				col1 = sc.nextInt();
				row1 = sc.nextInt();
				col2 = sc.nextInt();
				row2 = sc.nextInt();
				t = sc.nextInt();

				map[row1][col1] = HOLE;

				edges.add(new Edge(new Point(row1, col1), new Point(row2, col2), t));
			}

			//edge list에 edge를 마저 추가
			add_edges();

			//벨만포드 적용
			get_cost_bf();
		}

		for (String s : result)
		    System.out.println(s);
	}

	static void get_cost_bf()
	{
		//벨만 포드 알고리즘에서 cost를 기록할 배열
		cost = new long[H][W];
		for (int i = 0; i < H; i ++)
			for (int j = 0; j < W; j++)
				cost[i][j] = INF; //전부 INF로 초기화

		//출발점의 cost는 0으로 초기화
		cost[0][0] = 0;

		//묘비를 제외한 칸 개수. 즉 그래프의 전체 노드 개수를 의미함.
		int V = W * H - G;

		//V-1번 edge list를 선회
		for (int i = 0; i < V - 1; i++)
		{
			for (Edge e : edges)
			{
				Point start = e.start;
				Point end = e.end;

				//start가 귀신 구멍인데 cost가 음수인 경우
				//start의 cost가 INF이면 아직 start에 도달한적이 한번도 없는데
				//end의 cost가 INF인 경우 end의 cost가 업데이트 되는 경우가 생김
				//그 것을 막기 위해 조건을 걸어둠
				if (cost[start.row][start.col] == INF)
					continue;

				//cost 업데이트
				cost[end.row][end.col] =
					Math.min(cost[end.row][end.col], cost[start.row][start.col] + e.cost);
			}
		}

		//한번 더 cost를 업데이트함
		//바뀌는게 있다는 것은 음수 싸이클이 존재한다는 뜻
		for (Edge e : edges)
		{
			Point start = e.start;
			Point end = e.end;

			if (cost[start.row][start.col] == INF)
				continue;

			if (cost[end.row][end.col] > cost[start.row][start.col] + e.cost)
			{
				result.add("Never");
				return;
			}
		}

		//출구에 도달하지 못한 경우
		if (cost[H - 1][W - 1] == INF)
			result.add("Impossible");
		else
			result.add(Long.toString(cost[H - 1][W - 1]));
	}

	static void add_edges()
	{
		int[] mr = {-1, 1, 0 ,0};
		int[] mc = {0, 0, -1, 1};

		for (int i = 0; i < H; i ++)
		{
			for (int j = 0; j < W; j++)
			{
				//묘비 or 귀신구멍 or 출구에 대해서는 인접한 칸에 대한 간선 추가하지 않음
				if (map[i][j] != GRASS)
					continue;

				//모든 잔디에서 인접한 칸으로 이동 가능하면 간선 추가
				for (int k = 0; k < 4; k++)
				{
					int tr = i + mr[k];
					int tc = j + mc[k];

					if (can_move_to(tr, tc))
						edges.add(new Edge(new Point(i, j), new Point(tr, tc), 1));
				}
			}
		}
	}

	static boolean can_move_to(int row, int col)
	{
		return (row >= 0 && row < H && col >= 0 && col < W && map[row][col] != GRAVE);
	}

}

class Point
{
	int row;
	int col;

	public Point(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
}

class Edge
{
	Point start;
	Point end;
	int cost;

	public Edge(Point start, Point end, int cost)
	{
		this.start = start;
		this.end = end;
		this.cost = cost;
	}

	public void print()
	{
		System.out.printf("(%d, %d) -%d-> (%d, %d)\n", start.row, start.col, cost, end.row, end.col);
	}
}
