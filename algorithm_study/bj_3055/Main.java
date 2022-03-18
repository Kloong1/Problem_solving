import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int R, C;
		R = sc.nextInt();
		C = sc.nextInt();

		char[][] map = new char[R][C];
		int[][] dp = new int[R][C];
		Queue<Point> queue = new LinkedList<Point>();

		Point hedgehog_point = null;
		for (int i = 0; i < R; i++)
		{
			String line = sc.next();
			for (int j = 0; j < C; j++)
			{
				map[i][j] = line.charAt(j);

				if (map[i][j] == 'S')
					hedgehog_point = new Point(i, j, 'S');
				else if (map[i][j] == '*')
					queue.offer(new Point(i, j, '*'));
			}
		}
		queue.offer(hedgehog_point);

		Point p;
		int escape_time = -1;
		while (!queue.isEmpty())
		{
			//1. 큐에서 꺼낸다
			p = queue.poll();
			/* System.out.printf("%d %c\n", queue.size(), p.type); */
			//2. 목적지인가?
			if (p.type == 'D')
			{
				escape_time = dp[p.row][p.col];
				break;
			}
			else if (p.type == '*')
				move_water(p, map, R, C, queue);
			else
				move_hedgehog(p, map, dp, R, C, queue);
		}

		System.out.println(escape_time >= 0 ? escape_time : "KAKTUS");
	}

	static boolean is_valid_point(int row, int col, int R, int C)
	{
		return (row >= 0 && row < R && col >= 0 && col < C);
	}

	static void move_water(Point water, char[][] map, int R, int C, Queue<Point> queue)
	{
		int[] move_row = {-1, 1, 0, 0};
		int[] move_col = {0, 0, -1, 1};
		int tr, tc; //target_row, target_col

		for (int i = 0; i < 4; i++)
		{
			//3. 연결된 곳 순회
			tr = water.row + move_row[i];
			tc = water.col + move_col[i];

			if (is_valid_point(tr, tc, R, C))
			{
				//4. 갈 수 있는가?
				if (map[tr][tc] == '.' || map[tr][tc] == 'S')
				{
					//5. 체크인
					map[tr][tc] = '*';
					//6. 큐에 넣는다
					queue.offer(new Point(tr, tc, '*'));
				}
			}
		}
	}

	static void move_hedgehog(Point hedgehog, char[][] map, int[][] dp, int R, int C, Queue<Point> queue)
	{
		int[] move_row = {-1, 1, 0, 0};
		int[] move_col = {0, 0, -1, 1};
		int tr, tc; //target_row, target_col

		for (int i = 0; i < 4; i++)
		{
			tr = hedgehog.row + move_row[i];
			tc = hedgehog.col + move_col[i];

			if (is_valid_point(tr, tc, R, C))
			{
				if ((map[tr][tc] == '.' || map[tr][tc] == 'D') && dp[tr][tc] == 0)
				{
					dp[tr][tc] = dp[hedgehog.row][hedgehog.col] + 1;
					queue.offer(new Point(tr, tc, map[tr][tc]));
				}
			}
		}
	}

}

class Point
{
	int row;
	int col;
	char type;

	public Point(int row, int col, char type)
	{
		this.row = row;
		this.col = col;
		this.type = type;
	}
}
