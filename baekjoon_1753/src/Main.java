import java.io.*;
import java.util.*;

public class Main{
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;

        int numOfVertex, numOfEdge;
        st = new StringTokenizer(br.readLine());

        numOfVertex = Integer.parseInt(st.nextToken());
        numOfEdge = Integer.parseInt(st.nextToken());

        int startVertex;
        startVertex = Integer.parseInt(br.readLine());

        Vertex[] graph = new Vertex[numOfVertex + 1];

        for (int i = 1; i <= numOfVertex; i++)
            graph[i] = new Vertex(i, Integer.MAX_VALUE);

        //edges.get(i)는 i번째 Vertex에서 출발하는 Edge의 ArrayList
        ArrayList<ArrayList<Edge>> edges = new ArrayList<>(numOfVertex + 1);

        for (int i = 0; i <= numOfVertex; i++)
            edges.add(new ArrayList<>());

        //모든 edge와 cost에 대한 정보 입력
        addEdges(edges, numOfEdge, br);

        PriorityQueue<Vertex> pq = new PriorityQueue<>();

        //시작 정점의 cost를 0으로 초기화 한 후 PQ에 추가
        graph[startVertex].cost = 0;
        pq.add(graph[startVertex]);

        //다익스트라 알고리즘
        Vertex startV, endV;
        int newCost;
        while (!pq.isEmpty())
        {
            startV = pq.poll();

            //밑에서 PQ에 new Vertex를 해서 집어넣기 때문에 vertex를 poll 했을 때
            //poll한 vertex가 cost가 갱신 되기 이전에 PQ에 들어간 것이라면 그냥 무시한다.
            if (startV.cost > graph[startV.index].cost)
                continue;

            for (Edge e : edges.get(startV.index))
            {
                endV = graph[e.end];
                newCost = startV.cost + e.cost;

                //중복 방문을 막기 위해 cost가 갱신되는 vertex에 대해서만 pq에 추가해주면 된다
                if (endV.cost > newCost)
                {
                    endV.cost = newCost;
                    pq.add(new Vertex(endV.index, endV.cost));
                    //여기서 new Vertex를 안하고 endV를 그대로 넣어주면 나중에 endV의 cost를 갱신하는 과정에서
                    //이미 PriorityQueue에 들어가 있는 vertex의 cost가 변하는 경우가 생긴다.
                    //PQ에서는 이런 경우는 undefined이기 때문에 new Vertex를 해줬다.
                    //물론 이렇게 안해도 정답처리가 되긴 했다. 왜 되는건지는 잘 ㅎㅎ...
                }
            }
        }

        //정답 출력
        for (int i = 1; i <= numOfVertex; i++)
        {
            if (graph[i].cost == Integer.MAX_VALUE)
                bw.write("INF\n");
            else
                bw.write(graph[i].cost + "\n");
        }

        bw.flush();
        bw.close();
        br.close();
    }

    //Edge 입력받는 함수.
    static void addEdges(ArrayList<ArrayList<Edge>> edges, int numOfEdge, BufferedReader br) throws IOException
    {
        StringTokenizer st;
        int v1, v2, weight;
        for (int i = 0; i < numOfEdge; i++)
        {
            st = new StringTokenizer(br.readLine());
            v1 = Integer.parseInt(st.nextToken());
            v2 = Integer.parseInt(st.nextToken());
            weight = Integer.parseInt(st.nextToken());

            edges.get(v1).add(new Edge(v1, v2, weight));
        }
    }
}

class Vertex implements Comparable<Vertex>
{
    int index;
    int cost;

    Vertex(int index, int cost)
    {
        this.index = index;
        this.cost = cost;
    }

    public int compareTo(Vertex o)
    {
        return Integer.compare(this.cost, o.cost);
    }
}

class Edge
{
    int start; //start는 쓰이지는 않지만 두 정점을 잇는다는 edge의 개념을 살리기 위해 넣어둠
    int end;
    int cost;

    Edge(int start, int end, int cost)
    {
        this.start = start;
        this.end = end;
        this.cost = cost;
    }
}