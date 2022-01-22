import java.io.*;
import java.util.*;

/* https://kloong.tistory.com/entry/백준-다리-만들기-2146-Java */

public class Main
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int len = Integer.parseInt(br.readLine());

        char[][] map = new char[len][len];

        //입력받은 map을 char[][] 형태로 공백 없이 저장
        readMap(map, br);

        //모든 섬의 육지 좌표를 저장하는 list
        //각각의 ArrayList<Ground>에는 한 섬의 육지의 좌표가 저장되어 있음
        ArrayList<ArrayList<Ground>> islandList = new ArrayList<>();

        //BFS를 통해 모든 섬의 육지 좌표를 저장함.
        findAllIslands(map, islandList);

        //모든 가능한 두개의 섬의 조합을 선택해서
        //각 조합마다 최소 다리 길이를 측정하고, 모든 조합에 대해서 최소값을 반환하는 함수
        System.out.println(getMinBridgeLength(islandList));
    }

    static void findAllIslands(char[][] map, ArrayList<ArrayList<Ground>> islandList)
    {
        //bfs에 쓰일 visit 배열
        boolean[][] visit = new boolean[map.length][map.length];

        //map의 모든 공간을 차례로 탐색
        for (int row = 0; row < map.length; row++)
        {
            for (int col = 0; col < map.length; col++)
            {
                if (map[row][col] != '1' || visit[row][col])
                    continue;

                //바다가 아니고, 방문한 적이 없는 육지를 만나면 bfs 시작
                //이 육지를 시작점으로 bfs를 하므로, 이 육지에 대한 객체를 넘겨줌
                //bfs를 해서 얻어낸 섬의 모든 육지를 ArrayList<Ground> 형태로 반환하는 함수임
                islandList.add(getGroundListOfIsland(map, new Ground(row, col), visit));
            }
        }
    }

    static ArrayList<Ground> getGroundListOfIsland(char[][] map, Ground startGround, boolean[][] visit)
    {
        //반환해줄 함수. 섬의 모든 육지 좌표를 저장한다. 시작이 될 육지를 미리 넣어줌
        ArrayList<Ground> groundList = new ArrayList<>();
        groundList.add(startGround);

        //각 육지의 상하좌우를 탐색하기 편하게 하기 위한 방향 배열
        int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        //bfs를 위한 queue와 queue의 초기화
        Queue<Ground> bfsQueue = new LinkedList<>();
        bfsQueue.add(startGround);
        visit[startGround.row][startGround.col] = true;

        //bfs
        Ground pivot, neighborGround;
        int neighborRow, neighborCol;
        while (!bfsQueue.isEmpty())
        {
            pivot = bfsQueue.poll();

            for (int[] dir : dirs)
            {
                neighborRow = pivot.row + dir[0];
                neighborCol = pivot.col + dir[1];

                //pill한 육지와 이웃한 육지가 map 안에 있는지, 바다가 아닌지, 방문한적 없는지 체크
                if (!isInMap(neighborRow, neighborCol, map.length) ||
                        map[neighborRow][neighborCol] != '1' ||
                        visit[neighborRow][neighborCol])
                    continue;

                visit[neighborRow][neighborCol] = true;

                neighborGround = new Ground(neighborRow, neighborCol);
                bfsQueue.add(neighborGround);

                //반환해 줄 arrayList에도 add해줘야 한다.
                //바다에 인접한 육지에만 다리를 지을 수 있으므로,
                //바다에 인접한 육지만 arraylist에 add한다.
                if (canBuildBridge(neighborGround, map, dirs))
                    groundList.add(neighborGround);
            }
        }

        return groundList;
    }

    //islandList의 모든 섬에 대해서 최소 다리 길이를 반환하는 함수
    static int getMinBridgeLength(ArrayList<ArrayList<Ground>> islandList)
    {
        int minBridgeLen = Integer.MAX_VALUE;

        ArrayList<Ground> pivotIsland, otherIsland;

        for (int pivot = 0; pivot < islandList.size() - 1; pivot++)
        {
            pivotIsland = islandList.get(pivot);
            for (int idx = pivot + 1; idx < islandList.size(); idx++)
            {
                otherIsland = islandList.get(idx);

                //섬 두개를 뽑는 모든 조합에 대해서 다리 길이를 구함.
                //그 중 최소 길이를 저장한다.
                minBridgeLen = Math.min(minBridgeLen, getMinBridgeLength(pivotIsland, otherIsland));
            }
        }

        return minBridgeLen;
    }

    //각 섬에서 육지 하나씩을 뽑는 모든 조합에 대해서 맨해튼 거리를 구함
    //최소 맨해튼 거리 - 1 을 반환함. 다리를 지을 때는 육지 바로 옆에 지어야 하기 떄문.
    static int getMinBridgeLength(ArrayList<Ground> island1, ArrayList<Ground> island2)
    {
        int minBridgeLen = Integer.MAX_VALUE;

        for (Ground g1 : island1)
        {
            for (Ground g2: island2)
            {
                minBridgeLen = Math.min(minBridgeLen, g1.getManhattanDistTo(g2) - 1);
            }
        }

        return minBridgeLen;
    }

    static boolean isInMap(int row, int col, int len)
    {
        return row >= 0 && row < len && col >= 0 && col < len;
    }

    static boolean canBuildBridge(Ground g, char[][] map, int[][] dirs)
    {
        int neighborRow, neighborCol;

        for (int[] dir : dirs)
        {
            neighborRow = g.row + dir[0];
            neighborCol = g.col + dir[1];

            if (!isInMap(neighborRow, neighborCol, map.length))
                continue;

            //바다와 인접해 있는 육지인 경우를 찾는다.
            //섬 내부에 바다가 있어서 다리를 건설하지 못하지만 true를 반환할 수 있긴 한데
            //어차피 최소값을 구하기 때문에 이 경우는 최종 정답에서 걸러질 수밖에 없다
            if (map[neighborRow][neighborCol] == '0')
                return true;
        }

        return false;
    }

    static void readMap(char[][] map, BufferedReader br) throws IOException
    {
        String row;
        for (int i = 0; i < map.length; i++)
        {
            row = br.readLine();
            for (int j = 0; j < map.length; j++)
                map[i][j] = row.charAt(j * 2); //입력 주의. 사이사이 공백 있음.
        }
    }
}

class Ground
{
    int row;
    int col;

    Ground(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    int getManhattanDistTo(Ground other)
    {
        return Math.abs(other.row - this.row) + Math.abs(other.col - this.col);
    }
}