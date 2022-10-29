package reservation;

import dto.ReservationDto;
import lombok.Data;

@Data
public class RefusedReservationDto {
    private ReservationDto reservation;
    private int expireDay;

    public RefusedReservationDto(ReservationDto reservation, int expireDay) {
        this.reservation = reservation;
        this.expireDay = expireDay;
    }
}
