import java.util.Scanner;

/*
점화식 자체가 복잡하다고 볼 수는 없지만
경우의 수를 타일을 그려보며 하나하나 생각해야 하는 문제.
구글링해서 천천히 생각해보고 다시 도전해볼 것.
 */

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);

        int wallWidth = sc.nextInt();

        System.out.println(countTileCase(wallWidth));
    }

    static int countTileCase(int wallWidth)
    {
        if (wallWidth % 2 == 1)
            return 0;

        int cnt = 0;



        return cnt;
    }
}
