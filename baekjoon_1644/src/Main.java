import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();

        //N < 2이면 N 이하의 소수가 존재하지 않으므로 0 출력하고 종료
        if (N < 2)
        {
            System.out.println(0);
            return;
        }

        //N 이하의 소수에 대한 arraylist
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        getPrimeNumbersLessThanN(primeNumbers, N);

        //정답 구하고 출력
        System.out.println(countCases(primeNumbers, N));
    }

    //two pointers 를 이용해서 연속된 소수의 누적합을 오버헤드 없이 갱신
    //누적합이 N과 같으면 cnt++ 후 rightIdx++ & leftIdx++
    //N보다 작으면 rightIdx++, 크면 leftIdx++ 하며 누적합 갱신.
    //증감 연산자의 전위/후위에 주의하자.
    static int countCases(ArrayList<Integer> primeNumbers, int N)
    {
        int leftIdx, rightIdx;
        int cnt;
        int sum;

        leftIdx = 0;
        rightIdx = 0;
        cnt = 0;
        sum = primeNumbers.get(0);

        //사실상 leftIdx > rightIdx가 되는 경우는 while문 안에 있는 break 문 때문에
        //발생하지 않는다.
        while (leftIdx <= rightIdx)
        {
            if (sum == N)
            {
                cnt++;
                if (rightIdx + 1 == primeNumbers.size())
                    break;
                sum -= primeNumbers.get(leftIdx++);
                sum += primeNumbers.get(++rightIdx);
            }
            else if (sum < N)
            {
                if (rightIdx + 1 == primeNumbers.size())
                    break;
                sum += primeNumbers.get(++rightIdx);
            }
            else
                sum -= primeNumbers.get(leftIdx++);
        }

        return cnt;
    }

    //에라토스테네스의 체 알고리즘으로 N 이하의 모든 소수를 구하는 함수
    static void getPrimeNumbersLessThanN(ArrayList<Integer> primeNumbers, int N)
    {
        boolean[] primeCandidates = new boolean[N + 1];

        for (int prime = 2; prime <= N; prime++)
        {
            if (primeCandidates[prime])
                continue;

            primeNumbers.add(prime);

            //소수의 배수들을 모두 걸러낸다
            for (int j = 1; prime * j <= N; j++)
                primeCandidates[prime * j] = true;
        }
    }
}
