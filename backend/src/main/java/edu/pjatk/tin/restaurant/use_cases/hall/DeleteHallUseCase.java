package edu.pjatk.tin.restaurant.use_cases.hall;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.hall.HallId;
import edu.pjatk.tin.restaurant.domain.hall.HallRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;

@UseCase
public class DeleteHallUseCase {
    private final HallRepository hallRepository;
    private final RestaurantTableRepository tableRepository;

    public DeleteHallUseCase(HallRepository hallRepository, RestaurantTableRepository tableRepository) {
        this.hallRepository = hallRepository;
        this.tableRepository = tableRepository;
    }

    public void execute(HallId hallId){
        if(tableRepository.existsByHallId(hallId)){
            throw new IllegalStateException("Cannot delete hall with assigned tables.");
        }
        hallRepository.deleteById(hallId);
    }
}
