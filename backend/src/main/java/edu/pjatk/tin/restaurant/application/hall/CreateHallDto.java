package edu.pjatk.tin.restaurant.application.hall;

public record CreateHallDto(
        String name,
        int floor,
        int length,
        int width
) {
}
