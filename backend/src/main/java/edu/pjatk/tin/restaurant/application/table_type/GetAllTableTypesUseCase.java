package edu.pjatk.tin.restaurant.application.table_type;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeRepository;

import java.util.List;

@UseCase
public class GetAllTableTypesUseCase {
    private final TableTypeRepository tableTypeRepository;

    public GetAllTableTypesUseCase(TableTypeRepository tableTypeRepository) {
        this.tableTypeRepository = tableTypeRepository;
    }

    public List<TableTypeDetails> execute() {
        return tableTypeRepository.findAll()
                .stream()
                .map(TableTypeMapper::toDetails)
                .toList();
    }
}
