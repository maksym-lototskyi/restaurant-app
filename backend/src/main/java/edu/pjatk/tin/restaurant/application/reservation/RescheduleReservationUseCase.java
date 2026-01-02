package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.*;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUser;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class RescheduleReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final RestaurantUserRepository userRepository;
    private final RestaurantTableRepository tableRepository;

    public RescheduleReservationUseCase(ReservationRepository reservationRepository, RestaurantUserRepository userRepository, RestaurantTableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
    }

    @Transactional
    public ReservationDetails execute(ReservationId reservationId, TimeSlot newSlot) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id " + reservationId.value() + " not found"));

        RestaurantTable table = tableRepository.findById(reservation.getTableId())
                .orElseThrow(() -> new EntityNotFoundException("Table with id " + reservation.getTableId().value() + " not found"));

        RestaurantUser customer = userRepository.findById(reservation.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + reservation.getCustomerId().value() + " not found"));

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
        return ReservationMapper.toDetails(reservation, table.getTableNumber(), customer);
    }

}
