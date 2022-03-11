/* https://kloong.tistory.com/entry/욕심쟁이-판다-1937-Java */

import java.io.*;
import java.util.*;

public class Main
{
    static int N;
    static int[][] bamboos;
    static int[][] maxMovesArr; //DP 배열
    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //상하좌우 이동에 쓰일 배열

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        readN();

        bamboos = new int[N][N];
        readBamboos();

        System.out.println(getMaxMoves());
    }

    static int getMaxMoves()
    {
        int maxMoves = 0;

        //DP 배열 생성 및 0으로 초기화
        maxMovesArr = new int[N][N];

        for (int row = 0; row < N; row++)
        {
            for (int col = 0; col < N; col++)
                maxMoves = Math.max(maxMoves, dfs2GetMaxMoves(row, col)); //모든 칸에서 DFS
        }

        return maxMoves;
    }

    //DFS를 하며 최댓값을 구하고 기록하는 함수
    static int dfs2GetMaxMoves(int row, int col)
    {
        //이미 DP 배열에 값이 있으면 바로 return
        if (maxMovesArr[row][col] != 0)
            return maxMovesArr[row][col];

        //출발하는 칸을 포함해야 하기 때문에 1부터 시작
        int maxMoves = 1;

        //상하좌우로 움직이며 DFS가 가능할 경우 DFS
        int nextRow, nextCol;
        for (int[] dir : dirs)
        {
            nextRow = row + dir[0];
            nextCol = col + dir[1];

            if (!isInMap(nextRow, nextCol))
                continue;

            if (bamboos[nextRow][nextCol] <= bamboos[row][col])
                continue;

            //해당 칸으로 한 칸 움직였으므로 + 1 한 값과 비교해야함
            maxMoves = Math.max(maxMoves, dfs2GetMaxMoves(nextRow, nextCol) + 1);
        }

        //DP 배열에 기록
        maxMovesArr[row][col] = maxMoves;

        return maxMoves;
    }

    //해당 row, col이 범위 내의 값인지 확인
    static boolean isInMap(int row, int col)
    {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    static void readN() throws IOException
    {
        N = Integer.parseInt(br.readLine());
    }

    static void readBamboos() throws IOException
    {
        StringTokenizer st;

        for (int row = 0; row < N; row++)
        {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < N; col++)
                bamboos[row][col] = Integer.parseInt(st.nextToken());
        }
    }
}
