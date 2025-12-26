package edu.pjatk.tin.restaurant.domain.restaurant_user;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record RestaurantUserId(UUID value) implements Serializable {
    public RestaurantUserId {
        ValidationUtils.requireNonNull(value, "RestaurantUserId value cannot be null");
    }

    public static RestaurantUserId generate() {
        return new RestaurantUserId(UUID.randomUUID());
    }
}
