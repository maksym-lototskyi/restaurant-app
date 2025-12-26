package edu.pjatk.tin.restaurant.web.controller;

import edu.pjatk.tin.restaurant.use_cases.hall.*;
import edu.pjatk.tin.restaurant.domain.hall.HallDimensions;
import edu.pjatk.tin.restaurant.domain.hall.HallId;
import edu.pjatk.tin.restaurant.domain.hall.HallName;
import edu.pjatk.tin.restaurant.web.dtos.hall.CreateHallDto;
import edu.pjatk.tin.restaurant.web.dtos.hall.ResizeHallDto;
import edu.pjatk.tin.restaurant.web.dtos.hall.UpdateHallInfoDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/halls")
public class HallController {
    private final CreateHallUseCase createHallUseCase;
    private final ResizeHallUseCase resizeHallUseCase;
    private final UpdateHallInfoUseCase updateHallInfoUseCase;
    private final GetHallDetailsUseCase getHallDetailsUseCase;
    private final GetAllHallsUseCase getAllHallsUseCase;
    private final DeleteHallUseCase deleteHallUseCase;

    public HallController(CreateHallUseCase createHallUseCase, ResizeHallUseCase resizeHallUseCase, UpdateHallInfoUseCase updateHallInfoUseCase, GetHallDetailsUseCase getHallDetailsUseCase, GetAllHallsUseCase getAllHallsUseCase, DeleteHallUseCase deleteHallUseCase) {
        this.createHallUseCase = createHallUseCase;
        this.resizeHallUseCase = resizeHallUseCase;
        this.updateHallInfoUseCase = updateHallInfoUseCase;
        this.getHallDetailsUseCase = getHallDetailsUseCase;
        this.getAllHallsUseCase = getAllHallsUseCase;
        this.deleteHallUseCase = deleteHallUseCase;
    }

    @PostMapping
    public ResponseEntity<HallDetails> createHall(@Valid @RequestBody CreateHallDto dto){
        HallDetails hallDetails = createHallUseCase.execute(
                HallName.of(dto.name()),
                HallDimensions.of(dto.length(), dto.width()),
                dto.floor()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(hallDetails);
    }

    @PutMapping("/{hallId}/resize")
    public ResponseEntity<HallDetails> resizeHall(@PathVariable UUID hallId, @Valid @RequestBody ResizeHallDto dto){
        HallDetails hallDetails = resizeHallUseCase.execute(
                HallId.of(hallId),
                HallDimensions.of(dto.length(), dto.width())
        );
        return ResponseEntity.ok(hallDetails);
    }

    @PutMapping("/{hallId}")
    public ResponseEntity<HallDetails> updateHallInfo(@PathVariable UUID hallId, @Valid @RequestBody UpdateHallInfoDto dto){
        HallDetails hallDetails = updateHallInfoUseCase.execute(
                HallId.of(hallId),
                HallName.of(dto.name()),
                dto.floor()
        );
        return ResponseEntity.ok(hallDetails);
    }

    @GetMapping("/{hallId}")
    public ResponseEntity<HallDetails> getHallDetails(@PathVariable UUID hallId){
        HallDetails hallDetails = getHallDetailsUseCase.execute(HallId.of(hallId));
        return ResponseEntity.ok(hallDetails);
    }

    @GetMapping
    public ResponseEntity<Iterable<HallDetails>> getAllHalls(){
        Iterable<HallDetails> halls = getAllHallsUseCase.execute();
        return ResponseEntity.ok(halls);
    }

    @DeleteMapping("/{hallId}")
    public ResponseEntity<Void> deleteHall(@PathVariable UUID hallId){
        deleteHallUseCase.execute(HallId.of(hallId));
        return ResponseEntity.noContent().build();
    }
}
