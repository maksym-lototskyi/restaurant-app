package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class RescheduleReservationUseCase {
    private final ReservationRepository reservationRepository;

    public RescheduleReservationUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Transactional
    public ReservationSummary execute(ReservationId reservationId, TimeSlot newSlot) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id " + reservationId.value() + " not found"));

        boolean collision = reservationRepository.existsColliding(
                newSlot.startTime(),
                newSlot.endTime(),
                reservation.getId(),
                reservation.getTableId(),
                ReservationStatus.CONFIRMED
        );

        if (collision) throw new ReservationCollisionException();

        reservation.reschedule(newSlot);

        reservationRepository.save(reservation);
        return ReservationMapper.toSummary(reservation);
    }

}
