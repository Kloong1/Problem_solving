import java.util.*;

class Solution {
    public String solution(int numOfBus, int busInterval, int busCapacity, String[] stringTimetable) {
        
        int[] timetable = initTimetable(stringTimetable);
        
        Arrays.sort(timetable);
        
        ArrayList<Integer>[] crewsInBus = new ArrayList[numOfBus];
        initCrewsInBus(crewsInBus, busCapacity);
        
        int crewIdx = 0;
        for (int nthBus = 0; nthBus < numOfBus; nthBus++)
        {
            int busArrivalTime = convertStrTime2Int("09:00") + nthBus * busInterval;
            crewIdx = matchCrews2Bus(timetable, crewsInBus[nthBus], busArrivalTime, busCapacity, crewIdx);
        }
        
        ArrayList<Integer> crews = crewsInBus[numOfBus - 1];
            
       if (crews.size() < busCapacity)
            return convertIntTime2Str(convertStrTime2Int("09:00") + (numOfBus - 1) * busInterval);
        
        return convertIntTime2Str(crews.get(crews.size() - 1) - 1);
    }
    
    int matchCrews2Bus(int[] timetable, ArrayList<Integer> crewsInBus, int busArrivalTime, int busCapacity, int crewIdx)
    {
        while (crewIdx < timetable.length)
        {
            if (crewsInBus.size() == busCapacity)
                break;
            
            if (timetable[crewIdx] > busArrivalTime)
                break;
            
            crewsInBus.add(timetable[crewIdx]);
            crewIdx++;
        }
        
        return crewIdx;
    }
    
    void initCrewsInBus(ArrayList<Integer>[] crewsInBus, int busCapacity)
    {
        for (int i = 0; i < crewsInBus.length; i++)
            crewsInBus[i] = new ArrayList<Integer>(busCapacity);
    }
    
    int[] initTimetable(String[] stringTimetable)
    {
        int[] timetable = new int[stringTimetable.length];
        
        for (int i = 0; i < timetable.length; i++)
            timetable[i] = convertStrTime2Int(stringTimetable[i]);
        
        return timetable;
    }
    
    int convertStrTime2Int(String str)
    {
        String[] hourMin = str.split("\\:");
        return Integer.parseInt(hourMin[0]) * 60 + Integer.parseInt(hourMin[1]);
    }
    
    String convertIntTime2Str(int time)
    {
        int intHour = time / 60;
        int intMin = time % 60;
        
        String hour = Integer.toString(intHour);
        if (intHour < 10)
            hour = "0" + hour;
        
        String min = Integer.toString(intMin);
        if (intMin < 10)
            min = "0" + min;
        
        return hour + ":" + min;
    }
    
} 
