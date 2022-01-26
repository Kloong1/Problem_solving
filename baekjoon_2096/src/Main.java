import java.io.*;

public class Main
{
    final static BufferedReader br = new BufferedReader(new InputStreamReader((System.in)));
    static int N;

    public static void main(String[] args) throws IOException
    {
        N = Integer.parseInt(br.readLine());

        //입력받은 게임 맵을 int 배열로 입력받음
        int[][] map = new int[N][3];
        readMap(map);

        //DP 배열 두개. 각각 최댓값 최솟값에 대한 DP 배열.
        int[][] maxDP = new int[N][3];
        int[][] minDP = new int[N][3];

        //DP 배열의 값을 채운다
        getMaxAndMinScores(map, maxDP, minDP);

        //DP 배열의 맨 마지막 줄의 값에서 최댓값, 최솟값을 얻어낸다
        int maxScore = Math.max(maxDP[N - 1][0], Math.max(maxDP[N - 1][1], maxDP[N - 1][2]));
        int minScore = Math.min(minDP[N - 1][0], Math.min(minDP[N - 1][1], minDP[N - 1][2]));

        //정답 출력
        System.out.printf("%d %d\n", maxScore, minScore);
    }

    static void getMaxAndMinScores(int[][] map, int[][] maxDP, int[][] minDP)
    {
        //minDP 초기화.
        minDP[0][0] = map[0][0];
        minDP[0][1] = map[0][1];
        minDP[0][2] = map[0][2];

        //maxDP 초기화.
        maxDP[0][0] = map[0][0];
        maxDP[0][1] = map[0][1];
        maxDP[0][2] = map[0][2];

        //1줄부터 N줄까지 DP배열 값을 채움
        //maxDP[K}[0] => K번째 줄 0번째 칸에 도달했을 때 얻을 수 있는 최댓값
        //DP 배열의 이전 줄에서 현재 칸으로 올 수 있는 경우의 값의 최대/최소값을 통해 현재 줄의 최대/최소값을 기록함
        for (int i = 1; i < N; i++)
        {
            minDP[i][0] = map[i][0] +  Math.min(minDP[i - 1][0], minDP[i - 1][1]); //이전 줄의 첫번째 두번째 칸에서 올 수 있음
            minDP[i][1] = map[i][1] +  Math.min(minDP[i - 1][0], Math.min(minDP[i - 1][1], minDP[i - 1][2])); //이전 줄의 모든 칸에서 올 수 있음
            minDP[i][2] = map[i][2] +  Math.min(minDP[i - 1][1], minDP[i - 1][2]); //이전 줄의 두번째 세번째 칸에서 올 수 있음

            maxDP[i][0] = map[i][0] +  Math.max(maxDP[i - 1][0], maxDP[i - 1][1]);
            maxDP[i][1] = map[i][1] +  Math.max(maxDP[i - 1][0], Math.max(maxDP[i - 1][1], maxDP[i - 1][2]));
            maxDP[i][2] = map[i][2] +  Math.max(maxDP[i - 1][1], maxDP[i - 1][2]);
        }
    }

    static void readMap(int[][] map) throws IOException
    {
        String line;
        for (int i = 0; i < N; i++)
        {
            line = br.readLine();
            for (int j = 0; j < 3; j++)
                map[i][j] = line.charAt(j * 2) - '0';
        }
    }
}
