package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.hall.Hall;
import edu.pjatk.tin.restaurant.domain.hall.HallId;
import edu.pjatk.tin.restaurant.domain.hall.HallRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_table.TablePlacementValidator;
import edu.pjatk.tin.restaurant.domain.restaurant_table.TablePosition;
import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeId;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class CreateTableUseCase {
    private final RestaurantTableRepository restaurantTableRepository;
    private final HallRepository hallRepository;
    private final TableTypeRepository tableTypeRepository;

    public CreateTableUseCase(RestaurantTableRepository restaurantTableRepository, HallRepository hallRepository, TableTypeRepository tableTypeRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.hallRepository = hallRepository;
        this.tableTypeRepository = tableTypeRepository;
    }

    public TableDetails execute(String tableNumber, TablePosition tablePosition, TableTypeId tableTypeId, HallId hallId){
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new EntityNotFoundException("Hall with id " + hallId + " not found"));
        TableType tableType = tableTypeRepository.findById(tableTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Table type with id " + tableTypeId + " not found"));

        if(restaurantTableRepository.existsByHallIdAndNumber(hallId, tableNumber))
            throw new EntityExistsException("Table with number " + tableNumber + " already exists in hall with id " + hallId);

        TablePlacementValidator.validatePosition(tablePosition, hall.getDimensions(), tableType.getDimensions());
        RestaurantTable table = RestaurantTable.create(tableNumber, tablePosition, tableTypeId, hallId);

        return TableMapper.toDetails(restaurantTableRepository.save(table));
    }
}
