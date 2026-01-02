package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationStatus;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import jakarta.persistence.EntityNotFoundException;

import java.util.Comparator;

@UseCase
public class GetNextReservationUseCase {
    private final ReservationRepository reservationRepository;

    public GetNextReservationUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public ReservationSummary execute(RestaurantUserId customerId) {
        return reservationRepository.findNextReservationByCustomerId(customerId, ReservationStatus.CONFIRMED)
                .map(ReservationMapper::toSummary)
                .orElseThrow(() -> new EntityNotFoundException("No upcoming reservations found for user with id " + customerId.value()));
    }
}
