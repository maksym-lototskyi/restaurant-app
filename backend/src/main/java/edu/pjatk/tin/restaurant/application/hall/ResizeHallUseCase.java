package edu.pjatk.tin.restaurant.application.hall;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.hall.Hall;
import edu.pjatk.tin.restaurant.domain.hall.HallDimensions;
import edu.pjatk.tin.restaurant.domain.hall.HallId;
import edu.pjatk.tin.restaurant.domain.hall.HallRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_table.TablePlacementValidator;
import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeRepository;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@UseCase
public class ResizeHallUseCase {
    private final HallRepository hallRepository;
    private final RestaurantTableRepository restaurantTableRepository;
    private final TableTypeRepository tableTypeRepository;

    public ResizeHallUseCase(HallRepository hallRepository, RestaurantTableRepository restaurantTableRepository, TableTypeRepository tableTypeRepository) {
        this.hallRepository = hallRepository;
        this.restaurantTableRepository = restaurantTableRepository;
        this.tableTypeRepository = tableTypeRepository;
    }

    public HallDetails execute(HallId hallId, HallDimensions newDimensions){
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new EntityNotFoundException("Hall with value " + hallId + " not found"));

        List<RestaurantTable> hallTables = restaurantTableRepository.findAllByHallId(hallId);

        validateTablesWithinNewDimensions(hallTables, newDimensions);

        hall.resize(newDimensions);
        Hall updatedHall = hallRepository.save(hall);

        return HallMapper.toDetails(updatedHall);
    }

    private void validateTablesWithinNewDimensions(List<RestaurantTable> tables, HallDimensions newDimensions){
        for(RestaurantTable table : tables){
            TableType tableType = tableTypeRepository.findById(table.getTableTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("Table type with value " + table.getTableTypeId() + " not found"));
            TablePlacementValidator.validatePosition(table.getPosition(), tableType.getDimensions(), newDimensions);
        }
    }
}
