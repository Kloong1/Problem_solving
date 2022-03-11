import java.io.*;
import java.util.*;

public class Main
{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        int numOfAssignments= Integer.parseInt(br.readLine());

        Assignment[] assignments = new Assignment[numOfAssignments];
        readAssignments(assignments);

        Arrays.sort(assignments);

        System.out.println(getMaxScore(assignments));
    }

    static int getMaxScore(Assignment[] assignments)
    {
        PriorityQueue<Integer> scoreQueue = new PriorityQueue<>(assignments[assignments.length - 1].timeLimit);

        for (Assignment assignment : assignments)
        {
            scoreQueue.add(assignment.score);

            if (scoreQueue.size() > assignment.timeLimit)
                scoreQueue.poll();
        }

        int maxScore = 0;
        while (!scoreQueue.isEmpty())
            maxScore += scoreQueue.poll();

        return maxScore;
    }

    static void readAssignments(Assignment[] assignments) throws IOException
    {
        StringTokenizer st;

        for (int i = 0; i < assignments.length; i++)
        {
            st = new StringTokenizer(br.readLine());
            assignments[i] = new Assignment(
                    Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
    }
}

class Assignment implements Comparable<Assignment>
{
    int timeLimit;
    int score;

    public Assignment(int timeLimit, int score)
    {
        this.timeLimit = timeLimit;
        this.score = score;
    }

    @Override
    public int compareTo(Assignment o)
    {
        return Integer.compare(timeLimit, o.timeLimit);
    }
}