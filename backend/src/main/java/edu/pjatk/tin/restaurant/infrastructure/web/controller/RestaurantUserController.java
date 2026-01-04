package edu.pjatk.tin.restaurant.infrastructure.web.controller;

import edu.pjatk.tin.restaurant.application.restaurant_user.*;
import edu.pjatk.tin.restaurant.domain.restaurant_user.Email;
import edu.pjatk.tin.restaurant.domain.restaurant_user.Password;
import edu.pjatk.tin.restaurant.domain.restaurant_user.PasswordHasher;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.CreateUserDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class RestaurantUserController {
    private final RegisterUserUseCase registerUserUseCase;
    private final UpdateProfileUseCase updateProfileUseCase;
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetUserPageUseCase getUserPageUseCase;
    private final GetUserProfileDetailsUseCase getUserDetailsUseCase;
    private final PasswordHasher passwordHasher;

    public RestaurantUserController(RegisterUserUseCase registerUserUseCase, UpdateProfileUseCase updateProfileUseCase, DeleteUserUseCase deleteUserUseCase, GetUserPageUseCase getUserPageUseCase, GetUserProfileDetailsUseCase getUserDetailsUseCase, PasswordHasher passwordHasher) {
        this.registerUserUseCase = registerUserUseCase;
        this.updateProfileUseCase = updateProfileUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.getUserPageUseCase = getUserPageUseCase;
        this.getUserDetailsUseCase = getUserDetailsUseCase;
        this.passwordHasher = passwordHasher;
    }

    @PostMapping
    public ResponseEntity<RestaurantUserDetails> createUser(@Valid @RequestBody CreateUserDto dto) {
        RestaurantUserDetails userDetails = registerUserUseCase.execute(
                dto.firstName(),
                dto.lastName(),
                Email.of(dto.email()),
                Password.fromRaw(dto.password(), passwordHasher)
        );
        return ResponseEntity.ok(userDetails);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<RestaurantUserDetails> updateUser(@PathVariable UUID userId, @Valid @RequestBody CreateUserDto dto){
        RestaurantUserDetails userDetails = updateProfileUseCase.execute(
                RestaurantUserId.of(userId),
                dto.firstName(),
                dto.lastName(),
                Email.of(dto.email()),
                Password.fromRaw(dto.password(), passwordHasher)
        );

        return ResponseEntity.ok(userDetails);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId){
        deleteUserUseCase.execute(RestaurantUserId.of(userId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RestaurantUserDetails> getUserDetails(@PathVariable UUID userId){
        RestaurantUserDetails userDetails = getUserDetailsUseCase.execute(RestaurantUserId.of(userId));
        return ResponseEntity.ok(userDetails);
    }

    @GetMapping
    public ResponseEntity<Iterable<RestaurantUserDetails>> getAllUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                                                       @RequestParam(required = false, defaultValue = "10") int size){
        Page<RestaurantUserDetails> users = getUserPageUseCase.execute(page, size);
        return ResponseEntity.ok(users);
    }
}
