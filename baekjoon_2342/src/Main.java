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

        int[][] footLocStrength = new int[5][5];
        for (int[] row : footLocStrength)
            Arrays.fill(row ,Integer.MAX_VALUE);

        footLocStrength = getLastFootLocStrength(footLocStrength, inputList);

        int minStrength = Integer.MAX_VALUE;
        for (int[] row : footLocStrength)
            for (int strength : row)
                minStrength = Math.min(minStrength, strength);

        System.out.println(minStrength);
    }

    static int[][] getLastFootLocStrength(int[][] footLocStrength, ArrayList<Integer> inputList)
    {
        int startLoc = inputList.get(0);

        footLocStrength[startLoc][0] = 2;
        footLocStrength[0][startLoc] = 2;

        for (int i = 1; i < inputList.size(); i++)
            footLocStrength = getNextfootLocStrength(footLocStrength, inputList.get(i));

        return footLocStrength;
    }

    static int[][] getNextfootLocStrength(int[][] preFootLocStrength, int curLoc)
    {
        int[][] nextFootLocStrength = new int[5][5];
        for (int[] row : nextFootLocStrength)
            Arrays.fill(row, Integer.MAX_VALUE);

        for (int left = 0; left < 5; left++)
        {
            for (int right = 0; right < 5; right++)
            {
                if (preFootLocStrength[left][right] == Integer.MAX_VALUE)
                    continue;

                if (left == curLoc || right == curLoc)
                {
                    nextFootLocStrength[left][right] = Math.min(
                            preFootLocStrength[left][right] + 1, nextFootLocStrength[left][right]);
                }
                else
                {
                    nextFootLocStrength[curLoc][right] = Math.min(
                            preFootLocStrength[left][right] + getStrengthForMove(left, curLoc),
                            nextFootLocStrength[curLoc][right]);

                    nextFootLocStrength[left][curLoc] = Math.min(
                            preFootLocStrength[left][right] + getStrengthForMove(right, curLoc),
                            nextFootLocStrength[left][curLoc]);
                }
            }
        }

        return nextFootLocStrength;
    }

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