package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.Reservation;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationStatus;
import edu.pjatk.tin.restaurant.domain.reservation.TimeSlot;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableId;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.PageRequest;

@UseCase
public class CreateReservationUseCase {
    private final ReservationRepository reservationRepository;
    private final RestaurantUserRepository restaurantUserRepository;
    private final RestaurantTableRepository restaurantTableRepository;

    public CreateReservationUseCase(ReservationRepository reservationRepository, RestaurantUserRepository restaurantUserRepository, RestaurantTableRepository restaurantTableRepository) {
        this.reservationRepository = reservationRepository;
        this.restaurantUserRepository = restaurantUserRepository;
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public ReservationSummary execute(RestaurantUserId customerId, TimeSlot timeSlot, int numberOfGuests) {
        if(!restaurantUserRepository.existsById(customerId))
            throw new EntityNotFoundException("Customer with id " + customerId + " not found");

        RestaurantTable table = restaurantTableRepository.findAvailableTable(timeSlot.startTime(), timeSlot.endTime(), numberOfGuests, PageRequest.of(0, 1))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Table with id " + customerId + " not found"));

        Reservation reservation = Reservation.create(timeSlot, customerId, table.getId(), numberOfGuests);

        if(reservationRepository.existsColliding(timeSlot.startTime(), timeSlot.endTime(), reservation.getId(), table.getId(), ReservationStatus.CONFIRMED))
            throw new ReservationCollisionException();

        return ReservationMapper.toSummary(reservationRepository.save(reservation));
    }
}
