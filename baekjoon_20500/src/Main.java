import java.util.Scanner;

/* https://kloong.tistory.com/entry/Ezreal-여눈부터-가네-ㅈㅈ-20500-Java */

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        int digits = sc.nextInt();

        //자리수 1부터 자리수 digits 까지의 값을 기록할 dp 배열
        //dp[K][0] -> 자리수 K인 1과 5로 이루어진 숫자 중
        //일의 자리가 5이고 모든 자리수의 합을 3으로 나눈 나머지가 0인 수의 개수
        int[][] dp = new int[digits + 1][3];

        /* 한 자리 숫자에는 15의 배수가 존재하지 않음.
         * 그리고 어차피 dp의 초기값이라 배열을 직접 초기화 해줘야 하는데 그냥 답 출력하는게 빠름.
         * 그래서 두 자리 숫자까지는 그냥 답 출력해버리고, 그 이후부터만 dp로 답 구해서 출력함*/
        if (digits == 1) {
            System.out.println(0);
            return;
        }

        if (digits == 2) {
            System.out.println(1);
            return;
        }

        /* 1과 5로 이루어진 두자리수 숫자는 원래 15, 51, 55가 있지만
         * 15의 배수는 일의 자리가 5여야 하므로 51의 경우를 반드시 제외해 줘야 한다!
         * 따라서 두자리수에 대한 dp 배열을 아래와 같이 초기화 해 줘야 함
         * 한자리수에 대한 dp 배열부터 초기화하지 않는 이유는 한자리수 dp 배열을 초기화 한다 치면
         * dp 배열의 의미에 따라 dp[1][0] = dp[1][1] = dp[1][2] = 0 이어야 하는데 그러면
         * dp[2][0 ~ 2]가 점화식에 의해 제대로 초기화 될 수가 없기 때문! */
        dp[2][0] = 1;
        dp[2][1] = 1;
        dp[2][2] = 0;

        //점화식을 통한 dp 초기화
        for (int i = 3; i <= digits; i++) {
            dp[i][0] = (dp[i - 1][1] + dp[i - 1][2]) % 1_000_000_007;
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % 1_000_000_007;
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % 1_000_000_007;
        }

        //문제에서 요구하는 정답은 N 자리수 숫자에서 15의 배수이므로
        //3으로 나눴을 때 나머지가 0인 숫자의 개수를 출력해야 한다
        System.out.println(dp[digits][0]);
    }
}