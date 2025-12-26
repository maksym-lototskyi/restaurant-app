package edu.pjatk.tin.restaurant.domain.hall;

import edu.pjatk.tin.restaurant.util.validation.ValidationUtil;

import java.io.Serializable;
import java.util.UUID;

public record HallId (UUID value) implements Serializable {
    public HallId {
        ValidationUtil.requireNonNull(value, "HallId value cannot be null");
    }

    public static HallId generate() {
        return new HallId(UUID.randomUUID());
    }

    public static HallId of(UUID hallId) {
        return new HallId(hallId);
    }
}
