package edu.pjatk.tin.restaurant.application.restaurant_table;

public record TableDetailsDto(
        Long id,
        String hallName,
        String tableTypeName,
        int positionX,
        int positionY,
        int rotation
) {
}
