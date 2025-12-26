package edu.pjatk.tin.restaurant.web.dtos.restaurant_table;

import jakarta.validation.constraints.NotNull;

public record UpdateTableInfoDto(
        @NotNull
        String tableNumber
) {
}
