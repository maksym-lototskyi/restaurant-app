package edu.pjatk.tin.restaurant.web.dtos.request.hall;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateHallInfoDto(
        @NotBlank String name,
        @PositiveOrZero int floor
) {
}
