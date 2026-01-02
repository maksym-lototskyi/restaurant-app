package edu.pjatk.tin.restaurant.infrastructure.web.controller;

import edu.pjatk.tin.restaurant.application.restaurant_table.*;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableId;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.CreateTableDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tables")
public class RestaurantTableController {
    private final CreateTableUseCase createTableUseCase;
    private final DeleteTableUseCase deleteTableUseCase;
    private final UpdateTableInfoUseCase updateTableInfoUseCase;
    private final GetTableDetailsUseCase getTableDetailsUseCase;
    private final GetAllTablesUseCase getAllTablesUseCase;

    public RestaurantTableController(CreateTableUseCase createTableUseCase, DeleteTableUseCase deleteTableUseCase, UpdateTableInfoUseCase updateTableInfoUseCase, GetTableDetailsUseCase getTableDetailsUseCase, GetAllTablesUseCase getAllTablesUseCase) {
        this.createTableUseCase = createTableUseCase;
        this.deleteTableUseCase = deleteTableUseCase;
        this.updateTableInfoUseCase = updateTableInfoUseCase;
        this.getTableDetailsUseCase = getTableDetailsUseCase;
        this.getAllTablesUseCase = getAllTablesUseCase;
    }

    @PostMapping
    public ResponseEntity<TableDetails> createTable(@Valid @RequestBody CreateTableDto dto){
        TableDetails tableDetails = createTableUseCase.execute(
                dto.tableNumber(), dto.floorNumber(), dto.numberOfSeats()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(tableDetails);
    }

    @PutMapping("/{tableId}")
    public ResponseEntity<TableDetails> updateTableInfo(@PathVariable("tableId") UUID tableId, @Valid @RequestBody CreateTableDto dto){
        TableDetails result = updateTableInfoUseCase.execute(
                RestaurantTableId.of(tableId),
                dto.tableNumber(),
                dto.floorNumber(),
                dto.numberOfSeats());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{tableId}")
    public ResponseEntity<TableDetails> getTableDetails(@PathVariable("tableId") UUID tableId){
        TableDetails result = getTableDetailsUseCase.execute(RestaurantTableId.of(tableId));
        return ResponseEntity.ok(result);
    }

    @GetMapping
    public ResponseEntity<List<TableDetails>> getTablesInHall(){
        List<TableDetails> result = getAllTablesUseCase.execute();
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{tableId}")
    public ResponseEntity<Void> deleteTable(@PathVariable("tableId") UUID tableId){
        deleteTableUseCase.execute(RestaurantTableId.of(tableId));
        return ResponseEntity.noContent().build();
    }
}
