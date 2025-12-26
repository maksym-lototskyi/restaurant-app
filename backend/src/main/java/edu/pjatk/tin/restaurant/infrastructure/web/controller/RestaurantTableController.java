package edu.pjatk.tin.restaurant.infrastructure.web.controller;

import edu.pjatk.tin.restaurant.application.restaurant_table.*;
import edu.pjatk.tin.restaurant.domain.hall.HallId;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableId;
import edu.pjatk.tin.restaurant.domain.restaurant_table.TablePosition;
import edu.pjatk.tin.restaurant.domain.table_type.TableTypeId;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.restaurant_table.CreateTableDto;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.restaurant_table.MoveTableDto;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.restaurant_table.UpdateTableInfoDto;
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
    private final MoveTableUseCase moveTableUseCase;
    private final DeleteTableUseCase deleteTableUseCase;
    private final UpdateTableInfoUseCase updateTableInfoUseCase;
    private final GetTableDetailsUseCase getTableDetailsUseCase;
    private final GetTablesInHallUseCase getTablesInHallUseCase;

    public RestaurantTableController(CreateTableUseCase createTableUseCase, MoveTableUseCase moveTableUseCase, DeleteTableUseCase deleteTableUseCase, UpdateTableInfoUseCase updateTableInfoUseCase, GetTableDetailsUseCase getTableDetailsUseCase, GetTablesInHallUseCase getTablesInHallUseCase) {
        this.createTableUseCase = createTableUseCase;
        this.moveTableUseCase = moveTableUseCase;
        this.deleteTableUseCase = deleteTableUseCase;
        this.updateTableInfoUseCase = updateTableInfoUseCase;
        this.getTableDetailsUseCase = getTableDetailsUseCase;
        this.getTablesInHallUseCase = getTablesInHallUseCase;
    }

    @PostMapping
    public ResponseEntity<TableDetails> createTable(@Valid @RequestBody CreateTableDto dto){
        TableDetails tableDetails = createTableUseCase.execute(
                dto.tableNumber(),
                new TablePosition(
                        dto.positionX(),
                        dto.positionY(),
                        TablePosition.Rotation.fromDegree(dto.rotation())
                ),
                TableTypeId.of(dto.tableTypeId()),
                HallId.of(dto.hallId())
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(tableDetails);
    }

    @PutMapping("/{tableId}/move")
    public ResponseEntity<TableDetails> moveTable(@PathVariable("tableId") UUID tableId, @Valid @RequestBody MoveTableDto dto){
        TableDetails result = moveTableUseCase.execute(
                RestaurantTableId.of(tableId),
                new TablePosition(
                        dto.positionX(),
                        dto.positionY(),
                        TablePosition.Rotation.fromDegree(dto.rotationDegree())
                )
        );
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{tableId}")
    public ResponseEntity<TableDetails> updateTableInfo(@PathVariable("tableId") UUID tableId, @Valid @RequestBody UpdateTableInfoDto dto){
        TableDetails result = updateTableInfoUseCase.execute(
                RestaurantTableId.of(tableId),
                dto.tableNumber());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{tableId}")
    public ResponseEntity<TableDetails> getTableDetails(@PathVariable("tableId") UUID tableId){
        TableDetails result = getTableDetailsUseCase.execute(RestaurantTableId.of(tableId));
        return ResponseEntity.ok(result);
    }

    @GetMapping("/hall/{hallId}")
    public ResponseEntity<List<TableDetails>> getTablesInHall(@PathVariable("hallId") UUID hallId){
        List<TableDetails> result = getTablesInHallUseCase.execute(HallId.of(hallId));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{tableId}")
    public ResponseEntity<Void> deleteTable(@PathVariable("tableId") UUID tableId){
        deleteTableUseCase.execute(RestaurantTableId.of(tableId));
        return ResponseEntity.noContent().build();
    }
}
