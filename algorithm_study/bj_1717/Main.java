import java.util.Scanner;
import java.util.LinkedList;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N, M;
		N = sc.nextInt();
		M = sc.nextInt();

		DisjointSet ds = new DisjointSet(N);

		LinkedList<String> result = new LinkedList<String>();

		int cmd, a, b;
		for (int i = 0; i < M; i++)
		{
			cmd = sc.nextInt();
			a = sc.nextInt();
			b = sc.nextInt();
			if (cmd == 0)
				ds.union(a, b);
			else
			{
				if (ds.find_parent(a) == ds.find_parent(b))
					result.add("YES");
				else
					result.add("NO");
			}
		}

		for (String r : result)
			System.out.println(r);
	}
}

class DisjointSet
{
	int[] parr;

	public DisjointSet(int n)
	{
		parr = new int[n + 1];
		for (int i = 0; i < parr.length; i++)
			parr[i] = i;
	}

	public int find_parent(int element)
	{
		if (parr[element] == element)
			return element;
		parr[element] = find_parent(parr[element]);
		return parr[element];
	}

	public void union(int e1, int e2)
	{
		int pe1 = find_parent(e1);
		int pe2 = find_parent(e2);

		parr[pe2] = pe1;
	}

	public void print()
	{
		System.out.println("DisjointSet");
		for (int i = 0; i < parr.length; i++)
			System.out.printf("%d ", parr[i]);
		System.out.println();
	}
}
