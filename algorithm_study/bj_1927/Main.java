import java.util.Scanner;

public class Main
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);

		MinHeap mh = new MinHeap(100000);
		int[] result = new int[100000];
		
		int N;
		N = sc.nextInt();

		int x;
		int j = 0;
		for (int i = 0; i < N; i++)
		{
			x = sc.nextInt();
			if (x == 0)
				result[j++] = mh.pop();
			else
				mh.push(x);
		}

		for (int i = 0; i < j; i++)
			System.out.println(result[i]);
	}
}

class MinHeap
{
	int last;
	int capacity;
	int[] heap;

	public MinHeap(int capacity)
	{
		this.last = 0;
		this.capacity = capacity;
		this.heap = new int[capacity + 1];
	}

	public boolean isEmpty()
	{
		return (last < 1);
	}

	public boolean isFull()
	{
		return (last >= capacity);
	}

	public boolean push(int element)
	{
		if (isFull())
			return (false);

		heap[++last] = element;

		int parent, child;

		parent = last / 2;
		child = last;
		while (parent >= 1 && heap[parent] > heap[child])
		{
			swap(heap, parent, child);
			child = parent;
			parent = child / 2;
		}

		return (true);
	}

	public int pop()
	{
		int min_element;

		if (isEmpty())
			return (0);

		min_element = heap[1];
		heap[1] = heap[last--];

		int parent, left, right;
		int min_idx;

		parent = 1;
		while (true)
		{
			left = parent * 2;
			right = parent * 2 + 1;

			if (left <= last && right <= last)
				min_idx = get_min_idx(heap, left, right);
			else if (left <= last)
				min_idx = left;
			else
				break;

			if (heap[min_idx] < heap[parent])
				swap(heap, parent, min_idx);
			else
				break;

			parent = min_idx;
		}

		return (min_element);
	}

	private void swap(int[] heap, int i, int j)
	{
		int temp;

		temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}

	private int get_min_idx(int[] heap, int i, int j)
	{
		return (heap[i] < heap[j] ? i : j);
	}

	public void print()
	{
		for (int i = 1; i <= last; i++)
			System.out.printf("%d ", heap[i]);
		System.out.println();
	}
}
