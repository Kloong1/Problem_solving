class Solution {
    
    char[] friends = {'A', 'C', 'F', 'J', 'M', 'N', 'R', 'T'};
    Constraint[] constraints;
    
    public int solution(int n, String[] data) {
        
        char[] line = new char[8];
        boolean[] inLine = new boolean[8];
        int[] lineLoc = new int['T' + 1];
        
        initConstraints(data);
        
        return dfs2CountCases(line, inLine, lineLoc, 0);
    }
    
    int dfs2CountCases(char[] line, boolean[] inLine, int[] lineLoc, int lineIdx)
    {
        if (lineIdx == 8)
            return checkConstraints(line, lineLoc);
        
        int cnt = 0;
        
        for (int i = 0; i < inLine.length; i++)
        {
            if (!inLine[i])
            {
                inLine[i] = true;
               line[lineIdx] = friends[i];
                lineLoc[friends[i]] = lineIdx;
                
                cnt += dfs2CountCases(line, inLine, lineLoc, lineIdx + 1);
                
                inLine[i] = false;
            }
        }
        
        return cnt;
    }
    
    int checkConstraints(char[] line, int[] lineLoc)
    {
        int dist;
        for (Constraint c : constraints)
        {
            dist = Math.abs(lineLoc[c.friend1] - lineLoc[c.friend2]) - 1;
            
            if (c.type == '=')
            {
                if (dist != c.dist)
                    return 0;
            }
            else if (c.type == '<')
            {
                if (dist >= c.dist)
                    return 0;
            }
            else
            {
                if (dist <= c.dist)
                    return 0;
            }
        }
        
        return 1;
    }
    
    void initConstraints(String[] data)
    {
        constraints = new Constraint[data.length];
        
        for (int i = 0; i < data.length; i++)
            constraints[i] = new Constraint(data[i]);
    }
    
}

class Constraint
{
    char friend1;
    char friend2;
    char type;
    int dist;
    
    Constraint (String str)
    {
        friend1 = str.charAt(0);
        friend2 = str.charAt(2);
        type = str.charAt(3);
        dist = str.charAt(4) - '0';
    }
} 
