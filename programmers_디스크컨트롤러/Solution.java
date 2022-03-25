import java.util.*;

class Main
{
	public static void main(String[] args)
	{
		Solution s = new Solution();

		int[][] job = {{0, 3}, {1, 9}, {2,6}};
		System.out.println(s.solution(job));
	}
}

class Solution {
    public int solution(int[][] job) {
        Job[] jobs = initJobs(job);
        
        int jobIdx = 0;
        int curTime = 0;
        int timeSum = 0;
        
        PriorityQueue<Job> jobQueue = new PriorityQueue<>((Job j1, Job j2) -> Integer.compare(j1.procTime, j2.procTime));
        
        while (jobIdx < jobs.length || !jobQueue.isEmpty())
        {
            while (jobIdx < jobs.length && jobs[jobIdx].reqTime <= curTime)
                jobQueue.add(jobs[jobIdx++]);
            
            if (jobQueue.isEmpty() && jobIdx < jobs.length)
            {
                curTime = jobs[jobIdx].reqTime;
                while (jobIdx < jobs.length && jobs[jobIdx].reqTime <= curTime)
                    jobQueue.add(jobs[jobIdx++]);
            }
            
            Job j = jobQueue.poll();
            curTime += j.procTime;
            timeSum += curTime - j.reqTime;
        }
        
        return timeSum / jobs.length;
    }
    
    Job[] initJobs(int[][] job)
    {
        Job[] jobs = new Job[job.length];
        for (int i = 0; i < job.length; i++)
            jobs[i] = new Job(job[i][0], job[i][1]);
        
        Arrays.sort(jobs,
                   (Job j1, Job j2) -> Integer.compare(j1.reqTime, j2.reqTime));
        
        return jobs;
    }
}

class Job
{
    int reqTime;
    int procTime;
    
    Job (int reqTime, int procTime)
    {
        this.reqTime = reqTime;
        this.procTime = procTime;
    }
}
