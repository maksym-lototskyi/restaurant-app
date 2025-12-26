package edu.pjatk.tin.restaurant.application.restaurant_user;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;

@UseCase
public class DeleteUserUseCase {
    private final RestaurantUserRepository userRepository;

    public DeleteUserUseCase(RestaurantUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(RestaurantUserId userId) {
        userRepository.deleteById(userId);
    }
}
