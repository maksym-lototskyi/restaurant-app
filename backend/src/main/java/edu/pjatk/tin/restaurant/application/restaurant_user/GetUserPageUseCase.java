package edu.pjatk.tin.restaurant.application.restaurant_user;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@UseCase
public class GetUserPageUseCase {
    private final RestaurantUserRepository userRepository;

    public GetUserPageUseCase(RestaurantUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserDetails> execute(int pageNumber, int pageSize) {
        return userRepository.findAll(Pageable.ofSize(pageSize).withPage(pageNumber))
                .map(UserMapper::toDetails);
    }
}
