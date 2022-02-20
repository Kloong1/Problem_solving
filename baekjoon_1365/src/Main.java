import java.io.*;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int N = Integer.parseInt(br.readLine());

        int[] linkedPowerPole = new int[N];
        readLinkedPowerPoles(linkedPowerPole);

        ArrayList<Integer> longestIncreasingSeq = getLongestIncreasingSeq(linkedPowerPole);

        System.out.println(N - longestIncreasingSeq.size());
    }

    //LIST를 구하는 함수.
    static ArrayList<Integer> getLongestIncreasingSeq(int[] linkedPowerPole)
    {
        ArrayList<Integer> longestIncreasingSeq = new ArrayList<>(linkedPowerPole.length);

        //초기화
        longestIncreasingSeq.add(linkedPowerPole[0]);

        for (int poleIdx = 1; poleIdx < linkedPowerPole.length; poleIdx++)
        {
            if (linkedPowerPole[poleIdx] > longestIncreasingSeq.get(longestIncreasingSeq.size() - 1))
                longestIncreasingSeq.add(linkedPowerPole[poleIdx]);
            else
                longestIncreasingSeq.set(
                        lowerBound(longestIncreasingSeq, linkedPowerPole[poleIdx]),
                        linkedPowerPole[poleIdx]);
        }

        return longestIncreasingSeq;
    }

    //LIS를 구하기 위한 lowerBound 함수. 이분탐색을 사용해서 구현함.
    //num이 들어갈 자리의 index를 반환한다.
    static int lowerBound(ArrayList<Integer> list, int num)
    {
        int left, right, mid;

        left = 0;
        right = list.size() - 1;
        mid = (left + right) / 2;

        while (left < right)
        {
            if (num == list.get(mid))
                return mid;
            else if (num > list.get(mid))
                left = mid + 1;
            else
                right = mid;

            mid = (left + right) / 2;
        }

        return mid;
    }

    static void readLinkedPowerPoles(int[] linkedPowerPole) throws IOException
    {
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < linkedPowerPole.length; i++)
            linkedPowerPole[i] = Integer.parseInt(st.nextToken());
    }
}