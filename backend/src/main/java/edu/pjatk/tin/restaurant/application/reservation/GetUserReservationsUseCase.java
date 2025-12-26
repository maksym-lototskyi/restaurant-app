package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;

import java.util.List;

@UseCase
public class GetUserReservationsUseCase {
    private final ReservationRepository reservationRepository;

    public GetUserReservationsUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationSummary> execute(RestaurantUserId customerId){
        var reservations = reservationRepository.findByCustomerId(customerId);
        return reservations.stream()
                .map(ReservationMapper::toSummary)
                .toList();
    }
}
