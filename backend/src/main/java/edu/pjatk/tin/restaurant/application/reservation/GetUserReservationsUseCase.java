package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationStatus;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;

@UseCase
public class GetUserReservationsUseCase {
    private final ReservationRepository reservationRepository;

    public GetUserReservationsUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Page<ReservationSummary> execute(RestaurantUserId customerId, int pageNumber, int pageSize) {
        var reservations = reservationRepository.findAllByCustomerIdAndStatus(customerId, LocalDateTime.now(), ReservationStatus.CONFIRMED,  PageRequest.of(pageNumber, pageSize).withSort(Sort.by(Sort.Order.by("timeSlot.startTime"))));
        return reservations.map(r -> ReservationMapper.toSummary(r));
    }
}
