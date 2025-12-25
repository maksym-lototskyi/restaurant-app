package edu.pjatk.tin.restaurant.domain.repository;

import edu.pjatk.tin.restaurant.domain.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
