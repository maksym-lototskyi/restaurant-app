package edu.pjatk.tin.restaurant.use_cases.table_type;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.table_type.TableType;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeDimensions;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeRepository;
import jakarta.persistence.EntityExistsException;

@UseCase
public class CreateTableTypeUseCase {
    private final TableTypeRepository tableTypeRepository;

    public CreateTableTypeUseCase(TableTypeRepository tableTypeRepository) {
        this.tableTypeRepository = tableTypeRepository;
    }

    public TableTypeDetails execute(String name, int numberOfPeople, TableTypeDimensions dimensions) {
        if(tableTypeRepository.existsByName(name)) {
            throw new EntityExistsException("Table type with name " + name + " already exists");
        }
        TableType tableType = TableType.create(name, numberOfPeople, dimensions);
        return TableTypeMapper.toDetails(tableTypeRepository.save(tableType));
    }
}
