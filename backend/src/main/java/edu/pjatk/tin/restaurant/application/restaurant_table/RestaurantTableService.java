package edu.pjatk.tin.restaurant.application.restaurant_table;

import edu.pjatk.tin.restaurant.domain.hall.Hall;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import edu.pjatk.tin.restaurant.domain.restaurant_table.TablePosition;
import edu.pjatk.tin.restaurant.domain.hall.HallRepository;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;
    private final TableTypeRepository tableTypeRepository;
    private final HallRepository hallRepository;

    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository, TableTypeRepository tableTypeRepository, HallRepository hallRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
        this.tableTypeRepository = tableTypeRepository;
        this.hallRepository = hallRepository;
    }

    public TableDetailsDto createTable(CreateTableDto dto){
        TableType tableType = tableTypeRepository.findByName(dto.tableTypeName())
                .orElseThrow(() -> new EntityNotFoundException("Table type with name " + dto.tableTypeName()+ " not found"));
        Hall hall = hallRepository.findByName(dto.hallName())
                .orElseThrow(() -> new EntityNotFoundException("Hall with name " + dto.hallName() + " not found"));

        RestaurantTable restaurantTable = new RestaurantTable(
                dto.tableNumber(),
                new TablePosition(
                        dto.positionX(),
                        dto.positionY(),
                        dto.rotation()
                ),
                tableType,
                hall
        );

        RestaurantTable savedTable = restaurantTableRepository.save(restaurantTable);
        return RestaurantTableMapper.toTableDetailsDto(savedTable);
    }

    public TableDetailsDto moveTable(Long tableId, MoveTableDto dto) {
        RestaurantTable restaurantTable = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new EntityNotFoundException("Table with id " + tableId + " not found"));

        restaurantTable.move(TablePosition.of(
                dto.positionX(),
                dto.positionY(),
                TablePosition.Rotation.fromDegree(dto.rotationDegree()))
        );
        RestaurantTable updatedTable = restaurantTableRepository.save(restaurantTable);

        return RestaurantTableMapper.toTableDetailsDto(updatedTable);
    }

    public TableDetailsDto updateTableInfo(Long tableId, UpdateTableInfoDto dto){
        RestaurantTable restaurantTable = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new EntityNotFoundException("Table with id " + tableId + " not found"));

        restaurantTable.changeNumber(dto.tableNumber());
        RestaurantTable updatedTable = restaurantTableRepository.save(restaurantTable);

        return RestaurantTableMapper.toTableDetailsDto(updatedTable);
    }

    public TableDetailsDto getTableDetails(Long tableId){
        RestaurantTable restaurantTable = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new EntityNotFoundException("Table with id " + tableId + " not found"));

        return RestaurantTableMapper.toTableDetailsDto(restaurantTable);
    }

    public List<TableDetailsDto> getAllTablesForHall(Long hallId){
        List<RestaurantTable> tables = restaurantTableRepository.findAllByHallId(hallId);

        return tables.stream()
                .map(RestaurantTableMapper::toTableDetailsDto)
                .toList();
    }

    public void deleteTable(Long tableId){
        restaurantTableRepository.deleteById(tableId);
    }
}
