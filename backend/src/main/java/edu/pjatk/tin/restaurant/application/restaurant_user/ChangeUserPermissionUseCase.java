package edu.pjatk.tin.restaurant.application.restaurant_user;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUser;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_user.Role;
import edu.pjatk.tin.restaurant.infrastructure.web.security.CustomUserPrincipal;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class ChangeUserPermissionUseCase {
    private final RestaurantUserRepository userRepository;
    private final ReservationRepository reservationRepository;

    public ChangeUserPermissionUseCase(RestaurantUserRepository userRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.reservationRepository = reservationRepository;
    }

    public void execute(RestaurantUserId id, Role role, CustomUserPrincipal principal){
        if(principal.getId().equals(id.value())){
            throw new EntityExistsException("Users cannot change their own role");
        }
        if(reservationRepository.existsByCustomerId(id)){
            throw new EntityExistsException("Cannot change role of a user with existing reservations");
        }
        RestaurantUser user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        user.changeRole(role);
        userRepository.save(user);
    }
}
