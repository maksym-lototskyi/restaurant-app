package edu.pjatk.tin.restaurant.application.restaurant_user;

import java.util.UUID;

public record RestaurantUserProfileDetails(
        UUID id,
        String firstName,
        String lastName,
        String email
) {
}
