package edu.pjatk.tin.restaurant.application.hall;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.application.hall.HallDetails;
import edu.pjatk.tin.restaurant.application.hall.HallMapper;
import edu.pjatk.tin.restaurant.domain.hall.Hall;
import edu.pjatk.tin.restaurant.domain.hall.HallDimensions;
import edu.pjatk.tin.restaurant.domain.hall.HallName;
import edu.pjatk.tin.restaurant.domain.hall.HallRepository;

@UseCase
public class CreateHallUseCase {
    private final HallRepository hallRepository;

    public CreateHallUseCase(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public HallDetails execute(HallName name, HallDimensions dimensions, int floorNumber){
        Hall hall = Hall.create(name, dimensions, floorNumber);
        Hall savedHall = hallRepository.save(hall);

        return HallMapper.toDetails(savedHall);
    }
}
