import java.util.Arrays;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        int digits = sc.nextInt();

        if (digits == 1)
        {
            System.out.println(0);
            return;
        }

        if (digits == 2)
        {
            System.out.println(1);
            return;
        }

        long[] combArr = fillCombinationArr(digits);

        System.out.println(Arrays.toString(combArr));

        System.out.println(countMultiplesOf15(digits, combArr));
    }

    static long[] fillCombinationArr(int digits)
    {
        digits--;

        long[] combArr = new long[digits + 1];

        combArr[0] = 1;
        combArr[1] = digits;

        int maxIdx = digits / 2;
        for (int i = 2; i <= maxIdx; i++)
            combArr[i] = (combArr[i - 1] * (digits - i + 1) / i) % (long)1_000_000_007;

        for (int i = maxIdx + 1; i < combArr.length; i++)
            combArr[i] = combArr[digits - i];

        return combArr;
    }

    static long countMultiplesOf15(int digits, long[] combArr)
    {
        long cnt = 0;

        digits--;

        for (int numOf5 = 0; numOf5 <= digits; numOf5++)
        {
            int sumOfDigits = (numOf5 + 1) * 5 + (digits - numOf5);

            if (sumOfDigits % 3 == 0)
            {
                cnt += combArr[numOf5];
                cnt %= 1_000_000_007;
            }
//                cnt = (cnt + combArr[numOf5]) % (long)1_000_000_007;
        }

        return cnt;
    }
}
