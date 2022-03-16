import java.io.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int numOfTestcases = Integer.parseInt(br.readLine());

        int[] widths = new int[numOfTestcases];
        int maxWidth = readWidths(widths);

        int[] dp = getDpArr(maxWidth);

        StringBuilder sb = new StringBuilder();
        for (int width : widths)
            sb.append(dp[width]).append('\n');

        System.out.println(sb.toString());
    }

    static int[] getDpArr(int maxWidth)
    {
        int[] dp = new int[maxWidth + 2];

        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 5;

        for (int width = 3; width <= maxWidth; width++)
        {
            dp[width] = dp[width - 1] + 4 * dp[width - 2];
            for (int i = 3; i <= width; i += 2)
                dp[width] += dp[width - i] * 2;
            for (int i = 4; i <= width; i += 2)
                dp[width] += dp[width - i] * 3;
        }

        return dp;
    }

    static int readWidths(int[] widths) throws IOException
    {
        int maxWidth = 0;

        for (int i = 0; i < widths.length; i++)
        {
            widths[i] = Integer.parseInt(br.readLine());
            maxWidth = Math.max(maxWidth, widths[i]);
        }

        return maxWidth;
    }
}
