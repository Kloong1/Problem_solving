import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		String s1, s2;
		s1 = sc.nextLine();
		s2 = sc.nextLine();

		int[][] maxlens = new int[s1.length() + 1][s2.length() + 1];
		int maxlen = 0;

		for (int i = 0; i < s1.length(); i++)
		{
			for (int j = 0; j < s2.length(); j++)
			{
				if (s1.charAt(i) == s2.charAt(j))
				{
					maxlens[i + 1][j + 1] = maxlens[i][j] + 1;
					maxlen = Math.max(maxlen, maxlens[i + 1][j + 1]);
				}
			}
		}

		System.out.println(maxlen);
	}
}
