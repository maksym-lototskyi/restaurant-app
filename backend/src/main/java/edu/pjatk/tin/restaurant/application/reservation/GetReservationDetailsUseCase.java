package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationId;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;
import edu.pjatk.tin.restaurant.infrastructure.web.security.CustomUserPrincipal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@UseCase
public class GetReservationDetailsUseCase {
    private final ReservationRepository reservationRepository;
    private final RestaurantUserRepository userRepository;
    private final RestaurantTableRepository tableRepository;

    public GetReservationDetailsUseCase(ReservationRepository reservationRepository, RestaurantUserRepository userRepository, RestaurantTableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
    }

    public ReservationDetails execute(ReservationId reservationId, CustomUserPrincipal principal){
        var reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id " + reservationId + " not found"));

        if(!reservation.getCustomerId().equals(RestaurantUserId.of(principal.getId()))
                && !principal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            throw new EntityNotFoundException("Reservation with id " + reservationId + " not found");

        var user = userRepository.findById(reservation.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException("User with id " + reservation.getCustomerId() + " not found"));

        var table = tableRepository.findById(reservation.getTableId())
                .orElseThrow(() -> new EntityNotFoundException("Table with id " + reservation.getTableId() + " not found"));

        return ReservationMapper.toDetails(reservation, table.getTableNumber(), user);
    }
}
