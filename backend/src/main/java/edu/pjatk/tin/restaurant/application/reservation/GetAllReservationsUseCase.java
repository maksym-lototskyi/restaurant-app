package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.Reservation;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

@UseCase
public class GetAllReservationsUseCase {
    private final ReservationRepository reservationRepository;

    public GetAllReservationsUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Page<ReservationSummary> execute(int pageNumber, int pageSize) {
        Page<Reservation> reservations = reservationRepository.findAllByStatusAndTimeSlot_StartTimeAfterOrderByTimeSlot_StartTimeAsc(ReservationStatus.CONFIRMED, LocalDateTime.now(), PageRequest.of(pageNumber, pageSize));
        return reservations.map(ReservationMapper::toSummary);
    }
}
