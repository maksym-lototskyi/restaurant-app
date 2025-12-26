package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.Reservation;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationId;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class CancelReservationUseCase {
    private final ReservationRepository reservationRepository;

    public CancelReservationUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public void execute(ReservationId reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id " + reservationId + " not found"));

        reservation.cancel();
        reservationRepository.save(reservation);
    }
}
