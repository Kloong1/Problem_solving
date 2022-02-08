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

        Ball[] ballArr = readBalls();

        Arrays.sort(ballArr);

        int[] eatableSizeOfBall = getEatableSizeOfBall(ballArr);

        for (int i = 1; i <= numOfBalls; i++)
            System.out.println(eatableSizeOfBall[i]);
    }

    static int[] getEatableSizeOfBall(Ball[] ballArr)
    {
        int[] eatableSizeOfBall = new int[numOfBalls + 1];

        int[] colorSizeSum = new int[numOfBalls + 1];
        int[] tempColorSizeSum = new int[numOfBalls + 1];

        int sizeSum = 0;
        int tempSizeSum = 0;

        int curSize = 0;

        for (Ball b : ballArr)
        {
            if (b.size != curSize)
            {
                curSize = b.size;
                addArrAndInitTempArr(colorSizeSum, tempColorSizeSum);
                sizeSum += tempSizeSum;
                tempSizeSum = 0;
            }

            eatableSizeOfBall[b.num] = sizeSum - colorSizeSum[b.color];

            tempColorSizeSum[b.color] += b.size;
            tempSizeSum += b.size;
        }

        return eatableSizeOfBall;
    }

    static void addArrAndInitTempArr(int[] colorSizeSum, int[] tempColorSizeSum)
    {
        for (int i = 1; i <= numOfBalls; i++)
        {
            colorSizeSum[i] += tempColorSizeSum[i];
            tempColorSizeSum[i] = 0;
        }
    }

    static Ball[] readBalls() throws IOException
    {
        Ball[] ballArr = new Ball[numOfBalls + 1];
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