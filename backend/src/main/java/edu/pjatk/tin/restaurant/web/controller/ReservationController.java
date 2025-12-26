package edu.pjatk.tin.restaurant.web.controller;

import edu.pjatk.tin.restaurant.application.reservation.*;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationId;
import edu.pjatk.tin.restaurant.domain.reservation.TimeSlot;
import edu.pjatk.tin.restaurant.domain.restaurant_table.RestaurantTableId;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.web.dtos.request.reservation.CreateReservationDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final CreateReservationUseCase createReservationUseCase;
    private final RescheduleReservationUseCase rescheduleReservationUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final GetReservationDetailsUseCase getReservationDetailsUseCase;
    private final GetUserReservationsUseCase getUserReservationsUseCase;
    private final GetAllReservationsUseCase getAllReservationsUseCase;

    public ReservationController(CreateReservationUseCase createReservationUseCase, RescheduleReservationUseCase rescheduleReservationUseCase, CancelReservationUseCase cancelReservationUseCase, GetReservationDetailsUseCase getReservationDetailsUseCase, GetUserReservationsUseCase getUserReservationsUseCase, GetAllReservationsUseCase getAllReservationsUseCase) {
        this.createReservationUseCase = createReservationUseCase;
        this.rescheduleReservationUseCase = rescheduleReservationUseCase;
        this.cancelReservationUseCase = cancelReservationUseCase;
        this.getReservationDetailsUseCase = getReservationDetailsUseCase;
        this.getUserReservationsUseCase = getUserReservationsUseCase;
        this.getAllReservationsUseCase = getAllReservationsUseCase;
    }

    @PostMapping
    public ResponseEntity<ReservationSummary> createReservation(@Valid @RequestBody CreateReservationDto dto) {
        ReservationSummary reservationSummary = createReservationUseCase.execute(
                RestaurantUserId.of(dto.customerId()),
                RestaurantTableId.of(dto.tableId()),
                TimeSlot.of(dto.reservationStart(), dto.reservationStart().plusHours(2)),
                dto.numberOfGuests()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationSummary);
    }

    @PutMapping("/{reservationId}/reschedule")
    public ResponseEntity<ReservationSummary> rescheduleReservation(
            @PathVariable("reservationId") UUID reservationId,
            @RequestParam("newStartTime") LocalDateTime newStartTime
    ) {
        ReservationSummary reservationSummary = rescheduleReservationUseCase.execute(
                ReservationId.of(reservationId),
                TimeSlot.of(newStartTime, newStartTime.plusHours(2))
        );
        return ResponseEntity.ok(reservationSummary);
    }

    @PutMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable("reservationId") UUID reservationId) {
        cancelReservationUseCase.execute(ReservationId.of(reservationId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDetails> getReservationDetails(@PathVariable("reservationId") UUID reservationId) {
        ReservationDetails reservationDetails = getReservationDetailsUseCase.execute(ReservationId.of(reservationId));
        return ResponseEntity.ok(reservationDetails);
    }

    @GetMapping("/user/{customerId}")
    public ResponseEntity<List<ReservationSummary>> getUserReservations(@PathVariable("customerId") UUID customerId) {
        List<ReservationSummary> reservations = getUserReservationsUseCase.execute(RestaurantUserId.of(customerId));
        return ResponseEntity.ok(reservations);
    }

    @GetMapping
    public ResponseEntity<List<ReservationSummary>> getAllReservations() {
        List<ReservationSummary> reservations = getAllReservationsUseCase.execute();
        return ResponseEntity.ok(reservations);
    }
}
