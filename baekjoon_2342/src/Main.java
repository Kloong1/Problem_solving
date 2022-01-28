import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        ArrayList<Integer> inputList = new ArrayList<>(100_000);
        readInputs(inputList);

        if (inputList.size() == 0)
        {
            System.out.println("0");
            return;
        }

        //(0,1), (0,2), ... , (1,0), (1,2), ... , (4,3) 이렇게 가능한 모든 발의 위치에 대한 배열임
        //footLocStrength[1][2] => 왼발이 1, 오른발이 2에 있는 경우에 대해 현재까지 든 힘의 최솟값임.
        int[][] footLocStrength = new int[5][5];
        for (int[] row : footLocStrength)
            Arrays.fill(row ,Integer.MAX_VALUE);

        footLocStrength = getLastFootLocStrength(footLocStrength, inputList);

        //배열에서 최솟값을 찾으면 정답이다.
        int minStrength = Integer.MAX_VALUE;
        for (int[] row : footLocStrength)
            for (int strength : row)
                minStrength = Math.min(minStrength, strength);

        System.out.println(minStrength);
    }

    static int[][] getLastFootLocStrength(int[][] footLocStrength, ArrayList<Integer> inputList)
    {
        int startLoc = inputList.get(0);

        //초기화. 초기 발의 위치 (0,0)에서 왼발을 움직이는 경우와 오른발을 움직이는 경우.
        //0에서 움직이는 경우 2의 힘이 든다.
        footLocStrength[startLoc][0] = 2;
        footLocStrength[0][startLoc] = 2;

        //입력 순서대로 함수 호출
        //함수에서 반환한 배열로 footLocStrength가 계속해서 업데이트된다.
        for (int i = 1; i < inputList.size(); i++)
            footLocStrength = getNextfootLocStrength(footLocStrength, inputList.get(i));

        return footLocStrength;
    }

    //이전 턴에 가능했던 모든 발의 위치와 그 경우에서 최소힘에 대한 정보가 있는 배열 -> preFootLocStrength
    //curLoc은 이제 움직여야 할 발의 위치
    //이전 턴에 가능했던 모든 발의 위치를 preFootLocStrength에서 탐색하며, 해당 위치에서 한 발을 curLoc으로 움직이는 모든 경우에 대해
    //최소 힘을 구해서 nextFootLocStrength에 저장한 뒤 반환함
    static int[][] getNextfootLocStrength(int[][] preFootLocStrength, int curLoc)
    {
        //curLoc으로 발을 움직인 모든 경우에 대한 최소힘을 저장할 배열. 이 배열을 반환한다.
        int[][] nextFootLocStrength = new int[5][5];
        for (int[] row : nextFootLocStrength)
            Arrays.fill(row, Integer.MAX_VALUE);

        for (int left = 0; left < 5; left++)
        {
            for (int right = 0; right < 5; right++)
            {
                //이전 턴에서 불가능했던 발의 위치의 경우임.
                if (preFootLocStrength[left][right] == Integer.MAX_VALUE)
                    continue;

                //같은 발로 다시 밟아야 하는 경우
                if (left == curLoc || right == curLoc)
                {
                    nextFootLocStrength[left][right] = Math.min(
                            preFootLocStrength[left][right] + 1, nextFootLocStrength[left][right]);
                }
                else
                {
                    //왼발을 움직이는 경우
                    nextFootLocStrength[curLoc][right] = Math.min(
                            preFootLocStrength[left][right] + getStrengthForMove(left, curLoc),
                            nextFootLocStrength[curLoc][right]);

                    //오른발을 움직이는 경우
                    nextFootLocStrength[left][curLoc] = Math.min(
                            preFootLocStrength[left][right] + getStrengthForMove(right, curLoc),
                            nextFootLocStrength[left][curLoc]);
                }
            }
        }

        return nextFootLocStrength;
    }

    //발을 움직이는 데 드는 힘을 반환해주는 함수
    static int getStrengthForMove(int startLoc, int endLoc)
    {
        if (startLoc == 0)
            return 2;

        if (Math.abs(endLoc - startLoc) == 2)
            return 4;

        return 3;
    }

    static void readInputs(ArrayList<Integer> inputList)
    {
        Scanner sc = new Scanner(System.in);
        String line = sc.nextLine();
        int len = line.length();

        for (int i = 0; i < len; i += 2)
        {
            if (line.charAt(i) == '0')
                break;

            inputList.add(line.charAt(i) - '0');
        }
    }
}