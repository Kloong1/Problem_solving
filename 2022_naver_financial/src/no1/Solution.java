package no1;

import java.util.ArrayList;
import java.util.Collections;

class Solution {
    public int[] solution(int[][] recruits, int seniorMinCareer, int seniorMinScore) {
        int[] answer = {};

        ArrayList<Recruit> recruitList = new ArrayList<>(recruits.length);

        for (int[] recruit : recruits) {
            recruitList.add(new Recruit(recruit[0], recruit[1]));
        }

        Collections.sort(recruitList);

        int resultExpertMinCareer = 0, resultExpertMinScore = 0;

        for (int expertMinCareer = 100; expertMinCareer >= 0; expertMinCareer--) {
            for (int expertMinScore = 100; expertMinScore >= 0; expertMinScore--) {
                int expertCnt = 0, seniorCnt = 0, juniorCnt = 0;
                for (Recruit recruit : recruitList) {
                    if (recruit.career >= expertMinCareer && recruit.score >= expertMinScore)
                        expertCnt++;
                    else if (recruit.career >= seniorMinCareer || recruit.score >= seniorMinScore)
                        seniorCnt++;
                    else
                        juniorCnt++;
                }

                if (expertCnt > 0 && expertCnt < seniorCnt && seniorCnt < juniorCnt) {
                    if (expertMinCareer + expertMinScore > resultExpertMinCareer + resultExpertMinScore) {
                        resultExpertMinCareer = expertMinCareer;
                        resultExpertMinScore = expertMinScore;
                    }
                }
            }
        }

        return new int[]{resultExpertMinCareer, resultExpertMinScore};
    }
}

class Recruit implements Comparable<Recruit>{
    int career;
    int score;

    public Recruit(int career, int score) {
        this.career = career;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Element{" +
                "career=" + career +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(Recruit o) {
        if (this.career == o.career)
            return Integer.compare(this.score, o.score);
        else
            return Integer.compare(this.career, o.career);
    }
}