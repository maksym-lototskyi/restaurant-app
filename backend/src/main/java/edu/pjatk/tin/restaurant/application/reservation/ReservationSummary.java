package edu.pjatk.tin.restaurant.application.reservation;

import java.time.LocalDateTime;
import java.util.UUID;

public record ReservationSummary(
        UUID id,
        int numberOfGuests,
        LocalDateTime startTime,
        LocalDateTime endTime
) {
}
