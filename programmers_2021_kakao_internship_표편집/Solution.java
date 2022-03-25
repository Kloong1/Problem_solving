import java.util.*;

class Solution {
    
    int lastRowIdx;
    
    public String solution(int numOfRows, int startRow, String[] cmd) {
        
		//U와 D 명령어가 연속적으로 있는 경우 하나로 압축해준다.
		//동시에 String[] 형태의 명령어를 ArrayList<Command>로 바꿔준다.
        ArrayList<Command> commands = compressCmd(cmd);
        
		//문제에 언급된 것처럼 "이름"과 같은 실제 데이터가 존재하지 않기 때문에
		//행의 삭제 여부만 알면 됨. 따라서 행의 삭제 여부를 true/false로 표현가능한
		//boolean 배열을 사용했다.
        boolean[] rows = new boolean[numOfRows];
        Arrays.fill(rows, true); //true = 삭제되지 않음
        
		//삭제된 행의 index를 push할 stack
        Stack<Integer> deletedRows = new Stack<>();
        
        int rowIdx = startRow;

		//맨 마지막 행을 삭제하는 경우에는 커서가 위로 올라가기 때문에
		//지워지지 않은 맨 마지막 행의 index를 저장하고 있어야 한다.
        lastRowIdx = rows.length - 1;
        
        for (Command command : commands)
        {
            if (command.type == 'C')
                rowIdx = deleteRow(rows, rowIdx, deletedRows);
            else if (command.type == 'Z')
                undoDeleteRow(rows, deletedRows);
            else if (command.type == 'U')
                rowIdx = moveUpCursor(rows, rowIdx, command.val);
            else
                rowIdx = moveDownCursor(rows, rowIdx, command.val);
        }
        
        StringBuilder answer = new StringBuilder();
        
        for (boolean row : rows)
        {
            if (row)
                answer.append('O');
            else
                answer.append('X');
        }
        
        return answer.toString();
    }
    
    int moveUpCursor(boolean[] rows, int rowIdx, int val)
    {
        int moveCnt = 0;
        
		//삭제된 행은 이동 횟수에서 제외해야한다
        while (moveCnt < val)
            if (rows[--rowIdx])
                moveCnt++;
        
        return rowIdx;
    }
    
    int moveDownCursor(boolean[] rows, int rowIdx, int val)
    {
        int moveCnt = 0;
        
		//삭제된 행은 이동 횟수에서 제외해야한다
        while (moveCnt < val)
            if (rows[++rowIdx])
                moveCnt++;
        
        return rowIdx;
    }
    
    void undoDeleteRow(boolean[] rows, Stack<Integer> deletedRows)
    {
        int deletedRowIdx = deletedRows.pop(); //가장 최근에 지워진 row의 index
        
        rows[deletedRowIdx] = true;
        
		//복구된 row가 가장 마지막 row라면 lastRowIdx가 갱신된다.
        lastRowIdx = Math.max(lastRowIdx, deletedRowIdx);
    }
    
    int deleteRow(boolean[] rows, int rowIdx, Stack<Integer> deletedRows)
    {
        rows[rowIdx] = false;
        deletedRows.push(rowIdx); //'Z' 명령어를 위해 stack에 넣어준다
        
		//지운 row가 마지막 row인 경우 index가 삭제되지 않은 바로 위 row를 가리킴
        if (rowIdx == lastRowIdx)
        {
            while (!rows[rowIdx])
                rowIdx--;
            lastRowIdx = rowIdx; //마지막 row가 지워졌으므로 갱신
        }
		//지운 row가 마지막 row가 아닌 경우 index는 삭제되지 않은 바로 아래 row를 가리킴
        else
            while (!rows[rowIdx])
                rowIdx++;
        
        return rowIdx;
    }
    
    ArrayList<Command> compressCmd(String[] cmd)
    {
        ArrayList<Command> commands = new ArrayList<>();
        
        int idx = 0;
        while (idx < cmd.length)
        {
            if (cmd[idx].charAt(0) == 'C' || cmd[idx].charAt(0) == 'Z')
            {
                commands.add(new Command(cmd[idx].charAt(0)));
                idx++;
            }
			//연속된 U와 D를 압축. 이 작업 안해주면 효율성 테스트 뒷부분 시간초과 뜸.
            else
                idx = CompressSequentialUAndD(commands, cmd, idx);
        }
        
        return commands;
    }
    
    int CompressSequentialUAndD(ArrayList<Command> commands, String[] cmd, int idx)
    {
        int val = 0;
        char type;
        
        while (idx < cmd.length)
        {
            type = cmd[idx].charAt(0);
            
            if (type == 'U')
                val += Integer.parseInt(cmd[idx].substring(2));
            else if (type == 'D')
                val -= Integer.parseInt(cmd[idx].substring(2));
            else
                break;
            
            idx++;
        }
        
        if (val > 0)
            commands.add(new Command('U', val));
        else if (val < 0)
            commands.add(new Command('D', -val));
        
        return idx;
    }
}

class Command
{
    char type;
    int val;
    
    Command (char type, int val)
    {
        this.type = type;
        this.val = val;
    }
    
    Command (char type)
    {
        this.type = type;
    }
}
