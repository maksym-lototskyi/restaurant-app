package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationStatus;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@UseCase
public class GetUserReservationsUseCase {
    private final ReservationRepository reservationRepository;

    public GetUserReservationsUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationSummary> execute(RestaurantUserId customerId){
        var reservations = reservationRepository.findAllByCustomerIdAndStatus(customerId, LocalDateTime.now(), ReservationStatus.CONFIRMED, Sort.by(Sort.Order.by("timeSlot.startTime")));
        return reservations.stream()
                .map(ReservationMapper::toSummary)
                .toList();
    }
}
