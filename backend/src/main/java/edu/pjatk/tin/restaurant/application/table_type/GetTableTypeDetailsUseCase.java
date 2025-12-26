package edu.pjatk.tin.restaurant.application.table_type;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeId;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeRepository;
import jakarta.persistence.EntityNotFoundException;

@UseCase
public class GetTableTypeDetailsUseCase {
    private final TableTypeRepository tableTypeRepository;

    public GetTableTypeDetailsUseCase(TableTypeRepository tableTypeRepository) {
        this.tableTypeRepository = tableTypeRepository;
    }

    public TableTypeDetails execute(TableTypeId tableTypeId){
        return tableTypeRepository.findById(tableTypeId)
                .map(TableTypeMapper::toDetails)
                .orElseThrow(() -> new EntityNotFoundException("Table type with id " + tableTypeId + " not found"));
    }
}
