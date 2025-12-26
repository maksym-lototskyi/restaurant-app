package edu.pjatk.tin.restaurant.application.hall;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.hall.Hall;
import edu.pjatk.tin.restaurant.domain.hall.HallId;
import edu.pjatk.tin.restaurant.domain.hall.HallRepository;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class GetHallDetailsUseCase {
    private final HallRepository hallRepository;

    public GetHallDetailsUseCase(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public HallDetails execute(HallId id){
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Hall with value " + id.value() + " not found"));

        return HallMapper.toDetails(hall);
    }
}
