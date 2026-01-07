package edu.pjatk.tin.restaurant.infrastructure.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record UpdateReservationDto(
        @NotNull
        @Future
        LocalDateTime newStartTime,
        @Positive
        @Max(20)
        int newNumberOfGuests) {
}
