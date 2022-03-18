import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		String code;
		int K;
		code = sc.next();
		K = sc.nextInt();

		List<Integer> primes = get_primes(K);

		for (Integer prime : primes)
		{
			if (is_bad_code(code, prime))
			{
				System.out.printf("BAD %d\n", prime);
				return;
			}
		}

		System.out.println("GOOD");
	}

	static List<Integer> get_primes(int n)
	{
		boolean[] arr;
		List<Integer> primes;
		
		arr = new boolean[n];
		primes = new LinkedList<Integer>();

		for (int i = 2; i < n; i++)
		{
			if (arr[i] == false)
			{
				primes.add(i);
				for (int j = i; j < n; j += i)
					arr[j] = true;
			}
		}

		return (primes);
	}

	static boolean is_bad_code(String code, int divisor)
	{
		int remainder, len;

		len = code.length();
		remainder = 0;
		for (int i = 0; i < len; i++)
			remainder = (remainder * 10 + (code.charAt(i) - '0')) % divisor;

		if (remainder == 0)
			return (true);
		else
			return (false);
	}
}

class BigInt
{
	List<Integer> digits;

	public BigInt(String num)
	{
		int start, end;

		start = 0;
/*         while (start < num.length())
 *         {
 *
 *         } */
	}
}
