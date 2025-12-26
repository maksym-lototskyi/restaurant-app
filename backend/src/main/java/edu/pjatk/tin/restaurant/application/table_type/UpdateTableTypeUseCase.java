package edu.pjatk.tin.restaurant.application.table_type;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeId;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeRepository;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class UpdateTableTypeUseCase {
    private final TableTypeRepository tableTypeRepository;

    public UpdateTableTypeUseCase(TableTypeRepository tableTypeRepository) {
        this.tableTypeRepository = tableTypeRepository;
    }

    public TableTypeDetails execute(TableTypeId tableTypeId, String name, int numberOfSeats){
        TableType tableType = tableTypeRepository.findById(tableTypeId)
                .orElseThrow(() -> new EntityNotFoundException("Table type with id " + tableTypeId + " not found"));

        tableType.rename(name);
        tableType.changeNumberOfSeats(numberOfSeats);

        TableType updatedTableType = tableTypeRepository.save(tableType);
        return TableTypeMapper.toDetails(updatedTableType);
    }
}
