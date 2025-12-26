package edu.pjatk.tin.restaurant.domain.hall;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtils;

import java.io.Serializable;
import java.util.UUID;

public record HallId (UUID id) implements Serializable {
    public HallId {
        ValidationUtils.requireNonNull(id, "HallId id cannot be null");
    }

    public static HallId generate() {
        return new HallId(UUID.randomUUID());
    }
}
