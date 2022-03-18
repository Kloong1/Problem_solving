import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N;
		N = sc.nextInt();

		long[] cake = new long[N + 2];
		for (int i = 1; i <= N; i++)
			cake[i] = sc.nextLong();
		
		//a가 하나 챙김 & 인접한 케잌 pq에 넣음
		//b가 가능한 것 중 가장 큰거 챙김 -> PQ에서 하나 뽑음
		//
		//cake class { 크기, 크기순서(인덱스)}
		//cake 정렬한 배열 하나, 그냥 cake 배열 하나
		//
	}
}
