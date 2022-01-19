import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        //각 node의 indegree를 저장할 map. key: node - value: indegree 형태.
        HashMap<Integer, Integer> indegreeMap = new HashMap<>();

        int caseNum = 1;
        boolean finish = false;

        //n개의 case에 대한 반복문
        while (true) {

            //(0,0)이 나올 떄 까지 (한 case가 끝날 때까지) indegree를 입력받는 함수
            //(-1, -1)을 입력받으면 true를 return함
            finish = getIndegree(indegreeMap, sc);

            if (finish)
                break;

            //indegree 정보를 가지고 tree인지 판단함
            if (isTree(indegreeMap))
                sb.append("Case " + caseNum + " is a tree.\n");
            else
                sb.append("Case " + caseNum + " is not a tree.\n");

            caseNum++;
            indegreeMap.clear();
        }

        System.out.print(sb);
    }

    static boolean isTree(HashMap<Integer, Integer> indegreeMap)
    {
        boolean hasRoot = false;

        //node가 없어도 tree이다
        if (indegreeMap.size() == 0)
            return true;

        //모든 node의 indegree를 확인한다.
        for (Integer indegree : indegreeMap.values())
        {
            //indegree가 0인 node를 발견하면 root이다.
            if (indegree == 0)
            {
                //이미 root를 발견했는데 또 발견한 경우. tree가 아니므로 false return
                if (hasRoot)
                    return false;
                else
                    hasRoot = true;
            }
            //indegree가 1보다 크면 tree가 아니다.
            //이 부분에서 유일한 경로에 대한 조건도 따질 수 있다.
            else if (indegree > 1)
                return false;
        }

        //root가 아예 존재하지 않는 경우도 있음 (예: 하나의 cycle만 있는 graph)
        //그 경우도 역시 tree가 아니다
        return hasRoot;
    }

    static boolean getIndegree(HashMap<Integer, Integer> indegreeMap, Scanner sc)
    {
        int startNode, endNode;
        boolean finish = false;

        while (true) {
            startNode = sc.nextInt();
            endNode = sc.nextInt();

            //case에 대한 입력이 끝난 경우
            if (startNode == 0 && endNode == 0)
                break;

            //전체 프로그램에 대한 입력이 끝난 경우
            if (startNode == -1 && endNode == -1)
            {
                finish = true;
                break;
            }

            //edge가 출발하는 node는 indegree가 증가하지 않음
            //단 node가 존재한다는 것을 알고 있어야 하므로 (예를 들어 root node의 경우)
            //indegree가 0이 되게 map에 저장
            indegreeMap.putIfAbsent(startNode, 0);

            //edge가 도달하는 node는 indegree가 1 증가해야 한다.
            indegreeMap.putIfAbsent(endNode, 0);
            indegreeMap.put(endNode, indegreeMap.get(endNode) + 1);
        }

        return finish;
    }
}
