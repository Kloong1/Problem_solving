import java.util.Scanner;
import java.util.ArrayList;

public class Main
{
	static ArrayList<Integer> steps;
	int

	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		ArrayList<Integer> steps = new ArrayList<Integer>();

		int num = 0;
		while ((num = sc.nextInt()) != 0)
			steps.add(num);

		int max_strength = Math.min(
				get_dance_strength(steps, 1, steps.get(0), 0),
				get_dance_strength(steps, 1, 0, steps.get(0)));
	}

	static void get_dance_strength(ArrayList<Integer> arr, int idx, int left, int right)
	{
		if (idx == arr.size())
			return 0;

		//왼발
		

	}
}
