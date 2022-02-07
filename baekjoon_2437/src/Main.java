import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        //무게추를 int 배열로 입력받음
        int[] weights = readWeights();

        Arrays.sort(weights);

        System.out.println(getUnmeasurableMinWeight(weights));
    }

    static int getUnmeasurableMinWeight(int[] weights)
    {
        int measurableMaxWeight = 0;

        for (int i = 0; i < weights.length; i++)
        {
            if (weights[i] > measurableMaxWeight + 1)
                break;

            measurableMaxWeight += weights[i];
        }

        //측정 불가능한 가장 작은 무게 = 측정 가능한 최대 무게 + 1
        return measurableMaxWeight + 1;
    }

    static int[] readWeights()
    {
        Scanner sc = new Scanner(System.in);
        StringTokenizer st;

        int[] weights = new int[Integer.parseInt(sc.nextLine())];

        st = new StringTokenizer(sc.nextLine());
        for (int i = 0; i < weights.length; i++)
            weights[i] = Integer.parseInt(st.nextToken());

        return weights;
    }
}
