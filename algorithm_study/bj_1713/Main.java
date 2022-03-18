import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args)
	{
		Scanner sc = new Scanner();

		List<Student> frame = new ArrayList<Student>(20);
		Student[] student_arr = new Student[101];

		int N;
		N = sc.nextInt();

		int number;
		while (sc.hasNextInt())
		{
			number = sc.nextInt();

			if (Student[number] == null)
				Student[number] = new Student(number, 1, 0, false);

			if (Student[number].isIn)
				Student[number].recommand += 1;
			else
			{
				if (frame.size() == )
			}
		}

		//list<student>로 frame
		//student[101] 배열
		//student[num]이 이미 frame에 있으면 rec++
		//없으면 추가
		//이 때 꽉차있으면 frame 정렬 후 젤 작은거와 교체
		//
	}
}

class Student implements Comparable<Student>
{
	int number;
	int recommand;
	int timestamp;
	boolean isIn;

	public Student(int number, int recommand, int timestamp, boolean isIn)
	{
		super();
		this.number = number;
		this.recommand = recommand;
		this.timestamp = timestamp;
		this.isIn = isIn;
	}

	//여기서 reccomand와 timestamp 비교
	public int compareTo(Student other)
	{
		int result = recommand - other.recommand;
		if (result == 0)
			return (timestamp - other.timestamp);
		return (result);
	}
}
