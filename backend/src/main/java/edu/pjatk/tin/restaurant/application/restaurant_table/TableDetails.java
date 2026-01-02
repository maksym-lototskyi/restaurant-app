package edu.pjatk.tin.restaurant.application.restaurant_table;

import java.util.UUID;

public record TableDetails(
        UUID id,
        String tableNumber,
        int floorNumber,
        int numberOfSeats
) {
}
