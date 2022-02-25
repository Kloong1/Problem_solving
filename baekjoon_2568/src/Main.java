import java.io.*;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int numOfWires = Integer.parseInt(br.readLine());

        Wire[] wires = new Wire[numOfWires];
        readConnectedPolesOfWires(wires);

        Arrays.sort(wires);

        ArrayList<Wire> LIS = new ArrayList<>();
        ArrayList<Integer> LISLocation = new ArrayList<>();

        getLISOfWiresArr(LIS, LISLocation, wires);

        System.out.println(numOfWires - LIS.size());

        checkMustNotCutWires(LISLocation, LIS.size());

        for (int i = 0; i < wires.length; i++)
        {
            if (LISLocation.get(i) >= 0)
                System.out.println(wires[i].poleA);
        }
    }

    static void checkMustNotCutWires(ArrayList<Integer> LISLocation, int LISLength)
    {
        int loc = LISLength - 1;

        for (int i = LISLocation.size() - 1; i >= 0; i--)
        {
            if (LISLocation.get(i) == loc)
            {
                LISLocation.set(i, -1);
                loc--;
            }

            if (loc < 0)
                break;
        }
    }

    static void getLISOfWiresArr(ArrayList<Wire> LIS, ArrayList<Integer> LISLocation, Wire[] wires)
    {
        LIS.add(wires[0]);
        LISLocation.add(0);

        int loc;

        for (int i = 1; i < wires.length; i++)
        {
            if (wires[i].poleB > LIS.get(LIS.size() - 1).poleB)
            {
                LISLocation.add(LIS.size());
                LIS.add(wires[i]);
            }
            else
            {
                loc = lowerBound(LIS, wires[i]);
                LISLocation.add(loc);
                LIS.set(loc, wires[i]);
            }

        }
    }

    static int lowerBound(ArrayList<Wire> LIS, Wire w)
    {
        int left, right, mid;

        left = 0;
        right = LIS.size() - 1;
        mid = (left + right) / 2;

        while (left < right)
        {
            if (w.poleB > LIS.get(mid).poleB)
                left = mid + 1;
            else
                right = mid;

            mid = (left + right) / 2;
        }

        return mid;
    }

    static void readConnectedPolesOfWires(Wire[] wires) throws IOException
    {
        StringTokenizer st;

        for (int i = 0; i < wires.length; i++)
        {
            st = new StringTokenizer(br.readLine());
            wires[i] = new Wire(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
    }
}

class Wire implements Comparable<Wire>
{
    int poleA;
    int poleB;

    Wire (int poleA, int poleB)
    {
        this.poleA = poleA;
        this.poleB = poleB;
    }

    public int compareTo(Wire o)
    {
        return Integer.compare(this.poleA, o.poleA);
    }
}