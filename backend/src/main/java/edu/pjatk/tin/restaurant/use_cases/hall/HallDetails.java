package edu.pjatk.tin.restaurant.use_cases.hall;

import java.util.UUID;

public record HallDetails(
        UUID id,
        String name,
        int length,
        int width,
        int floor
) {
}
