import java.io.*;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int rows, cols;

        StringTokenizer st = new StringTokenizer(br.readLine());
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());

        int[][] originalMap = new int[rows][cols];
        readMap(originalMap, rows, cols);

        Map[] maps = new Map[4];
        getRotatedMaps(maps, new Map(originalMap, rows, cols));

        Tetromino[] tetrominos = new Tetromino[7];
        initTetrominos(tetrominos);

        System.out.println(getMaxSum(maps, tetrominos));
    }

    static int getMaxSum(Map[] maps, Tetromino[] tetrominos)
    {
        int maxSum = 0;

        for (Map map : maps)
            maxSum = Math.max(maxSum, getMaxSumOfMap(map, tetrominos));

        return maxSum;
    }

    static int getMaxSumOfMap(Map map, Tetromino[] tetrominos)
    {
        int maxSum = 0;

        int maxRow, maxCol;
        for (Tetromino tetromino : tetrominos)
        {
            maxRow = map.rows - tetromino.height;
            maxCol = map.cols - tetromino.width;

            for (int row = 0; row <= maxRow; row++)
            {
                for (int col = 0; col <= maxCol; col++)
                    maxSum = Math.max(maxSum, getSumOfTetromino(map.map, tetromino.points, col, row));
            }
        }

        return maxSum;
    }

    static int getSumOfTetromino(int[][] map, Point[] points, int startX, int startY)
    {
        int sum = 0;

        for (Point p : points)
            sum += map[startY + p.y][startX + p.x];

        return sum;
    }

    static void getRotatedMaps(Map[] maps, Map originalMap)
    {
        maps[0] = originalMap;
        maps[1] = maps[0].getRotatedMap();
        maps[2] = maps[1].getRotatedMap();
        maps[3] = maps[2].getRotatedMap();
    }

    static void initTetrominos(Tetromino[] tetrominos)
    {
        //일자 모양
        tetrominos[0] = new Tetromino(
                new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(3, 0));
        tetrominos[0].width = 4;
        tetrominos[0].height = 1;

        //네모 모양
        tetrominos[1] = new Tetromino(
                new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(1, 1));
        tetrominos[1].width = 2;
        tetrominos[1].height = 2;

        //ㄴ 모양
        tetrominos[2] = new Tetromino(
                new Point(0, 0), new Point(0, 1), new Point(0, 2), new Point(1, 2));
        tetrominos[2].width = 2;
        tetrominos[2].height = 3;

        //ㄴ 모양을 상하로 대칭시킨거
        tetrominos[3] = new Tetromino(
                new Point(0, 0), new Point(1, 0), new Point(0, 1), new Point(0, 2));
        tetrominos[3].width = 2;
        tetrominos[3].height = 3;

        //대충 ㄹ자 모양
        tetrominos[4] = new Tetromino(
                new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2));
        tetrominos[4].width = 2;
        tetrominos[4].height = 3;

        //대충 ㄹ자 모양 좌우 대칭시킨거
        tetrominos[5] = new Tetromino(
                new Point(1, 0), new Point(1, 1), new Point(0, 1), new Point(0, 2));
        tetrominos[5].width = 2;
        tetrominos[5].height = 3;

        //ㅜ 모양
        tetrominos[6] = new Tetromino(
                new Point(0, 0), new Point(1, 0), new Point(2, 0), new Point(1, 1));
        tetrominos[6].width = 3;
        tetrominos[6].height = 2;
    }

    static void readMap(int[][] map, int rows, int cols) throws IOException
    {
        StringTokenizer st;
        for (int row = 0; row < rows; row++)
        {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < cols; col++)
                map[row][col] = Integer.parseInt(st.nextToken());
        }
    }
}

class Map
{
    int[][] map;
    int rows;
    int cols;

    public Map(int[][] map, int rows, int cols)
    {
        this.map = map;
        this.rows = rows;
        this.cols = cols;
    }

    Map getRotatedMap()
    {
        int[][] rotatedMap = new int[cols][rows];

        for (int col = 0; col < cols; col++)
        {
            for (int row = rows - 1; row >= 0; row--)
                rotatedMap[col][rows - row - 1] = map[row][col];
        }

        return new Map(rotatedMap, cols, rows);
    }
}

class Tetromino
{
    Point[] points = new Point[4];
    int width;
    int height;

    Tetromino (Point p1, Point p2, Point p3, Point p4)
    {
        points[0] = p1;
        points[1] = p2;
        points[2] = p3;
        points[3] = p4;
    }
}

class Point
{
    int x;
    int y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
