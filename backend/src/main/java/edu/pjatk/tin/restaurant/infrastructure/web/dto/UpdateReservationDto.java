package edu.pjatk.tin.restaurant.infrastructure.web.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

public record UpdateReservationDto(
        @Future
        LocalDateTime newStartTime,
        @Positive
        int newNumberOfGuests) {
}
