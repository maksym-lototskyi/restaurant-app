package edu.pjatk.tin.restaurant.application.service;

import edu.pjatk.tin.restaurant.application.mapper.RestaurantTableMapper;
import edu.pjatk.tin.restaurant.domain.model.Hall;
import edu.pjatk.tin.restaurant.domain.model.RestaurantTable;
import edu.pjatk.tin.restaurant.domain.model.TableType;
import edu.pjatk.tin.restaurant.domain.value.TablePosition;
import edu.pjatk.tin.restaurant.application.exceptions.NotFoundException;
import edu.pjatk.tin.restaurant.domain.repository.HallRepository;
import edu.pjatk.tin.restaurant.domain.repository.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.repository.TableTypeRepository;
import edu.pjatk.tin.restaurant.web.dto.request.CreateTableDto;
import edu.pjatk.tin.restaurant.web.dto.request.MoveTableDto;
import edu.pjatk.tin.restaurant.web.dto.request.UpdateTableInfoDto;
import edu.pjatk.tin.restaurant.web.dto.response.TableDetailsDto;
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
                .orElseThrow(() -> new NotFoundException("Table type with name " + dto.tableTypeName()+ " not found"));
        Hall hall = hallRepository.findByName(dto.hallName())
                .orElseThrow(() -> new NotFoundException("Hall with name " + dto.hallName() + " not found"));

        RestaurantTable restaurantTable = hall.addTable(
                dto.tableNumber(),
                new TablePosition(
                        dto.positionX(),
                        dto.positionY(),
                        dto.rotation()
                ),
                tableType
        );

        RestaurantTable savedTable = restaurantTableRepository.save(restaurantTable);
        return RestaurantTableMapper.toTableDetailsDto(savedTable);
    }

    public TableDetailsDto moveTable(Long tableId, MoveTableDto dto) {
        RestaurantTable restaurantTable = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Table with id " + tableId + " not found"));

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
                .orElseThrow(() -> new NotFoundException("Table with id " + tableId + " not found"));

        restaurantTable.changeNumber(dto.tableNumber());
        RestaurantTable updatedTable = restaurantTableRepository.save(restaurantTable);

        return RestaurantTableMapper.toTableDetailsDto(updatedTable);
    }

    public TableDetailsDto getTableDetails(Long tableId){
        RestaurantTable restaurantTable = restaurantTableRepository.findById(tableId)
                .orElseThrow(() -> new NotFoundException("Table with id " + tableId + " not found"));

        return RestaurantTableMapper.toTableDetailsDto(restaurantTable);
    }

    public List<TableDetailsDto> getAllTablesForHall(Long hallId){
        List<RestaurantTable> tables = tableTypeRepository.findAllByHallId(hallId);

        return tables.stream()
                .map(RestaurantTableMapper::toTableDetailsDto)
                .toList();
    }

    public void deleteTable(Long tableId){
        restaurantTableRepository.deleteById(tableId);
    }
}
