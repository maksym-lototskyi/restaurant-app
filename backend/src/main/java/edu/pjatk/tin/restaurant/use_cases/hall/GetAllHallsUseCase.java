package edu.pjatk.tin.restaurant.use_cases.hall;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.hall.Hall;
import edu.pjatk.tin.restaurant.domain.hall.HallRepository;

import java.util.List;

@UseCase
public class GetAllHallsUseCase {

    private final HallRepository hallRepository;

    public GetAllHallsUseCase(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public List<HallDetails> execute(){
        List<Hall> halls = hallRepository.findAll();
        return halls.stream().map(HallMapper::toDetails).toList();
    }
}
