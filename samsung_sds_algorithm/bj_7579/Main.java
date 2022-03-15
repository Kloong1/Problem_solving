import java.util.*;
import java.io.*;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N, M;
		st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		App[] apps = new App[N + 1];
		
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++)
			apps[i] = new App(Integer.parseInt(st.nextToken()));

		int costsum = 0;
		st = new StringTokenizer(br.readLine());
		for (int i = 1; i <= N; i++)
		{
			apps[i].cost = Integer.parseInt(st.nextToken());
			costsum += apps[i].cost;
		}

		long[][] dp = new long[N + 1][costsum + 1];

		for (int i = 1; i <= N; i++)
		{
			for (int costLimit = 0; costLimit <= costsum; costLimit++)
			{
				if (apps[i].cost > costLimit)
					dp[i][costLimit] = dp[i - 1][costLimit];
				else
					dp[i][costLimit] = Math.max(dp[i - 1][costLimit], dp[i - 1][costLimit - apps[i].cost] + apps[i].mem);
			}
		}

		int mincost;
		for (mincost = 0; mincost <= costsum; mincost++)
		{
			if (dp[N][mincost] >= M)
				break;
		}
		System.out.println(mincost);
	}
}

class App
{
	int mem;
	int cost;

	App(int mem)
	{
		this.mem = mem;
	}
}
