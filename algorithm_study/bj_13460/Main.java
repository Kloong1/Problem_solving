import java.io.*;
import java.util.*;

class Main
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int rows, cols;

		st = new StringTokenizer(br.readLine());
		rows = Integer.parseInt(st.nextToken());
		cols = Integer.parseInt(st.nextToken());

		BoardStat board = new BoardStat(rows, cols);
		board.readBoard(br);

		br.close();

		System.out.println(moveBoard(board));
	}

	static int moveBoard(BoardStat initBoard)
	{
		Queue<BoardStat> queue = new LinkedList<>();

		int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

		queue.add(initBoard);

		BoardStat curBoard, nextBoard;

		while (!queue.isEmpty())
		{
			curBoard = queue.poll();

			for (int[] dir : dirs)
			{
				nextBoard = curBoard.tilt(dir[0], dir[1]);

				if (nextBoard.isSuccess())
					return nextBoard.moveCnt;

				if (!nextBoard.isFail() && nextBoard.moveCnt < 10)
					queue.add(nextBoard);
			}
		}

		return -1;
	}
}

class BoardStat 
{
	int rows;
	int cols;
	char[][] map;

	Marble redMarble;
	Marble blueMarble;

	enum STAT {NORMAL, SUCCESS, FAIL};
	STAT stat;

	int moveCnt;

	BoardStat(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;
		this.map = new char[rows][cols];
		this.stat = STAT.NORMAL;
		this.moveCnt = 0;
	}

	BoardStat(BoardStat other)
	{
		this.rows = other.rows;
		this.cols = other.cols;

		this.map = new char[rows][cols];

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				(this.map)[i][j] = (other.map)[i][j];

		this.redMarble = new Marble(other.redMarble);
		this.blueMarble = new Marble(other.blueMarble);

		this.stat = other.stat;

		this.moveCnt = other.moveCnt;
	}

	void readBoard(BufferedReader br) throws Exception
	{
		String row;
		for (int i = 0; i < rows; i++)
		{
			row = br.readLine();
			for (int j = 0; j < cols; j++)
			{
				map[i][j] = row.charAt(j);

				if (map[i][j] == 'R')
					redMarble = new Marble(i, j, 'R');
				else if (map[i][j] == 'B')
					blueMarble = new Marble(i, j, 'B');
			}
		}
	}

	BoardStat tilt(int rowDir, int colDir)
	{
		BoardStat newBoard = new BoardStat(this);

		int anyMoves = 0;

		newBoard.moveCnt++;

		anyMoves += newBoard.moveMarble(newBoard.blueMarble, rowDir, colDir);

		if (newBoard.stat == STAT.FAIL)
			return newBoard;

		anyMoves += newBoard.moveMarble(newBoard.redMarble, rowDir, colDir);

		anyMoves += newBoard.moveMarble(newBoard.blueMarble, rowDir, colDir);

		if (anyMoves == 0)
			newBoard.stat = STAT.FAIL;

		return newBoard;
	}

	int moveMarble(Marble marble, int rowDir, int colDir)
	{
		int anyMoves = 0;

		int marbleOriginRow, marbleOriginCol;
		marbleOriginRow = marble.row;
		marbleOriginCol = marble.col;

		char nextCell;
		int nextRow, nextCol;

		while (true)
		{
			nextRow = marble.row + rowDir;
			nextCol = marble.col + colDir;

			nextCell = map[nextRow][nextCol];

			if (!canMarbleMoveTo(nextCell))
			{
				map[marbleOriginRow][marbleOriginCol] = '.';

				if (marble.isRed())
					map[marble.row][marble.col] = 'R';
				else
					map[marble.row][marble.col] = 'B';

				return anyMoves;
			}

			anyMoves++;

			if (nextCell == 'O')
			{
				if (marble.isRed())
					this.stat = STAT.SUCCESS;
				else
					this.stat = STAT.FAIL;

				map[marbleOriginRow][marbleOriginCol] = '.';

				return anyMoves;
			}

			marble.row = nextRow;
			marble.col = nextCol;
		}
	}

	boolean canMarbleMoveTo(char cell)
	{
		return cell == '.' || cell == 'O';
	}

	boolean isSuccess()
	{
		return stat == STAT.SUCCESS;
	}

	boolean isFail()
	{
		return stat == STAT.FAIL;
	}
}

class Marble
{
	int row;
	int col;

	enum COLOR {RED, BLUE};
	
	COLOR color;

	Marble(int row, int col, char c)
	{
		this.row = row;
		this.col = col;

		if (c == 'R')
			this.color = COLOR.RED;
		else
			this.color = COLOR.BLUE; 
	}

	Marble(Marble other)
	{
		this.row = other.row;
		this.col = other.col;
		this.color = other.color;
	}

	boolean isRed()
	{
		return color == COLOR.RED;
	}
}
