package edu.pjatk.tin.restaurant.application.hall;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.hall.Hall;
import edu.pjatk.tin.restaurant.domain.hall.HallId;
import edu.pjatk.tin.restaurant.domain.hall.HallName;
import edu.pjatk.tin.restaurant.domain.hall.HallRepository;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class UpdateHallInfoUseCase {
    private final HallRepository hallRepository;

    public UpdateHallInfoUseCase(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public HallDetails execute(HallId hallId, HallName hallName, int floor) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new EntityNotFoundException("Hall with value " + hallId + " not found"));

        hall.changeName(hallName);
        hall.changeFloorNumber(floor);
        Hall saved = hallRepository.save(hall);

        return HallMapper.toDetails(saved);
    }
}
