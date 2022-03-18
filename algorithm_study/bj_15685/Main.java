import java.util.*;
import java.io.*;

class Main
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int[][] grid = new int[101][101];

		int N;
		N = Integer.parseInt(br.readLine());

		DragonCurve[] dragonCurves = new DragonCurve[N];
		int x, y, dir, generation;

		for (int i = 0; i < N; i++)
		{
			st = new StringTokenizer(br.readLine());

			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			dir = Integer.parseInt(st.nextToken());
			generation = Integer.parseInt(st.nextToken());

			dragonCurves[i] = new DragonCurve(x, y, dir, generation);
		}

		for (DragonCurve dragonCurve : dragonCurves)
			drawDragonCurve(dragonCurve, grid);

		System.out.println(countSquares(grid));

		br.close();
	}

	static void drawDragonCurve(DragonCurve dc, int[][] grid)
	{
		for (Point p : dc.points)
			grid[p.x][p.y] = 1;
	}

	static int countSquares(int[][] grid)
	{
		int count = 0;

		for (int i = 0; i < 100; i++)
		{
			for (int j = 0; j < 100; j++)
			{
				if (checkSquare(grid, i, j))
					count++;
			}
		}

		return count;
	}

	static boolean checkSquare(int[][] grid, int x, int y)
	{
		return grid[x][y] == 1 && grid[x + 1][y] == 1 && grid[x][y + 1] == 1 && grid[x + 1][y + 1] == 1;
	}
}

class DragonCurve
{
	ArrayList<Point> points;
	private ArrayList<Integer> dirs;

	private final int R = 0;
	private final int U = 1;
	private final int L = 2;
	private final int D = 3;

	DragonCurve(int x, int y, int dir, int generation)
	{
		int numOfPoint = (1 << generation) + 1;

		points = new ArrayList<Point>(numOfPoint);

		points.add(new Point(x, y));
		points.add(getNextPoint(x, y, dir));

		dirs = new ArrayList<Integer>(numOfPoint - 1);
		dirs.add(dir);

		generateDragonCurve(generation - 1);
	}

	private void generateDragonCurve(int generation)
	{
		if (generation < 0)
			return;

		int numOfDirs = dirs.size();

		ArrayList<Integer> nextDirs = new ArrayList<>(numOfDirs);

		for (int i = numOfDirs - 1; i >= 0; i--)
			nextDirs.add(dirMapping(dirs.get(i)));

		Point lastPoint = null;
		for (int dir : nextDirs)
		{
			lastPoint = points.get(points.size() - 1);
			points.add(getNextPoint(lastPoint.x, lastPoint.y, dir));
			dirs.add(dir);
		}

		generateDragonCurve(generation - 1);
	}

	private Point getNextPoint(int x, int y, int dir)
	{
		switch (dir)
		{
			case R:
				return new Point(x + 1, y);
			case U:
				return new Point(x, y - 1);
			case L:
				return new Point(x - 1, y);
			case D:
				return new Point(x, y + 1);
			default:
				return null;
		}
	}

	private int dirMapping(int dir)
	{
		return (dir + 1) % 4;
	}
}

class Point
{
	int x;
	int y;

	Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}
