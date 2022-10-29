package reservation;

import dto.ReplyReservationDto;
import dto.ReservationDto;
import dto.RoomAssignDto;

import java.util.*;

public class ReservationManager {

    private final RoomManager roomManager;

    private List<AcceptedReservationDto> acceptedReservationList = new LinkedList<>();
    private List<RefusedReservationDto> refusedReservationList = new LinkedList<>();

    private static final String ACCEPTED = "accepted";
    private static final String REFUSED = "refused";

    public ReservationManager(int problem) {
        roomManager = new RoomManager(problem);
    }

    /*
    * 1. 외부 단편화가 없을 것이란 가정 하에 방이 비어있으면 일단 받는다.
    *   - 답장이 늦으면 점수가 감소되기 때문.
    *   - 방 번호는 배정하지 않는다. compaction에 의해 방 번호가 예약 당일까지 계속 바뀐다.
    * 2. 방이 모자란 경우에는?
    *   1)
    *
    *
    * */

    public List<ReplyReservationDto> takeReservations(List<ReservationDto> reservationList) {
        List<ReplyReservationDto> replyReservationList = new ArrayList<>();

        for (ReservationDto reservation : reservationList) {
            Integer emptyRoomNumber = roomManager.getEmptyRoomNumber(reservation.getAmount(), reservation.getCheckInDate(), reservation.getCheckOutDate());
            if (emptyRoomNumber == null) {
                replyReservationList.add(new ReplyReservationDto(reservation.getId(), REFUSED));
            } else {
                replyReservationList.add(new ReplyReservationDto(reservation.getId(), ACCEPTED));
                acceptedReservationList.add(new AcceptedReservationDto(reservation, emptyRoomNumber));
            }
        }

        return replyReservationList;
    }

    public List<RoomAssignDto> assignRooms(int day) {
        List<RoomAssignDto> roomAssignList = new ArrayList<>();

        for (AcceptedReservationDto reservation : acceptedReservationList) {
            if (reservation.getReservation().getCheckInDate() == day) {
                roomAssignList.add(new RoomAssignDto(reservation.getReservation().getId(), reservation.getRoomNumber()));
            }
        }

        for (int i = (acceptedReservationList.size() - 1); i >= 0 ; i--) {
            if (acceptedReservationList.get(i).getReservation().getCheckInDate() == day) {
                acceptedReservationList.remove(i);
            }
        }

        return roomAssignList;
    }
}
