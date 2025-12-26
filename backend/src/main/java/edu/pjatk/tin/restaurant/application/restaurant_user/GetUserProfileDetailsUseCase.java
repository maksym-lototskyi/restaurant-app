package edu.pjatk.tin.restaurant.application.restaurant_user;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;

@UseCase
public class GetUserProfileDetailsUseCase {
    private final RestaurantUserRepository userRepository;

    public GetUserProfileDetailsUseCase(RestaurantUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails execute(RestaurantUserId userId) {
        return userRepository.findById(userId)
                .map(UserMapper::toDetails)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }
}
