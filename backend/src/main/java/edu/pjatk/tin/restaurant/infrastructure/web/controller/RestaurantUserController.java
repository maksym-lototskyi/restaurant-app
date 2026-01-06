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
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetUserPageUseCase getUserPageUseCase;
    private final GetUserProfileDetailsUseCase getUserDetailsUseCase;
    private final PasswordHasher passwordHasher;

    public RestaurantUserController(RegisterUserUseCase registerUserUseCase, DeleteUserUseCase deleteUserUseCase, GetUserPageUseCase getUserPageUseCase, GetUserProfileDetailsUseCase getUserDetailsUseCase, PasswordHasher passwordHasher) {
        this.registerUserUseCase = registerUserUseCase;
        this.deleteUserUseCase = deleteUserUseCase;
        this.getUserPageUseCase = getUserPageUseCase;
        this.getUserDetailsUseCase = getUserDetailsUseCase;
        this.passwordHasher = passwordHasher;
    }

    @PostMapping
    public ResponseEntity<RestaurantUserProfileDetails> createUser(@Valid @RequestBody CreateUserDto dto) {
        RestaurantUserProfileDetails userDetails = registerUserUseCase.execute(
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
    public ResponseEntity<RestaurantUserProfileDetails> getUserDetails(@PathVariable UUID userId){
        RestaurantUserProfileDetails userDetails = getUserDetailsUseCase.execute(RestaurantUserId.of(userId));
        return ResponseEntity.ok(userDetails);
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantUserAdminDetails>> getAllUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                                                              @RequestParam(required = false, defaultValue = "10") int size){
        Page<RestaurantUserAdminDetails> users = getUserPageUseCase.execute(page, size);
        return ResponseEntity.ok(users);
    }
}
