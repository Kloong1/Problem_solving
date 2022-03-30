import java.io.*;
import java.util.*;

public class Main
{
    static int rows, cols;
    static char[][] board;

    static Marble startRed, startBlue;

    public static void main(String[] args) throws IOException
    {
        readBoard();

        System.out.println(tiltBoard(board, startRed, startBlue, 0));
    }

    static int tiltBoard(char[][] board, Marble red, Marble blue, int tiltCnt)
    {
        if (tiltCnt == 10)
            return 0;

        char[][] nextBoard;
        Marble nextRed, nextBlue;
        int result, boardStat;

        result = 0;
        for (int dir = 0; dir < 4; dir++)
        {
            nextBoard = copyBoard(board);
            nextRed = new Marble(red);
            nextBlue = new Marble(blue);

            if ((boardStat = tiltBoardDir(nextBoard, nextRed, nextBlue, dir)) == 1)
                return 1;

            if (boardStat == -1)
                continue;

            if (!red.equals(nextRed) || !blue.equals(nextBlue))
                result = tiltBoard(nextBoard, nextRed, nextBlue, tiltCnt + 1);

            if (result == 1)
                return 1;

        }

        return 0;
    }

    static int tiltBoardDir(char[][] board, Marble red, Marble blue, int dir)
    {
        if (dir == 0)
            return tiltBoardLeft(board, red, blue);
        else if (dir == 1)
            return tiltBoardRight(board, red, blue);
        else if (dir == 2)
            return tiltBoardUp(board, red, blue);
        else
            return tiltBoardDown(board, red, blue);
    }

    static boolean moveMarbleLeft(char[][] board, Marble marble)
    {
        boolean escaped = false;
        int startRow = marble.row;
        int startCol = marble.col;

        while (marble.col > 0)
        {
            if (board[marble.row][marble.col - 1] == '.')
            {
                marble.col--;
            }
            else if (board[marble.row][marble.col - 1] == 'O')
            {
                escaped = true;
                break;
            }
            else
                break;
        }

        board[startRow][startCol] = '.';
        if (!escaped)
            board[marble.row][marble.col] = marble.color;

        return escaped;
    }

    static boolean moveMarbleRight(char[][] board, Marble marble)
    {
        boolean escaped = false;
        int startRow = marble.row;
        int startCol = marble.col;

        while (marble.col < cols - 1)
        {
            if (board[marble.row][marble.col + 1] == '.')
            {
                marble.col++;
            }
            else if (board[marble.row][marble.col + 1] == 'O')
            {
                escaped = true;
                break;
            }
            else
                break;
        }

        board[startRow][startCol] = '.';
        if (!escaped)
            board[marble.row][marble.col] = marble.color;

        return escaped;
    }

    static boolean moveMarbleUp(char[][] board, Marble marble)
    {
        boolean escaped = false;
        int startRow = marble.row;
        int startCol = marble.col;

        while (marble.row > 0)
        {
            if (board[marble.row - 1][marble.col] == '.')
            {
                marble.row--;
            }
            else if (board[marble.row - 1][marble.col] == 'O')
            {
                escaped = true;
                break;
            }
            else
                break;
        }

        board[startRow][startCol] = '.';
        if (!escaped)
            board[marble.row][marble.col] = marble.color;

        return escaped;
    }

    static boolean moveMarbleDown(char[][] board, Marble marble)
    {
        boolean escaped = false;
        int startRow = marble.row;
        int startCol = marble.col;

        while (marble.row < rows - 1)
        {
            if (board[marble.row + 1][marble.col] == '.')
            {
                marble.row++;
            }
            else if (board[marble.row + 1][marble.col] == 'O')
            {
                escaped = true;
                break;
            }
            else
                break;
        }

        board[startRow][startCol] = '.';
        if (!escaped)
            board[marble.row][marble.col] = marble.color;

        return escaped;
    }

    static int tiltBoardLeft(char[][] board, Marble red, Marble blue)
    {
        boolean redEscaped, blueEscaped;

        redEscaped = moveMarbleLeft(board, red);

        blueEscaped = moveMarbleLeft(board, blue);

        if (blueEscaped)
            return -1;

        if (!redEscaped)
            redEscaped = moveMarbleLeft(board, red);

        if (redEscaped)
            return 1;

        return 0;
    }

    static int tiltBoardRight(char[][] board, Marble red, Marble blue)
    {
        boolean redEscaped, blueEscaped;

        redEscaped = moveMarbleRight(board, red);

        blueEscaped = moveMarbleRight(board, blue);

        if (blueEscaped)
            return -1;

        if (!redEscaped)
            redEscaped = moveMarbleRight(board, red);

        if (redEscaped)
            return 1;

        return 0;
    }

    static int tiltBoardUp(char[][] board, Marble red, Marble blue)
    {
        boolean redEscaped, blueEscaped;

        redEscaped = moveMarbleUp(board, red);

        blueEscaped = moveMarbleUp(board, blue);

        if (blueEscaped)
            return -1;

        if (!redEscaped)
            redEscaped = moveMarbleUp(board, red);

        if (redEscaped)
            return 1;

        return 0;
    }

    static int tiltBoardDown(char[][] board, Marble red, Marble blue)
    {
        boolean redEscaped, blueEscaped;

        redEscaped = moveMarbleDown(board, red);

        blueEscaped = moveMarbleDown(board, blue);

        if (blueEscaped)
            return -1;

        if (!redEscaped)
            redEscaped = moveMarbleDown(board, red);

        if (redEscaped)
            return 1;

        return 0;
    }

    static char[][] copyBoard(char[][] board)
    {
        char[][] newBoard = new char[rows][cols];

        for (int row = 0; row < rows; row++)
            for (int col = 0; col < cols; col++)
                newBoard[row][col] = board[row][col];

        return newBoard;
    }

    static void readBoard() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());

        board = new char[rows][cols];

        for (int row = 0; row < rows; row++)
        {
            String line = br.readLine();
            for (int col = 0; col < cols; col++)
            {
                board[row][col] = line.charAt(col);
                if (board[row][col] == 'R')
                    startRed = new Marble(row ,col, 'R');
                else if (board[row][col] == 'B')
                    startBlue = new Marble(row, col, 'B');
            }
        }
    }
}

class Marble
{
    int row;
    int col;
    char color;

    public Marble(int row, int col, char color)
    {
        this.color = color;
        this.row = row;
        this.col = col;
    }

    public Marble(Marble m)
    {
        this.color = m.color;
        this.row = m.row;
        this.col = m.col;
    }

    public boolean equals(Marble o)
    {
        return row == o.row && col == o.col;
    }
}