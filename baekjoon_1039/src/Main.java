import java.util.*;

/* https://kloong.tistory.com/entry/백준-교환1039-Java */

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        int num, numOfSwap;
        num = sc.nextInt();
        numOfSwap = sc.nextInt();

        //입력받은 수가 swap이 가능한 숫자인지 체크
        if (!canSwap(num))
        {
            System.out.println(-1);
            return;
        }

        //매 stage마다 겹치는 숫자가 있는지 체크하기 위한 HashSet
        HashSet<String> hs = new HashSet<>();

        //BFS를 위한 queue. swap을 편하게 하기 위해 String 형태로 처리한다.
        Queue<String> queue = new LinkedList<>();
        queue.add(Integer.toString(num));

        int curStagePivots = 1;
        int nextStagePivots = 1;
        String pivot;
        int max = -1;

        while (numOfSwap >= 0)
        {
            curStagePivots = nextStagePivots;
            nextStagePivots = 0;
            max = -1;

            //각 stage에서는 이전 stage에서 enqueue되었던 String만 처리해주면 된다
            for (int i = 0; i < curStagePivots; i ++)
            {
                pivot = queue.poll();

                //K번 swap을 했으면 최종적으로 swap한 String들을 하나씩 뽑으면서 최댓값을 구해줌
                if (numOfSwap == 0)
                    max = Math.max(max, Integer.parseInt(pivot));
                //이전 stage에서 enqueue했던 string을 swap해서 다시 queue에 넣어줌
                else
                    nextStagePivots += swapAndEnqueue(pivot, queue, hs);
            }

            //이전 stage에서 enqueue했던 string은 다음 stage에서 enqueue할 string에 영향을 주지 않음
            //왜냐하면 무조건 K번 swap을 해야만 하기 때문에 같은 stage에서 겹치는 것만 빼주면 되는거지
            //이전 stage에서 넣었다고 다음 stage에서 빼버리면 원하는 답이 안나온다!
            hs.clear();
            numOfSwap--;
        }

        System.out.println(max);
    }

    static int swapAndEnqueue(String pivot, Queue<String> queue, HashSet<String> hs)
    {
        String numSwapped;
        int enqueueCnt = 0;

        for (int i = 0; i < pivot.length() - 1; i++)
        {
            for (int j = i + 1; j < pivot.length(); j++)
            {
                numSwapped = swap(pivot, i, j);

                //이 stage에서 이미 넣었던 string인지 확인
                if (hs.contains(numSwapped))
                    continue;

                queue.add(numSwapped);
                enqueueCnt++;

                hs.add(numSwapped);
            }
        }

        //enqeue한 string 개수를 반환함
        return enqueueCnt;
    }

    static String swap(String str, int idx1, int idx2)
    {
        char[] result = str.toCharArray();
        result[idx1] = str.charAt(idx2);
        result[idx2] = str.charAt(idx1);

        return new String(result);
    }

    static boolean canSwap(int num)
    {
        //1,2,3,...,9
        if (num < 10)
            return false;

        //10,20,30,...,90
        if (num < 100 && num % 10 == 0)
            return false;

        return true;
    }
}
