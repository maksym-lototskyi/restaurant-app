package edu.pjatk.tin.restaurant.application.restaurant_user;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_user.*;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class UpdateProfileUseCase {
    private final RestaurantUserRepository userRepository;

    public UpdateProfileUseCase(RestaurantUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails execute(RestaurantUserId userId, String firstName, String lastName, Email email, Password password) {
        RestaurantUser user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        if(userRepository.existsByEmailExceptSelf(userId, email))
            throw new EntityExistsException("User with email " + email.value() + " already exists");

        user.changeEmail(email);
        user.changePassword(password);
        user.changeFirstName(firstName);
        user.changeLastName(lastName);

        var updatedUser = userRepository.save(user);
        return UserMapper.toDetails(updatedUser);
    }
}
