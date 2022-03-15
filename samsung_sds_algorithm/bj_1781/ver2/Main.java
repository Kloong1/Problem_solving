import java.util.*;
import java.io.*;

public class Main
{
	static BufferedReader br;

	public static void main(String[] args) throws Exception
	{
		br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		ArrayList<Problem> pList = new ArrayList<>(N);
		inputProblems(pList, N);

		Collections.sort(pList);

		System.out.println(solveProblemsGreedy(pList));
	}

	static void inputProblems(ArrayList<Problem> pList, int N) throws Exception
	{
		StringTokenizer st = null;
		int deadline, ramens;

		for (int i = 0; i < N; i++)
		{
			st = new StringTokenizer(br.readLine());
			deadline = Integer.parseInt(st.nextToken());
			ramens = Integer.parseInt(st.nextToken());

			pList.add(new Problem(deadline, ramens));
		}
	}

	static int solveProblemsGreedy(ArrayList<Problem> pList)
	{
		int maxDeadline = pList.get(pList.size() - 1).deadline;
		PriorityQueue<Integer> ramenPQ = new PriorityQueue<>(maxDeadline + 1);

		int curDeadline;
		for (Problem p : pList)
		{
			curDeadline = p.deadline;

			ramenPQ.offer(p.ramens);

			if (ramenPQ.size() > curDeadline)
				ramenPQ.poll();
		}

		int ramens = 0;
		while (!ramenPQ.isEmpty())
			ramens += ramenPQ.poll();

		return ramens;
	}
}

class Problem implements Comparable<Problem>
{
	int deadline;
	int ramens;

	Problem(int deadline, int ramens)
	{
		this.deadline = deadline;
		this.ramens = ramens;
	}

	public int compareTo(Problem other)
	{
		if (this.deadline > other.deadline)
			return 1;

		if (this.deadline == other.deadline)
			return 0;

		return -1;
	}
}
