import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

class Solution {
    private static final String[][] CATEGORIES = new String[][]
            {{"cpp", "java", "python"}, {"backend", "frontend"}, {"junior", "senior"}, {"chicken", "pizza"}};

    private static final HashMap<String, ArrayList<Integer>> scoreListHashMap = new HashMap<>();

    public int[] solution(String[] info, String[] queryArr) {
        initScoreListHashMap();

        storeInfoToScoreListHashMap(info);

        int[] answer = new int[queryArr.length];
        for (int i = 0; i < queryArr.length; i++)
            answer[i] = processQuery(queryArr[i]);

        return answer;
    }

    private int processQuery(String query) {
        String[] keysAndScore = query.split(" ");

        int score = Integer.parseInt(keysAndScore[keysAndScore.length - 1]);

        String[] keys = new String[CATEGORIES.length];
        for (int i = 0; i < keys.length; i++)
            keys[i] = keysAndScore[i * 2];

        return dfsToCountScores(keys, score, 0, "");
    }

    private int dfsToCountScores(String[] keys, int score, int keyIdx, String keyString) {
        if (keyIdx == keys.length) {
            ArrayList<Integer> scoreList = scoreListHashMap.get(keyString);
            int idx = lowerBound(scoreList, score);
            return scoreList.size() - idx;
        }

        if (keys[keyIdx].equals("-")) {
            int cnt = 0;
            for (String key : CATEGORIES[keyIdx])
                cnt += dfsToCountScores(keys, score, keyIdx + 1, keyString + key);
            return cnt;
        }

        return dfsToCountScores(keys, score, keyIdx + 1, keyString + keys[keyIdx]);
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

    private void storeInfoToScoreListHashMap(String[] infoArr) {
        for (String info : infoArr) {
            String[] keysAndScore = info.split(" ");

            StringBuilder keyStringBuilder = new StringBuilder();
            for (int i = 0; i < keysAndScore.length - 1; i++)
                keyStringBuilder.append(keysAndScore[i]);

            String key = keyStringBuilder.toString();
            int score = Integer.parseInt(keysAndScore[keysAndScore.length - 1]);

            scoreListHashMap.get(key).add(score);
        }

        for (ArrayList<Integer> scoreList : scoreListHashMap.values())
            Collections.sort(scoreList);
    }

    private void initScoreListHashMap() {
        ArrayList<String> keyArrayList = new ArrayList<>(24);
        generateKeyArrayList(keyArrayList, "", 0);

        for (String key : keyArrayList)
            scoreListHashMap.put(key, new ArrayList<Integer>());
    }

    private void generateKeyArrayList(ArrayList<String> keyArrayList, String keyString, int categoryIdx) {
        if (categoryIdx == CATEGORIES.length) {
            keyArrayList.add(keyString);
            return;
        }

        for (String key : CATEGORIES[categoryIdx])
            generateKeyArrayList(keyArrayList, keyString + key, categoryIdx + 1);
    }
}