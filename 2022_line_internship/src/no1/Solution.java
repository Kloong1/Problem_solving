package no1;

import java.util.*;

class Solution {
    public String[] solution(String[] logs) {
        String[] answer = {};

        Set<String> userSet = new HashSet<>();
        Map<String, HashSet<String>> questionSolverMap = new HashMap<>();

        for (String log : logs) {
            String[] userAndQuestion = log.split(" ");
            String user = userAndQuestion[0];
            String question = userAndQuestion[1];

            userSet.add(user);

            if (questionSolverMap.containsKey(question)) {
                questionSolverMap.get(question).add(user);
            }
            else {
                questionSolverMap.put(question, new HashSet<>());
                questionSolverMap.get(question).add(user);
            }
        }

        ArrayList<String> wellKnownQuestions = new ArrayList<>();
        int solvedUsers = userSet.size();
        int wellKnown = solvedUsers % 2 == 1 ? (solvedUsers + 1) / 2 : solvedUsers / 2;

        for (String question : questionSolverMap.keySet()) {
            System.out.println(question + ": " + questionSolverMap.get(question).size());
            if (questionSolverMap.get(question).size() >= wellKnown)
                wellKnownQuestions.add(question);
        }

        Collections.sort(wellKnownQuestions);

        return wellKnownQuestions.toArray(new String[0]);
    }
}