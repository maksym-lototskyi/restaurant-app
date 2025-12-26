package edu.pjatk.tin.restaurant.web.controller;

import edu.pjatk.tin.restaurant.domain.table_type.TableTypeDimensions;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeId;
import edu.pjatk.tin.restaurant.use_cases.table_type.*;
import edu.pjatk.tin.restaurant.web.dtos.table_type.CreateTableTypeDto;
import edu.pjatk.tin.restaurant.web.dtos.table_type.UpdateTableTypeDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/table-types")
public class TableTypeController {
    private final CreateTableTypeUseCase createTableTypeUseCase;
    private final UpdateTableTypeUseCase updateTableTypeUseCase;
    private final GetTableTypeDetailsUseCase getTableTypeDetailsUseCase;
    private final GetAllTableTypesUseCase getAllTableTypesUseCase;
    private final DeleteTableTypeUseCase deleteTableTypeUseCase;

    public TableTypeController(CreateTableTypeUseCase createTableTypeUseCase, UpdateTableTypeUseCase updateTableTypeUseCase, GetTableTypeDetailsUseCase getTableTypeDetailsUseCase, GetAllTableTypesUseCase getAllTableTypesUseCase, DeleteTableTypeUseCase deleteTableTypeUseCase) {
        this.createTableTypeUseCase = createTableTypeUseCase;
        this.updateTableTypeUseCase = updateTableTypeUseCase;
        this.getTableTypeDetailsUseCase = getTableTypeDetailsUseCase;
        this.getAllTableTypesUseCase = getAllTableTypesUseCase;
        this.deleteTableTypeUseCase = deleteTableTypeUseCase;
    }

    @PostMapping
    public ResponseEntity<TableTypeDetails> createTableType(@Valid @RequestBody CreateTableTypeDto dto){
        TableTypeDetails result = createTableTypeUseCase.execute(
                dto.name(),
                dto.numberOfSeats(),
                TableTypeDimensions.of(dto.length(), dto.width())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TableTypeDetails> updateTableType(@PathVariable UUID id, @Valid @RequestBody UpdateTableTypeDto dto) {
        TableTypeDetails result = updateTableTypeUseCase.execute(
                TableTypeId.of(id),
                dto.name(),
                dto.numberOfSeats()
        );
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TableTypeDetails> getTableTypeDetails(@PathVariable UUID id) {
        TableTypeDetails result = getTableTypeDetailsUseCase.execute(TableTypeId.of(id));
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<TableTypeDetails>> getAllTableTypes() {
        List<TableTypeDetails> result = getAllTableTypesUseCase.execute();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableType(@PathVariable UUID id) {
        deleteTableTypeUseCase.execute(TableTypeId.of(id));
        return ResponseEntity.noContent().build();
    }
}
