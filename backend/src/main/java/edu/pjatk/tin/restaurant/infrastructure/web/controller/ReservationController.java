package edu.pjatk.tin.restaurant.infrastructure.web.controller;

import edu.pjatk.tin.restaurant.application.reservation.*;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationId;
import edu.pjatk.tin.restaurant.domain.reservation.TimeSlot;
import edu.pjatk.tin.restaurant.domain.restaurant_user.RestaurantUserId;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.CreateReservationDto;
import edu.pjatk.tin.restaurant.infrastructure.web.dto.UpdateReservationDto;
import edu.pjatk.tin.restaurant.infrastructure.web.security.CustomUserPrincipal;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final CreateReservationUseCase createReservationUseCase;
    private final EditReservationUseCase editReservationUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;
    private final GetReservationDetailsUseCase getReservationDetailsUseCase;
    private final GetUserReservationsUseCase getUserReservationsUseCase;
    private final GetNextReservationUseCase getNextReservationUseCase;
    private final GetAllReservationsUseCase getAllReservationsUseCase;

    public ReservationController(CreateReservationUseCase createReservationUseCase, EditReservationUseCase editReservationUseCase, CancelReservationUseCase cancelReservationUseCase, GetReservationDetailsUseCase getReservationDetailsUseCase, GetUserReservationsUseCase getUserReservationsUseCase, GetNextReservationUseCase getNextReservationUseCase, GetAllReservationsUseCase getAllReservationsUseCase) {
        this.createReservationUseCase = createReservationUseCase;
        this.editReservationUseCase = editReservationUseCase;
        this.cancelReservationUseCase = cancelReservationUseCase;
        this.getReservationDetailsUseCase = getReservationDetailsUseCase;
        this.getUserReservationsUseCase = getUserReservationsUseCase;
        this.getNextReservationUseCase = getNextReservationUseCase;
        this.getAllReservationsUseCase = getAllReservationsUseCase;
    }

    @PostMapping
    public ResponseEntity<ReservationSummary> createReservation(@Valid @RequestBody CreateReservationDto dto, Authentication authentication) {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();

        ReservationSummary reservationSummary = createReservationUseCase.execute(
                RestaurantUserId.of(customUserPrincipal.getId()),
                TimeSlot.of(dto.reservationStart()),
                dto.numberOfGuests()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationSummary);
    }

    @PutMapping("/{reservationId}/reschedule")
    public ResponseEntity<ReservationDetails> rescheduleReservation(
            @PathVariable("reservationId") UUID reservationId,
            @RequestBody UpdateReservationDto dto,
            Authentication authentication
    ) {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        ReservationDetails reservationDetails = editReservationUseCase.execute(
                ReservationId.of(reservationId),
                customUserPrincipal,
                TimeSlot.of(dto.newStartTime()),
                dto.newNumberOfGuests()
        );
        return ResponseEntity.ok(reservationDetails);
    }

    @PutMapping("/{reservationId}/cancel")
    public ResponseEntity<Void> cancelReservation(@PathVariable("reservationId") UUID reservationId, Authentication authentication) {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        cancelReservationUseCase.execute(ReservationId.of(reservationId), customUserPrincipal);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{reservationId}")
    public ResponseEntity<ReservationDetails> getReservationDetails(@PathVariable("reservationId") UUID reservationId, Authentication authentication) {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        ReservationDetails reservationDetails = getReservationDetailsUseCase.execute(ReservationId.of(reservationId), customUserPrincipal);
        return ResponseEntity.ok(reservationDetails);
    }

    @GetMapping("/next")
    public ResponseEntity<ReservationSummary> getNextReservation(Authentication authentication) {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();

        ReservationSummary nextReservation = getNextReservationUseCase.execute(RestaurantUserId.of(customUserPrincipal.getId()));
        return ResponseEntity.ok(nextReservation);
    }

    @GetMapping
    public ResponseEntity<Page<ReservationSummary>> getReservations(Authentication authentication, @RequestParam (defaultValue = "0") int page, @RequestParam (defaultValue = "10") int size) {
        CustomUserPrincipal customUserPrincipal = (CustomUserPrincipal) authentication.getPrincipal();
        if(customUserPrincipal.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            Page<ReservationSummary> reservations = getAllReservationsUseCase.execute(page, size);
            return ResponseEntity.ok(reservations);
        }
        Page<ReservationSummary> reservations = getUserReservationsUseCase.execute(RestaurantUserId.of(customUserPrincipal.getId()), page, size);
        return ResponseEntity.ok(reservations);
    }
}
