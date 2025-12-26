package edu.pjatk.tin.restaurant.application.reservation;

import edu.pjatk.tin.restaurant.UseCase;
import edu.pjatk.tin.restaurant.domain.reservation.ReservationRepository;

import java.util.List;

@UseCase
public class GetAllReservationsUseCase {
    private final ReservationRepository reservationRepository;

    public GetAllReservationsUseCase(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public List<ReservationSummary> execute(){
        var reservations = reservationRepository.findAll();
        return reservations.stream()
                .map(ReservationMapper::toSummary)
                .toList();
    }
}
