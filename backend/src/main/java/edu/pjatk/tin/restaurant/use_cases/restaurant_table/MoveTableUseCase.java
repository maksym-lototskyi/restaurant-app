package edu.pjatk.tin.restaurant.use_cases.restaurant_table;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.hall.Hall;
import edu.pjatk.tin.restaurant.domain.hall.HallRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_table.*;
import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeRepository;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class MoveTableUseCase {
    private final RestaurantTableRepository restaurantTableRepository;
    private final HallRepository hallRepository;
    private final TableTypeRepository tableTypeRepository;

    public MoveTableUseCase(RestaurantTableRepository restaurantTableRepository, HallRepository hallRepository, TableTypeRepository tableTypeRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.hallRepository = hallRepository;
        this.tableTypeRepository = tableTypeRepository;
    }

    public TableDetails execute(RestaurantTableId tableId, TablePosition position){
        RestaurantTable restaurantTable = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new EntityNotFoundException("Table with id " + tableId + " not found"));

        Hall hall = hallRepository.findById(restaurantTable.getHallId())
                .orElseThrow(() -> new EntityNotFoundException("Hall with id " + restaurantTable.getHallId() + " not found"));

        TableType tableType = tableTypeRepository.findById(restaurantTable.getTableTypeId())
                .orElseThrow(() -> new EntityNotFoundException("Table type with id " + restaurantTable.getTableTypeId() + " not found"));


        TablePlacementValidator.validatePosition(position, hall.getDimensions(), tableType.getDimensions());

        restaurantTable.move(position);

        RestaurantTable updatedTable = restaurantTableRepository.save(restaurantTable);
        return TableMapper.toDetails(updatedTable);
    }

}
