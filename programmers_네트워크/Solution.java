import java.util.*;

class Solution {
    public int solution(int numOfComputers, int[][] computers) {
        boolean[] visit = new boolean[numOfComputers];
        
        int networks = 0;
        for (int idx = 0; idx < numOfComputers; idx++)
        {
            if (!visit[idx])
            {
                networks++;
                bfs2FindNetwork(computers, idx, visit);
            }
        }
        
        return networks;
    }
    
    void bfs2FindNetwork(int[][] computers, int startIdx, boolean[] visit)
    {
        Queue<Integer> computerQueue = new LinkedList<Integer>();
        computerQueue.add(startIdx);
        visit[startIdx] = true;
        
        while (!computerQueue.isEmpty())
        {
            int pivot = computerQueue.poll();
            
            for (int idx = 0; idx < computers.length; idx++)
            {
                if (computers[pivot][idx] == 1 && !visit[idx])
                {
                    visit[idx] = true;
                    computerQueue.add(idx);
                }
            }
        }
    }
}
