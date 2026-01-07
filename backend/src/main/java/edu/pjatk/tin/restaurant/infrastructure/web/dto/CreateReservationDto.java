package edu.pjatk.tin.restaurant.infrastructure.web.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Positive;
import org.jspecify.annotations.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateReservationDto(
        @NonNull
        @FutureOrPresent LocalDateTime reservationStart,
        @Positive
        @Max(20)
        int numberOfGuests
) {
}
