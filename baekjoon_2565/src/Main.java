import java.io.*;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int numOfWires;
        numOfWires = Integer.parseInt(br.readLine());

        WireConnection[] wireConnections = new WireConnection[numOfWires];
        readWireConnections(wireConnections);

        Arrays.sort(wireConnections);

        ArrayList<Integer> longestIncreasingSeq = new ArrayList<>();
        getLongestIncreasingSeqOfPoleB(longestIncreasingSeq, wireConnections);

        System.out.println(numOfWires - longestIncreasingSeq.size());
    }

    static void getLongestIncreasingSeqOfPoleB(ArrayList<Integer> lis, WireConnection[] wireConnections)
    {
        lis.add(wireConnections[0].poleB);

        for (int i = 1; i < wireConnections.length; i++)
        {
            if (wireConnections[i].poleB > lis.get(lis.size() - 1))
                lis.add(wireConnections[i].poleB);
            else
                lis.set(lowerBound(lis, wireConnections[i].poleB), wireConnections[i].poleB);
        }
    }

    static int lowerBound(ArrayList<Integer> lis, int num)
    {
        int left, right, mid;

        left = 0;
        right = lis.size() - 1;
        mid = (left + right) / 2;

        while (left < right)
        {
            if (num > lis.get(mid))
                left = mid + 1;
            else
                right = mid;

            mid = (left + right) / 2;
        }

        return mid;
    }

    static void readWireConnections(WireConnection[] wireConnections) throws IOException
    {
        StringTokenizer st;

        for (int i = 0; i < wireConnections.length; i++)
        {
            st = new StringTokenizer(br.readLine());
            wireConnections[i] = new WireConnection(
                    Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
    }
}

class WireConnection implements Comparable<WireConnection>
{
    int poleA;
    int poleB;

    public WireConnection(int poleA, int poleB)
    {
        this.poleA = poleA;
        this.poleB = poleB;
    }

    @Override
    public int compareTo(WireConnection o)
    {
        return Integer.compare(poleA, o.poleA);
    }
}