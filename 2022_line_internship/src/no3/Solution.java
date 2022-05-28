package no3;

import java.util.Arrays;

class Solution {
    public int solution(int fuel, int[] powers, int[] distances) {

        Rocket[] rockets = new Rocket[powers.length];
        for (int i = 0; i <powers.length; i++) {
            rockets[i] = new Rocket(powers[i], distances[i]);
        }

        fuel -= rockets.length;

        int sumDistPerPower = 0;
        for (Rocket rocket : rockets) {
            sumDistPerPower += rocket.distPerPower;
        }

        for (Rocket rocket : rockets) {
            rocket.fuelRatio = rocket.distPerPower / (double)sumDistPerPower;
        }

        int tempFuel = fuel;
        for (Rocket rocket : rockets) {
            int fuelForRocket = getFuelForRocket(rocket, fuel);
            tempFuel -= fuelForRocket;
            rocket.fuel += fuelForRocket;

            rocket.travelTime = getRocketTravelTime(rocket);
        }

        fuel = tempFuel;

        Arrays.sort(rockets, (rocket1, rocket2) -> {
            if (rocket2.distPerPower > rocket1.distPerPower)
                return 1;
            else if (rocket2.distPerPower < rocket1.distPerPower)
                return -1;
            return 0;
        });

        System.out.println(fuel);
        System.out.println();

        for (Rocket rocket : rockets)
            System.out.println(rocket.fuel);

        return 0;
    }

    int getRocketTravelTime(Rocket rocket) {
        int velocity = rocket.power * rocket.fuel;
        double dist = velocity * rocket.fuel / 2.0;

        if ((int)(Math.floor(dist) + 0.1) >= rocket.distance)
            return rocket.fuel;

        dist = rocket.distance - dist;
        return (int)(Math.ceil(rocket.fuel + (dist / velocity)) + 0.1);
    }

    int getFuelForRocket(Rocket rocket, int fuel) {
        return (int)(Math.floor(fuel * rocket.fuelRatio) + 0.1);
    }
}

class Rocket {
    int power;
    int distance;
    double distPerPower;

    double fuelRatio;

    int fuel;
    int travelTime;

    public Rocket(int power, int distance) {
        this.power = power;
        this.distance = distance;
        distPerPower = (double)distance / power;

        fuel = 1;
    }
}