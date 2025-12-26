package edu.pjatk.tin.restaurant.application.hall;

import edu.pjatk.tin.restaurant.domain.hall.Hall;
import edu.pjatk.tin.restaurant.domain.hall.HallDimensions;

class HallMapper {
    static HallDetailsDto toHallDetailsDto(Hall hall) {
        return new HallDetailsDto(
                hall.getId(),
                hall.getName(),
                hall.getDimensions().length(),
                hall.getDimensions().width()
        );
    }

    static Hall toHall(CreateHallDto createHallDto) {
        return new Hall(
                createHallDto.name(),
                HallDimensions.of(
                        createHallDto.length(),
                        createHallDto.width()
                ),
                createHallDto.floor()
        );
    }
}
