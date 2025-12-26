package edu.pjatk.tin.restaurant.application.hall;

import edu.pjatk.tin.restaurant.domain.hall.Hall;

public class HallMapper {
    public static HallDetails toDetails(Hall hall) {
        return new HallDetails(
                hall.getId().value(),
                hall.getName().value(),
                hall.getDimensions().length(),
                hall.getDimensions().width(),
                hall.getFloorNumber()
        );
    }
}
