package edu.pjatk.tin.restaurant.infrastructure.web.dtos.request.reservation;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateReservationDto(
        UUID tableId,
        UUID customerId,
        LocalDateTime reservationStart,
        int numberOfGuests
) {
}
