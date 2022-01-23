import java.util.*;

/* https://kloong.tistory.com/entry/백준-종이-조각-14391-Java */

public class Main
{
    static int rows, cols;
    static Scanner sc;

    public static void main(String[] args)
    {
        sc = new Scanner(System.in);
        StringTokenizer st;

        st = new StringTokenizer(sc.nextLine());
        rows = Integer.parseInt(st.nextToken());
        cols = Integer.parseInt(st.nextToken());

        //종이의 숫자들을 2차원 배열 형태로 입력받음
        int[][] paper = new int[rows][cols];
        readPaper(paper);

        System.out.println(getMaxSum(paper));
    }

    static int getMaxSum(int[][] paper)
    {
        int maxSum = 0; //반환할 값
        int bitmaskMax = 1 << rows * cols; //bitmask의 최대값 + 1

        //rows * cols 길이의 bitmask를 이용해서 종이를 찢는 모든 경우를 브루트포싱함
        //bit가 0인 칸은 가로로 찢는 칸, 1인 칸은 세로로 찢는 칸임
        for (int bitmask = 0; bitmask < bitmaskMax; bitmask++)
        {
            int sum = 0;
            int num;

            //bitmask의 각 bit를 맨 왼쪽 bit부터 차례로 검사하기 위한 변수
            //bitmask와는 다르게 항상 1인 bit가 한개만 존재함에 유의
            int bitIdx = 1 << (rows * cols - 1);
            //가로로 찢는 칸이 0을 의미하므로 not 연산을 통해 0을 1로 만들어줌
            bitmask = ~bitmask;

            //가로로 찢은 숫자들의 합을 구하는 반복문
            for (int row = 0; row < rows; row++)
            {
                num = 0;
                for (int col = 0; col < cols; col++)
                {
                    //bitmask는 2차원이 아닌 1차원의 연속된 bit이므로
                    //이런식으로 bitIdx를 움직이며 검사를 해 주며 숫자를 만들어야 한다
                    if ((bitmask & (bitIdx >> row * cols + col)) != 0)
                    {
                        num *= 10;
                        num += paper[row][col];
                    }
                    //bitmask에서 0인 bit를 만났을 경우 가로로 찢은 숫자가 끝난것이므로
                    //sum에 더해줘야 한다
                    else
                    {
                        sum += num;
                        num = 0;
                    }
                }
                //col == cols가 되어서 끝나는 경우도 있으므로 sum에 더해줘야 함을 잊지 말자
                sum += num;
            }

            bitIdx = 1 << (rows * cols - 1);
            bitmask = ~bitmask; //다시 not 연산을 해서 이제 세로로 찢은 칸을 찾는다

            //이번엔 가로가 아니라 세로로 봐야 하므로 반복문의 row와 col이 바뀌어야 한다
            for (int col = 0; col < cols; col++)
            {
                num = 0;
                for (int row = 0; row < rows; row++)
                {
                    if ((bitmask & (bitIdx >> row * cols + col)) != 0)
                    {
                        num *= 10;
                        num += paper[row][col];
                    }
                    else
                    {
                        sum += num;
                        num = 0;
                    }
                }
                sum += num;
            }

            //모든 bitmask 경우를 다 살펴보며 maxsum을 구한다
            maxSum = Math.max(maxSum, sum);
        }

        return maxSum;
    }

    static void readPaper(int[][] paper)
    {
        String line;
        for (int i = 0; i < rows; i++)
        {
            line = sc.nextLine();
            for (int j = 0; j < cols; j++)
                paper[i][j] = line.charAt(j) - '0';
        }
    }
}
