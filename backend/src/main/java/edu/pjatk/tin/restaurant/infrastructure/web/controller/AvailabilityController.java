package edu.pjatk.tin.restaurant.infrastructure.web.controller;

import edu.pjatk.tin.restaurant.application.GetAvailableTimeSlotsUseCase;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/availability")
@Validated
public class AvailabilityController {
    private final GetAvailableTimeSlotsUseCase getAvailableTimeSlotsUseCase;

    public AvailabilityController(GetAvailableTimeSlotsUseCase getAvailableTimeSlotsUseCase) {
        this.getAvailableTimeSlotsUseCase = getAvailableTimeSlotsUseCase;
    }

    @GetMapping
    public ResponseEntity<List<LocalTime>> getAvailableTimeSlots(
            @RequestParam
            @NotNull
            @FutureOrPresent
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDate startDate,
            @RequestParam
            @NotNull
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalTime preferredStartTime,
            @RequestParam
            @Positive
            int numberOfGuests, @RequestParam @Positive int pageSize
    ){
        return ResponseEntity.ok(getAvailableTimeSlotsUseCase.execute(startDate, preferredStartTime, numberOfGuests, pageSize));
    }
}
