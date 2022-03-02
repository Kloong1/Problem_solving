import java.io.*;
import java.util.*;

public class Main
{
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int sizeOfSequence = Integer.parseInt(br.readLine());

        //수열을 입력 받음. 질문에서 사용하는 index가 1부터 시작하므로 배열 크기를 + 1 해준다.
        int[] sequence = new int[sizeOfSequence + 1];
        readSequence(sequence);

        //질문을 입력받음.
        int numOfQueries = Integer.parseInt(br.readLine());
        Query[] queries = new Query[numOfQueries];
        readQueries(queries);

        //정답 구하는 메소드 호출 후 정답 출력.
        System.out.println(getAnswers(queries, sequence));
    }

    //정답을 구하는 함수.
    //가장 먼저 DP 배열을 초기화한다.
    //질문을 순서대로 탐색하면서 질문에 대한 답을 구하는데, 이 때 각 질문의 답을 구하면서 DP 배열을 업데이트한다.
    //이후의 질문의 답을 구할 때 DP 배열의 값을 이용한다.
    static String getAnswers(Query[] queries, int[] sequence)
    {
        StringBuilder sb = new StringBuilder();

        //DP 배열 초기화. -1, 0 , 1의 값을 가질 수 있다.
        //palindromeDP[i][j] -> S = i 이고 E = j 인 수열이 팰린드롬인지의 여부.
        //-1이면 아직 모름. 0이면 팰린드롬 아님. 1이면 팰린드롬.
        int[][] palindromeDP = new int[sequence.length][sequence.length];
        initPalindromeDP(palindromeDP);

        //각 질문마다 팰린드롬인지 체크하는 함수를 호출해서 정답을 구한다.
        for (Query q : queries)
            sb.append(checkPalindrome(sequence, q.startIdx, q.endIdx, palindromeDP)).append('\n');

        return sb.toString();
    }

    //startIdx부터 endIdx까지의 sequence가 팰린드롬인지 확인하는 재귀함수.
    //palindoremDP에 0 또는 1의 값이 있으면 사용하고, 없으면 재귀함수를 호출해서 DP를 갱신하며 답을 찾음.
    //s <= E 라고 문제 조건에 있으므로 S가 E보다 커지는 경우는 고려하지 않아도 됨.
    static int checkPalindrome(int[] sequence, int startIdx, int endIdx, int[][] palindromeDP)
    {
        int subSequenceResult;

        //이전 질문에서 팰린드롬인지 확인했던 경우 그대로 반환.
        if (palindromeDP[startIdx][endIdx] != -1)
            return palindromeDP[startIdx][endIdx];

        //endIdx == startIdx인 경우는 이미 1로 초기화 되어있어서 따로 처리 안해줘도 되는데
        //이 경우는 따로 이렇게 처리 해줘야 한다.
        if (endIdx - startIdx == 1)
        {
            //팰린드롬인지 확인
            if (sequence[startIdx] == sequence[endIdx])
                palindromeDP[startIdx][endIdx] = 1;
            else
                palindromeDP[startIdx][endIdx] = 0;
            return palindromeDP[startIdx][endIdx];
        }

        //이전 질문에서 startIdx ~ endIdx 범위가 팰린드롬인지 확인한 적이 없음.
        //따라서 재귀 호출을 이용하여 startIdx + 1 ~ endIdx -1 범위가 팰린드롬인지 먼저 확인
        subSequenceResult = checkPalindrome(sequence, startIdx + 1, endIdx - 1, palindromeDP);

        if (subSequenceResult == 1 && sequence[startIdx] == sequence[endIdx])
        {
            palindromeDP[startIdx][endIdx] = 1;
            return 1;
        }

        palindromeDP[startIdx][endIdx] = 0;
        return 0;
    }

    static void initPalindromeDP(int[][] palindromeDP)
    {
        //-1로 초기화
        for (int[] row : palindromeDP)
            Arrays.fill(row, -1);

        //길이 1의 수열은 팰린드롬이다.
        for (int i = 0; i < palindromeDP.length; i++)
            palindromeDP[i][i] = 1;
    }

    static void readQueries(Query[] queries) throws IOException
    {
        StringTokenizer st;

        for (int i = 0; i < queries.length; i++)
        {
            st = new StringTokenizer(br.readLine());
            queries[i] = new Query(
                    Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
    }

    static void readSequence(int[] sequence) throws IOException
    {
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 1; i < sequence.length; i++)
            sequence[i] = Integer.parseInt(st.nextToken());
    }
}

class Query
{
    int startIdx;
    int endIdx;

    Query(int startIdx, int endIdx)
    {
        this.startIdx = startIdx;
        this.endIdx = endIdx;
    }
}