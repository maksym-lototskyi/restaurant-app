package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.Reservation;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationId;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.infrastructure.web.security.CustomUserPrincipal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@UseCase
public class CancelReservationUseCase {
    private final ReservationRepository reservationRepository;

    public CancelReservationUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void execute(ReservationId reservationId, CustomUserPrincipal principal){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id " + reservationId + " not found"));

        if(!reservation.getCustomerId().equals(RestaurantUserId.of(principal.getId()))
                && !principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            throw new EntityNotFoundException("Reservation with id " + reservationId + " not found");

        reservation.cancel();
        reservationRepository.save(reservation);
    }
}
