package no3;

import java.util.*;

class Solution {
    public int solution(int n, int[][] trains, int[][] bookings) {
        int answer = 0;

        ArrayList<Integer> enableTrains = new ArrayList<>(Collections.nCopies(n, 0));

        for (int i = 0; i < trains.length; i++) {
            int k = trains[i][2];
            for (int j = trains[i][0] - 1; j < trains[i][1] - 1; j++) {
                enableTrains.set(j, enableTrains.get(j) + k);
            }
        }

        ArrayList<ArrayList<Integer>> bookingList = new ArrayList<>();

        for (int[] row : bookings) {
            ArrayList<Integer> arrayList = new ArrayList<>();
            for (int e : row) {
                arrayList.add(e);
            }
            bookingList.add(arrayList);
        }

        for (int i = 0; i < bookings.length; i++){
            int s = bookings[i][0];
            int e = bookings[i][1];
            bookingList.get(i).add(e - s);
        }

        Collections.sort(bookingList, new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                if (o1.get(2) < o2.get(2))
                    return -1;
                else if (o1.get(2) > o2.get(2))
                    return 1;

                if (o1.get(0) < o2.get(0))
                    return -1;
                else if (o1.get(0) > o2.get(0))
                    return 1;
                return 0;
            }
        });

        for (int i = 0; i < bookingList.size(); i++) {
            ArrayList<Integer> list = bookingList.get(i);
            boolean flag = false;


            for (int j = list.get(0) - 1; j < list.get(1) - 1; j++) {
                if (enableTrains.get(j) == 0) {
                    flag = true;
                    break;
                }
            }

            if (flag)
                continue;

            for (int j = list.get(0) - 1; j < list.get(1) - 1; j++) {
                enableTrains.set(j, enableTrains.get(j) - 1);
            }
            answer++;
        }

        return answer;
    }
}