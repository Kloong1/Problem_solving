import java.io.*;
import java.util.*;

public class Main
{
    static int numOfStations, numOfConnections, numOfHypertubes;
    static ArrayList<ArrayList<Integer>> stationAdjacentList;
    static ArrayList<ArrayList<Integer>> hypertubeAdjacentList;

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException
    {
        readNums();

        initStationAdjacentList();
        initHypertubeAdjacentList();

        readAdjacent();

        System.out.println(bfsFrom1ToN());
    }

    static int bfsFrom1ToN()
    {
        int minStationCnt = Integer.MAX_VALUE;

        boolean[] stationVisit = new boolean[numOfStations + 1];
        boolean[] hypertubeVisit = new boolean[numOfHypertubes + 1];

        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(new Node(1, true, 1));

        Node node;
        ArrayList<Integer> adjacentNodeIds;
        boolean[] visit;
        int nextStationCnt;

        while (!nodeQueue.isEmpty())
        {
            node = nodeQueue.poll();

            if (node.isStation && node.id == numOfStations)
            {
                minStationCnt = Math.min(minStationCnt, node.stationCnt);
                continue;
            }

            if (node.isStation)
            {
                adjacentNodeIds = stationAdjacentList.get(node.id);
                visit = hypertubeVisit;
                nextStationCnt = node.stationCnt;
            }
            else
            {
                adjacentNodeIds = hypertubeAdjacentList.get(node.id);
                visit = stationVisit;
                nextStationCnt = node.stationCnt + 1;
            }

            for (Integer adjacentNodeId : adjacentNodeIds)
            {
                if (visit[adjacentNodeId])
                    continue;

                visit[adjacentNodeId] = true;
                nodeQueue.add(new Node(adjacentNodeId, !node.isStation, nextStationCnt));
            }
        }

        if (minStationCnt == Integer.MAX_VALUE)
            return -1;

        return minStationCnt;
    }

    static void readAdjacent() throws IOException
    {
        StringTokenizer st;
        ArrayList<Integer> stationAdjacent, hypertubeAdjacent;
        int stationId;

        for (int hypertubeId = 1; hypertubeId <= numOfHypertubes; hypertubeId++)
        {
            hypertubeAdjacent = hypertubeAdjacentList.get(hypertubeId);
            st = new StringTokenizer(br.readLine());
            while (st.hasMoreTokens())
            {
                stationId = Integer.parseInt(st.nextToken());
                stationAdjacent = stationAdjacentList.get(stationId);

                hypertubeAdjacent.add(stationId);
                stationAdjacent.add(hypertubeId);
            }
        }
    }

    static void initStationAdjacentList()
    {
        stationAdjacentList = new ArrayList<>(numOfStations + 1);
        stationAdjacentList.add(null);
        for (int i = 1; i <= numOfStations; i++)
            stationAdjacentList.add(new ArrayList<>());
    }

    static void initHypertubeAdjacentList()
    {
        hypertubeAdjacentList = new ArrayList<>(numOfHypertubes + 1);
        hypertubeAdjacentList.add(null);
        for (int i = 1; i <= numOfHypertubes; i++)
            hypertubeAdjacentList.add(new ArrayList<>(numOfConnections));
    }

    static void readNums() throws IOException
    {
        StringTokenizer st = new StringTokenizer(br.readLine());
        numOfStations = Integer.parseInt(st.nextToken());
        numOfConnections = Integer.parseInt(st.nextToken());
        numOfHypertubes = Integer.parseInt(st.nextToken());
    }
}

class Node
{
    int id;
    boolean isStation;
    int stationCnt;

    public Node(int id, boolean isStation, int stationCnt)
    {
        this.id = id;
        this.isStation = isStation;
        this.stationCnt = stationCnt;
    }
}