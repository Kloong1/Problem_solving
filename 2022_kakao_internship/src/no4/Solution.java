package no4;

import java.util.*;

public class Solution {

    public int[] solution(int n, int[][] paths, int[] gates, int[] summits) {

        Node[] nodes = new Node[n + 1];
        for (int id = 1; id <= n; id++) {
            nodes[id] = new Node(id, NodeType.SHELTER);
        }

        for (int gateNode : gates) {
            nodes[gateNode].nodeType = NodeType.GATE;
        }

        for (int summitNode : summits) {
            nodes[summitNode].nodeType = NodeType.SUMMIT;
        }

        for (int[] path : paths) {
            nodes[path[0]].edges.add(new Edge(path[1], path[2]));
            nodes[path[1]].edges.add(new Edge(path[0], path[2]));
        }

        int[] answer = null;
        boolean[] visit = new boolean[n + 1];
        for (int gateNode : gates) {
            int[] tempAnswer = dfsToHike(gateNode, nodes, visit, 0);
            if (answer == null) {
                answer = tempAnswer;
            }
            else if (answer[1] > tempAnswer[1]) {
                answer[0] = tempAnswer[0];
                answer[1] = tempAnswer[1];
            }
            else if (answer[1] == tempAnswer[1]) {
                answer[0] = Math.min(answer[0], tempAnswer[0]);
            }
        }

        return answer;
    }

    private int[] dfsToHike(int nodeIdx, Node[] nodes, boolean[] visit, int weight) {
        Node node = nodes[nodeIdx];

        if (node.minIntensitySummit != -1) {
            return new int[]{node.minIntensitySummit, node.minIntensity};
        }

        if (node.nodeType == NodeType.SUMMIT) {
            return new int[]{nodeIdx, 0};
        }

        visit[nodeIdx] = true;

        for (Edge edge: node.edges) {
            if (visit[edge.node] || nodes[edge.node].nodeType == NodeType.GATE)
                continue;

            if (node.minIntensitySummit != -1 && edge.weight > node.minIntensity)
                continue;

            int[] returnValues = dfsToHike(edge.node, nodes, visit, edge.weight);

            returnValues[1] = Math.max(returnValues[1], edge.weight);

            if (node.minIntensitySummit == -1) {
                node.minIntensitySummit = returnValues[0];
                node.minIntensity = returnValues[1];
                continue;
            }

            if (node.minIntensity > returnValues[1]) {
                node.minIntensitySummit = returnValues[0];
                node.minIntensity = returnValues[1];
            }
            else if (node.minIntensity == returnValues[1]) {
                node.minIntensitySummit = Math.min(node.minIntensitySummit, returnValues[0]);
            }
        }

        visit[nodeIdx] = false;

        return new int[]{node.minIntensitySummit, node.minIntensity};
    }
}

class Node {
    int id;
    NodeType nodeType;
    ArrayList<Edge> edges = new ArrayList<>();

    int minIntensity = -1;
    int minIntensitySummit = -1;

    public Node(int id, NodeType nodeType) {
        this.id = id;
        this.nodeType = nodeType;
    }
}

class Edge {
    int node;
    int weight;

    public Edge(int node, int weight) {
        this.node = node;
        this.weight = weight;
    }
}

enum NodeType {
    GATE,
    SUMMIT,
    SHELTER
}