import java.util.*;

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

        int[][] paper = new int[rows][cols];
        readPaper(paper);

        System.out.println(getMaxSum(paper));
    }

    static int getMaxSum(int[][] paper)
    {
        int maxSum = 0;
        int bitmaskMax = 1 << rows * cols;

        for (int bitmask = 0; bitmask < bitmaskMax; bitmask++)
        {
            int sum = 0;
            int subSum;

            int bitIdx = 1 << (rows * cols - 1);
            bitmask = ~bitmask;

            for (int row = 0; row < rows; row++)
            {
                subSum = 0;
                for (int col = 0; col < cols; col++)
                {
                    if ((bitmask & (bitIdx >> row * cols + col)) != 0)
                    {
                        subSum *= 10;
                        subSum += paper[row][col];
                    }
                    else
                    {
                        sum += subSum;
                        subSum = 0;
                    }
                }
                sum += subSum;
            }

            bitIdx = 1 << (rows * cols - 1);
            bitmask = ~bitmask;

            for (int col = 0; col < cols; col++)
            {
                subSum = 0;
                for (int row = 0; row < rows; row++)
                {
                    if ((bitmask & (bitIdx >> row * cols + col)) != 0)
                    {
                        subSum *= 10;
                        subSum += paper[row][col];
                    }
                    else
                    {
                        sum += subSum;
                        subSum = 0;
                    }
                }
                sum += subSum;
            }

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
