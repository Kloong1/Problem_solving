package no1;

import java.util.*;

public class Solution {

    private final static char[][] CHARACTERISTICS = new char[][]{{'R', 'T'}, {'C', 'F'}, {'J', 'M'}, {'A', 'N'}};

    public String solution(String[] survey, int[] choices) {
        HashMap<Character, Integer> characteristicScore = new HashMap<>();

        for (char[] characteristic : CHARACTERISTICS) {
            characteristicScore.put(characteristic[0], 0);
            characteristicScore.put(characteristic[1], 0);
        }

        for (int idx = 0; idx < survey.length; idx++) {
            scoring(characteristicScore, survey[idx], choices[idx]);
        }

        StringBuilder stringBuilder = new StringBuilder();

        for (char[] characteristic : CHARACTERISTICS) {
            if (characteristicScore.get(characteristic[0]) >= characteristicScore.get(characteristic[1]))
                stringBuilder.append(characteristic[0]);
            else
                stringBuilder.append(characteristic[1]);
        }

        for (char key: characteristicScore.keySet())
            System.out.printf("%c = %d\n", key, characteristicScore.get(key));

        return stringBuilder.toString();
    }

    private static void scoring(HashMap<Character, Integer> characteristicScore, String characteristic, int choice) {
        if (choice == 4)
            return;

        int score;
        switch (choice) {
            case 1, 7 -> score = 3;
            case 2, 6 -> score = 2;
            case 3, 5 -> score = 4;
            default ->  score = 0;
        }

        if (choice <= 3) {
            characteristicScore.put(characteristic.charAt(0), characteristicScore.get(characteristic.charAt(0)) + score);
        }
        else {
            characteristicScore.put(characteristic.charAt(1), characteristicScore.get(characteristic.charAt(1)) + score);
        }
    }
}
