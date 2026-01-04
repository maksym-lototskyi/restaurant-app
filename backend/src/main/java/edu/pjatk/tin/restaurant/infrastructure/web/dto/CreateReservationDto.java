package edu.pjatk.tin.restaurant.infrastructure.web.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import org.jspecify.annotations.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateReservationDto(
        @NonNull UUID customerId,
        @NonNull @FutureOrPresent LocalDateTime reservationStart,
        @Positive int numberOfGuests
) {
}
