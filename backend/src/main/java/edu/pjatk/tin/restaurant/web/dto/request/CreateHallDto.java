package edu.pjatk.tin.restaurant.web.dto.request;

public record CreateHallDto(
        String name,
        int floor,
        int length,
        int width
) {
}
