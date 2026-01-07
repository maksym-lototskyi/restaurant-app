package edu.pjatk.tin.restaurant.application.restaurant_user;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class GetUserAdminDetailsUseCase {
    private final RestaurantUserRepository userRepository;

    public GetUserAdminDetailsUseCase(RestaurantUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RestaurantUserAdminDetails execute(RestaurantUserId userId) {
        return userRepository.findById(userId)
                .map(UserMapper::toAdminDetails)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }
}