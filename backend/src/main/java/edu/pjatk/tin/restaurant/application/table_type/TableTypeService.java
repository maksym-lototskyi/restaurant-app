package edu.pjatk.tin.restaurant.application.table_type;

import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableRepository;
import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableTypeService {
    private final TableTypeRepository tableTypeRepository;
    private final RestaurantTableRepository restaurantTableRepository;

    public TableTypeService(TableTypeRepository tableTypeRepository, RestaurantTableRepository restaurantTableRepository) {
        this.tableTypeRepository = tableTypeRepository;
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public TableTypeDetailsDto createTableType(CreateTableTypeDto dto){
        if(tableTypeRepository.existsByName(dto.name())){
            throw new EntityExistsException("Table type with name " + dto.name() + " already exists");
        }
        TableType tableType = TableTypeMapper.toEntity(dto);

        TableType savedTableType = tableTypeRepository.save(tableType);
        return TableTypeMapper.toDto(savedTableType);
    }

    public TableTypeDetailsDto updateTableInfo(Long tableTypeId, UpdateTableTypeDto dto){
        TableType tableType = tableTypeRepository.findById(tableTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Table type with id " + tableTypeId + " not found"));

        tableType.rename(dto.name());
        tableType.changeNumberOfSeats(dto.numberOfSeats());

        TableType updatedTableType = tableTypeRepository.save(tableType);
        return TableTypeMapper.toDto(updatedTableType);
    }

    public TableTypeDetailsDto getTableTypeDetails(Long tableTypeId){
        TableType tableType = tableTypeRepository.findById(tableTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Table type with id " + tableTypeId + " not found"));

        return TableTypeMapper.toDto(tableType);
    }

    public List<TableTypeDetailsDto> getAllTableTypes(){
        List<TableType> tableTypes = tableTypeRepository.findAll();
        return tableTypes.stream()
                .map(TableTypeMapper::toDto)
                .toList();
    }

    public void deleteTableType(Long id){
        TableType tableType = tableTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Table type with id " + id + " not found"));

        boolean isTableTypeInUse = restaurantTableRepository.existsByTableType(tableType);
        if(isTableTypeInUse){
            throw new IllegalStateException("Cannot delete table type with id " + id + " because it is in use by existing tables");
        }

        tableTypeRepository.delete(tableType);
    }
}
