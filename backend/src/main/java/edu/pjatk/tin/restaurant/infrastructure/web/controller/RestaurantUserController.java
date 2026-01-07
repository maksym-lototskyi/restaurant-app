package edu.pjatk.tin.restaurant.infrastructure.web.controller;

import edu.pjatk.tin.restaurant.application.restaurant_user.*;
import edu.pjatk.tin.restaurant.domain.restaurant_user.*;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.CreateUserDto;
import edu.pjatk.tin.restaurant.infrastructure.web.exception.ApiError;
import edu.pjatk.tin.restaurant.infrastructure.web.security.CustomUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class RestaurantUserController {
    private final DeleteUserUseCase deleteUserUseCase;
    private final GetUserPageUseCase getUserPageUseCase;
    private final GetUserAdminDetailsUseCase getUserAdminDetailsUseCase;
    private final ChangeUserPermissionUseCase changeUserPermissionUseCase;

    public RestaurantUserController(DeleteUserUseCase deleteUserUseCase, GetUserPageUseCase getUserPageUseCase, GetUserAdminDetailsUseCase getUserAdminDetailsUseCase, ChangeUserPermissionUseCase changeUserPermissionUseCase) {
        this.deleteUserUseCase = deleteUserUseCase;
        this.getUserPageUseCase = getUserPageUseCase;
        this.getUserAdminDetailsUseCase = getUserAdminDetailsUseCase;
        this.changeUserPermissionUseCase = changeUserPermissionUseCase;
    }


    @PutMapping("/{userId}/permissions")
    public ResponseEntity<RestaurantUserAdminDetails> changeUserPermission(@PathVariable UUID userId,
                                                                           @RequestParam String role,
                                                                           Authentication authentication){
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        try {
            Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        changeUserPermissionUseCase.execute(
                RestaurantUserId.of(userId),
                Role.valueOf(role),
                principal
        );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId){
        deleteUserUseCase.execute(RestaurantUserId.of(userId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<RestaurantUserAdminDetails> getUserDetails(@PathVariable UUID userId){
        RestaurantUserAdminDetails userDetails = getUserAdminDetailsUseCase.execute(RestaurantUserId.of(userId));
        return ResponseEntity.ok(userDetails);
    }

    @GetMapping
    public ResponseEntity<Page<RestaurantUserProfileDetails>> getAllUsers(@RequestParam(required = false, defaultValue = "0") int page,
                                                                              @RequestParam(required = false, defaultValue = "10") int size){
        Page<RestaurantUserProfileDetails> users = getUserPageUseCase.execute(page, size);
        return ResponseEntity.ok(users);
    }
}
