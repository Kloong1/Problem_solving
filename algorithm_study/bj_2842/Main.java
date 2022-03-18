import java.util.*;
import java.io.*;

public class Main
{
	static int N;

	static int[][] map;
	static int[][] height;
	static boolean[][] visited;

	static Queue<Point> q;

	static int houseCnt;

	static int min, max;

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

		ArrayList<Integer> allHeightArr = new ArrayList<Integer>(N * N);

		min = Integer.MAX_VALUE;
		max = 0;

		for (int i = 0; i < N; i++)
		{
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++)
			{
				height[i][j] = Integer.parseInt(st.nextToken());
				allHeightArr.add(height[i][j]);

				if (map[i][j] != 0)
				{
					min = Math.min(min, height[i][j]);
					max = Math.max(max, height[i][j]);
				}
			}
		}

		Collections.sort(allHeightArr);

		ArrayList<Integer> heightArr = new ArrayList<>(N * N);

		int preHeight = 0;
		for (Integer h : allHeightArr)
		{
			if (preHeight == h)
				continue;

			heightArr.add(h);
			preHeight = h;
		}

		int left, right;
		left = 0;
		for (right = 0; right < heightArr.size(); right++)		
			if (heightArr.get(right) == max)
				break;

		int diff = Integer.MAX_VALUE;

		while (right < heightArr.size())
		{
			q.add(startPoint);
			while (canDeliver(heightArr.get(left), heightArr.get(right)))
			{
				diff = Math.min(diff, heightArr.get(right) - heightArr.get(left));
				left++;
				q.add(startPoint);
			}

			if (left > min)
				break;

			right++;
		}

		System.out.println(diff);
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

	public static boolean canMoveNoCost(int row, int col, int leftHeight, int rightHeight)
	{
		return (height[row][col] >= leftHeight && height[row][col] <= rightHeight);
	}

	public static boolean canDeliver(int leftHeight, int rightHeight) 
	{
		int reachCnt = 0;

		Point p;
		while (!q.isEmpty())
		{
			p = q.poll();

			if (visited[p.row][p.col])
				continue;

			visited[p.row][p.col] = true;

			if (map[p.row][p.col] > 0)
				reachCnt++;

			if (reachCnt == houseCnt)
			{
				q.clear();
				clearVisited();
			    return true;
			}

			for (int i = 0; i < 8; i++)
			{
				int nr = p.row + mr[i];
				int nc = p.col + mc[i];

				if (isNotOutOfMap(nr, nc) && canMoveNoCost(nr, nc, leftHeight, rightHeight) && !visited[nr][nc])
						q.add(new Point(nr, nc));
			}
		}
		clearVisited();

		return false;
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
