import java.io.*;
import java.util.*;

public class Main
{
    static int numOfBalls;
    static BufferedReader br;

    public static void main(String[] args) throws IOException
    {
        br = new BufferedReader(new InputStreamReader(System.in));
        numOfBalls = Integer.parseInt(br.readLine());

        //Ball[] 형태로 입력 받음
        Ball[] ballArr = readBalls();

        //크기를 기준으로 오름차순 정렬
        Arrays.sort(ballArr);

        //출력은 공 번호 순서대로 출력해야 하므로
        //오름차순 정렬한 ballArr로 구한 답을 공 번호를 index로 하는 배열에 저장
        int[] eatableSizeOfBall = getEatableSizeOfBall(ballArr);

        //출력. 최적화를 위해 StringBuilder 사용.
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= numOfBalls; i++)
            sb.append(eatableSizeOfBall[i]).append('\n');

        System.out.println(sb.toString());
    }

    static int[] getEatableSizeOfBall(Ball[] ballArr)
    {
        //답을 저장할 배열. 공 번호가 index이다.
        int[] eatableSizeOfBall = new int[numOfBalls + 1];

        //colorSizeSum[i] => 색이 i인 공의 크기의 누적합.
        int[] colorSizeSum = new int[numOfBalls + 1];
        int totalSum = 0;
        int index = 0;

        //오름차순 정렬된 Ball 배열을 순차 탐색
        for (int pivot = 1; pivot <= numOfBalls; pivot++)
        {
            //크기가 같은 공이 있음에 유의! 크기가 같은 공은 색이 달라도 먹지 못한다.
            //크기가 같은 공을 탐색할 때는 이 while문에 안걸리다가, 크기가 커지는 순간
            //여태까지 넘어갔던 크기가 같은 공(현재 pivot의 크기보다는 작음)들의 크기를
            //totalSum과 colorSizeSum[]에 갱신해준다.
            while (ballArr[index].size < ballArr[pivot].size)
            {
                totalSum += ballArr[index].size;
                colorSizeSum[ballArr[index].color] += ballArr[index].size;
                index++;
            }

            //totalSum은 ballArr[pivot]보다 크기가 작은 공들의 크기의 누적합.
            //색이 다르고, 크기가 작아야 먹을 수 있으므로
            //전체 누적합에서 pivot과 색이 같은 공들의 크기의 누적합을 빼준것이
            //pivot이 먹을 수 있는 공의 크기의 합이다!
            eatableSizeOfBall[ballArr[pivot].num] = totalSum - colorSizeSum[ballArr[pivot].color];
        }

        return eatableSizeOfBall;
    }

    static Ball[] readBalls() throws IOException
    {
        Ball[] ballArr = new Ball[numOfBalls + 1];
        //입력으로 들어오는 size가 1 이상의 값이므로
        //size가 0인 Ball을 넣어줘도 문제 없음. 어차피 size 기준으로 오름차순 정렬할 것이기 때문.
        //ballArr[0]는 그냥 dummy임.
        ballArr[0] = new Ball(0, 0, 0);

        StringTokenizer st;
        int color, size;

        for (int i = 1; i <= numOfBalls; i++)
        {
            st = new StringTokenizer(br.readLine());
            color = Integer.parseInt(st.nextToken());
            size = Integer.parseInt(st.nextToken());

            ballArr[i] = new Ball(i, color, size);
        }

        return ballArr;
    }
}

class Ball implements Comparable<Ball>
{
    int num;
    int color;
    int size;

    Ball(int num, int color, int size)
    {
        this.num = num;
        this.color = color;
        this.size = size;
    }

    public int compareTo(Ball o)
    {
        return Integer.compare(this.size, o.size);
    }
}