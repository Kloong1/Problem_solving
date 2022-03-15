import java.util.*;
import java.io.*;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N, K;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		Stuff[] stuffs = new Stuff[N + 1];

		int weight, value;
		for (int i = 1; i <= N; i++)
		{
			st = new StringTokenizer(br.readLine());
			weight = Integer.parseInt(st.nextToken());
			value = Integer.parseInt(st.nextToken());
			stuffs[i] = new Stuff(weight, value);
		}

		//0으로 초기화 되어있는 dp 배열 
		int[][] dp = new int[N + 1][K + 1];

		for (int i = 1; i <= N; i++)
		{
			//물건의 무게가 1 이상이므로 배낭 크기가 0일때는 고려하지 않아도 됨
			for (int weightLimit = 1; weightLimit <= K; weightLimit++)
			{
				if (stuffs[i].weight > weightLimit)
					dp[i][weightLimit] = dp[i - 1][weightLimit];
				else
					dp[i][weightLimit] =
						Math.max(dp[i - 1][weightLimit], dp[i - 1][weightLimit - stuffs[i].weight] + stuffs[i].value);
			}
		}

		System.out.println(dp[N][K]);
	}
}

class Stuff
{
	int weight;
	int value;

	Stuff(int weight, int value)
	{
		this.weight = weight;
		this.value = value;
	}
}
