import java.util.*;
import java.io.*;

public class Main
{
	static int N;

	static int[][] map;
	static int[][] height;
	static boolean[][] visited;

	static Queue<Point> q;

	static int min, max;
	static int nextMin, nextMax;

	static int houseCnt;

	static int[] mr = {-1, 1, 0, 0, -1, -1, 1, 1};
	static int[] mc = {0, 0, -1, 1, -1, 1, -1, 1};

	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());

		map = new int[N][N];
		height = new int[N][N];
		visited = new boolean[N][N];

		q = new LinkedList<Point>();

		houseCnt = 0;

		String row;
		Point startPoint = null;
		for (int i = 0; i < N; i++)
		{
			row = br.readLine();
			for (int j = 0; j < N; j++)
			{
				if (row.charAt(j) == 'P')
				{
					map[i][j] = -1;
					startPoint = new Point(i, j);
				}
				else if(row.charAt(j) == 'K')
				{
					map[i][j] = 1;
					houseCnt++;
				}
			}
		}

		min = Integer.MAX_VALUE;
		max = 0;

		for (int i = 0; i < N; i++)
		{
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++)
			{
				height[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] != 0)
				{
					min = Math.min(min, height[i][j]);
					max = Math.max(max, height[i][j]);
				}
			}
		}

		System.out.println(findWay(min, max));
	}

	//중복된 (min, max) 범위를 계속 체크함 ㅜㅜ
	public static int findWay(int min, int max)
	{
		int diff = Integer.MAX_VALUE;

		int nextMin, nextMax;
		nextMin = 0;
		nextMax = Integer.MAX_VALUE;

		int[] next = {nextMin, nextMax};

		q.add(startPoint);
		clearVisited();

		if (bfs(min, max, next))
		{
			q.clear();
			return (max - min);
		}

		nextMin = next[0];
		nextMax = next[1];

		if (nextMin > 0)
			diff = findWay(nextMin, max);

		if (nextMax < Integer.MAX_VALUE && (nextMax - min) < diff)
			diff = findWay(min, nextMax);

		return diff;
	}

	public static void clearVisited()
	{
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				visited[i][j] = false;
	}

	public static boolean isNotOutOfMap(int row, int col)
	{
		return (row >= 0 && row < N && col >= 0 && col < N);
	}

	public static boolean canMoveNoCost(int row, int col)
	{
		return (!visited[row][col] && height[row][col] >= min && height[row][col] <= max);
	}

	public static boolean bfs(int min, int max , int[] next)
	{
		int reachCnt = 0;
		int nextMin, nextMax;

		nextMin = next[0];
		nextMax = next[1];

		Point p;
		while (!q.isEmpty())
		{
			p = q.poll();

			if (visited[p.row][p.col])
				continue;

			visited[p.row][p.col] = true;

			if (map[p.row][p.col] > 0)
				reachCnt++;

			/* if (reachCnt == houseCnt)
			 * {
			 *     next[0] = nextMin;
			 *     next[1] = nextMax;
			 *     return true;
			 * } */

			for (int i = 0; i < 8; i++)
			{
				int nr = p.row + mr[i];
				int nc = p.col + mc[i];

				if (isNotOutOfMap(nr, nc))
				{
					if (nextMin < height[nr][nc] && height[nr][nc] < min)
						nextMin = height[nr][nc];
					if (max < height[nr][nc] && height[nr][nc] < nextMax)
						nextMax = height[nr][nc];

					if (canMoveNoCost(nr, nc))
						q.add(new Point(nr, nc));
				}
			}
		}

		next[0] = nextMin;
		next[1] = nextMax;

		return (reachCnt == houseCnt);
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
