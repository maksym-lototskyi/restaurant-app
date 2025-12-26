package edu.pjatk.tin.restaurant.domain.reservation;

import edu.pjatk.tin.restaurant.util.ValidationUtil;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.UUID;

@Embeddable
public record ReservationId(UUID value) implements Serializable {
    public ReservationId {
        ValidationUtil.requireNonNull(value, "ReservationId value cannot be null");
    }

    public static ReservationId generate() {
        return new ReservationId(UUID.randomUUID());
    }

    public static ReservationId of(UUID reservationId) {
        return new ReservationId(reservationId);
    }
}
