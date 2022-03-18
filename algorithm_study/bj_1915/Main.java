import java.util.*;
import java.io.*;

public class Main
{
	static BufferedReader br;
	static int nRow, nCol;

	public static void main(String[] args) throws Exception
	{
		br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine());
		nRow = Integer.parseInt(st.nextToken());
		nCol = Integer.parseInt(st.nextToken());

		System.out.println(getMaxSquareArea(readMap()));
	}

	public static int getMaxSquareArea(int[][] map)
	{
		int[][] maxLenMap = new int[nRow][nCol];

		initMaxLenMap(maxLenMap, map);

		for (int i = 1; i < nRow; i++)
		{
			for (int j = 1; j < nCol; j++)
				maxLenMap[i][j] = getMaxLenAt(i, j, maxLenMap, map);
		}

		int maxLen = 0;
		for (int i = 0; i < nRow; i++)
		{
			for (int j = 0; j < nCol; j++)
				maxLen = Math.max(maxLen, maxLenMap[i][j]);
		}

		return maxLen * maxLen;
	}

	public static int getMaxLenAt(int row, int col, int[][] maxLenMap, int[][] map)
	{
		if (map[row][col] == 0)
			return 0;

		return Math.min(maxLenMap[row - 1][col - 1],
				Math.min(maxLenMap[row - 1][col], maxLenMap[row][col - 1])) + 1;
	}

	public static void initMaxLenMap(int[][] maxLenMap, int[][] map)
	{
		for (int i = 0; i < nCol; i++)
			maxLenMap[0][i] = map[0][i];

		for (int i = 0; i < nRow; i++)
			maxLenMap[i][0] = map[i][0];
	}

	public static int[][] readMap() throws Exception
	{
		int[][] map = new int[nRow][nCol];

		for (int i = 0; i < nRow; i++)
			lineToRow(map[i], br.readLine());

		return map;
	}

	public static void lineToRow(int[] row, String line)
	{
		for (int i = 0; i < row.length; i++)
			row[i] = line.charAt(i) - '0';
	}
}
