import java.util.Scanner;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		int N, K;
		N = sc.nextInt();
		K = sc.nextInt();
		
		LinkedList<Gem> gems = new LinkedList<Gem>();

		for (int i = 0; i < N; i++)
			gems.add(new Gem(sc.nextLong(), sc.nextLong()));

		LinkedList<Long> bags = new LinkedList<Long>();

		for (int i = 0; i< K; i++)
			bags.add(sc.nextLong());

		Collections.sort(gems, Comparator.comparingLong(Gem::get_mass));

		Collections.sort(bags);

		PriorityQueue<Gem> pq = new PriorityQueue<Gem>(Comparator.comparingLong(Gem::get_price).reversed());

		long bag;
		long price = 0;
		while (!(bags.isEmpty()))
		{
			bag = bags.pop();
			/* System.out.println(bag); */

			while (!(gems.isEmpty()) && gems.peek().mass <= bag)
				pq.offer(gems.pop());

			if (!(pq.isEmpty()))
				price += pq.poll().price;
		}

		System.out.println(price);
	}
}

class Gem
{
	long mass;
	long price;

	public Gem(long mass, long price)
	{
		this.mass = mass;
		this.price = price;
	}

	public long get_mass()
	{
		return (mass);
	}

	public long get_price()
	{
		return (price);
	}
}
