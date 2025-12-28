package edu.pjatk.tin.restaurant.domain.restaurant_table;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtil;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record RestaurantTableId(UUID value) implements Serializable {
    public RestaurantTableId {
        ValidationUtil.requireNonNull(value, "RestaurantTableId value cannot be null");
    }

    public static RestaurantTableId generate() {
        return new RestaurantTableId(UUID.randomUUID());
    }

    public static RestaurantTableId of(UUID tableId) {
        return new RestaurantTableId(tableId);
    }
}
