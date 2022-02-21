import java.io.*;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int numOfPorts = Integer.parseInt(br.readLine());

        int[] ports = new int[numOfPorts];
        readPortNumbers(ports);

        ArrayList<Integer> longestIncreasingSeq = getLongestIncreasingSeq(ports);

        System.out.println(longestIncreasingSeq.size());
    }

    static ArrayList<Integer> getLongestIncreasingSeq(int[] ports)
    {
        ArrayList<Integer> LIS = new ArrayList<>();

        LIS.add(ports[0]);

        for (int i = 1; i < ports.length; i++)
        {
            if (ports[i] > LIS.get(LIS.size() - 1))
                LIS.add(ports[i]);
            else
                LIS.set(lowerBound(LIS, ports[i]), ports[i]);
        }

        return LIS;
    }

    static int lowerBound(ArrayList<Integer> LIS, int num)
    {
        int left, right, mid;

        left = 0;
        right = LIS.size() - 1;
        mid = (left + right) / 2;

        while (left < right)
        {
            if (num == LIS.get(mid))
                return mid;
            else if (num > LIS.get(mid))
                left = mid + 1;
            else
                right = mid;

            mid = (left + right) / 2;
        }

        return mid;
    }

    static void readPortNumbers(int[] ports) throws IOException
    {
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < ports.length; i++)
            ports[i] = Integer.parseInt(st.nextToken());
    }
}
