package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.*;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUser;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;
import edu.pjatk.tin.restaurant.infrastructure.web.security.CustomUserPrincipal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.transaction.annotation.Transactional;

@UseCase
public class EditReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final RestaurantUserRepository userRepository;
    private final RestaurantTableRepository tableRepository;

    public EditReservationUseCase(ReservationRepository reservationRepository, RestaurantUserRepository userRepository, RestaurantTableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
    }

    public ReservationDetails execute(ReservationId reservationId, CustomUserPrincipal principal, TimeSlot newSlot, int numberOfGuests) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id " + reservationId.value() + " not found"));

        if(!reservation.getCustomerId().equals(RestaurantUserId.of(principal.getId()))
                && !principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            throw new EntityNotFoundException("Reservation with id " + reservationId + " not found");

        RestaurantTable table = tableRepository.findAvailableTable(newSlot.startTime(), newSlot.endTime(), numberOfGuests, ReservationStatus.CONFIRMED, PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Available table not found for the new time slot"));

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

        Reservation updatedReservation = new Reservation(
                reservation.getId(),
                newSlot,
                reservation.getCustomerId(),
                table.getId(),
                numberOfGuests
        );

        reservationRepository.save(updatedReservation);
        return ReservationMapper.toDetails(updatedReservation, table.getTableNumber(), customer);
    }

}
