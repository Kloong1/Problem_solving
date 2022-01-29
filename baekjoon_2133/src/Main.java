import java.util.Scanner;

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
