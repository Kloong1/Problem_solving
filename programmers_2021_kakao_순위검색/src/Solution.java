import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Solution {
    public int[] solution(String[] info, String[] query) {
        QueryTree queryTree = new QueryTree();
        queryTree.storeInfo(info);

        int[] answer = new int[query.length];
        for (int i = 0; i < query.length; i++)
            answer[i] = queryTree.query(query[i]);

        return answer;
    }
}

class QueryTree {
    private static final String[][] CATEGORIES = new String[][]
            {{"cpp", "java", "python"}, {"backend", "frontend"}, {"junior", "senior"}, {"chicken", "pizza"}};

    private final Node root;

    public QueryTree() {
        this.root = new Node(null);
        initQueryTree(root, 0);
    }

    private void initQueryTree(Node node, int categoryIdx) {
        if (categoryIdx == CATEGORIES.length) {
            node.scoreList = new ArrayList<>(10_000);
            node.isLeaf = true;
            return;
        }

        for (String key : CATEGORIES[categoryIdx]) {
            Node childNode = new Node(key);
            node.childMap.put(key, childNode);
            initQueryTree(childNode, categoryIdx + 1);
        }
    }

    public void storeInfo(String[] infoStrings) {
        for (String infoString : infoStrings)
            storeInfo(infoString);

        sortAllScoreList(root);
    }

    private void storeInfo(String infoString) {
        String[] keys = infoString.split(" ");

        Node node = root;
        int idx = 0;
        while (!node.isLeaf) {
            node = node.childMap.get(keys[idx]);
            idx++;
        }

        node.scoreList.add(Integer.parseInt(keys[idx]));
    }

    private void sortAllScoreList(Node node) {
        if (!node.isLeaf) {
            for (Node childNode : node.childMap.values())
                sortAllScoreList(childNode);
        } else {
            Collections.sort(node.scoreList);
        }
    }

    public int query(String queryString) {
        String[] words = queryString.split(" ");

        String[] keys = new String[4];

        for (int i = 0; i <= 3; i++)
            keys[i] = words[i * 2];

        int score = Integer.parseInt(words[7]);

        return getAnswer(root, score, keys, 0);
    }

    private int getAnswer(Node node, int score, String[] keys, int keyIdx) {
        if (node.isLeaf) {
            int idx = lowerBound(node.scoreList, score);
            return node.scoreList.size() - idx;
        }

        if (keys[keyIdx].equals("-")) {
            int cnt = 0;
            for (Node childNode : node.childMap.values())
                cnt += getAnswer(childNode, score, keys, keyIdx + 1);
            return cnt;
        }

        Node childNode = node.childMap.get(keys[keyIdx]);
        return getAnswer(childNode, score, keys, keyIdx + 1);
    }

    private int lowerBound(ArrayList<Integer> list, int target) {
        int left, right, mid;

        left = 0;
        right = list.size();

        while (left < right) {
            mid = (left + right) / 2;
            if (target > list.get(mid))
                left = mid + 1;
            else
                right = mid;
        }

        return right;
    }
}

class Node {
    String key;
    HashMap<String, Node> childMap = new HashMap<>(10);
    ArrayList<Integer> scoreList = null;
    boolean isLeaf = false;

    public Node(String key) {
        this.key = key;
    }
}