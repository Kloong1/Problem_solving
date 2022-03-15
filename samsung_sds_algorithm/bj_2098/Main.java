import java.util.Scanner;

public class Main
{
	static int[][] map;
	static int[][] dp;

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N;
		N = sc.nextInt();

		int[][] map = new int[N][N];
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				map[i][j] = sc.nextInt();

		int cost = Integer.MAX_VALUE;
		for (int i = 0; i < 16; i++)
			cost = Math.min(cost, travel(i, 0));
	}

	static int travel(int start, int visited)
	{
	}
}
