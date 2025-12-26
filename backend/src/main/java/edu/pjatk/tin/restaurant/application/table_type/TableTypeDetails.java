package edu.pjatk.tin.restaurant.application.table_type;

import java.util.UUID;

public record TableTypeDetails(
        UUID id,
        String name,
        int numberOfSeats,
        int length,
        int width
) {
}
