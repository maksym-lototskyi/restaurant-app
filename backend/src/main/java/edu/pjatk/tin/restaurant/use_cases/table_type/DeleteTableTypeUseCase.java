package edu.pjatk.tin.restaurant.use_cases.table_type;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeId;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeRepository;

@UseCase
public class DeleteTableTypeUseCase {
    private final TableTypeRepository tableTypeRepository;

    public DeleteTableTypeUseCase(TableTypeRepository tableTypeRepository) {
        this.tableTypeRepository = tableTypeRepository;
    }

    public void execute(TableTypeId tableTypeId){
        tableTypeRepository.deleteById(tableTypeId);
    }
}
