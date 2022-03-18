import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main
{
	public static void main(String[] args) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int N;
		N = Integer.parseInt(br.readLine());
		
		String str;
		str = br.readLine();

		int maxLen = 0;
		int alpha = 0;
		int[] alphaCnt = new int[26];
		
		int left, right;
		left = 0;
		right = 0;

		char c;

		while (right < str.length())
		{
			c = str.charAt(right);

			if (alphaCnt[c - 'a'] == 0)
				alpha++;

			alphaCnt[c - 'a']++;

			if (alpha <= N)
				maxLen = Math.max(maxLen, right - left + 1);
			else
			{
				while (true)
				{
					c = str.charAt(left);

					alphaCnt[c - 'a']--;
					left++;

					if (alphaCnt[c - 'a'] == 0)
					{
						alpha--;
						break;
					}
				}
			}

			right++;
		}

		System.out.println(maxLen);

		br.close();
	}
}
