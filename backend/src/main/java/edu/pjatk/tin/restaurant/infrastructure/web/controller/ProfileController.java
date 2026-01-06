package edu.pjatk.tin.restaurant.infrastructure.web.controller;

import edu.pjatk.tin.restaurant.application.restaurant_user.GetUserProfileDetailsUseCase;
import edu.pjatk.tin.restaurant.application.restaurant_user.RestaurantUserProfileDetails;
import edu.pjatk.tin.restaurant.application.restaurant_user.UpdateProfileUseCase;
import edu.pjatk.tin.restaurant.domain.restaurant_user.Email;
import edu.pjatk.tin.restaurant.domain.restaurant_user.Password;
import edu.pjatk.tin.restaurant.domain.restaurant_user.PasswordHasher;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.UpdateUserDto;
import edu.pjatk.tin.restaurant.infrastructure.web.security.CustomUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final GetUserProfileDetailsUseCase getUserProfileDetailsUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final PasswordHasher passwordHasher;

    public ProfileController(GetUserProfileDetailsUseCase getUserProfileDetailsUseCase, UpdateProfileUseCase updateProfileUseCase, PasswordHasher passwordHasher) {
        this.getUserProfileDetailsUseCase = getUserProfileDetailsUseCase;
        this.updateProfileUseCase = updateProfileUseCase;
        this.passwordHasher = passwordHasher;
    }

    @GetMapping
    public ResponseEntity<RestaurantUserProfileDetails> getUserProfileDetails(Authentication authentication){
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        return ResponseEntity.ok(getUserProfileDetailsUseCase.execute(RestaurantUserId.of(customUserPrincipal.getId())));
    }

    @PutMapping
    public ResponseEntity<RestaurantUserProfileDetails> updateUser(Authentication authentication, @Valid @RequestBody UpdateUserDto dto){
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        UUID userId = customUserPrincipal.getId();
        RestaurantUserProfileDetails userDetails = updateProfileUseCase.execute(
                RestaurantUserId.of(userId),
                dto.firstName(),
                dto.lastName(),
                Email.of(dto.email()),
                dto.password() == null ? Optional.empty() : Optional.of(Password.fromRaw(dto.password(), passwordHasher))
        );

        return ResponseEntity.ok(userDetails);
    }
}
