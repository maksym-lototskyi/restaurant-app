package edu.pjatk.tin.restaurant.application.restaurant_user;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_user.Email;
import edu.pjatk.tin.restaurant.domain.restaurant_user.Password;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUser;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserRepository;
import jakarta.persistence.EntityExistsException;

@UseCase
public class CreateUserUseCase {
    private final RestaurantUserRepository userRepository;

    public CreateUserUseCase(RestaurantUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails execute(String firstName, String lastName, Email email, Password password){
        if(userRepository.existsByEmail(email))
            throw new EntityExistsException("User with email " + email.value() + " already exists");

        RestaurantUser user = RestaurantUser.create(firstName, lastName, email, password);
        RestaurantUser savedUser = userRepository.save(user);

        return UserMapper.toDetails(savedUser);
    }
}
