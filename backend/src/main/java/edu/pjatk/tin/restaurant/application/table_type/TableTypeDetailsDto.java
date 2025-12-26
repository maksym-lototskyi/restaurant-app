package edu.pjatk.tin.restaurant.application.table_type;

public record TableTypeDetailsDto(
        Long id,
        String name,
        int numberOfSeats,
        int length,
        int width
) {
}
