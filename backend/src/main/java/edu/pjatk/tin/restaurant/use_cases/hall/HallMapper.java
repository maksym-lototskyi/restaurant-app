package edu.pjatk.tin.restaurant.use_cases.hall;

import edu.pjatk.tin.restaurant.domain.hall.Hall;

class HallMapper {
    static HallDetails toDetails(Hall hall) {
        return new HallDetails(
                hall.getId().value(),
                hall.getName().value(),
                hall.getDimensions().length(),
                hall.getDimensions().width(),
                hall.getFloorNumber()
        );
    }
}
