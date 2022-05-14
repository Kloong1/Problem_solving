package no2;

import java.util.*;

class Solution {

    private int adj[][];
    private long dist[][];

    public String[] solution(String[] cityNames, String[] roads, String[] cars, String[] customers) {
        Arrays.sort(cityNames);

        HashMap<String, Integer> cityNameIndexMap = new HashMap<>(cityNames.length * 2);
        for (int i = 0; i < cityNames.length; i++)
            cityNameIndexMap.put(cityNames[i], i);

        City[] cityArr = new City[cityNames.length];
        for (int i = 0; i < cityArr.length; i++)
            cityArr[i] = new City(cityNames[i]);

        adj = new int[cityArr.length][cityArr.length];
        for (String road : roads) {
            String[] strings = road.split(" ");
            int startCityIdx = cityNameIndexMap.get(strings[0]);
            int endCityIdx = cityNameIndexMap.get(strings[1]);
            int km = Integer.parseInt(strings[2]);

            adj[startCityIdx][endCityIdx] = km;
            adj[endCityIdx][startCityIdx] = km;
        }

        for (String car : cars) {
            String[] strings = car.split(" ");
            int cityIdx = cityNameIndexMap.get(strings[0]);
            int maxWeight = Integer.parseInt(strings[1]);
            int costPerKm = Integer.parseInt(strings[2]);

            cityArr[cityIdx].cars.add(new Car(maxWeight, costPerKm));
        }

        for (City city : cityArr)
            Collections.sort(city.cars);

        dist = new long[cityArr.length][cityArr.length];
        getDist(cityArr);

        String[] answer = new String[customers.length];
        for (int i = 0; i < customers.length; i++) {
            String[] strings = customers[i].split(" ");
            int startCityIdx = cityNameIndexMap.get(strings[0]);
            int endCityIdx = cityNameIndexMap.get(strings[1]);
            int weight = Integer.parseInt(strings[2]);

            long minCost = Integer.MAX_VALUE;
            int minIdx = -1;
            for (int j = 0; j < cityArr.length; j++) {
                City city = cityArr[j];
                if (dist[j][startCityIdx] == Integer.MAX_VALUE)
                    continue;

                Car car = city.getCar(weight);
                if (car == null)
                    continue;

                long length = dist[j][startCityIdx] + dist[startCityIdx][endCityIdx];
                long cost = car.costPerKm * length;

                if (cost < minCost) {
                    minCost = cost;
                    minIdx = j;
                }
            }
            answer[i] = cityArr[minIdx].name;
        }

        return answer;
    }

    private void getDist(City[] cityArr) {
        for (int i = 0; i < cityArr.length; i++) {
            for (int j = 0; j < cityArr.length; j++) {
                if (i == j)
                    dist[i][j] = 0;
                else if (adj[i][j] > 0)
                    dist[i][j] = adj[i][j];
                else
                    dist[i][j] = Integer.MAX_VALUE;
            }
        }

        for (int k = 0; k < cityArr.length; k++) {
            for (int i = 0; i < cityArr.length; i++) {
                for (int j = 0; j < cityArr.length; j++) {
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }
    }
}

class City {
    String name;
    ArrayList<Car> cars = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }

    public Car getCar(int weight) {
        Car c = null;
        for (Car car : cars)
            if (car.maxWeight >= weight)
                return car;
        return c;
    }
}

class Car implements Comparable<Car>{
    int maxWeight;
    int costPerKm;

    public Car(int maxWeight, int costPerKm) {
        this.maxWeight = maxWeight;
        this.costPerKm = costPerKm;
    }

    @Override
    public int compareTo(Car o) {
        if (maxWeight == o.maxWeight)
            return Integer.compare(costPerKm, o.costPerKm);
        else
            return Integer.compare(maxWeight, o.maxWeight);
    }
}