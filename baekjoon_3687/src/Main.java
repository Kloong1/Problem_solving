import java.io.*;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //테스트 케이스 수 입력
        int numOfTestCases = Integer.parseInt(br.readLine());

        //테스트 케이스 별 성냥개비 개수 입력
        int[] matchesArr = new int[numOfTestCases];
        readMatches(matchesArr, br);

        //테스트 케이스 별 주어진 성냥개비로 만들 수 있는 가장 작은 수 구함
        //int[] 배열로 만들었더니 숫자 범위 떄문에 틀렸음.
        //String[]으로 하지 않는 이유는 숫자 비교 과정이 있기 때문.
        long[] minNumArr = new long[numOfTestCases];
        getMinNumMadeFromMatches(minNumArr, matchesArr);

        //테스트 케이스 별 주어진 성냥개비로 만들 수 있는 가장 큰 수 구함.
        //성냥개비 100개일 때 50자리 숫자 만들어지므로 String[]으로 처리해야 한다.
        String[] maxNumArr = new String[numOfTestCases];
        getMaxNumMadeFromMatches(maxNumArr, matchesArr);

        //정답 출력
        for (int i = 0; i < numOfTestCases; i++)
            System.out.printf("%d %s\n", minNumArr[i], maxNumArr[i]);
    }

    //주어진 성냥개비로 만들 수 있는 최대 숫자 구하는 함수
    static void getMaxNumMadeFromMatches(String[] maxNumArr, int[] matchesArr)
    {
        StringBuilder maxNum;
        int matchRemains;

        //모든 테스트 케이스에 대해서 반복
        for (int i = 0; i < matchesArr.length; i++)
        {
            maxNum = new StringBuilder(); //현재 테스트 케이스에서의 정답
            matchRemains = matchesArr[i]; //현재 남은 성냥개비 수

            //자릿수를 늘리는 것이 큰 수를 만들기 가장 유리함
            //따라서 성냥개비가 2개로 가장 적게 드는 1을 가장 작은 자릿수부터 채워나감
            //성냥개비가 3개 남았을 경우 1을 넣으면 성냥개비가 1개 남아서 숫자를 만들 수 없음
            //따라서 성냥개비가 3개 드는 7로 채운다
            while (matchRemains > 0)
            {
                if (matchRemains == 3)
                {
                    maxNum.insert(0, "7");
                    break;
                }
                else
                {
                    maxNum.insert(0, "1");
                    matchRemains -= 2;
                }
            }

            maxNumArr[i] = maxNum.toString();
        }
    }

    //DP를 사용해서 주어진 성냥개비로 만들 수 있는 가장 작은 수를 구하는 함수
    static void getMinNumMadeFromMatches(long[] minNumArr, int[] matchesArr)
    {
        //dp 배열 초기화. 성냥개비의 최댓값이 100이므로 배열 index를 100까지 만듦.
        //이 dpArr[i] -> 성냥개비 i를 모두 사용해서 만들 수 있는 가장 작은 숫자
        long[] dpArr = new long[101];
        initDpArr(dpArr);

        //matchesOfNum[i] -> 한자리 숫자 i를 만드는데 드는 성냥개비 개수
        int[] matchesOfNum = new int[] {6, 2, 5, 5, 4, 5, 6, 3, 7, 6};

        //숫자를 만들기 위해 쓰는 후보 숫자들
        //이 숫자들만 쓰는 이유는 가장 작은 숫자를 만들어야 하는데
        //2,3,5를 만들기 위해 동일하게 5개의 성냥개비가 필요하고, 0,6,9는 6개의 성냥개비가 필요함
        //따라서 3,5와 6, 9는 고려할 필요가 없음. 어차피 가장 작은 숫자만 사용할 거니까.
        //숫자 채울 때도 맨 마지막 숫자만 채우기 때문에 0으로 시작하는 숫자 조건도 고려할 필요 없음.
        int[] numCandidates = new int[] {0, 1, 2, 4, 7, 8};

        long minNum, tempNum;

        //8까지 초기화해뒀으므로 9부터 채워나간다
        for (int matches = 9; matches <= 100; matches++)
        {
            minNum = Long.MAX_VALUE;

            //후보 숫자들을 마지막 숫자 자리에 하나씩 넣어서 숫자를 만들어보며 minNum 갱신
            for (int lastDigit : numCandidates)
            {
                //현재 성냥개비 숫자에서 후보 숫자를 만드는 데 드는 성냥개비 숫자를 뺌
                //그 성냥개비로 만들 수 있는 가장 작은 숫자를 dp 배열에서 찾는다
                tempNum = dpArr[matches - matchesOfNum[lastDigit]];
                //dp 배열에서 찾은 숫자와 후보 숫자를 concatenate
                tempNum = tempNum * 10 + lastDigit;
                //minNum을 갱신
                minNum = Math.min(minNum, tempNum);
            }

            dpArr[matches] = minNum;
        }

        for (int i = 0; i < minNumArr.length; i++)
            minNumArr[i] = dpArr[matchesArr[i]];
    }

    //dp에 필요한 초기값.
    //2~7 까지는 한자리 수만 만들 수 있는 경우
    //8은 두자리 수인데, 알고리즘상 dpArr[8]을 초기화 안하면
    //성냥개비가 1개 남아버려서 숫자를 못 만드는 경우가 생김. 예외처리 귀찮아서 직접 초기화함.
    static void initDpArr(long[] dpArr)
    {
        dpArr[2] = 1;
        dpArr[3] = 7;
        dpArr[4] = 4;
        dpArr[5] = 2;
        dpArr[6] = 6;
        dpArr[7] = 8;
        dpArr[8] = 10;
    }

    static void readMatches(int[] matchesArr, BufferedReader br) throws IOException
    {
        for (int i = 0; i < matchesArr.length; i++)
            matchesArr[i] = Integer.parseInt(br.readLine());
    }
}
