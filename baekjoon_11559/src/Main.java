import java.io.*;
import java.util.*;

/* https://kloong.tistory.com/entry/백준-Puyo-Puyo-11559-Java */

public class Main
{
    public static void main(String[] args) throws IOException
    {
        char[][] field = new char[12][6];
        boolean[][] visit = new boolean[12][6];

        //입력되는 필드를 2차원 char 배열 형태로 그대로 입력받음
        readField(field);

        int chainCnt = 0;

        while (true)
        {
            //현재 필드 상태에서 터질수 있는 뿌요들을 전부 터트림
            //터진 뿌요 뭉탱이(?)의 개수를 반환
            int boomCnt = boomAllPuyos(field, visit);

            //만약 터진 뿌요가 하나도 없다면 연쇄가 끊긴 것임
            if (boomCnt == 0)
                break;

            //공중에 떠 있는 뿌요가 있다면 빈공간을 채워서 내려줌
            makeAllPuyosDown(field);

            chainCnt++;
        }

        System.out.println(chainCnt);
    }

    static void readField(char[][] field) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line;

        for (int i = 0; i < 12; i++)
        {
            line = br.readLine();
            for (int j = 0; j < 6; j++)
                field[i][j] = line.charAt(j);
        }
    }

    static int boomAllPuyos(char[][] field, boolean[][] visit)
    {
        int boomCnt = 0;
        Queue<Puyo> connectedPuyos = new LinkedList<>();

        for (int row = 0; row < 12; row++)
        {
            for (int col = 0; col < 6; col++)
            {
                //.(빈공간)이 아니고, 방문한 적 없는 뿌요를 찾을 때 까지 필드 탐색
                if (field[row][col] == '.' || visit[row][col])
                    continue;

                visit[row][col] = true; //방문 표시
                //현재 뿌요를 기준으로 주위의 같은 색깔 뿌요를 탐색해 나갈 것임
                connectedPuyos.add(new Puyo(field[row][col], row, col));

                //BFS를 통해 주위에 같은 색깔 뿌요들을 찾아서
                //connectedPuyos Queue에다가 넣어주는 함수
                getConnectedPuyos(field, visit, connectedPuyos);

                //주위 같은색깔 뿌요가 4개 이상이면 터트린다!
                if (connectedPuyos.size() >= 4)
                {
                    for (Puyo p : connectedPuyos)
                        field[p.row][p.col] = '.';
                    boomCnt++;
                }

                //뿌요 뭉탱이 초기화
                connectedPuyos.clear();
            }
        }

        //visit 초기화
        for (boolean[] v : visit)
            Arrays.fill(v, false);

        return boomCnt;
    }

    static void getConnectedPuyos(char[][] field, boolean[][] visit, Queue<Puyo> connectedPuyos)
    {
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; //상하좌우 이웃 뿌요들에 접근하기 쉽게 하기 위한 배열
        Puyo pivotPuyo, connectedPuyo;
        int neighborRow, neighborCol;

        //bfsQueue는 이웃 뿌요들을 탐색하기 위한 BFS를 할 때 쓰는 queue이다. 이 함수에서만 사용함.
        //connectedPuyos는 BFS를 통해 같은 색깔 뿌요 뭉탱이들을 찾아서 그 뿌요들을 보관하는 queue
        //connectedPuyos는 boomAllPuyos 함수에서도 사용한다.
        Queue<Puyo> bfsQueue = new LinkedList<>();
        bfsQueue.add(connectedPuyos.peek()); //BFS의 시작이 될 뿌요를 넣어줌

        while (!bfsQueue.isEmpty())
        {
            pivotPuyo = bfsQueue.poll();

            //poll한 뿌요의 상하좌우 뿌요를 확인
            for (int[] dir : dirs)
            {
                neighborRow = pivotPuyo.row + dir[0];
                neighborCol = pivotPuyo.col + dir[1];

                //이 뿌요가 필드 내에 있지 않거나, 같은 색깔 뿌요가 아니거나, 이미 방문한 뿌요인 경우
                if (!isInField(neighborRow, neighborCol) ||
                        field[neighborRow][neighborCol] != pivotPuyo.type ||
                        visit[neighborRow][neighborCol])
                    continue;

                visit[neighborRow][neighborCol] = true;

                //bfs에도, connectedPuyo에도 둘 다 넣어줘야하는 것을 잊지 말자
                connectedPuyo = new Puyo(field[neighborRow][neighborCol], neighborRow, neighborCol);
                bfsQueue.add(connectedPuyo);
                connectedPuyos.add(connectedPuyo);
            }
        }
    }

    static void makeAllPuyosDown(char[][] field)
    {
        int dotCnt;

        //세로줄 단위로 본다
        for (int col = 0; col < 6; col++)
        {
            dotCnt = 0;

            //맨 아래 뿌요부터 위로 하나씩 확인
            for (int row = 11; row >= 0; row--)
            {
                //.(빈공간)의 개수를 센다
                if (field[row][col] == '.')
                {
                    dotCnt++;
                    continue;
                }

                //뿌요를 만났을 때, 이전에 빈공간을 만난 적이 있다면
                //그 개수만큼 아래로 내려주고, 해당 공간은 빈공간으로 바꿔준다
                if (dotCnt != 0)
                {
                    field[row + dotCnt][col] = field[row][col];
                    field[row][col] = '.';
                }
            }
        }
    }

    static boolean isInField(int row, int col)
    {
        return row >= 0 && row < 12 && col >= 0 && col < 6;
    }
}

class Puyo
{
    char type;
    int row;
    int col;

    Puyo(char type, int row, int col)
    {
        this.type = type;
        this.row = row;
        this.col = col;
    }
}
