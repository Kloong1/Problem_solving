import java.util.*;

class Solution {
    public int solution(int n, int[][] rawEdges) {
        
        ArrayList<Integer>[] edges = new ArrayList[n + 1];
        initEdges(edges, rawEdges);
        
        Node[] nodes = new Node[n + 1];
        initNodes(nodes);
        
        getNodeDist(nodes, edges);
        
        return countFarthestNodes(nodes);
    }
    
    void getNodeDist(Node[] nodes, ArrayList<Integer>[] graph)
    {
        PriorityQueue<Node> nodeQueue = new PriorityQueue<>((Node n1, Node n2) -> Integer.compare(n1.dist, n2.dist));
        nodeQueue.add(new Node(1, 0));
        nodes[1].dist = 0;
        
        Node node;
        while (!nodeQueue.isEmpty())
        {
            node = nodeQueue.poll();
            
           for (int adjNodeId : graph[node.id])
            {
                if (nodes[adjNodeId].dist > node.dist + 1)
                {
                    nodes[adjNodeId].dist = node.dist + 1;
                    nodeQueue.add(new Node(adjNodeId, node.dist + 1));
                }
            }
        }
    }
    
    int countFarthestNodes(Node[] nodes)
    {
        int maxDist = 0;
        for (int i = 1; i < nodes.length; i++)
            maxDist = Math.max(maxDist, nodes[i].dist);
        
        int cnt = 0;
        for (int i = 1; i < nodes.length; i++)
            if (nodes[i].dist == maxDist)
                cnt++;
        
        return cnt;
            
    }
    
    void initNodes(Node[] nodes)
    {
        for (int i = 1; i < nodes.length; i++)
            nodes[i] = new Node(i, Integer.MAX_VALUE);
    }
    
    void initEdges(ArrayList<Integer>[] edges, int[][] rawEdges)
    {
        for (int i = 1; i < edges.length; i++)
            edges[i] = new ArrayList<Integer>();
        
        for (int[] edge : rawEdges)
        {
            edges[edge[0]].add(edge[1]);
            edges[edge[1]].add(edge[0]);
        }
    }
}

class Node
{
    int id;
    int dist;
    
    Node (int id, int dist)
    {
        this.id = id;
        this.dist = dist;
    }
} 
