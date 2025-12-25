package edu.pjatk.tin.restaurant.application.mapper;

import edu.pjatk.tin.restaurant.domain.model.Hall;
import edu.pjatk.tin.restaurant.domain.value.HallDimensions;
import edu.pjatk.tin.restaurant.web.dto.request.CreateHallDto;
import edu.pjatk.tin.restaurant.web.dto.response.HallDetailsDto;

public class HallMapper {
    public static HallDetailsDto toHallDetailsDto(Hall hall) {
        return new HallDetailsDto(
                hall.getId(),
                hall.getName(),
                hall.getDimensions().length(),
                hall.getDimensions().width()
        );
    }

    public static Hall toHall(CreateHallDto createHallDto) {
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
