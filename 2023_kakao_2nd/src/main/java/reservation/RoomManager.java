package reservation;

import java.util.ArrayList;
import java.util.List;

public class RoomManager {

    private int dayLimit;
    private int numOfFloor;
    private int numOfRoom;
    private int bitmaskNum;

    private long[][][] rooms;

    /*
    * -- 구현에서의 최적화 --
    * Chunck 형태의 list 사용
    * 초기 상태 - 한 층 = 리스트 하나. 연속된 빈 방은 한 노드.
    * 예약이 들어오면 빈 공간 노드를 2등분해서 예약을 표시.
    * 매번 compaction을 해서 외부 단편화를 막으면 단위가 큰 예약을
    * 쉽게 받을 수 있음
    * 즉 방 번호 배정을 예약 당일까지 미룬다!
    * */
    public RoomManager(int problem) {
        if (problem == 1) {
            dayLimit = 200;
            numOfFloor = 3;
            numOfRoom = 20;
            bitmaskNum = 1;
        } else {
            dayLimit = 1000;
            numOfFloor = 10;
            numOfRoom = 200;
            bitmaskNum = 4;
        }

        rooms = new long[dayLimit + 1][numOfFloor + 1][bitmaskNum];
    }

    public Integer getEmptyRoomNumber(int roomAmount, int checkInDay, int checkOutDay) {
        long roomBitmask1 = getRoomBitmask(roomAmount);
        long roomBitmask2 = ~0L;

        int floor = 1;
        int index = 0;
        int splitIndex = 0;
        boolean splitFlag = false;
        boolean isEmptyRoomFound = false;

        for (floor = 1; floor <= numOfFloor; floor++) {
            index = 0;
            splitIndex = 0;
            splitFlag = false;
            roomBitmask2 = ~0;

            //현재 층의 맨 마지막 방까지 예약할 수 있는 범위의 방이 있는지 체크
            while ((splitIndex * Long.SIZE + index) + roomAmount <= numOfRoom) {
                if (splitFlag) {
                    if ((((roomBitmask1 >>> index) & rooms[checkInDay][floor][splitIndex]) == 0)
                            && ((roomBitmask2 & rooms[checkInDay][floor][splitIndex + 1]) == 0)) {
                        if (checkToCheckOutDay(roomBitmask1, roomBitmask2, floor, index, checkInDay + 1, checkOutDay, splitIndex, true)) {
                            isEmptyRoomFound = true;
                            break;
                        }
                    }
                } else {
                    if (((roomBitmask1 >>> index) & rooms[checkInDay][floor][splitIndex]) == 0) {
                        if (checkToCheckOutDay(roomBitmask1, roomBitmask2, floor, index, checkInDay + 1, checkOutDay, splitIndex, false)) {
                            isEmptyRoomFound = true;
                            break;
                        }
                    }
                }

                index++;

                if (index + roomAmount > Long.SIZE) {
                    roomBitmask2 = (~0L >>> (index + roomAmount) - Long.SIZE);
                    roomBitmask2 = ~roomBitmask2;
                    splitFlag = true;
                }

                if (index == 64) {
                   splitFlag = false;
                   splitIndex++;
                   roomBitmask2 = ~0L;
                   index = 0;
                }
            }

            if (isEmptyRoomFound) {
                break;
            }
        }

        if (!isEmptyRoomFound) {
            return null;
        }

        for (int day = checkInDay; day < checkOutDay; day++) {
            if (splitFlag) {
                rooms[day][floor][splitIndex] = rooms[day][floor][splitIndex] | (roomBitmask1 >>> index);
                rooms[day][floor][splitIndex + 1] = rooms[day][floor][splitIndex + 1] | roomBitmask2;
            } else {
                rooms[day][floor][splitIndex] = rooms[day][floor][splitIndex] | (roomBitmask1 >>> index);
            }
        }

        int resultRoomNumber = splitIndex * Long.SIZE + index + 1;

        return floor * 1000 + resultRoomNumber;
    }

    private boolean checkToCheckOutDay(long roomBitmask1, long roomBitmask2, int floor, int index, int start, int end, int splitIndex, boolean splitFlag) {
        for (int day = start; day <= end; day++) {
            if (splitFlag) {
                if (!((((roomBitmask1 >>> index) & rooms[day][floor][splitIndex]) == 0)
                        && ((roomBitmask2 & rooms[day][floor][splitIndex + 1]) == 0))) {
                    return false;
                }
            } else {
                if (((roomBitmask1 >>> index) & rooms[day][floor][splitIndex]) != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private long getRoomBitmask(int roomAmount) {
        long roomBitmask = ~0L;
        for (int i = 0; i < roomAmount; i++) {
            roomBitmask = (roomBitmask >>> 1);
        }
        roomBitmask = ~roomBitmask;
        return roomBitmask;
    }
}
