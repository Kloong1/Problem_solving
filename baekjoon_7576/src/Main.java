import java.io.*;
import java.util.*;

public class Main
{
    static int rows, cols; //토마토 창고의 행,열 길이
    static int[][] warehouse; //토마토 창고의 토마토를 int[][] 배열로 표현

    public static void main(String[] args) throws IOException
    {
        //토마토 창고 크기와 토마토 상태 및 위치를 입력 받음
        readInput();

        //unripeCnt => 안 익은 토마토의 개수
        //tomatoesOfDayCnt => 0이 되면 하루가 지났다는 의미
        //어떤 날에 익은 토마토가 k개 있을 때, k개의 토마토를 하나씩 확인하며
        //인접한 토마토를 전부 익히면 하루가 지났다는 것이다.
        //이 k값을 저장해두고 하루가 지났다는 것을 확인하기 위해 쓰는 변수가 tomatoesOfDayCnt
        int unripeCnt, tomatoesOfDayCnt;
        unripeCnt = 0;
        tomatoesOfDayCnt = 0;

        //BFS에 사용할 queue
        Queue<RipeTomato> ripeTomatoQueue = new LinkedList<>();

        //warehouse[][]를 탐색하며 토마토의 상태를 확인
        for (int row = 0; row < rows; row++)
        {
            for (int col = 0; col < cols; col++)
            {
                //안 익은 토마토의 개수를 센다
                if (warehouse[row][col] == 0)
                    unripeCnt++;
                //익은 토마토면 bfs를 위해 queue에 넣어준다
                //여기서 넣어준 tomatoesOfDayCnt 개의 토마토들이
                //인접한 안 익은 토마토를 전부 익히면 둘째날이 된다.
                else if (warehouse[row][col] == 1)
                {
                    ripeTomatoQueue.add(new RipeTomato(row, col));
                    tomatoesOfDayCnt++;
                }
            }
        }

        //정답 출력
        System.out.println(getRipeTime(ripeTomatoQueue, unripeCnt, tomatoesOfDayCnt));
    }

    static int getRipeTime(Queue<RipeTomato> ripeTomatoQueue, int unripeCnt, int tomatoesOfDayCnt)
    {
        //안 익은 토마토가 존재하지 않으면 0 반환
        if (unripeCnt == 0)
            return 0;

        int ripeTime = 1; //반환할 변수. 모든 토마토를 익히는데 걸리는 날짜.
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //bfs를 위한 방향 배열
        RipeTomato ripeTomato;
        int unripeRow, unripeCol;

        //BFS
        while (!ripeTomatoQueue.isEmpty())
        {
            ripeTomato = ripeTomatoQueue.poll();
            tomatoesOfDayCnt--; //이 값이 0이 되면 하루가 지난 것.

            //인접한 토마토를 확인
            for (int[] dir : dirs)
            {
                //인접한 지역이 범위를 벗어나거나, 안익은 토마토가 아니면 continue
                if (!unripeTomatoExists(ripeTomato.row + dir[0], ripeTomato.col + dir[1]))
                    continue;

                //최적화를 위해 토마토가 전부 익으면 바로 return
                unripeCnt--;
                if (unripeCnt == 0)
                    return ripeTime;

                unripeRow = ripeTomato.row + dir[0];
                unripeCol = ripeTomato.col + dir[1];

                //안익은 토마토를 익은 토마토로 바꿔주고 queue에 넣어준다
                warehouse[unripeRow][unripeCol] = 1;
                ripeTomatoQueue.add(new RipeTomato(unripeRow, unripeCol));
            }

            //하루가 지난 경우 ripeTime++
            //새롭게 익은 토마토 개수 == ripeTomatoQueue.size()
            if (tomatoesOfDayCnt == 0)
            {
                tomatoesOfDayCnt = ripeTomatoQueue.size();
                ripeTime++;
            }
        }

        //bfs가 끝났는데 안 익은 토마토가 남아있는 경우.
        return  -1;
    }

    static boolean unripeTomatoExists(int row, int col)
    {
        return row >= 0 && row < rows && col >= 0 && col < cols && warehouse[row][col] == 0;
    }

    static void readInput() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        cols = Integer.parseInt(st.nextToken());
        rows = Integer.parseInt(st.nextToken());

        warehouse = new int[rows][cols];

        for (int row = 0; row < rows; row++)
        {
            st = new StringTokenizer(br.readLine());
            for (int col = 0; col < cols; col++)
                warehouse[row][col] = Integer.parseInt(st.nextToken());
        }

        br.close();
    }
}

class RipeTomato
{
    int row;
    int col;

    RipeTomato(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
}
