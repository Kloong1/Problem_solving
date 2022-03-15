import java.util.*;
import java.io.*;

public class Main
{
	static BufferedReader br;

	static int[][] board;
	static boolean[][] visited;
	static int[][] maxMoves;
	static int nRow, nCol;

	static int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

	static final int HOLE = -1;

	static boolean infMoves = false;

	public static void main(String[] args) throws Exception
	{
		br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		nRow = Integer.parseInt(st.nextToken());
		nCol = Integer.parseInt(st.nextToken());

		board = readBoard();
		visited = new boolean[nRow][nCol];
		maxMoves = new int[nRow][nCol];

		System.out.println(getMaxMoves(0, 0));
	}

	public static int getMaxMoves(int row, int col)
	{
		if (visited[row][col])
		{
			infMoves = true;
			return -1;
		}

		if (maxMoves[row][col] > 0)
			return maxMoves[row][col];

		visited[row][col] = true;
		maxMoves[row][col] = 1;

		int nextRow, nextCol;
		int dist = board[row][col];

		for (int[] dir : directions)
		{
			nextRow = row + dir[0] * dist;
			nextCol = col + dir[1] * dist;

			if (canMoveTo(nextRow, nextCol))
			{
				maxMoves[row][col] = Math.max(maxMoves[row][col],
										getMaxMoves(nextRow, nextCol) + 1);
			}

			if (infMoves)
				return -1;
		}

		visited[row][col] = false;

		return maxMoves[row][col];
	}

	public static boolean canMoveTo(int row, int col)
	{
		return row >= 0 && row < nRow && col >= 0 && col < nCol && board[row][col] != HOLE;
	}

	public static int[][] readBoard() throws Exception
	{
		int[][] board = new int[nRow][nCol];

		for (int i = 0; i < nRow; i++)
			lineToBoard(board[i], br.readLine());

		return board;
	}

	public static void lineToBoard(int[] boardRow, String line)
	{
		for (int i = 0; i < nCol; i++)
		{
			if (line.charAt(i) == 'H')
				boardRow[i] = HOLE;
			else
				boardRow[i] = line.charAt(i) - '0';
		}
	}
}
